package com.test;

// 속성만 존재 getter, setter
public class MemberDTO
{
	private String sid, name, tel;
	
	String getSid()
	{
		return sid;
	}
	
	void setSid(String sid)
	{
		this.sid = sid;
	}
	
	String getName()
	{
		return name;
	}
	
	void setName(String name)
	{
		this.name = name;
	}
	
	String getTel()
	{
		return tel;
	}
	
	void setTel(String tel)
	{
		this.tel = tel;
	}
}
