/*====================
	MemberDAO.java
=====================*/
// data 에 access 할 거 새로 생기면 / 제거해야 할 거 있으면 
// DAO 만 수정해주면 됨

// 필요 기능
// 데이터베이스에 액세스 하는 기능
// → DBConn 활용(전담 계층) 

// 데이터를 입력하는 기능 → insert

// 인원 수 확인하는 기능
// 즉, 대상 테이블(TBL_MEMBER)의 레코드 카운팅 기능 → select 

// 전체 리스트를 조회하는 기능
// 즉, 대상 테이블(TBL_MEMBER)의 데이터를 조회하는 기능 → select 


package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO_teacher
{
	// 주요 속성 구성 → DB 연결 객체
	private Connection conn;
	// 내부에서만 쓸 거면 getter/setter 굳이 만들 필요 없음

	// getter / setter 구성
/*	public Connection getConn()
	{
		return conn;
	}

	public void setConn(Connection conn)
	{
		this.conn = conn;
	}
*/	
	// MemberDAO에 있는 모든 메소드가 connection 필요하면
	// connect() 하는 메소드 따로 만들 수도 있고,
	// 생성자에 아예 connection 추가할 수도 있음 
	// 등등 여러 가지 방법이 있을 거임
	
	// 생성자 정의(사용자 정의 생성자)
	public MemberDAO_teacher() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		// 클래스 안에서는 이거 못하니까,
		// 생성자 안에서 하려고 이 생성자 만든거임
	}
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	//public add(String name, String tel) {}
	//-- 이렇게 하면 MemberDTO 만든 의미가 없음!!!
	
	// ※ 이 틀을 한 번 잡아놓으면 계속 사용할거고,
	//    이거 외우면 이제 JDBC가 따분해질거임
	// executeUpdate() 가 적용/수행한 쿼리문의 개수를 반환해주므로
	// int 를 반환값으로 해두면, 잘 수행됐나 확인 가능하다.
	//     ===
	public int add(MemberDTO_teacher dto) throws SQLException
	{
		// 반환할 결과값을 담아낼 변수(적용된 행의 개수)
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
								+ " VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')", dto.getName(), dto.getTel());
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		result = stmt.executeUpdate(sql);
				
		// 사용한 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환 → 적용된 행의 개수(int)
		return result;
		
	}//end add()
	
	
	// ※ 메소드를 호출한 곳에서 뭘 매개변수로 넘겨줄 지 고민하는건
	//    매우매우 중요한 일
	//    여러 각도로 많이 생각해야 한다.
	
	// 메소드 정의 → 전체 인원 수 확인하는 기능 → select
	public int count() throws SQLException
	{
		// 결과값으로 반환하게 될 변수 선언 및 초기화
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER"; 
		// ※ 컬럼명에 * (특수문자) 있고 수식있고 이런 경우에는 
		//    꼭 !!!! 단순화되어 있는 별칭(ALIAS) 붙여주기
		// 그냥 바인딩시켜주면 자바 쪽에서는 문제들이 생김...
		
		// ex) sql = "SELECT SID || NAME || TEL FROM TBL_MEMBER";
		//	   result = rs.getInt("SID || NAME || TEL");
		//     → (Ⅹ) 이렇게 하지 말기..........
		
		// ex) sql = "SELECT SID || NAME || TEL AS RESULT FROM TBL_MEMBER";
	    //	   result = rs.getInt("RESULT");
		//     → (○)   
		
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select 
		//		→ executeQuery() → ResultSet 반환 → 일반적으로 반복문 구성	
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성 → 결과값 수신
		while (rs.next())
		{
			result = rs.getInt("COUNT");	//getInt(1);  (○)
			//					-----
			// 					DB 조회했을 때 나오는 컬럼 이름
			//-- 그래서 result = rs.getInt(1); 로 넘겨도 된다.
			//   (→ 컬럼의 인덱스 넘겨도 된다.)
			//       테이블의 인덱스(Ⅹ) 
			//       select 하고 있는 컬럼 인덱스(○)
			//   자바에서의 인덱스는 항상 0부터 시작했지만,
			//   오라클은 인덱스 1부터 시작함 !! (컬럼 인덱스는 1 부터...)
			// BUT, 코드 작성할 때는 컬럼의 인덱스 넘겨주는 것보다는
			//      [ 컬럼의 이름/별칭 ] 을 직접 써주는게 더 바람직하다.
			//      다른 사람이 봤을 때도 뭘 가져온 건지/바인딩 했는지
			//      한 눈에 볼 수 있어야 되니까
		}
		//-- while 반복문으로 처리해놨지만,
		//   여러 행을 반환하는 게 아니라, 반복은 한 번 수행될 것이다.
		//   이런 경우에는! while 문 아니고 if 문으로 처리해도 상관없다.
		//   하지만, 일반적으로는 ResultSet 처리할 때는 while 사용함
//		if (rs.next())
//			result = rs.getInt("COUNT");
			
		
		// 리소스 반납
		rs.close();		// ResultSet 을 나중에 열었으니까 → 먼저 반납하고
		stmt.close();	// 더 일찍 open 한 Statement 는 그 다음에 반납
		// open 순서 : a b c  → close 순서 : c b a
		// html 의 태그 생각하면 된다.  ex) <a><b><c></c></b></a>
		
		
		// 최종 결과값 반환
		return result;
		
	}//end count()
	
	
	// 메소드 정의 → 전체 리스트를 조회하는 기능 → select
	//-- 반환타입 뭘로 해야할까? 
	//   String : sql = "SELECT TEL FROM TBL_MEMBER WHERE SID = 1"; 이런경우면 가능
	//   우리는 → id, name, tel 다 받아야한다.
	//   근데 그걸 한 멤버만 처리할꺼면 → MemberDTO
	//   MemberDTO : sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER WHERE SID = 1"; 이런경우면 가능
	//   그런데 우리는 그걸 여러 명 받을거니까, WHERE 절 사라져야 한다.
	//   → MemberDTO 들을 담을 수 있는 자료구조가 필요하다.
	//      어떤 자료구조를 쓰는 건 사용자 마음(배열, 리스트, ....)
	//      이 경우에는 MAP, SET 보다는 LIST가 적합	
	//-- 여기 메소드에 출력하는 기능까지 넣는 건 좋지 않다.
	//   우리가 정의하는 건 DAO 이기 때문에
	//   이렇게 정의한 걸 어떻게 활용할 지는 DAO를 활용하는 쪽에 결정하는 게 좋은 방법이다.
	public ArrayList<MemberDTO_teacher> lists() throws SQLException
	{
		// 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<MemberDTO_teacher> result = new ArrayList<MemberDTO_teacher>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select 
		String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select
		//		→ executeQuery() → ResultSet 반환 → 일반적으로 반복 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 일반적 반복문 활용
		while (rs.next())
		{
			MemberDTO_teacher dto = new MemberDTO_teacher();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setTel(rs.getString("TEL"));
			
			result.add(dto);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}//end lists()
	
	
	// Connection close() 하는 메소드는 그냥 DBConn에 static으로 있으니
	// 그거 활용해도 됨
	// 선생님은 안 만들고 넘어가려고 했음 
	// 질문나와서 만드는 거 보여줌
	public void close() throws SQLException
	{
		// 주의 check~!!!
		//conn.close();
		//-- 이렇게 하면 안된다.
		//   DBConn 을 호출하는게 아니라 Connection 이 가지고 있는 걸 부르게 되는거
		
		DBConn.close();
	}
	
}//end class MemberDAO
















