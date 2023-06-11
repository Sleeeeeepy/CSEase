package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.CommentBean;
import dto.Pagination;

public interface CommentDAO extends DAO<CommentBean>{
	List<CommentBean> selectList();
	List<CommentBean> selectListByUser(int userId);
	List<CommentBean> selectListByPost(int postId);
	int getAllCount(int postId);
	List<CommentBean> selectListByPost(Pagination page, int postId);
}
