package dao;

class DBConstants {
	// JDBC driver name and database URL
	public static final String DB_PROPERTIES = "?serverTimezone=UTC&useSSL=false"; // MySQL Connector J 8.0
	public static final String DB_SCHEMAS = "csease";
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // deprecated "com.mysql.jdbc.Driver";  // try "com.mysql.cj.jdbc.Driver"
	public static final String DB_URL = "jdbc:mysql://localhost/" + DB_SCHEMAS + DB_PROPERTIES; 

	//  Database credentials
	public static final String USER = "root";
	public static final String PASS = "123456";
	
	//Table name
	public static final String COMMENT_TABLE = "`comment`";
	public static final String POST_TABLE = "`post`";
	public static final String TAG_TABLE = "`tag`";
	public static final String TAG_MAP_TABLE="`tagmap`";
	public static final String USER_TABLE = "`user`";
	public static final String GROUP_TABLE = "`group`";
	public static final String GROUP_MAP_TABLE = "`groupmap`";
	public static final String REPORT_TABLE = "`report`";
	public static final String GROUP_INVITE_TABLE = "`group_invite`";
	public static final String POST_RECOMMEND = "`post_recommend`";
}
