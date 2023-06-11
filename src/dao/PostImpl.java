package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.OpenRange;
import dto.Pagination;
import dto.PostBean;
import dto.PostReport;
import service.ListMode;
import service.SortMode;

public class PostImpl implements PostDAO {
	private Connection conn = null;
	//private String groupTable = DBConstants.GROUP_TABLE;
	private String table = DBConstants.POST_TABLE; 
	private String tagMappingTable = DBConstants.TAG_MAP_TABLE;
	private String tagTable = DBConstants.TAG_TABLE;
	
	public PostImpl(Connection conn) {
		this.conn = conn;
	}
	
	public String getTableName() {
		return table;
	}
	
	@Override
	public int insert(PostBean post) {
		String sql = "INSERT INTO " + table + " (`id`, `user_id`, `group_id`, `title`, `contents`, `explain`, `link`, `postDate`, `expiredDate`, `password`, `openRange`, `recommend`, `type`, `burn`, `hit`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, post.getId());
			pstmt.setInt(2, post.getUserId());
			pstmt.setInt(3, post.getGroupId());
			pstmt.setString(4, post.getTitle());
			pstmt.setString(5, post.getContents());
			pstmt.setString(6, post.getExplain());
			pstmt.setString(7, post.getLink());
			pstmt.setTimestamp(8, post.getPostDate());
			pstmt.setTimestamp(9, post.getExpiredDate());
			pstmt.setString(10, post.getPassword());
			pstmt.setString(11, post.getOpenRange().name());
			pstmt.setInt(12, post.getRecommend());
			pstmt.setString(13, post.getType());
			pstmt.setBoolean(14, post.isBurn());
			pstmt.setInt(15, post.getHit());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		String sql = "DELETE FROM " + table + " WHERE id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(PostBean post) {
		String sql = "UPDATE " + table + " SET user_id=?, group_id=?, title=?, contents=?, explain=?, link=?, postDate=?, expiredDate=?, password=?, openRange=?, recommend=?, type=?, burn=?, hit=? WHERE id=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, post.getUserId());
			pstmt.setInt(2, post.getGroupId());
			pstmt.setString(3, post.getTitle());
			pstmt.setString(4, post.getContents());
			pstmt.setString(5, post.getExplain());
			pstmt.setString(6, post.getLink());
			pstmt.setTimestamp(7, post.getPostDate());
			pstmt.setTimestamp(8, post.getExpiredDate());
			pstmt.setString(9, post.getPassword());
			pstmt.setString(10, post.getOpenRange().name());
			pstmt.setInt(11, post.getRecommend());
			pstmt.setString(12, post.getType());
			pstmt.setInt(13, post.getId());
			pstmt.setBoolean(14, post.isBurn());
			pstmt.setInt(15, post.getHit());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	@Override
	public int read(int id) {
		//데이터베이스의 원자성 때문에 해당 명령은 Race Condition이 일어나지 않음.
		String sql = "UPDATE " + table + " SET hit=hit+1 WHERE id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	@Override
	public PostBean select(int id) {
		String sql = "SELECT * FROM " + table + " WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				read(id);
				return createFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public List<PostBean> selectList() {
		String sql = "SELECT * FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<PostBean> list = new ArrayList<>();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public List<PostBean> selectList(boolean isPublic) {
		String sql = "SELECT * FROM " + table + " WHERE openRange='public'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<PostBean> list = new ArrayList<>();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public List<PostBean> selectListByUser(int userId) {
		String sql = "SELECT * FROM " + table + " WHERE user_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<PostBean> selectListByTag(int tagId) {
		//p post
		//tm tagmap
		//t tag
		//(tm.tage_id = t.tag_id) and (t.tag_id=tagId) and (p.id=tm.post_id)
		String sql = "SELECT p.* FROM " + table + " p, "+ tagMappingTable + " tm, "+ tagTable + " t WHERE "
				+ "tm.tag_id=t.tag_id AND t.tag_id=? AND p.id=tm.post_id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostBean> list = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tagId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<PostBean> selectListByTags(List<Integer> tags) {
		return this.selectListByTagsAnd(tags);
	}
	
	//@see http://howto.philippkeller.com/2005/04/24/Tags-Database-schemas/
	public List<PostBean> selectListByTagsAnd(List<Integer> tags) {
		//p post
		//tm tagmap
		//t tag
		//(tm.tage_id = tag_id) and (t.tag_id=tagId) and (p.id=tm.post_id)
		String temp = tags.size() != 0 ? "'" + tags.get(0) + "'" : "";
		int size = tags.size();
		if (tags.size() != 0) tags.remove(0); 
		for (int i : tags) {
			temp += "'" + i + "'";
		}
		
		String sql = "SELECT p.* FROM " + table + " p, "+ tagMappingTable + " tm, "+ tagTable + " t WHERE "
				+ "tm.tag_id=t.tag_id AND (t.tag_id IN (" + temp + ")) AND p.id=tm.post_id GROUP BY p.id HAVING COUNT(p.id)="+size;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public List<PostBean> selectListByTagsOr(List<Integer> tags) {
		String temp = tags.size() != 0 ? "'" + tags.get(0) + "'" : "";
		if (tags.size() != 0) tags.remove(0); 
		for (int i : tags) {
			temp += "'" + i + "'";
		}
		
		String sql = "SELECT p.* FROM " + table + " p, "+ tagMappingTable + " tm, "+ tagTable + " t WHERE "
				+ "tm.tag_id=t.tag_id AND (t.tag_id IN (" + temp + ")) AND p.id=tm.post_id GROUP BY p.id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(createFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public PostBean createFromResultSet(ResultSet rs) {		
		try {
			int id = rs.getInt("id");
			int userId = rs.getInt("user_id");
			int groupId = rs.getInt("group_id");
			String title = rs.getString("title");
			String contents = rs.getString("contents");
			String explain = rs.getString("explain");
			String link = rs.getString("link");
			Timestamp postDate = rs.getTimestamp("postDate");
			Timestamp expiredDate = rs.getTimestamp("expiredDate");
			String password = rs.getString("password");
			OpenRange openRange = OpenRange.parseOpenRange(rs.getString("openRange"));
			int recommend = rs.getInt("recommend");
			String type = rs.getString("type");
			boolean burn = rs.getBoolean("burn");
			int hit = rs.getInt("hit");
			return new PostBean(id, userId, groupId, title, contents, explain, link, postDate, expiredDate, password, openRange, recommend, type, burn, hit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getAllCount(int userId) {
		String countSql = String.format("SELECT COUNT(`id`) FROM %s WHERE user_id=?", table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			 pstmt = conn.prepareStatement(countSql);
			 pstmt.setInt(1, userId);
			 rs = pstmt.executeQuery();
			 while (rs.next()) {
				 totalCount += rs.getInt(1);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return totalCount;
	}
	
	public int getAllCount() {
		String countSql = String.format("SELECT COUNT(`id`) FROM %s", table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			 pstmt = conn.prepareStatement(countSql);
			 rs = pstmt.executeQuery();
			 while (rs.next()) {
				 totalCount += rs.getInt(1);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return totalCount;
	}
	
	@Override
	public int searchCount(ListMode mode, String regex) {
		String sql = "";
		if (mode.equals(ListMode.GROUP)) {
			sql = String.format("SELECT COUNT(j.id) FROM (SELECT `id` FROM `group` WHERE name REGEXP '%s') as i JOIN `post` as j ON i.id=j.group_id", regex);
		} else if (mode.equals(ListMode.TITLE)) {
			sql = String.format("SELECT COUNT(j.id) FROM (SELECT `id` FROM %s WHERE title REGEXP '%s') as i JOIN %s as j ON i.id=j.id;", table, regex, table);
		} else if (mode.equals(ListMode.USER)) {
			sql = String.format("SELECT COUNT(j.id) FROM (SELECT `id` FROM %s WHERE nickname REGEXP '%s') as i JOIN post as j ON i.id=j.user_id ", DBConstants.USER_TABLE, regex);
		} else if (mode.equals(ListMode.TAG)) {
			sql = String.format("SELECT COUNT(k.id) FROM ((SELECT `id` FROM `tag` WHERE tag_name REGEXP '%s') as i JOIN `tagmap` as j ON i.id = j.tag_id) JOIN `post` as k ON k.id = j.post_id  GROUP BY post_id", regex);
		} else if (mode.equals(ListMode.NONE)) {
			return this.getAllCount();
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while (rs.next()) {
				 totalCount += rs.getInt(1);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return totalCount;
	}
	
	@Override
	public List<PostBean> searchList(Pagination page, SortMode sort, ListMode mode, String regex) {
		List<PostBean> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = "";
		String col = "";
		if (sort.equals(SortMode.HIT)) {
			col = "ORDER BY hit DESC";
		} else if (sort.equals(SortMode.LATEST)) {
			col = "ORDER BY postDate DESC";
		} else if (sort.equals(SortMode.MATCH)) {
			col = "";
		} else if (sort.equals(SortMode.RECOMMEND)) {
			col = "ORDER BY recommend DESC";
		} else {
			col = "";
		}
		
		if (mode.equals(ListMode.GROUP)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM `group` WHERE name REGEXP '%s') as i JOIN `post` as j ON i.id=j.group_id %s LIMIT %d, %d", regex, col, offset, limit);
		} else if (mode.equals(ListMode.TITLE)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE title REGEXP '%s' %s LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, regex, col, offset, limit, table);
		} else if (mode.equals(ListMode.USER)) {
			//sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE nickname REGEXP '%s' %s LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", DBConstants.USER_TABLE, regex, col, offset, limit, table);
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE nickname REGEXP '%s') as i JOIN post as j ON i.id=j.user_id %s LIMIT %d, %d", DBConstants.USER_TABLE, regex, col, offset, limit);
		} else if (mode.equals(ListMode.TAG)) {
			sql = String.format("SELECT k.* FROM ((SELECT `id` FROM `tag` WHERE tag_name REGEXP '%s') as i JOIN `tagmap` as j ON i.id = j.tag_id) JOIN `post` as k ON k.id = j.post_id  GROUP BY post_id %s LIMIT %d, %d", regex, col, offset, limit);
		} else if (mode.equals(ListMode.NONE)) {
			return this.selectList(page, sort);
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(createFromResultSet(rs));
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public List<PostBean> selectList(Pagination page, SortMode sort) {
		List<PostBean> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = "";
		if (sort.equals(SortMode.HIT)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s ORDER BY post.hit DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		} else if (sort.equals(SortMode.LATEST)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s ORDER BY post.id DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		} else if (sort.equals(SortMode.MATCH)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		} else if (sort.equals(SortMode.RECOMMEND)) {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s ORDER BY post.recommend DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		} else {
			sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(createFromResultSet(rs));
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public List<PostBean> selectList(Pagination page, int userId) {
		List<PostBean> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE user_id=? ORDER BY post.postDate DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(createFromResultSet(rs));
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	public List<PostBean> selectListByGroup(Pagination page, int groupId) {
		List<PostBean> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE group_id=? ORDER BY post.postDate DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(createFromResultSet(rs));
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	public int getAllCountByGroup(int groupId) {
		String countSql = String.format("SELECT COUNT(`id`) FROM %s WHERE group_id=?", table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			 pstmt = conn.prepareStatement(countSql);
			 pstmt.setInt(1, groupId);
			 rs = pstmt.executeQuery();
			 while (rs.next()) {
				 totalCount += rs.getInt(1);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return totalCount;
	}
}
