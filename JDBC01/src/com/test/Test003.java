/*=============================
 	Test003.java
 	- 데이터베이스 연결 실습
 	- 데이터 입력 실습
==============================*/

package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test003
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		// 연결 객체 생성(구성)
		Connection conn = DBConn.getConnection();
		// 예외 throws 처리함

		
//		if (conn == null) 
//		{ 
//			System.out.println("데이터베이스 연결 실패~!!!"); 
//		}
//	
//		System.out.println("데이터베이스 연결 성공~!!!");



		// 코드 이렇게 짜버리면
		// 코드가 null 이더라도 데이터베이스 연결 실패가 아니라 성공도 뜸

		// 그래서 아래와 같이 짜야함

		if (conn == null)
		{
			System.out.println("데이터베이스 연결 실패~!!!");
			System.exit(0);
		}

		//System.out.println("데이터베이스 연결 성공~!!!");
		
		try
		{
			// 작업 객체 구성(준비)
			Statement stmt = conn.createStatement();
			//			     ---- ----------------
			//              연결객체    작업객체
			// 작업객체를 connection 에 매달아서 쓰고 있음
			// 선생님 그림 생각하기
			// 연결객체 와이어에 작업객체(바구니) 매달아서 밖(java)에서 탄광(oracle)쪽으로 내려보낸거
			
			// ※ 데이터 입력 쿼리 실행 과정
			//    한 번 실행하면 다시 실행하지 못하는 상황이다.
			//    기본키 제약조건이 설정되어 있으므로
			//    동일한 키 값이 중복될 수 없기 때문이다.
			
			// 쿼리문 구성(준비)
			String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(2, '김정용', '010-2731-3153')";
			// 탄광(DB)에 있는 광부들에게 작업객체(바구니) 안에 쪽지[이번엔 석탄 좀 넣어줘] 넣어서 보낸거
			//-- 주의. 쿼리문 끝에 『;』 붙이지 않는다.
			//-- 주의. 자바에서 실행한 DML 구문은 내부적으로 자동 COMMIT 된다.
			//-- 주의. 오라클에서 트랜잭션 처리가 끝나지 않은 상태에서 자바로 처리하게 되면
			//         데이터 액션 처리가 이루어지지 않는다.
			//         (자바에서 insert, update, delete 날려도 실행되지 않는다.)
			//         오라클에서 트랜잭션 처리가 끝나지 않았다는 뜻은 
			//         → sqldeveloper에서 DML 구문 작성하고 commit 안 한 경우
			//→ 수레에 쪽지를 실은거지, 아직 보낸 건 아님
			
			// 아래 구문이 직접 쪽지 보낸 거!
			int result = stmt.executeUpdate(sql);
			//	         ---- --------------
			//          작업객체   execute: 실행
			//-- 적용된 행의 개수 반환
			
			// 우리가 대표적으로 자주 쓰는건
			// stmt.executeQuery()  : select 실행한다고 해서 오라클 내부적으로 
			//                        뭔가 바뀌지는 않음 → executeQuery()
			//						  → ResultSet 객체의 값을 반환						  
			// stmt.executeUpdate() : 'insert, update, delete 처럼 전달됐을 때
			//                         오라클 내부에서 뭔가 바뀐다' 하면 executeUpdate()
			//						   → 적용된 행의 개수를 반환한다.
			//   ex) insert 쿼리 하나 넘김 → return 값 = 1
			//       update 쿼리 where 절 없이 넘김(테이블의 전체 행 2개) → return 값 = 2
			
			
			// 그래서 적용된 행의 개수 반환값이 > 0  이면   크면 데이터 입력 성공
			//                                  <= 0 이면 작으면 데이터 입력 실패
			if (result > 0)
			{
				System.out.println("데이터 입력 성공~!!!");
			}
			else 
			{
				System.out.println("입력 실패~ ㅠ_ㅠ");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		DBConn.close();
		//-- 리소스 반납(연결 종료)
		
	}
}


// 실행 결과
/*
데이터 입력 성공~!!!
--> 한 번 밖에 실행 못함
-- 또 실행하면
-- 에러 발생
-- (java.sql.SQLIntegrityConstraintViolationException: ORA-00001: unique constraint (SCOTT.MEMBER_SID_PK) violated)
*/


/*
 	++) 검색
  	자바 try ~ catch / try with resources
  	
  	자바를 이용해서 외부 자원에 접근하는 경우에 주의해야 할 점은
  	외부 자원을 사용한 뒤 반납을 해줘야 한다는 점이다.
  	
1. 자원 반납과 finally
  1-1) 자원반납 문제
  	
  		변수1 = new 자원객체1();	//-- 자원 할당
  		변수1.작업();
  		변수1.close();				//-- 자원 반납
  	
  	  	→ 위 코드에서 작업() 메소드가 에러없이 실행된다면, 
  	  	   close() 메소드가 정상적으로 작동해서 자원을 반납한다.   
  	  	→ 하지만, 작업() 메소드에서 에러가 발생한다면,
  	  	   close() 메소드가 실행되지 않아서 자원을 반납하지 못하는 문제가 생긴다.
  	  	    
  	  	     
  1-2) finally 블럭에서 자원 반납
  	
  	   	변수1 = null;
  	   	변수2 = null;
  	   	try {
	  	   	변수1 = new 자원객체1();
	  	   	변수2 = new 자원객체2();
	  	   	변수1.작업();
	  	   	변수2.작업();
  	   	 	...
  	   	} finally {
	  	   	if (변수1 != null) 변수1.close();
	  	   	if (변수2 != null) 변수2.close();
  	   	}
  	   	 
  	   	→ 위처럼 finally{} 안에 close() 메소드를 쓰면, 
  	   	   에러와 관계없이 항상 실행되기 때문에 자원 반납이 가능해진다.  	
*/



