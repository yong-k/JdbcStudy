/*========================
	Test001.java
	- 쿼리문 전송 실습
=========================*/

package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.util.DBConn;

public class Test001
{
	public static void main(String[] args)
	{
		try
		{
			Connection conn = DBConn.getConnection();
			
			if (conn != null)
			{
				System.out.println("데이터베이스 연결 성공~!!!");
				
				try
				{
					/*
					Statement stmt = conn.createStatement();
					
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
							  + " VALUES(MEMBERSEQ.NEXTVAL, '한충희', '010-5555-5555')";
					
					int result = stmt.executeUpdate(sql);
					
					if (result > 0)
						System.out.println("데이터 입력 성공~!!!");
					
					stmt.close();
					DBConn.close();
					*/
					
					// PreparedStatement 경우에는
					// 작업개체를 생성하기 전에 먼저 쿼리문이 준비되어 있어야 한다.
					// 아예 다 값이 채워진 쿼리문이 아니라
					// 나중에 매개변수로 값 건네줄거기 때문에, 틀만 잡아놓으면 됨
					// PreparedStatement 에게 넘겨주면
					// '아 얘가 나중에 값 넘겨줄거니까 일단 공간 만들어달라는거구나'라고 파악
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
							  + " VALUES(MEMBERSEQ.NEXTVAL, ?, ?)";
					//		  + " VALUES(MEMBERSEQ.NEXTVAL, '?', '?')";		--(Ⅹ)
					//		                                --------
					//										이렇게 하면 안된다!!!
					// PreparedStatement 로 넘겨줄때는 '' 붙이면 안된다.
					// 타입은 아래 [ pstmt.set~~ ] 여기서 결정된다.
					// setString() 이냐, setInt() 냐, setDouble() 이냐 등등...
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					// IN 매개변수 넘겨주기
					pstmt.setString(1, "박현수");
					// 첫번째로 나온 물음표에 → "박현수" 라는 값 넣어줘
					pstmt.setString(2, "010-6666-6666");
					// 두번째로 나온 물음표에 → "010-6666-6666" 라는 값 넣어줘
					
					// insert, update, delete → executeUpdate();
					// select				  → executeQuery();
					// 로 똑같고, 반환타입도 똑같은데
					// 이미 앞에서 쿼리문 건네줬으니까, 
					// 여기서는 쿼리문 건네주지 않는 것만 다름!!
					int result = pstmt.executeUpdate();
					
					if (result > 0)
						System.out.println("데이터 입력 성공~!!!");
					
					pstmt.close();
					DBConn.close();
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
