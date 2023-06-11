package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.Pagination;
import dto.PostBean;
import service.ListMode;
import service.SortMode;

public interface PostDAO extends DAO<PostBean>{
	List<PostBean> selectList();
	List<PostBean> selectList(boolean isPublic);
	List<PostBean> selectListByUser(int userId);
	List<PostBean> selectListByTag(int tagId);
	List<PostBean> selectListByTags(List<Integer> tags);
	List<PostBean> selectList(Pagination page, SortMode sort);
	List<PostBean> selectList(Pagination page, int userId);
	List<PostBean> selectListByGroup(Pagination page, int groupId);
	int read(int id);
	int getAllCount();
	int getAllCount(int id);
	int getAllCountByGroup(int groupId);
	int searchCount(ListMode mode, String regex);
	List<PostBean> searchList(Pagination page, SortMode sort, ListMode mode, String regex);
}
