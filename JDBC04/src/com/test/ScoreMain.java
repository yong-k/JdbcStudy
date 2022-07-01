/*======================
	ScoreMain.java
=======================*/
/*
○ 성적 처리 → 데이터베이스 연동(데이터베이스 연결 및 액션 처리)
                ScoreDTO 클래스 활용(속성만 존재하는 클래스, getter / setter 구성)
                ScoreDAO 클래스 활용(데이터베이스 액션 처리)
                Process 클래스 활용(업무 단위 기능 구성)
                DBConn 클래스 활용(데이터베이스 연결 전담 전용 객체 처리)

여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
총점, 평균, 석차 등을 계산하여 출력하는 프로그램을 구현한다.
※ 서브 메뉴 구성 → Process 클래스 활용

실행 예)

====[ 성적 처리 ]====
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
=====================
>> 선택(1~5, -1 종료) : 1

7번 학생 성적 입력(이름 국어 영어 수학) : 이시우 50 60 70
성적 입력이 완료되었습니다.

8번 학생 성적 입력(이름 국어 영어 수학) : 이지연 80 80 80
성적 입력이 완료되었습니다.

9번 학생 성적 입력(이름 국어 영어 수학) : . 
                
====[ 성적 처리 ]==========
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
===========================
>> 선택(1~5, -1 종료) : 2

전체 인원 : 8명
번호	이름	국어	영어	수학	총점	평균	석차
  1
  2
  3
  4					...........
  5
  6
  7
  8              
                
====[ 성적 처리 ]==========
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
===========================
>> 선택(1~5, -1 종료) : 3

====[ 성적 처리 ]==========
1. 성적 입력
2. 성적 전체 출력
3. 이름 검색 출력
4. 성적 수정
5. 성적 삭제
===========================
>> 선택(1~5, -1 종료) : -1

프로그램이 종료되었습니다.
 
 
 +) 찾았으면 보여주고, 없으면 없다고 띄워주고
	다른이름으로 다시검색할래? 라고 물어볼수도
	이름 다시입력하라고 안내할수도
	이름 입력하지않고 종료하려면 어케하라고 넣을수도

	수정도 sid 입력해서 기존성적보여주고 수정하게 만들 수 있음

	삭제도 수정과 마찬가지로

	마지막 종료는 -1        
*/
package com.test;

import java.util.Scanner;

public class ScoreMain
{
	public static void main(String[] args)
	{
		Process prc = new Process();
		Scanner sc = new Scanner(System.in);
		
		// 아래 과정을 그만한다고 전달받기 전까지는 계속할거기 때문에
		// do~while 문으로 구성
		do
		{
			System.out.println();
			System.out.println("====[ 성적 처리 ]====");
			System.out.println("1. 성적 입력");
			System.out.println("2. 성적 전체 출력");
			System.out.println("3. 이름 검색 출력");
			System.out.println("4. 성적 수정");
			System.out.println("5. 성적 삭제");
			System.out.println("=====================");
			System.out.print(">> 선택(1~5, -1 종료) : ");
			String menus = sc.next();
			
			try
			{
				int menu = Integer.parseInt(menus);
				
				if (menu == -1)
				{
					System.out.println();
					System.out.println("프로그램이 종료되었습니다.");
					return;
				} 
				
				switch (menu)
				{
					case 1:
						// 성적 입력 기능 수행
						prc.sungjukInsert();
						break;
					case 2:
						// 성적 전체 출력 기능 수행
						prc.sungjukSelectAll();
						break;
					case 3:
						// 이름 검색 출력 기능 수행
						prc.sungjukSearchName();
						break;
					case 4:
						// 성적 수정 기능 수행
						prc.sungjukUpdate();
						break;
					case 5:
						// 성적 삭제 기능 수행
						prc.sungjukDelete();
						break;
				}		
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		} while (true);
	} 
	
}

