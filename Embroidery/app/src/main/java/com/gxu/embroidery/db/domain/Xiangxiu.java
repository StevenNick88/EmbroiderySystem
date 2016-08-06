package com.gxu.embroidery.db.domain;

import java.io.Serializable;


public class Xiangxiu implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x_id;
	private String x_num;
	private String title;
	private String img;
	public int getX_id() {
		return x_id;
	}
	public void setX_id(int x_id) {
		this.x_id = x_id;
	}
	public String getX_num() {
		return x_num;
	}
	public void setX_num(String x_num) {
		this.x_num = x_num;
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
		return "Xiangxiu [x_id=" + x_id + ", x_num=" + x_num + ", title="
				+ title + ", img=" + img + "]";
	}
	public Xiangxiu(int x_id, String x_num, String title, String img) {
		super();
		this.x_id = x_id;
		this.x_num = x_num;
		this.title = title;
		this.img = img;
	}
	public Xiangxiu() {
		super();
	}




}
