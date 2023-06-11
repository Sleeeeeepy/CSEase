package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.GroupBean;

public interface GroupDAO extends DAO<GroupBean>{
	int addMember(int userId, int groupId);
	List<GroupBean> selectList();
	List<GroupBean> selectListByUser(int userId);
	int leaveMember(int userId, int groupId);
	int addMember(String name, int userId);
	boolean isExist(int groupId, int userId);
	GroupBean select(String name);
}
