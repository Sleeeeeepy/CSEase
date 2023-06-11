package service.group;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import dao.ConnectionProvider;
import dao.GroupDAO;
import dao.GroupImpl;
import dto.GroupBean;
import dto.MemberBean;
import service.Service;
import service.ServiceException;

public class GroupEditService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		Connection conn = null;
		GroupDAO gDao = null;
		try {
			conn = ConnectionProvider.getConnection();
			gDao = new GroupImpl(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid id.");
		}
		
		GroupBean group = gDao.select(id);
		if (request.getSession().getAttribute("user") != null) {
			MemberBean member = (MemberBean)request.getSession().getAttribute("user");
			if (member.getId() != group.getAdminId()) {
				throw new ServiceException("Invalid Access.");
			}
		} else {
			throw new ServiceException("Invalid Access.");
		}
		
		Blob photo = null;
		try {
			Part part = request.getPart("photo");
			InputStream ins = part.getInputStream();
			byte barr[] = ins.readAllBytes();
			photo = new SerialBlob(barr);
		} catch (SQLException e) {
			throw new ServiceException("Failed to insert image into database.");
		} catch (IOException e) {
			throw new ServiceException("I/O operation failed.");
		} catch (ServletException e) {
			throw new ServiceException("Failed to receive parameters.");
		}
		
		try {
			if (photo != null && photo.length() != 0) {
				group.setPhoto(photo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String explain = request.getParameter("explain");
		group.setExplain(explain);
		
		gDao.update(group);
	}

}
