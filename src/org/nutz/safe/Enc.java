package org.nutz.safe;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;

/**
 * 加密解密帮助函数
 *
 */
public class Enc {

	String md5key;
	PublicKey publicKey;
	PrivateKey privateKey;
	
	private static Enc sys = new Enc();
	public static Enc sys() {
		return sys;
	}
	public static void initSys(String md5key, PublicKey publicKey, PrivateKey privateKey) {
		sys().md5key = md5key;
		sys().publicKey = publicKey;
		sys().privateKey = privateKey;
	}
	
	public String enc(String source, String...encs) {
		if (source == null)
			return "";
		byte[] dest = source.getBytes(Encoding.CHARSET_UTF8);
		try {
			for (String enc : encs) {
				if ("rsa".equals(enc)) {
					Cipher cipher = Cipher.getInstance("RSA");
					cipher.init(Cipher.ENCRYPT_MODE, publicKey);
					dest = cipher.doFinal(dest);
				} 
				else if ("rsa-de".equals(enc)) {
					Cipher cipher = Cipher.getInstance("RSA");
					cipher.init(Cipher.DECRYPT_MODE, privateKey);
					dest = cipher.doFinal(dest);
				} 
				else if ("md5".equals(enc)) {
					MessageDigest md5 = MessageDigest.getInstance("md5");
					dest = md5.digest(dest);
				} 
				else if ("base64".equals(enc)) {
					//
				} 
				else if ("base64-de".equals(enc)) {
					//
				} 
				else if ("sha1".equals(enc)) {
					//
				} 
			}
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
		return new String(dest, Encoding.CHARSET_UTF8);
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
}
