package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.Pagination;
import dto.PostReport;

public class ReportImpl implements ReportDAO {
	private Connection conn = null;
	private String table = DBConstants.REPORT_TABLE; 
	
	public ReportImpl(Connection conn) {
		this.conn = conn;
	}
	
	public String getTableName() {
		return table;
	}
	
	@Override
	public int insert(PostReport report) {
		String sql = "INSERT INTO " + table + " (`id`, `date`, `post_id`, `user_id`, `reason`, `confirm`) VALUES (?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, report.getId());
			pstmt.setTimestamp(2, report.getDate());
			pstmt.setInt(3, report.getPostId());
			pstmt.setInt(4, report.getUserId());
			pstmt.setString(5, report.getReason());
			pstmt.setBoolean(6, report.isConfirm());
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
	public int update(PostReport report) {
		String sql = "UPDATE " + table + " SET date=?, post_id=?, user_id=?, reason=?, confirm=? WHERE id=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, report.getDate());
			pstmt.setInt(2, report.getPostId());
			pstmt.setInt(3, report.getUserId());
			pstmt.setString(4, report.getReason());
			pstmt.setBoolean(5, report.isConfirm());
			pstmt.setInt(6, report.getId());
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
	public PostReport select(int id) {
		String sql = "SELECT * FROM " + table + " WHERE id=?";
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
	public PostReport selectByPostId(int postId) {
		String sql = "SELECT * FROM " + table + " WHERE post_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
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
	public List<PostReport> selectList(boolean confirm) {
		String sql = "SELECT * FROM " + table + " WHERE confirm=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostReport> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, confirm);
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
	public List<PostReport> selectList() {
		String sql = "SELECT * FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PostReport> list = new ArrayList<>();
		
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
	public PostReport createFromResultSet(ResultSet rs) {
		int id = 0;
		Timestamp date = null;
		int postId = 0;
		int userId = 0;
		String reason = "";
		boolean confirm = false;
		
		try {
			id = rs.getInt("id");
			date = rs.getTimestamp("date");
			postId = rs.getInt("post_id");
			userId = rs.getInt("user_id");
			reason = rs.getString("reason");
			confirm = rs.getBoolean("confirm");
			return new PostReport(id, date, postId, userId, reason, confirm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PostReport> selectList(Pagination page) {
		List<PostReport> ret = new ArrayList<>();
		int offset = page.getOffset();
		int limit = page.getRowPerPage();
		String sql = String.format("SELECT j.* FROM (SELECT `id` FROM %s LIMIT %d, %d) as i JOIN %s ON i.id=j.id;", table, offset, limit, table);
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try {
			pstmt2 = conn.prepareStatement(sql);
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
}
