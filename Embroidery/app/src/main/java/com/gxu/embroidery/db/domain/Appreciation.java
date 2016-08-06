package com.gxu.embroidery.db.domain;

import java.io.Serializable;


public class Appreciation implements Serializable {

	private static final long serialVersionUID = 1L;
	private int a_id;
	private String a_num;
	private String title;
	private String img;
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	public String getA_num() {
		return a_num;
	}
	public void setA_num(String a_num) {
		this.a_num = a_num;
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
		return "Appreciation [a_id=" + a_id + ", a_num=" + a_num + ", title="
				+ title + ", img=" + img + "]";
	}
	public Appreciation(int a_id, String a_num, String title, String img) {
		super();
		this.a_id = a_id;
		this.a_num = a_num;
		this.title = title;
		this.img = img;
	}
	public Appreciation() {
		super();
	}



}
