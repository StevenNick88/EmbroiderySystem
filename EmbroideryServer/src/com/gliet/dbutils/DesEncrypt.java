package com.gliet.dbutils;


/**
 *
 * 使用DES加密,可对byte[],String类型进行加密
 * 
 */

public abstract class DesEncrypt {
	
	
	public  static  DesEncrypt getInstance(){
		DesEncrypt instance =null;
		try {
			instance = (DesEncrypt)Class.forName(DesEncrypt.class.getName() + "Local").newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * MD5加密算法
	 * @param strPass
	 * @return
	 */
	public  abstract String encryptTriDesOldSys(String strPass);
	
	
	
	/**
	 * DES加密函数
	 *
	 * @param strPass
	 * @return
	 * 暂时先不用该加密算法，用老系统的加密算法
	 */
	public abstract String encryptTriDes(String strPass);

	
	
	/**
	 * DES解密函数
	 *
	 * @param strPass
	 * @return
	 */
	public abstract String TriDesDecrypt(String strPass);

	 

}
