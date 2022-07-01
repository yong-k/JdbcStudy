/*====================================
	ScoreDTO.java
	- 데이터 보관 및 전송 전용 객체
=====================================*/

package com.test;

public class ScoreDTO
{
	// 주요 속성 구성
	private String sid, name;		//-- 번호, 이름
	private int kor, eng, mat;		//-- 국어점수, 영어점수, 수학점수
	private int tot, rank;			//-- 총점, 석차
	private double avg;				//-- 평균
	
	// 테이블에는 총점, 석차, 평균이 없지만,
	// 우리는 값 처리하면서 총점, 석차, 평균 저장해주고 돌려주고 해야하므로
	// 테이블의 레코드 구조를 그대로 띄게 만든게 아니라
	// 우리가 처리해야 하는 업무 속성을 갖게 만들었음
	
	// sid는 int로 해도 되지만, String 으로 하는게 편하니까
	// 일단 String으로 하자
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
	public int getKor()
	{
		return kor;
	}
	public void setKor(int kor)
	{
		this.kor = kor;
	}
	public int getEng()
	{
		return eng;
	}
	public void setEng(int eng)
	{
		this.eng = eng;
	}
	public int getMat()
	{
		return mat;
	}
	public void setMat(int mat)
	{
		this.mat = mat;
	}
	public int getTot()
	{
		return tot;
	}
	public void setTot(int tot)
	{
		this.tot = tot;
	}
	public int getRank()
	{
		return rank;
	}
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	public double getAvg()
	{
		return avg;
	}
	public void setAvg(double avg)
	{
		this.avg = avg;
	}
	
}

