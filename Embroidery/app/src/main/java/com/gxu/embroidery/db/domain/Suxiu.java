package com.gxu.embroidery.db.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class Suxiu implements Serializable{

	private static final long serialVersionUID = 1L;

	private int ss_id;
	private String ss_num;
	private String title;
	private String img;
	public int getSs_id() {
		return ss_id;
	}
	public void setSs_id(int ss_id) {
		this.ss_id = ss_id;
	}
	public String getSs_num() {
		return ss_num;
	}
	public void setSs_num(String ss_num) {
		this.ss_num = ss_num;
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
		return "Suxiu [ss_id=" + ss_id + ", ss_num=" + ss_num + ", title="
				+ title + ", img=" + img + "]";
	}
	public Suxiu(int ss_id, String ss_num, String title, String img) {
		super();
		this.ss_id = ss_id;
		this.ss_num = ss_num;
		this.title = title;
		this.img = img;
	}
	public Suxiu() {
		super();
	}




}
