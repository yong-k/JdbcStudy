/*=====================
	MemberMain.java
======================*/

/*
○ TBL_MEMBER 테이블을 활용하여
   이름과 전화번호를 여러 건 입력받고, 전체 출력하는 프로그램을 구현한다.
   단, 데이터베이스 연동이 이루어져야 하고,
   MemberDAO, MemberDTO 클래스를 활용해야 한다.
   
실행 예)
		-> 데이터가 한 건 들어있는 상태니까 '2'로 나오게 해야함
이름 전화번호 입력(2) : 오이삭 010-2222-2222
>> 회원 정보 입력 완료~!!!	
이름 전화번호 입력(3) : 임소민 010-3333-3333
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(4) : .

--------------------------------
전체 회원 수 : 3명
--------------------------------
번호	이름	   전화번호
 1		이호석	010-1111-1111
 2		오이삭	010-2222-2222
 3		임소민	010-3333-3333
--------------------------------
*/

package com.test;

import java.sql.SQLException;

public class MemberMain
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		MemberDAO dao = new MemberDAO();
	
		dao.connection();
		dao.inputData();	
		dao.printAll();
		dao.disconnection(); 
	}
}
