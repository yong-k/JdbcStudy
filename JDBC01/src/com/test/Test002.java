/*============================================
 	Test002.java
 	- main() 메소드를 포함하는 테스트 클래스
=============================================*/

package com.test;

import java.sql.Connection;

//import com.util.DBConn;		// import 하고 안써도 노란색 느낌표 뜸
// //  → 블럭잡고 ctrl + /  OR   ctrl + space + /
// /**/ 주석처리 → 블럭잡고 ctrl + shift + /
// /**/주석 푸는 건        → 블럭잡고 ctrl + \
// // 주석 푸는 건 → 블럭잡고 ctrl + /
// 한 줄 삭제 : ctrl + d
// 컴파일 : ctrl + f11
// 디버깅 : f11
import com.util.DBConnBackup;

public class Test002
{
	public static void main(String[] args)
	{
		//Connection conn = DBConn.getConnection();
		// DBConn 에서 예외 throws 했기 때문에 빨간 줄 뜸
		
		Connection conn = DBConnBackup.getConnection();
		// ctrl 누르고 getConnection() 누르면 getConnection() 있는 곳으로 감
		// 다시 alt + ← 누르면 돌아옴
		// 이건 DBConnBackup에서 예외 try/catch로 처리해서 빨간 줄 뜨지 않음
		// ※ DB 연결 과정이 가장 부하가 크기 때문에
		//    한 번 연결된 객체를 계속 사용할 수 있도록 Singleton 패턴 적용~!!!
		
		// 위의 getConnection() 메소드를 통해 데이터베이스와 정상적인 연결이 이루어진 상황이라면...
		if (conn != null)
		{
			System.out.println("데이터베이스 연결 성공~!!!");
		}
		
		DBConnBackup.close();
		//-- close() 메소드 호출을 통해 연결 종료~!!!
		
	}
}



