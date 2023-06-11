package dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import dto.GroupBean;

public class GroupImpl implements GroupDAO {
	private Connection conn = null;
	private String table = DBConstants.GROUP_TABLE;
	private String mappingTable = DBConstants.GROUP_MAP_TABLE;
	
	public GroupImpl(Connection conn) {
		this.conn = conn;
	}
	
	public String getTableName() {
		return table;
	}
	
	@Override
	public int insert(GroupBean group) {
		String sql = "INSERT INTO " + table + " (`name`, `image`, `admin_id`, `explain`) VALUES (?, ?, ?, ?)";

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, group.getName());
			pstmt.setBlob(2, group.getPhoto());
			pstmt.setInt(3, group.getAdminId());
			pstmt.setString(4, group.getExplain());
			pstmt.executeUpdate();
			return addMember(group.getName(), group.getAdminId());
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
	public int addMember(String name, int userId) {
		GroupBean group = this.select(name);
		String sql = "INSERT INTO " + mappingTable + " (`user_id`, `group_id`) VALUES (?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, group.getId());
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
	public int leaveMember(int userId, int groupId) {
		String sql = "DELETE FROM " + mappingTable + " WHERE user_id=? AND group_id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, groupId);
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
	public int update(GroupBean group) {
		String updateSql = "UPDATE " + table + " SET name=?, image=?, admin_id=?, `explain`=? WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(updateSql);
			pstmt.setString(1, group.getName());
			pstmt.setBlob(2, group.getPhoto());
			pstmt.setInt(3, group.getAdminId());
			pstmt.setString(4, group.getExplain());
			pstmt.setInt(5, group.getId());
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
	public GroupBean select(int id) {
		String sql = "SELECT `id`, `name`, `image`, `admin_id`, `explain` FROM " + table + " WHERE id=?";
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
		}
		
		return null;
	}

	@Override
	public GroupBean select(String name) {
		String sql = "SELECT `id`, `name`, `image`, `admin_id`, `explain` FROM " + table + " WHERE name=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return createFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<GroupBean> selectList() {
		String sql = "SELECT `id`, `name`, `image`, `admin_id`, `explain` FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GroupBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list.add(createFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public List<GroupBean> selectListByUser(int userId) {
		//String sql = "SELECT * FROM " + table + " JOIN " + mappingTable  +" ON " + table + ".user_id = " + mappingTable + ".user_id WHERE user_id=?";
		String sql = String.format("SELECT j.* FROM (SELECT `group_id` FROM %s WHERE user_id=?) as i JOIN %s as j ON i.group_id=j.id", mappingTable, table);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GroupBean> list = new ArrayList<>();
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
	public GroupBean createFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			Blob photo = rs.getBlob("image");
			int adminId = rs.getInt("admin_id");
			String explain = rs.getString("explain");
			return new GroupBean(id, name, photo, adminId, explain);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int addMember(int userId, int groupId) {
		String sql = "INSERT INTO " + mappingTable + " (`user_id`, `group_id`) VALUES (?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, groupId);
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
	public boolean isExist(int groupId, int userId) {
		String sql = String.format("SELECT * FROM %s WHERE user_id=? AND group_id=?", mappingTable);
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
}
