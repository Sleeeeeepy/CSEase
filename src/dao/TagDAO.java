package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.TagBean;

public interface TagDAO extends DAO<TagBean>{
	List<TagBean> selectList();
	List<TagBean> selectListByPost(int postId);
	boolean isExist(String tagName);
	TagBean select(String tagName);
	int insert(TagBean tag, int postId);
	int insertToTagmap(TagBean tag, int postId);
}
