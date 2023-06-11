package dto;

import java.sql.Blob;

public class MemberBean { 
	private int id;
	private String nickname;
	private Blob photo;
	private String userId;
	private String email;
	private String password;
	private String phone;
	private String name;
	private boolean phonePublic;
	private boolean emailPublic;
	private boolean namePublic;
	private boolean verify;
	
	public MemberBean() {
		this(0,"", null, "", "", "", "", "", false, false, false, false);
	}
	
	public MemberBean(int id, String nickname, Blob photo, String userId, String email, String password, String phone,
			String name, boolean phonePublic, boolean emailPublic, boolean namePublic, boolean verify) {
		this.id = id;
		this.nickname = nickname;
		this.photo = photo;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.name = name;
		this.phonePublic = phonePublic;
		this.emailPublic = emailPublic;
		this.namePublic = namePublic;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Blob getPhoto() {
		return photo;
	}
	
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPhonePublic() {
		return phonePublic;
	}
	
	public void setPhonePublic(boolean phonePublic) {
		this.phonePublic = phonePublic;
	}
	
	public boolean isEmailPublic() {
		return emailPublic;
	}
	
	public void setEmailPublic(boolean emailPublic) {
		this.emailPublic = emailPublic;
	}
	
	public boolean isNamePublic() {
		return namePublic;
	}
	
	public void setNamePublic(boolean namePublic) {
		this.namePublic = namePublic;
	}
	
	public boolean isVerify() {
		return verify;
	}
	
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
	
	public boolean isAllPrivate() {
		return !(namePublic || emailPublic || phonePublic);
	}
}
