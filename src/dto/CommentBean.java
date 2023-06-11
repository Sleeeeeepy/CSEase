package dto;

import java.sql.Timestamp;

public class CommentBean {
	private int id;
	private int postId;
	private int userId;
	private String comment;
	private Timestamp date;
	private String password;
	
	public CommentBean() {
		this(0, 0, 0, "", null, "");
	}

	public CommentBean(int id, int postId, int userId, String comment, Timestamp date, String password) {
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.comment = comment;
		this.date = date;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
