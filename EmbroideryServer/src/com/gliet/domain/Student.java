package com.gliet.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/9.
 */

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    private int s_id;
    private String s_num;
    private String s_name;
    private String s_age;
    private String s_sex;
    private String s_department;
    private String s_pwd;
    private String s_permitborrowtime;
    
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
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_age() {
		return s_age;
	}
	public void setS_age(String s_age) {
		this.s_age = s_age;
	}
	public String getS_sex() {
		return s_sex;
	}
	public void setS_sex(String s_sex) {
		this.s_sex = s_sex;
	}
	public String getS_department() {
		return s_department;
	}
	public void setS_department(String s_department) {
		this.s_department = s_department;
	}
	public String getS_pwd() {
		return s_pwd;
	}
	public void setS_pwd(String s_pwd) {
		this.s_pwd = s_pwd;
	}
	public String getS_permitborrowtime() {
		return s_permitborrowtime;
	}
	public void setS_permitborrowtime(String s_permitborrowtime) {
		this.s_permitborrowtime = s_permitborrowtime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Student() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public Student(int s_id, String s_num, String s_name, String s_age,
			String s_sex, String s_department, String s_pwd,
			String s_permitborrowtime) {
		super();
		this.s_id = s_id;
		this.s_num = s_num;
		this.s_name = s_name;
		this.s_age = s_age;
		this.s_sex = s_sex;
		this.s_department = s_department;
		this.s_pwd = s_pwd;
		this.s_permitborrowtime = s_permitborrowtime;
	}
	
	@Override
	public String toString() {
		return "Student [s_id=" + s_id + ", s_num=" + s_num + ", s_name="
				+ s_name + ", s_age=" + s_age + ", s_sex=" + s_sex
				+ ", s_department=" + s_department + ", s_pwd=" + s_pwd
				+ ", s_permitborrowtime=" + s_permitborrowtime + "]";
	}

    
    

}
