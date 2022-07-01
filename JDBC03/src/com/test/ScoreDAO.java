package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connect()
	{
		conn = DBConn.getConnection();
		
		return conn;
	}
	
	// 입력
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '%s', %s, %s, %s)"
								  , dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// 인원 수 계산 
	public int count() throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 총점 계산
	public int calTot(int kor, int eng, int mat)
	{
		int total = kor + eng + mat;
		
		return total;
	}
	
	// 평균 계산
	public double calAvg(int kor, int eng, int mat)
	{
		double avg = calTot(kor, eng, mat) / 3.0;  
				
		return avg;
	}
	
	// 전체 조회
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT SID, NAME, KOR, ENG, MAT FROM TBL_SCORE ORDER BY SID";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getInt("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			
			dto.setTot(calTot(dto.getKor(), dto.getEng(), dto.getMat()));
			dto.setAvg(calAvg(dto.getKor(), dto.getEng(), dto.getMat()));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
}
