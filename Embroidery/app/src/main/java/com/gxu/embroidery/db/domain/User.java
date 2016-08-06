package com.gxu.embroidery.db.domain;

import java.io.Serializable;

/**
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int u_id;
	private String u_num;
	private String u_name;
	private String u_pwd;
	private String u_phone;
	private String u_img;
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getU_num() {
		return u_num;
	}
	public void setU_num(String u_num) {
		this.u_num = u_num;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_pwd() {
		return u_pwd;
	}
	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_img() {
		return u_img;
	}
	public void setU_img(String u_img) {
		this.u_img = u_img;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", u_num=" + u_num + ", u_name=" + u_name
				+ ", u_pwd=" + u_pwd + ", u_phone=" + u_phone + ", u_img="
				+ u_img + "]";
	}
	public User(int u_id, String u_num, String u_name, String u_pwd,
				String u_phone, String u_img) {
		super();
		this.u_id = u_id;
		this.u_num = u_num;
		this.u_name = u_name;
		this.u_pwd = u_pwd;
		this.u_phone = u_phone;
		this.u_img = u_img;
	}
	public User() {
		super();
	}




}
