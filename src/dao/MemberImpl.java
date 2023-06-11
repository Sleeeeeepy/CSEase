package dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MemberBean;

public class MemberImpl implements MemberDAO {
	private Connection conn = null;
	//private String groupTable = DBConstants.GROUP_TABLE;
	private String table = DBConstants.USER_TABLE; 
	private String mappingTable = DBConstants.GROUP_MAP_TABLE;
	
	public MemberImpl(Connection conn) {
		this.conn = conn;
	}

	public String getTableName() {
		return table;
	}
	
	@Override
	public int insert(MemberBean member) {
		String sql = "INSERT INTO " + table + " (`id`, `user_id`, `image`, `nickname`, `name`, `email`, `password`, `phone`, `phone_public`, `name_public`, `email_public`, `verify`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getId());
			pstmt.setString(2, member.getUserId());
			if (member.getPhoto() != null) {
				pstmt.setBlob(3, member.getPhoto());
			} else {
				pstmt.setNull(3, java.sql.Types.BLOB);
			}
			pstmt.setString(4, member.getNickname());
			pstmt.setString(5, member.getName());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPassword());
			pstmt.setString(8, member.getPhone());
			pstmt.setBoolean(9, member.isPhonePublic());
			pstmt.setBoolean(10, member.isNamePublic());
			pstmt.setBoolean(11, member.isEmailPublic());
			pstmt.setBoolean(12, member.isVerify());
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
	public int update(MemberBean member) {
		String sql = "UPDATE " + table + " SET user_id=?, image=?, nickname=?, name=?, email=?, password=?, phone=?, phone_public=?, name_public=?, email_public=?, verify=? WHERE id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserId());
			if (member.getPhoto() != null) {
				pstmt.setBlob(2, member.getPhoto());
			} else {
				pstmt.setNull(2, java.sql.Types.BLOB);
			}
			pstmt.setString(3, member.getNickname());
			pstmt.setString(4, member.getName());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPassword());
			pstmt.setString(7, member.getPhone());
			pstmt.setBoolean(8, member.isPhonePublic());
			pstmt.setBoolean(9, member.isNamePublic());
			pstmt.setBoolean(10, member.isEmailPublic());
			pstmt.setBoolean(11, member.isVerify());
			pstmt.setInt(12, member.getId());			
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
	public MemberBean select(int id) {
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
	public List<MemberBean> selectList() {
		String sql = "SELECT * FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberBean> list = new ArrayList<>();
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

	public List<MemberBean> selectListByGroup(int groupId) {
		String mapSql = "SELECT * FROM " + table + " JOIN " + mappingTable  +" ON " + table + ".user_id = " + mappingTable + ".user_id WHERE group_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberBean> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(mapSql);
			pstmt.setInt(1, groupId);
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
	public MemberBean createFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String userId = rs.getString("user_id");
			String nickname = rs.getString("nickname");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String phone = rs.getString("phone");
			boolean isPhonePublic = rs.getBoolean("phone_public");
			boolean isNamePublic = rs.getBoolean("name_public");
			boolean isEmailPublic = rs.getBoolean("email_public");
			Blob photo = rs.getBlob("image");
			boolean verify = rs.getBoolean("verify");
			return new MemberBean(id, nickname, photo, userId, email, password, phone, name, isPhonePublic, isEmailPublic, isNamePublic, verify);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MemberBean select(String userId) {
		String sql = "SELECT * FROM " + table + " WHERE user_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
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
	public int EmailVerify(int id) {
		String sql = "UPDATE " + table + " SET verify=true WHERE id=?";
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
	
	public boolean isExist(String username, String email, String nickname) {
		String sql = "SELECT * FROM " + table + " WHERE user_id=? OR email=? OR nickname=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, email);
			pstmt.setString(3, nickname);
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
