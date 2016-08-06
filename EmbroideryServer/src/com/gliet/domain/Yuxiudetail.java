package com.gliet.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/9.
 */
public class Yuxiudetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String num;
    private String img1;
    private String detail1;
    private String img2;
    private String detail2;
    private String img3;
    private String detail3;
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
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getDetail1() {
		return detail1;
	}
	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getDetail2() {
		return detail2;
	}
	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getDetail3() {
		return detail3;
	}
	public void setDetail3(String detail3) {
		this.detail3 = detail3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Appreciationdetail [id=" + id + ", num=" + num + ", img1="
				+ img1 + ", detail1=" + detail1 + ", img2=" + img2
				+ ", detail2=" + detail2 + ", img3=" + img3 + ", detail3="
				+ detail3 + "]";
	}
	public Yuxiudetail(int id, String num, String img1, String detail1,
			String img2, String detail2, String img3, String detail3) {
		super();
		this.id = id;
		this.num = num;
		this.img1 = img1;
		this.detail1 = detail1;
		this.img2 = img2;
		this.detail2 = detail2;
		this.img3 = img3;
		this.detail3 = detail3;
	}
	public Yuxiudetail() {
		super();
	}
	
    
    
	
	
}