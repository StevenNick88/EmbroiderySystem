package com.gliet.domain;

import java.io.Serializable;

/**
 * CREATE TABLE `save` ( `id` int(40) NOT NULL AUTO_INCREMENT, `u_num`
 * varchar(50) DEFAULT NULL, `s_table` varchar(50) DEFAULT NULL, `s_num`
 * varchar(30) DEFAULT NULL, `s_title` varchar(200) DEFAULT NULL, `s_img`
 * varchar(100) DEFAULT NULL, PRIMARY KEY (`id`)
 */
public class Save implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String u_num;
	private String s_table;
	private String s_num;
	private String s_title;
	private String s_img;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getU_num() {
		return u_num;
	}

	public void setU_num(String u_num) {
		this.u_num = u_num;
	}

	public String getS_table() {
		return s_table;
	}

	public void setS_table(String s_table) {
		this.s_table = s_table;
	}

	public String getS_num() {
		return s_num;
	}

	public void setS_num(String s_num) {
		this.s_num = s_num;
	}

	public String getS_title() {
		return s_title;
	}

	public void setS_title(String s_title) {
		this.s_title = s_title;
	}

	public String getS_img() {
		return s_img;
	}

	public void setS_img(String s_img) {
		this.s_img = s_img;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Save [id=" + id + ", u_num=" + u_num + ", s_table=" + s_table
				+ ", s_num=" + s_num + ", s_title=" + s_title + ", s_img="
				+ s_img + "]";
	}

	public Save(int id, String u_num, String s_table, String s_num,
			String s_title, String s_img) {
		super();
		this.id = id;
		this.u_num = u_num;
		this.s_table = s_table;
		this.s_num = s_num;
		this.s_title = s_title;
		this.s_img = s_img;
	}

	public Save() {
		super();
	}

}
