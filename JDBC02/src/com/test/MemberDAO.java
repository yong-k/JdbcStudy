package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

// action 처리하는 애들만
// 필요 기능 : 데이터입력, 전체조회, 인원수확인 등등
public class MemberDAO
{
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection() throws ClassNotFoundException, SQLException 
	{
		conn = DBConn.getConnection();
		
		return conn;
	}
	 
	// 데이터 입력
	void inputData() throws ClassNotFoundException, SQLException
	{
		Scanner sc = new Scanner(System.in);
		
		do
		{
			System.out.printf("이름 전화번호 입력(%d) : ", countAll()+1);
			
			String name = sc.next();
			
			if (name.equals("."))
				break;

			String tel = sc.next();
					
			Statement stmt = conn.createStatement();

			String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES (%s, '%s', '%s')", countAll()+1, name, tel);

			int result = stmt.executeUpdate(sql);

			if (result > 0)
				System.out.println(">> 회원 정보 입력 완료~!!!");
			
			
		} while (true);
		
		sc.close();
		
	}
	
	// 인원 수 확인
	int countAll() throws ClassNotFoundException, SQLException
	{	
		int count = 0;
		
		if (conn != null)
		{
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next())
				count = rs.getInt("COUNT");
			
			rs.close();
			stmt.close();
		}
			
		return count;
	}
	
	// 전체 조회
	void printAll() throws ClassNotFoundException, SQLException
	{
		
		if (conn != null)
		{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println();
			System.out.println("--------------------------------");
			System.out.printf("전체 회원 수 : %d명\n", countAll());
			System.out.println("--------------------------------");
			System.out.println(" 번호	  이름	   전화번호");
			while (rs.next())
			{
				String sid = rs.getString("SID");
				String name = rs.getString("NAME");
				String tel = rs.getString("TEL");
				
				String str = String.format("%3s %8s %14s", sid, name, tel);
				
				System.out.println(str);
			}
			System.out.println("--------------------------------");
			
			rs.close();
			stmt.close();
		}
		
	}
	
	// 데이터베이스 연결 종료
	public void disconnection() throws SQLException
	{
		DBConn.close();
	}
	
}










