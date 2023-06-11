package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 implements IHash {
	@Override
	public String crypto(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(input.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString(b & 0xFF));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
