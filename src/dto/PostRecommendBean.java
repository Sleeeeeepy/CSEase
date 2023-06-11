package dto;

public class PostRecommendBean {
	private int id;
	private int postId;
	private int userId;
	private String ip;

	public PostRecommendBean() {
		this(0, 0, 0, "");
	}
	
	public PostRecommendBean(int id, int postId, int userId, String ip) {
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.ip = ip;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
