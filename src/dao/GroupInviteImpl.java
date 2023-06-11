package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.GroupInviteBean;
import dto.PostReport;

public class GroupInviteImpl implements GroupInviteDAO {

	String table = DBConstants.GROUP_INVITE_TABLE;
	Connection conn = null;
	
	public GroupInviteImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public String getTableName() {
		return table;
	}

	@Override
	public GroupInviteBean select(int id) {
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
	public int insert(GroupInviteBean invite) {
		String sql = "INSERT INTO " + table + " (`user_id`, `group_id`, `invite`) VALUES (?, ?, ?)";

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, invite.getUserId());
			pstmt.setInt(2, invite.getGroupId());
			pstmt.setBoolean(3, invite.isInvite());
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
	public int update(GroupInviteBean invite) {
		throw new UnsupportedOperationException("update is not support.");
	}

	@Override
	public GroupInviteBean createFromResultSet(ResultSet rs) {
		int id = 0;
		int userId = 0;
		int groupId = 0;
		boolean invite = true;
		
		try {
			id = rs.getInt("id");
			userId = rs.getInt("user_id");
			groupId = rs.getInt("group_id");
			invite = rs.getBoolean("invite");
			return new GroupInviteBean(id, userId, groupId, invite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GroupInviteBean> selectList(int groupId) {
		String sql = "SELECT * FROM " + table + " WHERE group_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GroupInviteBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			rs = pstmt.executeQuery();
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
		
		return list;
	}

	@Override
	public boolean isExist(int groupId, int userId) {
		String sql = String.format("SELECT * FROM %s WHERE user_id=? AND group_id=?", table);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, groupId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
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
		return false;
	}

	@Override
	public List<GroupInviteBean> selectListByUser(int userId) {
		String sql = "SELECT * FROM " + table + " WHERE user_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GroupInviteBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
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
		
		return list;
	}
}
