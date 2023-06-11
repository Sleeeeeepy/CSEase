package service;

public enum ListMode {
	USER,
	GROUP,
	TITLE,
	TAG,
	NONE;
	
	public static ListMode getInstance(String name) {
		String _name = name.toUpperCase();
		if (_name.toUpperCase().equals("USER")) {
			return USER;
		} else if (_name.equals("GROUP")) {
			return GROUP;
		} else if (_name.equals("TITLE")) {
			return TITLE;
		} else if (_name.equals("TAG")) {
			return TAG;
		} else {
			return NONE;
		}
	}
}
