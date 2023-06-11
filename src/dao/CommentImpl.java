package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.CommentBean;
import dto.Pagination;
import dto.PostReport;

public class CommentImpl implements CommentDAO {
	private Connection conn = null;
	private String table = DBConstants.COMMENT_TABLE;
	public CommentImpl(Connection conn) {
		this.conn = conn;
	}
	
	public String getTableName() {
		return table;
	}
	
	@Override
	public int insert(CommentBean comment) {
		String sql = "INSERT INTO " + table + " (`id`, `date`, `post_id`, `user_id`, `contents`, `password`) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getId());
			pstmt.setTimestamp(2, comment.getDate());
			pstmt.setInt(3, comment.getPostId());
			pstmt.setInt(4, comment.getUserId());
			pstmt.setString(5, comment.getComment());
			pstmt.setString(6, comment.getPassword());
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
	public int delete(int id) {
		String sql = "DELETE FROM " + table + " WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int update(CommentBean comment) {
		String sql = "UPDATE " + table + " SET contents=? WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getComment());
			pstmt.setInt(2, comment.getId());
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
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public CommentBean select(int id) {
		String sql = "SELECT `id`, `date`, `post_id`, `user_id`, `contents`, `password` FROM " + table + " WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
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
	public List<CommentBean> selectList() {
		String sql = "SELECT `id`, `date`, `post_id`, `user_id`, `contents`, `password` FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentBean> list = new ArrayList<>();
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
	public List<CommentBean> selectListByUser(int userId) {
		String sql = "SELECT `id`, `date`, `post_id`, `user_id`, `contents`, `password` FROM " + table + " WHERE user_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentBean> list = new ArrayList<>();
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
	public CommentBean createFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			Timestamp time = rs.getTimestamp("date");
			int postId = rs.getInt("post_id");
			int userId = rs.getInt("user_id");
			String contents = rs.getString("contents");
			String password = rs.getString("password");
			return new CommentBean(id, postId, userId, contents, time, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CommentBean> selectListByPost(int postId) {
		String sql = "SELECT `id`, `date`, `post_id`, `user_id`, `contents`, `password` FROM " + table + " WHERE post_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
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
	public List<CommentBean> selectListByPost(Pagination page, int postId) {
		List<CommentBean> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s WHERE post_id=? ORDER BY `id` DESC LIMIT %d, %d) as i JOIN %s as j ON i.id=j.id;", table, offset, limit, table);
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try {
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, postId);
			rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				ret.add(createFromResultSet(rs2));
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	public int getAllCount(int postId) {
		String countSql = String.format("SELECT COUNT(`id`) FROM %s WHERE post_id=?", table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			 pstmt = conn.prepareStatement(countSql);
			 pstmt.setInt(1, postId);
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
