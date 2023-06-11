package dto;

import java.awt.Image;
import java.sql.Blob;

public class GroupBean {
	private int id;
	private String name;
	private Blob photo;
	private int adminId;
	private String explain;
	
	public GroupBean() {
		this(-1, "", null, 0, "");
	}

	public GroupBean(int id, String name, Blob photo, int adminId, String explain) {
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.adminId = adminId;
		this.explain = explain;
	}
	
	public String getExplain() {
		return explain;
	}
	
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
	public int getAdminId() {
		return adminId;
	}
	
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
}
