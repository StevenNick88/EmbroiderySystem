package com.gliet.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/9.
 */
public class Informationdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String num;
    private String img1;
    private String detail1;
    private String img2;
    private String detail2;
    private String img3;
    private String detail3;
    private String img4;
    private String detail4;
    private String img5;
    private String detail5;
    private String img6;
    private String detail6;
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
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getDetail4() {
		return detail4;
	}
	public void setDetail4(String detail4) {
		this.detail4 = detail4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getDetail5() {
		return detail5;
	}
	public void setDetail5(String detail5) {
		this.detail5 = detail5;
	}
	public String getImg6() {
		return img6;
	}
	public void setImg6(String img6) {
		this.img6 = img6;
	}
	public String getDetail6() {
		return detail6;
	}
	public void setDetail6(String detail6) {
		this.detail6 = detail6;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Informationdetail [id=" + id + ", num=" + num + ", img1="
				+ img1 + ", detail1=" + detail1 + ", img2=" + img2
				+ ", detail2=" + detail2 + ", img3=" + img3 + ", detail3="
				+ detail3 + ", img4=" + img4 + ", detail4=" + detail4
				+ ", img5=" + img5 + ", detail5=" + detail5 + ", img6=" + img6
				+ ", detail6=" + detail6 + "]";
	}
	public Informationdetail(int id, String num, String img1, String detail1,
			String img2, String detail2, String img3, String detail3,
			String img4, String detail4, String img5, String detail5,
			String img6, String detail6) {
		super();
		this.id = id;
		this.num = num;
		this.img1 = img1;
		this.detail1 = detail1;
		this.img2 = img2;
		this.detail2 = detail2;
		this.img3 = img3;
		this.detail3 = detail3;
		this.img4 = img4;
		this.detail4 = detail4;
		this.img5 = img5;
		this.detail5 = detail5;
		this.img6 = img6;
		this.detail6 = detail6;
	}
	public Informationdetail() {
		super();
	}
    
   
    
    
	
	
}