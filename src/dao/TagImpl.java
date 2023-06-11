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
import dto.PostBean;
import dto.PostReport;
import dto.TagBean;

public class TagImpl implements TagDAO {
	private Connection conn = null;
	private String table = DBConstants.TAG_TABLE; 
	private String tagMappingTable = DBConstants.TAG_MAP_TABLE;
	private String postTable = DBConstants.POST_TABLE;
	
	public TagImpl(Connection conn) {
		this.conn = conn;
	}
	
	public String getTableName() {
		return table;
	}
	
	@Override
	public int insertToTagmap(TagBean tag, int postId) {
		String sql = "INSERT INTO " + tagMappingTable + "(`post_id`, `tag_id`) VALUES (?,?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			pstmt.setInt(2, tag.getId());
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
	public int insert(TagBean tag, int postId) {
		String sql = "INSERT INTO " + tagMappingTable + "(`post_id`, `tag_id`) VALUES (?,?)";
		PreparedStatement pstmt = null;
		
		int key = insert(tag);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);
			pstmt.setInt(2, key);
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
	public int update(TagBean tag) {
		String sql = "UPDATE " + table + " tag_name=? WHERE id=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tag.getTagName());
			pstmt.setInt(2, tag.getId());
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
	public TagBean select(int id) {
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
	public List<TagBean> selectList() {
		String sql = "SELECT * FROM " + table;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TagBean> list = new ArrayList<>();
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
	public List<TagBean> selectListByPost(int postId) {
		//p post
		//tm tagmap
		//t tag
		//(tm.tage_id = tag_id) and (t.tag_id=tagId) and (p.id=tm.post_id)
		
		String sql = "SELECT t.* FROM " + postTable + " p, "+ tagMappingTable + " tm, "+ table + " t WHERE "
				+ "tm.tag_id=t.id AND p.id=tm.post_id AND p.id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TagBean> list = new ArrayList<>();
		
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
	public TagBean createFromResultSet(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String tagName = rs.getString("tag_name");
			return new TagBean(id, tagName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean isExist(String tagName) {
		String sql = "SELECT * FROM " + table + " WHERE tag_name=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tagName);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public TagBean select(String tagName) {
		String sql = "SELECT * FROM " + table + " WHERE tag_name=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tagName);
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

	//return generate key 
	@Override
	public int insert(TagBean tag) {
		String sql = "INSERT INTO " + table + " (`id`, `tag_name`) VALUES (?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, tag.getId());
			pstmt.setString(2, tag.getTagName());
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
}
