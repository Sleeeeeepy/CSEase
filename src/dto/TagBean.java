package dto;

public class TagBean {
	private int id;
	private String tagName;

	public TagBean() {
		this(0, "");
	}

	public TagBean(int id, String tagName) {
		this.id = id;
		this.tagName = tagName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
