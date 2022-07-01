/*================
 	DBConn.java
=================*/
//--★★★ !!!!! DBConn 과 DBConnBackup 파일 외우기 !!!!! ★★★ 
// DBConn 과 DBConn 똑같은 코드
// 예외처리 방식만 다름
// DBConn 	 	: throws
// DBConnBackup : try/catch

// 지금 만든 DBConn 실행 될까??
// → No
//    main() 없으니까

// ※ 싱글톤(singleton) 디자인 패턴을 이용한 Database 연결 객체 생성 전용 클래스
//	  → DB 연결 과정이 가장 부하가 크기 때문에 
//       한 번 연결된 객체를 계속 사용하는 것이 좋지 않을까...

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	// 변수 선언
	private static Connection dbConn;	// → 추상적인 연결
	// 여기서의 static 은 『공유』의 개념으로 사용
	// 탄생시점 의미도 있기는 하지만
	// Conn 까지 치고 ctrl+space 누르면 
	// 파란색동그라미 안에 I  : 인터페이스 
	// 초록색 동그라미 안에 C : 클래스
	// 위에 마우스 올리면 그거에 대한 설명까지 쭉 볼 수 있음
	
	
	// 메소드 정의 → 연결 (구체적인 연결)
	public static Connection getConnection() throws ClassNotFoundException, SQLException	
	{
		// 한 번 연결된 객체를 계속 사용
		// 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
		// → 싱글톤(디자인 패턴)
		if (dbConn == null)
		{
			String url = "jdbc:oracle:thin:@localhost:1521:xe";		// ★암기★
			//-- 『localhost』 는 오라클 서버의 ip 주소를 기재하는 부분
			//   『1521』 은 오라클 리스너 Port Number
			//   『xe』 는 오라클 SID(Express Edition 의 SID 는 xe)
			//-- Line 44 에 노란색 표시 → 조심,위험 하다는 의미
			//   → 지금은 url이라 변수 선언은 했는데 지금은 쓰이는 곳이 없어서!
			//      나중에 url 변수 사용하면 사라질 거임
			// 실무에서는 @localhost 바꿔서 쓰고,
			//            1521 도 바꿔서 쓰는 경우 있음
			// @localhost에 @127.0.0.1 로 되어있으면, 127로 시작하면 
			// → loop back address 이다.
			// → @localhost 로 되어있는 것과 똑같은 거다.
			
			
			String user = "scott";
			//-- 오라클 사용자 계정 이름
			
			String pwd = "tiger";
			//-- 오라클 사용자 계정 암호
			
			// lang 패키지에 Class의 이름이 Class 인 클래스가 있음...
			// Class 클래스 → 클래스를 찾아주는 클래스
			Class.forName("oracle.jdbc.driver.OracleDriver");	// ★암기★
			//→ OracleDriver 클래스에 대한 객체 생성(클래스 찾아줘~)
			//→ oracle 안에 jdbc 안에 driver 안에 OracleDriver 라는 
			//   이름을 가진 클래스를 찾아줘
			//→ 빨간줄로 밑줄 → 이 문장에서 예외 발생하고 있는 상황
			//   빨간줄 위에 마우스 커서 올려보면
			//   Unhandled exception type ClassNotFoundException
			//   → 찾으려는 클래스 없을 때, 해당 클래스 없으면 
			// checked exception 인 ClassNotFoundException 발생하는데,
			// 그거 처리해주지 않았기 때문에 쓸 수 없다는 의미
			// 그래서 해결 방법 2개 아래에 써줌
			// add throws declaration  : 예외 던지거나
			// surround with try/catch : try/catch 로 감싸거나
			// add throws declaration 클릭했더니 위에 『throws ClassNotFoundException』 생김
			
			dbConn = DriverManager.getConnection(url, user, pwd);
			//-- 쓰고 빨간 줄 떠서 마우스 올려보니
			//   → Unhandled exception type SQLException 발생
			//   → throws 로 처리
			//-- 오라클 서버 실제 연결
			//   갖고 있는 인자값(매개변수)은 오라클주소, 계정명, 패스워드		
		}
		
		return dbConn;
		//-- 구성된 연결 객체 반환
	}

	// 위의 메소드 이름도 getConnection() 이다.
	// → getConnection() 메소드의 오버로딩 → 연결
	// 매개변수로 건네준 url, user, pwd 로 DB 연결하는 메소드
	public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException
	{
		if (dbConn == null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(url, user, pwd);
			// Line 94,95 모두 throws 로 예외처리함
			// forName(), getConnection() 메소드 모두 → static 메소드
			// 객체 생성할 필요 없이, 클래스의 이름으로 직접 접근해서 메소드 호출하고 있음
		}
		
		return dbConn;
	}
	
	// 메소드 정의 → 연결 종료
	public static void close() throws SQLException
	{	
		// 이미 연결 끊어져있는데 또 끊을 수 없음
		// Database 가 연결된 상태일 경우 Connection 을 갖는다.
		// 연결되지 않은 상태라면... null 인 상태가 된다.
		
		
		// dbConn 이 연결되어 있는 상태라면, 연결 종료시키겠다.
		if (dbConn != null)
		{
			// 연결 객체(dbConn)의 isClosed() 메소드를 통해 연결 상태 확인
			//-- ! (연결이 닫혀있는 경우 true 반환) = false
			//   ! (연결이 닫혀있지 않은 경우 false 반환) = true
			if (!dbConn.isClosed())	//-- throws 로 예외 처리
			{
				dbConn.close();
				//-- 연결 객체의 close() 메소드 호출을 통해 연결 종료~!!!
			}
		}
		
		dbConn = null;
		//-- 연결 객체 초기화
	}
}



