package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 전체 학생 수 조회
	public int stdCount() throws SQLException
	{
		int result = 0;
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
			result = rs.getInt("COUNT");

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	// 검색 학생 수 조회
	public int stdCount(String key, String value) throws SQLException
	{
		int result = 0;
		String sql = "SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE ? = ?"; 
				
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, key);
		if (key.equals("NAME"))
			pstmt.setString(2, value);
		else
			pstmt.setInt(2, Integer.parseInt(value));
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		pstmt.close();
		
		return result;
	}

	// 1. 성적 정보 입력
	public int add(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
				  + " VALUES(SCORESEQ.NEXTVAL, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getName());
		pstmt.setInt(2, dto.getKor());
		pstmt.setInt(3, dto.getEng());
		pstmt.setInt(4, dto.getMat());
		
		result = pstmt.executeUpdate();
		pstmt.close();
		
		return result;
	}
	
	// 2. 성적 전체 조회 (1.학번  2.이름  3.석차)
	public ArrayList<ScoreDTO> lists(String key) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
				  + " FROM VIEW_SCORE ORDER BY ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getInt("SID"));
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
		pstmt.close();
		
		return result;
	}
	
	// 3. 성적 검색 조회 (1.학번  2.이름  3.석차)
	public ArrayList<ScoreDTO> searchLists(String key, String value) throws SQLException
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
				  + " FROM VIEW_SCORE WHERE ? = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		if (key.equals("NAME"))
			pstmt.setString(2, value);
		else
			pstmt.setInt(2, Integer.parseInt(value));
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getInt("SID"));
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
		pstmt.close();
		
		return result;
	}
	
	// 4. 성적 정보 수정
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "UPDATE TBL_SCORE SET NAME = ?, kor = ?, eng = ?, mat = ?"
				  + " WHERE SID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getName());
		pstmt.setInt(2, dto.getKor());
		pstmt.setInt(3, dto.getEng());
		pstmt.setInt(4, dto.getMat());
		pstmt.setInt(5, dto.getSid());
		
		result = pstmt.executeUpdate();
		pstmt.close();
		
		return result;
	}
	
	// 5. 성적 정보 삭제
	public int remove(int sid) throws SQLException
	{
		int result = 0;
		String sql = "DELETE TBL_SCORE WHERE SID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, sid);
		
		result = pstmt.executeUpdate(); 
		pstmt.close();
		
		return result;
	}
	
}
