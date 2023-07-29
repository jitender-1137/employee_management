package com.universal.em.utils;

import java.security.Key;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.universal.em.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesEncryption {

	static Logger log = LoggerFactory.getLogger(AesEncryption.class);

	public static String iv = "aibKcM9Jq6i8NIt+ACg8LQ==";
	public static String ivKey = "dxW/a/raDOtWV9T/8UL8OLVig0am9k4kBMw4x9rddfg=";

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'J', '|','T', 'E', 'N', 'D', 'E', 'R', '#', 'Y', '@', 'D', '@', 'V',
			'1', '1' };

	/**
	 * Encrypt a string with AES algorithm.
	 *
	 * @param data is a string
	 * @return the encrypted string
	 */
	public static String encrypt(String data) {
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			String encodeToString = Base64.getEncoder().encodeToString(encVal);
			if (Pattern.compile("\\/").matcher(encodeToString).find()) {
				encodeToString = encodeToString.replaceAll("/", "_");
			}
			return encodeToString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Decrypt a string with AES algorithm.
	 *
	 * @param encryptedData is a string
	 * @return the decrypted string
	 */
	public static String decrypt(String encryptedData) {
		try {
			if (Pattern.compile("_").matcher(encryptedData).find()) {
				encryptedData = encryptedData.replaceAll("_", "/");
			}
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			return new String(decValue);
		} catch (Exception e) {
			log.info("Error in Decryption :: " + e.getMessage());
			throw new ServiceException("EM006");
		}
	}

	/**
	 * Generate a new encryption key.
	 */
	private static Key generateKey() throws Exception {
		return new SecretKeySpec(keyValue, ALGO);
	}

	public static String ivEncrypt(String string) {
		try {
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);

			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String ivDecrypt(String message) {
		try {
			byte[] msg = Base64.getDecoder().decode(message.getBytes());

			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");

			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
			return new String(cipher.doFinal(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
