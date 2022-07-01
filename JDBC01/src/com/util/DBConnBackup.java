/*======================
 	DBConnBackup.java
=======================*/
//--★★★ !!!!! DBConn 과 DBConnBackup 파일 외우기 !!!!! ★★★
// DBConn 과 DBConn 똑같은 코드
// 예외처리 방식만 다름
// DBConn 	 	: throws
// DBConnBackup : try/catch

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnBackup
{
	private static Connection dbConn;
	
	public static Connection getConnection()
	{
		if (dbConn == null)
		{
			try
			{
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "scott";
				String pwd = "tiger";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
			} catch (Exception e)
			{
				System.out.println(e.toString());
				//-- 오라클 연결 실패 시 오류 메세지 출력 부분
				// 이거 빼놓고 오류 내용 뭔지 안나와요 하면 안됨.....!
			}
		}
		
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String pwd)
	{
		if (dbConn == null)
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);		
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return dbConn;
		
	}
	
	public static void close()
	{
		if (dbConn != null)
		{
			try
			{
				if (!dbConn.isClosed())
					dbConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		dbConn = null;
	}
	
}
