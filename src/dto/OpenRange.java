package dto;

public enum OpenRange {
	PUBLIC, 
	PRIVATE, 
	MEMBER_ONLY;
	
	public static OpenRange parseOpenRange(String str) {
		if(str.toLowerCase().equals("public"))
			return PUBLIC;
		else if(str.toLowerCase().equals("private"))
			return PRIVATE;
		else
			return MEMBER_ONLY;
	}
}
