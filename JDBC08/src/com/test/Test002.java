/*======================================================
	Test002.java
	- CallableStatement 를 활용한 SQL 구문 전송 실습
=======================================================*/

package com.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

public class Test002
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
					// 쿼리문 준비 check~!!!
					String sql = "{call PRC_MEMBERSELECT(?)}";
					
					// CallableStatement 작업 객체 생성 check~!!!
					CallableStatement cstmt = conn.prepareCall(sql);
					
					// check~!!!
					// 프로시저 내부에서 SYS_REFCURSOR 를 사용하고 있기 때문에
					// OracleTypes.CURSOR 를 사용하기 위한 등록 과정이 필요한 상황이다.
					// 1. Project Explorer 상에서 해당 프로젝트 마우스 우클릭
					//    > Build Path > Configure Build Path > Libraries 탭 선택
					//	  > 우측 Add External JAR 버튼 클릭 → 『ojdbc6.jar』 파일 추가등록
					//    									   (외부 jar 파일 연결)
					// 2. 『import oracle.jdbc.OracleTypes;』 구문 추가 등록
					
					// registerOutParameter() 메소드는 SQL 타입을 사용
					// 그래서 OracleTypes의 CURSOR 사용
					cstmt.registerOutParameter(1, OracleTypes.CURSOR);
					
					// 그냥 select 구문이라
					// execute() 를 하던지, executeQuery() 를 하던지 상관 없음
					// 지금까지 작성해온 것들 executeUpdate(), executeQuery() 만 사용했지만,
					// 그냥 execute() 도 사용가능하다.
					// 근데, 그냥 execute() 는 이거 DML 구문이냐, select 구문이냐
					// 따로 확인해서 처리해줘야해서 사용 안 했던 것,,
					
					// CURSOR 로 바인딩 해 온 건 Object 형식으로 넘겨받음
					cstmt.execute();
					//ResultSet rs = cstmt.getObject(1);
					//-- 이렇게 하면 에러 발생
					ResultSet rs = (ResultSet)cstmt.getObject(1);
					//-- Type은 Object 지만, ResultSet 이다.
					//   Object 타입을 ResultSet으로 다운캐스팅
					
					while (rs.next())
					{
						String sid = rs.getString("SID");
						String name = rs.getString("NAME");
						String tel = rs.getString("TEL");
						
						String str = String.format("%3s %7s %10s", sid, name, tel);
						
						System.out.println(str);
					}
					
					rs.close();
					cstmt.close();
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
			DBConn.close();
			
			System.out.println("\n데이터베이스 연결 닫힘~!!!");
			System.out.println("프로그램 종료됨~!!!");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}


// 실행 결과
/*
데이터베이스 연결 성공~!!!
  1     이호석 010-1111-1111
  2     서민지 010-2222-2222
  3     이연주 010-3333-3333
  4     홍은혜 010-4444-4444
  5     김태형 010-5555-5555

데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!

*/