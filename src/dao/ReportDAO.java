package dao;

import java.sql.ResultSet;
import java.util.List;

import dto.Pagination;
import dto.PostReport;

public interface ReportDAO extends DAO<PostReport>{
	List<PostReport> selectList(boolean confirm);
	List<PostReport> selectList();
	List<PostReport> selectList(Pagination pagination);
	int getAllCount();
	PostReport selectByPostId(int postId);
}
