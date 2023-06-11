package dto;

public class GroupInviteBean {
	private int id;
	private int userId;
	private int groupId;
	private boolean invite;
	
	public GroupInviteBean() {
		this(0, 0, 0, false);
	}

	public GroupInviteBean(int id, int userId, int groupId, boolean invite) {
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
		this.invite = invite;
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

	public boolean isInvite() {
		return invite;
	}

	public void setInvite(boolean invite) {
		this.invite = invite;
	}

}
