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

// 작업순서는 DTO - DAO - MAIN 순으로 하는게 논리적으로 편함

package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class MemberMain_teacher
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 생성자 자체도 예외를 throws 하고 있기 때문에
			// try~catch 구문 안에 MemberDAO 객체 생성
			MemberDAO_teacher dao = new MemberDAO_teacher();
			
			int count = dao.count();
			
			do
			{
				System.out.printf("이름 전화번호 입력(%d) : ", ++count);	
				String name = sc.next();
				
				// 반복의 조건을 무너뜨리는 코드 구성
				if (name.equals("."))
					break;
				
				String tel = sc.next();
				
				// 여기까지의 과정을 통해 이름과 전화번호를 사용자로부터 입력받은 이유는
				// 입력받은 데이터를 데이터베이스에 입력하기 위함
				// 데이터 입력을 위해서는 dao 의 add() 메소드 호출 필요
				// add() 메소드 호출하기 위해서는 MemberDTO 값을 넘겨주는 과정이 필요
				// MemberDTO 값을 넘겨주기 위해서는 객체의 속성값 구성 필요
				
				// MemberDTO 객체 생성
				MemberDTO_teacher dto = new MemberDTO_teacher();
				
				// 속성값 구성
				dto.setName(name);
				dto.setTel(tel);
				
				// 데이터베이스에 데이터 입력하는 메소드 호출 → add() : 적용된 행의 개수 반환
				int result = dao.add(dto);
				if (result > 0)
					System.out.println(">> 회원 정보 입력 완료~!!!");
		
			} while (true);
			
			System.out.println();
			System.out.println("--------------------------------");
			System.out.printf("전체 회원 수 : %d명\n", dao.count());	// dao.count() 호출해서 최종 확인하는 게 맞음
																		// 그냥 count 호출하는 거 X
			System.out.println("--------------------------------");
			System.out.println("번호	이름	전화번호");
			
			// 리스트 가져와 출력
			for (MemberDTO_teacher obj : dao.lists())
			{
				System.out.printf("%3s %7s %12s\n", obj.getSid(), obj.getName(), obj.getTel());
			}
			System.out.println("--------------------------------");
					
		} catch (Exception e)
		{
			System.out.println(e.toString()); 
		}
		finally 
		{
			try
			{
				DBConn.close();
				System.out.println("데이터베이스 연결 닫힘~!!!");
				System.out.println("프로그램 종료됨~!!!");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
}

// 실행 결과
/*
이름 전화번호 입력(2) : 오이삭 010-2222-2222
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(3) : 임소민 010-3333-3333
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(4) : .

--------------------------------
전체 회원 수 : 3명
--------------------------------
번호	이름	전화번호
  1     이호석 010-1111-1111
  2     오이삭 010-2222-2222
  3     임소민 010-3333-3333
--------------------------------
데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!
*/









