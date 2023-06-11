package dto;

import java.sql.Timestamp;

public class PostBean { 
	private int id;
	private int userId;
	private int groupId;
	private String title;
	private String contents;
	private String explain;
	private String link;
	private Timestamp postDate;
	private Timestamp expiredDate;
	private String password;
	private OpenRange openRange;
	private int recommend;
	private String type;
	private boolean burn; //burn after read
	private int hit;
	
	public PostBean() {
		this(0, 0, 0, "", "", "", "", null, null, "", null, 0, "", false, 0);
	}
	
	public PostBean(int id, int userId, int groupId, String title, String contents, String explain, String link,
			Timestamp postDate, Timestamp expireDate, String password, OpenRange openRange, int recommend,
			String type, boolean burn, int hit) {
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
		this.title = title;
		this.contents = contents;
		this.explain = explain;
		this.link = link;
		this.postDate = postDate;
		this.expiredDate = expireDate;
		this.password = password;
		this.openRange = openRange;
		this.recommend = recommend;
		this.type = type;
		this.burn = burn;
		this.hit = hit;
	}
	
	public int getHit() {
		return hit;
	}
	
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public boolean isBurn() {
		return burn;
	}
	
	public void setBurn(boolean burn) {
		this.burn = burn;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getExplain() {
		return explain;
	}
	
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Timestamp getPostDate() {
		return postDate;
	}
	
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
	
	public Timestamp getExpiredDate() {
		return expiredDate;
	}
	
	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public OpenRange getOpenRange() {
		return openRange;
	}
	
	public void setOpenRange(OpenRange openRange) {
		this.openRange = openRange;
	}
	
	public int getRecommend() {
		return recommend;
	}
	
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
