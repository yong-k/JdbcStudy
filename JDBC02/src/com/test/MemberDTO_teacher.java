/*====================
	MemberDTO.java
=====================*/

package com.test;

public class MemberDTO_teacher
{
	// 지금 상황에서는 sid를 int, String 중 뭘로 만들어도 상관 없음
	// 지금은 고작 가져와서 출력해주는 상황이니까
	// 연산이 필요하면 int 로 하면되지만, 지금은 연산 필요없으니까
	// String 으로 해도 상관없음
	
	// private 으로 선언해놔서 이대로 놔주면 아무도 사용못함
	// 그래서 만들어주는게 → getter/setter
	// 만들고 싶으면 만들고, 아니면 안 만드는 게 아니라 개발자들의 약속이다.
	// 규칙 잘 지켜서 만들어야 한다.
	
	// 다 private 으로 만들어놓으면 이클립스도 getter/setter 필요하다고 생각함
	// source > generate getters and setters...
	// 어떤 속성을 getter/setter 만들건지 물어봄
	
	private String sid, name, tel;

	// getter / setter 구성
	public String getSid()
	{
		return sid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}
	
	
	
	
	
	
}
