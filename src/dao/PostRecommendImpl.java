package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dto.OpenRange;
import dto.PostBean;
import dto.PostRecommendBean;

public class PostRecommendImpl implements PostRecommendDAO {

	Connection conn = null;
	private String table = DBConstants.POST_RECOMMEND;
	private String post_table = DBConstants.POST_TABLE;
	
	public PostRecommendImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public String getTableName() {
		return table;
	}
	
	private int recommend(int postId) {
		String sql = "UPDATE " + post_table + " SET recommend=recommend+1 WHERE id=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
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
	public PostRecommendBean select(int id) {
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
	public PostRecommendBean select(String ip, int post_id) {
		String sql = "SELECT * FROM " + table + " WHERE ip=? AND post_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setInt(2, post_id);
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
	public int insert(PostRecommendBean recommend) {
		String sql = "INSERT INTO " + table + " (`id`, `user_id`, `post_id`, `ip`) VALUES (?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommend.getId());
			pstmt.setInt(2, recommend.getUserId());
			pstmt.setInt(3, recommend.getPostId());
			pstmt.setString(4, recommend.getIp());
			recommend(recommend.getPostId());
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
		throw new UnsupportedOperationException("delete recommend is not supported.");
	}

	@Override
	public int update(PostRecommendBean recommend) {
		throw new UnsupportedOperationException("update recommend is not supported.");
	}

	@Override
	public PostRecommendBean createFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			int postId = rs.getInt("post_id");
			int userId = rs.getInt("user_id");
			String ip = rs.getString("ip");
			return new PostRecommendBean(id, postId, userId, ip);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
