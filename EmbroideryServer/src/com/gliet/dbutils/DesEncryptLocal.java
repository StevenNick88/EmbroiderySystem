package com.gliet.dbutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import Decoder.BASE64Encoder;


/**
 * DES算法把64位的明文输入块变为64位的密文输出块，它所使用的密钥也是64位
 * 使用DES加密,可对byte[],String类型进行加密
 */

public class DesEncryptLocal extends DesEncrypt {
	static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	static final byte[] desKeyData = { (byte) 0xF4, (byte) 0x88, (byte) 0xFD, (byte) 0x58, (byte) 0x4E, (byte) 0x49,
			(byte) 0xDB, (byte) 0xCD, (byte) 0x20, (byte) 0xB4, (byte) 0x9D, (byte) 0xE4, (byte) 0x91, (byte) 0x07,
			(byte) 0x36, (byte) 0x6B, (byte) 0x33, (byte) 0x6C, (byte) 0x38, (byte) 0x0D, (byte) 0x45, (byte) 0x1D,
			(byte) 0x0F, (byte) 0x7C, };



	/**
	 *  使用MD5加密算法 
	 */
	public  String encryptTriDesOldSys(String strPass) {
		MessageDigest userNamePassword = null;
		String encode = "";
		try {
			
			//此 MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值。 
			userNamePassword = MessageDigest.getInstance("MD5");
			//使用指定的字节数组更新摘要。
			userNamePassword.update(strPass.getBytes());
			//通过执行诸如填充之类的最终操作完成哈希计算。
			byte[] digestPassword = userNamePassword.digest();
			//Base64的加密解密
			BASE64Encoder base64Encoder = new BASE64Encoder();
			encode = base64Encoder.encode(digestPassword);
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("得到安全密码 MessageDigest失败! ! ! ");
		}
		return encode;
	}

	/**
	 * DES加密函数
	 *
	 * @param strPass
	 * @return
	 * 
	 */
	public String encryptTriDes(String strPass) {
		/*
		 * {'\0xF4','\0x88','\0xFD','\0x58','\0x4E','\0x49','\0xDB','\0xCD'},
		 * {'\0x20','\0xB4','\0x9D','\0xE4','\0x91','\0x07','\0x36','\0x6B'},
		 * {'\0x33','\0x6C','\0x38','\0x0D','\0x45','\0x1D','\0x0F','\0x7C'},
		 */
		try {
			//获取UTF-8字节数组
			byte[] pass = strPass.getBytes("UTF-8");
			int tail = pass.length % 8;
			int block = pass.length / 8 + 1;
			byte fill = (byte) (8 - tail);
			byte[] fillPass = new byte[block * 8];
			//从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
			System.arraycopy(pass, 0, fillPass, 0, pass.length);
			for (int i = 0; i < 8 - tail; i++)
				fillPass[pass.length + i] = fill;
            //此类表示秘密密钥的工厂。 
			//密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范（基础密钥材料的透明表示形式），反之亦然。秘密密钥工厂只对秘密（对称）密钥进行操作。 
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			//创建一个 DESedeKeySpec 对象，使用 key 中的前 24 个字节作为 DES-EDE 密钥的密钥内容。
			DESedeKeySpec desKeySpec = new DESedeKeySpec(desKeyData);
			//根据提供的密钥规范（密钥材料）生成 SecretKey 对象。
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			//此类提供了针对加密和解密的密码 cipher 功能。 生成一个实现指定转换的 Cipher 对象。
			Cipher cipher = Cipher.getInstance("DESede");
			//用密钥初始化此 cipher。ENCRYPT_MODE:用于将 cipher 初始化为加密模式的常量。
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] result = new byte[block * 8];
			for (int i = 0; i < block; i++) {
				// 按单部分操作加密或解密数据，或者结束一个多部分操作。
				byte[] tmp = cipher.doFinal(fillPass, i * 8, 8);
				//从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
				System.arraycopy(tmp, 0, result, i * 8, 8);
			}
			//将字节数组转换为十六进制字符串 
			String strResult = toHexString(result);
			return strResult;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}

	}

	/**
	 * DES解密函数
	 *
	 * @param strPass
	 * @return
	 */
	public String TriDesDecrypt(String strPass) {
		try {
			byte[] pass = toByteArray(strPass);
			int block = pass.length / 8;
			//此类表示秘密密钥的工厂。 
			//密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范（基础密钥材料的透明表示形式），反之亦然。秘密密钥工厂只对秘密（对称）密钥进行操作。 
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			//创建一个 DESedeKeySpec 对象，使用 key 中的前 24 个字节作为 DES-EDE 密钥的密钥内容。
			DESedeKeySpec desKeySpec = new DESedeKeySpec(desKeyData);
			//根据提供的密钥规范（密钥材料）生成 SecretKey 对象。
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			//此类提供了针对加密和解密的密码 cipher 功能。 生成一个实现指定转换的 Cipher 对象。
			Cipher cipher = Cipher.getInstance("DESede");
			//用密钥初始化此 cipher。ENCRYPT_MODE:用于将 cipher 初始化为加密模式的常量。
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] result = new byte[block * 8];
			for (int i = 0; i < block; i++) {
				// 按单部分操作加密或解密数据，或者结束一个多部分操作。
				byte[] tmp = cipher.doFinal(pass, i * 8, pass.length - i * 8);
				//从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
				System.arraycopy(tmp, 0, result, i * 8, tmp.length);
			}
			int realLen = result.length;
			for (int i = 0; i < result.length; i++) {
				if (result[i] == 0) {
					realLen = i;
					break;
				}
			}
			// 通过使用指定的 charset 解码指定的 byte 子数组，构造一个新的 String。
			String strResult = new String(result, 0, realLen, "UTF-8");
			return strResult;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	/**
	 * 将一个字节转换为十六进制数和写入提供的缓冲区 
	 * @param b		字节
	 * @param buf	缓冲区 
	 */
	private void byte2hex(byte b, StringBuffer buf) {
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}
	
	/**
	 * 将字节数组转换为十六进制字符串 
	 * @param block		字节数组
	 * @return			十六进制字符串 
	 */
	private String toHexString(byte[] block) {
		int len = block.length;
		StringBuffer buf = new StringBuffer(len * 2);
		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
		}
		return buf.toString();
	}

	/**
	 * 将十六进制字符串转换为字节数组
	 * @param hexStr  	十六进制字符串
	 * @return			字节数组
	 */
	private byte[] toByteArray(String hexStr) {
		int len = hexStr.length() / 2;
		byte[] arr = new byte[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
		}
		return arr;
	}
	
	
	/**
	 * 测试方法
	 */
	public static void main(String[] args){
		DesEncryptLocal local = new DesEncryptLocal();
		String password = "LPA159lpa";
		String jiami=local.encryptTriDes(password);
		System.out.println(jiami);
		String jiemi=local.TriDesDecrypt(jiami);
		System.out.println(jiemi);
		
	}
	
	 

}
