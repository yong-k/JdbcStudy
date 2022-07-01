/*====================================================
 	Test005.java
 	- 테이블 내의 데이터 읽어오기 (select 쿼리문)
=====================================================*/

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test005
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Connection conn = DBConn.getConnection();
		
		if (conn != null)
		{
			System.out.println("데이터베이스 연결 성공~!!!");
			
			try
			{
				// 작업 객체 생성
				Statement stmt = conn.createStatement();
				
				// 쿼리문 준비(select)
				// !! mySQL 들어가기 전까지는 sqldeveloper에서 작성한 쿼리문
				//   이상 있는지 없는지 test 해 보고 사용한다. !!
				//-- sqldeveloper 로 가서 test 진행하고 데려옴
				String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
				/*
				한 줄 말고 여러 줄 형식으로 쿼리문 작성하려면
				→ 꼭 『공백』도 신경써서 해줘야 한다. 
				
				String sql = "SELECT SID, NAME, TEL"
							 + " FROM TBL_MEMBER"
							 + " ORDER BY SID"
				*/
				// ※ 쿼리문을 구성하는 과정에서 공백이나 개행 처리 CHECK~!!!
				
				// ※executeQuery() 메소드를 사용하면
				//   질의 결과를 ResultSet 객체로 가져올 수 있다.
				//   하지만, ResultSet 객체가 질의에 대한 결과물 모두를
				//   한꺼번에 갖고있는 구조는 아니다.
				//   단지, 데이터베이스로부터 획득한 질의 결과물에 대한
				//   관리가 가능한 상태가 되는 것이다.
				//   이 때문에 데이터베이스와의 연결을 끊게 되면
				//   ResultSet 객체는 더 이상 질의 결과를 관리할 수 없게 된다.
				
				// 쿼리문 실행
				ResultSet rs = stmt.executeQuery(sql);
				// executeUpdate() 는 적용/변경된 쿼리문 개수 반환해줌
				// select 를 날린 이유는 뭘까? 
				// → 뭔가 데이터를 얻어내려고 / 안에 값 확인하려고
				// 그래서
				// executeQuery() 는 결과집합을 반환함
				// ResultSet 이라는 결과집합을 받는 객체 있음
				
				//DBConn.close(); 
				//→ 해버리면 ResultSet 객체는 더 이상 질의 결과를 관리할 수 없게 된다.
				
				// ResultSet 에 대한 처리 (반복문 구성)
				// select 의 반환값이 단일행이더라도 
				// ResultSet의 다음 값이 있는지 없는지 확인해줘야 한다.
				while (rs.next())
				{
					// 타입이 int면 getInt(), double이면 getDouble()...해주면 됨
					String sid = rs.getString("SID");
					String name = rs.getString("NAME");
					String tel = rs.getString("TEL");
					
					// format 과 같은 형태로 문자열 반환해서 str에 담아줌
					String str = String.format("%3s %8s %12s", sid, name, tel);
					
					System.out.println(str);
				}
				// ResultSet 리소스 반납
				rs.close();
				
				// Statement 리소스 반납
				stmt.close();
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		else 
		{
			System.out.println("데이터베이스 연결 실패~!!!");
		}
		
		DBConn.close();
		
		System.out.println("데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료됨~!!!");
		
	}
}

// ResultSet이나 Statement 리소스도 Scanner 처럼 쓰고 close 안해도 크게 상관은 없지만,,!
// ResultSet, Statement, DBConnection 은 
// 열고 닫고, 또 다음에 쓰려면 또 열어줘야 함 = 커서와 같은 개념
// scanner 와 달리 일반적으로 쓰면 닫아줌!!




// 실행 결과
/*
데이터베이스 연결 성공~!!!
  1      홍길동 010-1111-1111
  2      김정용 010-2731-3153
  3      김정용 010-3333-3333
  4      오이삭 010-4444-4444
  5      김태형 010-5555-5555
데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!
*/

//--> TBL_MEMBER 테이블의 내용을 SELECT 한 결과
