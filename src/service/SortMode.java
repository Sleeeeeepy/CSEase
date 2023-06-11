package service;

public enum SortMode {
	NONE,
	MATCH,
	LATEST,
	HIT,
	RECOMMEND;
	
	public static SortMode getInstance(String name) {
		String _name = name.toUpperCase();
		if (_name.toUpperCase().equals("MATCH")) {
			return MATCH;
		} else if (_name.equals("LATEST")) {
			return LATEST;
		} else if (_name.equals("HIT")) {
			return HIT;
		} else if (_name.equals("RECOMMEND")) {
			return RECOMMEND;
		} else {
			return NONE;
		}
	}
}
