package dao;

import java.sql.ResultSet;

public interface DAO<T> {
	String getTableName();
	T select(int id);
	int insert(T comment);
	int delete(int id);
	int update(T comment);
	T createFromResultSet(ResultSet rs);
}
