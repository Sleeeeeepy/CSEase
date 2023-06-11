package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.MemberBean;

public interface MemberDAO extends DAO<MemberBean> {
	MemberBean select(String id);
	List<MemberBean> selectList();
	//List<MemberBean> selectListByGroup(int groupId);
	int EmailVerify(int id);
	boolean isExist(String username, String email, String nickname);
}
