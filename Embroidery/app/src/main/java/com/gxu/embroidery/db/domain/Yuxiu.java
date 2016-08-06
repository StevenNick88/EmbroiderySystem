package com.gxu.embroidery.db.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Yuxiu implements Serializable{

	private static final long serialVersionUID = 1L;
	private int y_id;
	private String y_num;
	private String title;
	private String img;
	public int getY_id() {
		return y_id;
	}
	public void setY_id(int y_id) {
		this.y_id = y_id;
	}
	public String getY_num() {
		return y_num;
	}
	public void setY_num(String y_num) {
		this.y_num = y_num;
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
		return "Yuxiu [y_id=" + y_id + ", y_num=" + y_num + ", title=" + title
				+ ", img=" + img + "]";
	}
	public Yuxiu(int y_id, String y_num, String title, String img) {
		super();
		this.y_id = y_id;
		this.y_num = y_num;
		this.title = title;
		this.img = img;
	}
	public Yuxiu() {
		super();
	}


}
