/*====================
 	ScoreMain.java
=====================*/

/*
○ 성적 처리 프로그램 구현 → 데이터베이스 연동 → ScoreDAO, ScoreDTO 클래스 활용

    여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
    총점, 평균을 연산하여 내용을 출력하는 프로그램을 구현한다.
    출력 시 번호(이름, 총점 등) 오름차순 정렬하여 출력한다.
    
실행 예)
(만약 DB에 이미 30명 있다면, [31번 학생 성적 입력] 이라고 나와야 한다.)
1번 학생 성적 입력(이름 국어 영어 수학) : 신시은 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학) : 이호석 100 90 80  
3번 학생 성적 입력(이름 국어 영어 수학) : 이연주 80 85 80 
4번 학생 성적 입력(이름 국어 영어 수학) : .

-----------------------------------------------------------
번호	이름	국어	영어	수학	총점	평균
-----------------------------------------------------------
  1		신시은	 80		 75		 60		xxx		 xx.x
  2		이호석  100		 90		 80		xxx		 xx.x
  3		이연주	 80		 85		 80		xxx		 xx.x
-----------------------------------------------------------  
*/

package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class ScoreMain
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		ScoreDAO dao = new ScoreDAO();
		
		try
		{
			dao.connect();
			
			int count = dao.count();
			
			do
			{
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : ", ++count);
				String name = sc.next();
				
				if (name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				ScoreDTO dto = new ScoreDTO();
				
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				int result = dao.add(dto);
				
			} while (true);
			
			System.out.println("-----------------------------------------------------------");
			System.out.println("번호	이름	국어	영어	수학	총점	평균");
			System.out.println("-----------------------------------------------------------");
			
			for (ScoreDTO obj : dao.lists())
			{
				System.out.printf("%3d %6s %6d %6d %6d %8d %8.1f\n", obj.getSid(), obj.getName()
								 , obj.getKor(), obj.getEng(), obj.getMat(), obj.getTot(), obj.getAvg());
			}
			
			System.out.println("-----------------------------------------------------------");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		finally 
		{
			try
			{
				DBConn.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
}















