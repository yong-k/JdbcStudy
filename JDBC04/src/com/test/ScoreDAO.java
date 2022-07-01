/*=======================================
	ScoreDAO.java
	- 데이터베이스 액션 처리 전용 객체
========================================*/

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 속성 구성
	private Connection conn;
	
	// 데이터베이스 연결 담당 메소드
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		
		return conn;
	}
	
	// 데이터 입력 담당 메소드
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		// %d 말고 '%d'로 쓰면 오라클한테는 문자로 저장하라는 뜻이 된다.
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
								  , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		result = stmt.executeUpdate(sql);
		stmt.close(); 
		return result;
	}
	
	// 전체 리스트 출력 담당 메소드
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		Statement stmt = conn.createStatement();
		
		// sql 문장 개행하는 tip → 컴마 앞에서 하기. 이클립스가 알아서 공백, + 넣어줌
		String sql = "SELECT SID, NAME, KOR, ENG, MAT"
				+ ", (KOR + ENG + MAT) AS TOT"
				+ ", (KOR + ENG + MAT)/3 AS AVG"
				+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
				+ " FROM TBL_SCORE"
				+ " ORDER BY SID ASC";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 이름 검색 담당 메소드
	// → 전체 리스트 출력 담당 메소드 오버로딩
	// return 타입 그냥 ScoreDTO 로 바꾸는 순간, 이건 메소드 오버로딩이 아니게 된다.
	public ArrayList<ScoreDTO> lists(String name) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
								+ " FROM"
								+ " (SELECT SID, NAME, KOR, ENG, MAT"
								+ ", (KOR + ENG + MAT) AS TOT"
								+ ", (KOR + ENG + MAT)/3 AS AVG"
								+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
								+ " FROM TBL_SCORE ORDER BY SID ASC)"
								+ " WHERE NAME = '%s'", name);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			// ★★★ 잊지 말기 ★★★ → 객체 생성!
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}	
		rs.close();
		stmt.close();
		
		return result;
	}
	 
	// 번호 검색 담당 메소드
	// 번호로 검색하면 동명이인 나올 일 없지만
	// 오버로딩으로 처리해보기로 했으니 반환값 ArrayList<ScoreDTO>
	public ArrayList<ScoreDTO> lists(int sid) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
								+ " FROM"
								+ " (SELECT SID, NAME, KOR, ENG, MAT"
								+ ", (KOR + ENG + MAT) AS TOT"
								+ ", (KOR + ENG + MAT)/3 AS AVG"
								+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
								+ " FROM TBL_SCORE ORDER BY SID ASC)"
								+ " WHERE SID = %d", sid);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		// 여기는 while 대신 if 로 바꿔도 상관 없음
		// 어차피 레코드 한 개니까
		while (rs.next())
		{
			// ★★★ 잊지 말기 ★★★ → 객체 생성!
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			dto.setTot(rs.getInt("TOT"));
			dto.setAvg(rs.getDouble("AVG"));
			dto.setRank(rs.getInt("RANK"));
			
			result.add(dto);
		}	
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 인원 수 확인 담당 메소드
	public int count() throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		// 단일 값이니까 if 로 바꿔도 됨
//		if (rs.next())
//			result = rs.getInt(1);
		while (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 데이터 수정 담당 메소드 → 매개변수의 유형 check~!!!
	// ★ 수정 메소드에 넘겨줄 매개변수 → int sid (Ⅹ)
	// 걔를 찾는 업무와 변경하는 업무가 따로
	// 걔를 찾는 업무 → 번호 검색 담당 메소드 만들었음
	// ★★ 변경하는  업무 → 걔가 갖고 있는 속성들을 다 바꾸는 거 !! ★★
	// → 바꿀 대상의 식별자를 매개변수로 받는게 아니라, 대상 자체를 다 받아야 한다.
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		// getSid() 는 String 을 반환함
		// 홑따옴표 없이 %s로 주면 내부적으로는 문자열로 처리하고 있지만
		// 오라클은 숫자로 인식함
		String sql = String.format("UPDATE TBL_SCORE"
				+ " SET NAME = '%s', KOR = %d, ENG = %d, MAT = %d"
				+ " WHERE SID = %s"
				, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat(), dto.getSid());
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// 데이터 삭제 담당 메소드
	// sid → 자바에서는 편하게 String으로 처리하고 있지만,
	//        실제 내용은 숫자들로 구성되어 있기 때문에 int 로 처리함
	public int remove(int sid) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("DELETE FROM TBL_SCORE WHERE SID = %d", sid);
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// 데이터베이스 연결 종료 담당 메소드
	public void close() throws SQLException
	{
		DBConn.close();
	}
}


