package dto;

import java.sql.Timestamp;

public class PostReport {
	private int id;
	private Timestamp date;
	private int postId;
	private int userId;
	private String reason;
	private boolean confirm;
	
	public PostReport() {
		this(0, null, 0, 0, "", false);
	}

	public PostReport(int id, Timestamp date, int postId, int userId, String reason, boolean confirm) {
		this.id = id;
		this.date = date;
		this.postId = postId;
		this.userId = userId;
		this.reason = reason;
		this.confirm = confirm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
	
}
