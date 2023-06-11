package dto.json;

import com.google.gson.annotations.SerializedName;

public class Tag {
	@SerializedName("value")
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
