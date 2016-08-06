package com.gxu.embroidery.db.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Shuxiu implements Serializable {

	private static final long serialVersionUID = 1L;
	private int s_id;
	private String s_num;
	private String title;
	private String img;
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getS_num() {
		return s_num;
	}
	public void setS_num(String s_num) {
		this.s_num = s_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Shuxiu [s_id=" + s_id + ", s_num=" + s_num + ", title=" + title
				+ ", img=" + img + "]";
	}
	public Shuxiu(int s_id, String s_num, String title, String img) {
		super();
		this.s_id = s_id;
		this.s_num = s_num;
		this.title = title;
		this.img = img;
	}
	public Shuxiu() {
		super();
	}




}
