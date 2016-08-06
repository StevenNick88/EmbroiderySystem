package com.gxu.embroidery.db.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/7.
 */

public class Information implements Serializable {

	private static final long serialVersionUID = 1L;
	private int i_id;
	private String i_num;
	private String title;
	private String img;
	public int getI_id() {
		return i_id;
	}
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}
	public String getI_num() {
		return i_num;
	}
	public void setI_num(String i_num) {
		this.i_num = i_num;
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
		return "Information [i_id=" + i_id + ", i_num=" + i_num + ", title="
				+ title + ", img=" + img + "]";
	}
	public Information(int i_id, String i_num, String title, String img) {
		super();
		this.i_id = i_id;
		this.i_num = i_num;
		this.title = title;
		this.img = img;
	}
	public Information() {
		super();
	}



}