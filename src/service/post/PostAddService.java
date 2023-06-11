package service.post;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.ConnectionProvider;
import dao.MemberDAO;
import dao.MemberImpl;
import dao.PostDAO;
import dao.PostImpl;
import dao.TagDAO;
import dao.TagImpl;
import dto.MemberBean;
import dto.OpenRange;
import dto.PostBean;
import dto.TagBean;
import dto.json.Tag;
import hash.IHash;
import hash.SHA1;
import service.Service;

public class PostAddService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PostDAO pDao = null;
		MemberDAO mDao = null;
		TagDAO tDao = null;
		try {
			pDao = new PostImpl(ConnectionProvider.getConnection());
			mDao = new MemberImpl(ConnectionProvider.getConnection());
			tDao = new TagImpl(ConnectionProvider.getConnection());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		Date now = new Date();
		HttpSession session = request.getSession();
		
		int userId = 0;
		if (session.getAttribute("user") != null) {
			MemberBean user = (MemberBean)session.getAttribute("user");
			userId = user.getId();
		}

		int groupId = 0;
		try {
			groupId = Integer.parseInt(request.getParameter("group"));
		} catch (NumberFormatException e) {
			groupId = 0;
		}
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String explain = request.getParameter("explain");
		String link = request.getParameter("link");
		Timestamp postDate = new Timestamp(now.getTime());
		
		String expire = request.getParameter("expire");
		boolean burn = false;
		Timestamp expiredDate = null;
		if (expire.equals("forever")) {
			burn = false;
		} else if (expire.equals("burn")) {
			burn = true;
		} else {
			long time = 0;
			try {
				time = Long.parseLong(request.getParameter("expire"));
			} catch (NumberFormatException e) {
				time = 0;
				e.printStackTrace();
			}
			expiredDate = new Timestamp((now.getTime() + time * 1000));
		}
		
		String password = request.getParameter("password");
		if (password != null) {
			IHash hash = new SHA1();
			password = hash.crypto(password);
		}
		OpenRange openRange = OpenRange.parseOpenRange(request.getParameter("openRange"));
		System.out.println(request.getParameter("openRange"));
		int recommend = 0;
		String type = request.getParameter("type");
		int hit = 0;

		//글 작성
		PostBean post = new PostBean(0, userId, groupId, title, contents, explain, link, postDate, expiredDate, password, openRange, recommend, type, burn, hit);
		int pid = pDao.insert(post);
		
		//태그 처리
		
		//1단계: json 처리
		String json = request.getParameter("tags");
		System.out.println("tags\n" + json);
		Gson gson = new Gson();
		Tag[] tags = gson.fromJson(json, Tag[].class);
		
		//2단계: 태그 처리
		if (tags != null) {
			for (Tag t : tags) {
				String value = t.getValue();
				if (!tDao.isExist(value)) {
					tDao.insert(new TagBean(0, value), pid);
				} else {
					TagBean tag = tDao.select(value);
					tDao.insertToTagmap(tag, pid);
				}
			}
		}
		
		request.setAttribute("postId", pid);
	}
}
