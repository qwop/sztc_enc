package cn.szsctc;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES算法编程实现加密/解密
 *
 */
public class AESUtil {

	/**
	 * 生成密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception{
		//密钥生成器
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		//初始化密钥生成器
		keyGen.init(128);  //默认128，获得无政策权限后可用192或256
		//生成密钥
		SecretKey secretKey = keyGen.generateKey();
		return secretKey.getEncoded();
	}


	/**
	 * 加密
	 * @throws Exception 
	 */
	public static byte[] encryptAES(byte[] data, byte[] key) throws Exception{
		//恢复密钥
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		//Cipher完成加密
		Cipher cipher = Cipher.getInstance("AES");
		//根据密钥对cipher进行初始化
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//加密
		byte[] encrypt = cipher.doFinal(data);
		
		return encrypt;
	}


	/**
	 * 解密
	 */
	public static byte[] decryptAES(byte[] data, byte[] key) throws Exception{
		//恢复密钥生成器
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		//Cipher完成解密
		Cipher cipher = Cipher.getInstance("AES");
		//根据密钥对cipher进行初始化
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plain = cipher.doFinal(data);
		return plain;
	}

	static String ENCRYPT_CHARSET = "UTF-8";
	static String mode = "AES/ECB/PKCS7Padding";
	/**
	 * 解密
	 */
	public static byte[] decryptAES1(byte[] data, byte[] key) throws Exception{
		//恢复密钥生成器
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		//Cipher完成解密
		Cipher cipher = Cipher.getInstance("AES");
		//根据密钥对cipher进行初始化
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plain = cipher.doFinal(data);
		return plain;
	}


	/**
	 * 解密
	 *
	 * @param content  待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static String decrypt(String content, String password, String mode)  {
			if (content == null) {
				return null;
			}

			byte[] newContent = parseHexStr2Byte(content);// 将16进制转换为二进制
		SecureRandom secureRandom = null;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			// kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance(mode);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(newContent);
			if (result != null && result.length > 0) {
				return new String(result, "UTF-8");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
