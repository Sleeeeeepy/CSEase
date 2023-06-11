package dao;

import java.util.List;

import dto.GroupInviteBean;

public interface GroupInviteDAO extends DAO<GroupInviteBean>{
	List<GroupInviteBean> selectList(int groupId);
	boolean isExist(int groupId, int userId);
	List<GroupInviteBean> selectListByUser(int userId);
}
