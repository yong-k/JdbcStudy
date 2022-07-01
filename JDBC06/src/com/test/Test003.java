/*========================
	Test003.java
	- 쿼리문 전송 실습
=========================*/

package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class Test003
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
					// 쿼리문 준비
					String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
					
					// 작업 객체 생성(쿼리문 전달)
					PreparedStatement pstmt = conn.prepareStatement(sql);
					
					ResultSet rs = pstmt.executeQuery();
					
					while (rs.next())
					{
						int sid = rs.getInt("SID");
						String name = rs.getString("NAME");
						String tel = rs.getString("TEL");
						
						String str = String.format("%3d %7s %10s", sid, name, tel);
						
						System.out.println(str);
					}
					
					rs.close();
					pstmt.close();
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		DBConn.close();
		
		System.out.println("\n데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료됨~!!!");
		
	}
}
