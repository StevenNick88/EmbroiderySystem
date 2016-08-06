package com.gliet.domain;

import java.io.Serializable;

/**
 */
public class Xiangxiu implements Serializable {

    private static final long serialVersionUID = 1L;
	private int id;
	private String num;
	private String title;
	private String img;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
		return "Appreciation [id=" + id + ", num=" + num + ", title=" + title
				+ ", img=" + img + "]";
	}
	public Xiangxiu(int id, String num, String title, String img) {
		super();
		this.id = id;
		this.num = num;
		this.title = title;
		this.img = img;
	}
	public Xiangxiu() {
		super();
	}
	
}
