package util;

public class RegexEscape {
	public static String escape(String string){
	    final String[] blacklist = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"};
	    for (String escape : blacklist) {
	    	if (string.contains(escape)) {
	    		string = string.replace(escape, "\\\\" + escape);
	    	}
	    }
	    return string;
	}
}
