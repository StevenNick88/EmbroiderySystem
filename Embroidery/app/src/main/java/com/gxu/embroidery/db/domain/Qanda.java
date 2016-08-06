package com.gxu.embroidery.db.domain;

import java.io.Serializable;


public class Qanda implements Serializable{

	private static final long serialVersionUID = 1L;
	private int q_id;
	private String q_num;
	private String title;
	private String img;
	public int getQ_id() {
		return q_id;
	}
	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}
	public String getQ_num() {
		return q_num;
	}
	public void setQ_num(String q_num) {
		this.q_num = q_num;
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
		return "Qanda [q_id=" + q_id + ", q_num=" + q_num + ", title=" + title
				+ ", img=" + img + "]";
	}
	public Qanda(int q_id, String q_num, String title, String img) {
		super();
		this.q_id = q_id;
		this.q_num = q_num;
		this.title = title;
		this.img = img;
	}
	public Qanda() {
		super();
	}




}
