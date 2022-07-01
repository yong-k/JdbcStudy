package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;


//★★★ sqlDeveloper 에서는 쿼리문 작성하고 테스트 했으면
//       마지막에 꼭 COMMIT; 또는 ROLLBACK; 하고 넘어오기  ★★★

public class ScoreDAO
{
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// INSERT 실행할 때, 띄워 줄 학번(SID) 값 조회
	public int nextSid() throws SQLException
	{
		int nextSid = 0;
		
		// ★ 시퀀스 다음 값 조회 구문 ★
		String sql = "SELECT LAST_NUMBER AS NEXTSID"
				  + " FROM USER_SEQUENCES"
				  + " WHERE SEQUENCE_NAME = 'SCORESEQ'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			nextSid = rs.getInt("NEXTSID");
			
		return nextSid;
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
	// ★★★ 구문 잘못 작성해서 에러났었음 ★★★ 
	public int stdCount(String key, String value) throws SQLException
	{
		/* 처음에 작성했다가 에러 발생한 구문
		String sql = "SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE ? = ?"; 				
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		*/
		
		int result = 0;
		String sql = "SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE " + key + " = ?"; 
				
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		if (key.equals("NAME"))
			pstmt.setString(1, value);
		else
			pstmt.setInt(1, Integer.parseInt(value));
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		pstmt.close();
		
		return result;
	}

	// 1. 성적 정보 입력
	// ★★★ 구문 잘못 작성해서 에러났었음 ★★★
	// ★★★ INSERT 구문에서 SEQUENCE 사용할 때, 『시퀀스명.NEXTVAL』 꼭 하기,,,,,,,★★★
	public int add(ScoreDTO dto) throws SQLException
	{
		/* 처음에 작성했다가 에러 발생한 구문
		String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
				  + " VALUES(SCORESEQ, ?, ?, ?, ?)";
		*/
		
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
	// ★★★ 구문 잘못 작성해서 에러났었음 ★★★
	public ArrayList<ScoreDTO> lists(String key) throws SQLException
	{
		/* 처음에 작성했다가 에러 발생한 구문
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
				  + " FROM VIEW_SCORE ORDER BY ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		
		의도: key 값 사용자한테 SID, NAME, RANK 입력받아서 『?』 자리에 넣으려고 했음
		      → But, 
		         PreparedStatement 객체로 pstmt.setString(); 해서 값 넣으면 '' 형태로 들어감
		         내가 원했던 건   : ORDER BY NAME;   이었지만
		         실제로 들어간 건 : ORDER BY 'NAME';
		         그래서 실행 안됐고, 아래와 같이 코드 바꿈
		 
		*/
		
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
				  + " FROM VIEW_SCORE ORDER BY " + key;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
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
		String sql = ("SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
				  + " FROM VIEW_SCORE WHERE " + key + " = ?");
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (key.equals("NAME"))
			pstmt.setString(1, value);
		else
			pstmt.setInt(1, Integer.parseInt(value));
		
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
	// ★★★ 구문 잘못 작성해서 에러났었음 ★★★
	public int modify(ScoreDTO dto) throws SQLException
	{
		int result = 0;
		String sql = "UPDATE TBL_SCORE"
				  + " SET NAME = ?, KOR = ?, ENG = ?, MAT = ?"
				  + " WHERE SID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getName());
		pstmt.setInt(2, dto.getKor());
		pstmt.setInt(3, dto.getEng());
		pstmt.setInt(4, dto.getMat());
		
		// 이거 작성 안해서 에러 발생했음
		pstmt.setInt(5, dto.getSid());	// ★★★
		
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
