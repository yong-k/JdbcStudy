package com.test;

import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

public class ScoreProcess
{
	private ScoreDAO dao;
	
	public ScoreProcess()
	{
		dao = new ScoreDAO();
	}
	
	// 성적 정보 입력
	public void scoreInsert()
	{
		Scanner sc = new Scanner(System.in);
		try
		{
			dao.connection();

			System.out.println();
			// ★★★ 잘못 작성했던 부분 ★★★
			//System.out.printf("%d번 학생 성적 입력  ------------------\n", dao.stdCount() + 1); 
			//-- 『dao.stdCount() + 1』를 학번으로 사용하려고 했는데
			//   시퀀스를 사용하고 있어서 학생을 삭제하면 학생 인원 수는 줄어드는데,
			//   시퀀스는 계속 증가하는 걸 생각못해서 아래와 같이 코드 수정함
			System.out.printf("%d번째 학생 성적 입력  ------------------\n", dao.stdCount() + 1);
			System.out.println("학번 : " + dao.nextSid());
			System.out.print("이름 : ");
			String name = sc.next();
			System.out.print("국어 성적 : ");
			int kor = sc.nextInt();
			System.out.print("영어 성적 : ");
			int eng = sc.nextInt();
			System.out.print("수학 성적 : ");
			int mat = sc.nextInt();
			System.out.println();
			
			ScoreDTO dto = new ScoreDTO();
			dto.setName(name);
			dto.setKor(kor);
			dto.setEng(eng);
			dto.setMat(mat);
			
			int result = dao.add(dto);
			if (result > 0)
				System.out.println(">> 성적 입력이 완료되었습니다.");
			System.out.println("-----------------------------------------");		
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				DBConn.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	
	// 성적 전체 출력
	public void scoreLists()
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("1. 학번 정렬");		// SID              
		System.out.println("2. 이름 정렬");		// NAME
		System.out.println("3. 석차 정렬");		// RANK
		System.out.print(">> 선택(1~3, -1 메인) : ");	
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			
			if (menu == -1)
				return;
			
			String key = "";
			
			switch (menu)
			{
				case 1:
					key = "SID";
					break;
				case 2:
					key = "NAME";
					break;
				case 3:
					key = "RANK";
					break;
			}
			
			dao.connection();
			
			System.out.println();
			System.out.printf("전체 학생 수 : %d명\n", dao.stdCount());
			System.out.println("학번   이름   국어   영어   수학   총점   평균   석차");
			
			for (ScoreDTO obj : dao.lists(key))
			{
				System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.2f  %5d\n"
						, obj.getSid(), obj.getName()
						, obj.getKor(), obj.getEng(), obj.getMat()
						, obj.getTot(), obj.getAvg(), obj.getRank());
			}	
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				DBConn.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	
	// 성적 검색 출력
	public void scoreSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("1. 학번 검색");		// SID              
		System.out.println("2. 이름 검색");		// NAME
		System.out.println("3. 석차 검색");		// RANK
		System.out.print(">> 선택(1~3, -1 메인) : ");	
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			
			if (menu == -1)
				return;
			
			String key = "";
			String value = "";
			
			switch (menu)
			{
				case 1:
					key = "SID";
					System.out.print("\n검색할 학번 입력 : ");
					value = sc.next();
					break;
				case 2: 
					key = "NAME";
					System.out.print("\n검색할 이름 입력 : ");
					value = sc.next();
					break;
				case 3:
					key = "RANK";
					System.out.print("\n검색할 석차 입력 : ");
					value = sc.next();
					break;
			}
			
			dao.connection();
			
			ArrayList<ScoreDTO> lists = dao.searchLists(key, value);
			
			if (lists.size() > 0)
			{
				System.out.println();
				System.out.printf("검색 학생 수 : %d명\n", dao.stdCount(key, value));
				System.out.println("학번   이름   국어   영어   수학   총점   평균   석차");
				
				for (ScoreDTO obj : lists)
				{
					System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.2f  %5d\n"
							, obj.getSid(), obj.getName()
							, obj.getKor(), obj.getEng(), obj.getMat()
							, obj.getTot(), obj.getAvg(), obj.getRank());
				}
			} 
			else
			{
				System.out.println(">> 검색 정보가 없습니다.");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				DBConn.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	
	// 성적 정보 수정
	public void scoreUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("\n수정할 학생의 학번 입력 : ");
			String value = sc.next();
			
			dao.connection();
			ArrayList<ScoreDTO> lists = dao.searchLists("SID", value);
			
			if (lists.size() > 0)
			{
				// '-' 입력하면, 예전 값 그대로 처리
				ScoreDTO dto = lists.get(0);
				System.out.printf("%d번 학생 성적 수정  ------------------\n", dto.getSid());
				System.out.printf("기존 이름 : %s\n", dto.getName());
				System.out.print("수정 이름 : ");
				String name = sc.next();
				if (name.equals("-"))
					name = dto.getName();
				
				System.out.printf("기존 국어 성적 : %d\n", dto.getKor());
				System.out.print("수정 국어 성적 : ");
				String korStr = sc.next();
				int kor = 0;
				if (korStr.equals("-"))
					kor = dto.getKor();
				else
				{
					try
					{
						kor = Integer.parseInt(korStr);
					} 
					catch (Exception e)
					{
						System.out.println(e.toString());
						e.printStackTrace();
					}
				}		
				
				System.out.printf("기존 영어 성적 : %d\n", dto.getEng());
				System.out.print("수정 영어 성적 : ");
				String engStr = sc.next();
				int eng = 0;
				if (engStr.equals("-"))
					eng = dto.getEng();
				else
				{
					
				}				

				System.out.printf("기존 수학 성적 : %d\n", dto.getMat());
				System.out.print("수정 수학 성적 : ");
				String matStr = sc.next();
				int mat = 0;
				if (matStr.equals("-"))
					mat = dto.getMat();
				else
				{
					try
					{
						mat = Integer.parseInt(matStr);
					} 
					catch (Exception e)
					{
						System.out.println(e.toString());
						e.printStackTrace();
					}
				}
				System.out.println();
				
				ScoreDTO dtoUpdate = new ScoreDTO();
				dtoUpdate.setName(name);
				dtoUpdate.setKor(kor);
				dtoUpdate.setEng(eng);
				dtoUpdate.setMat(mat);
				dtoUpdate.setSid(Integer.parseInt(value));
				
				int result = dao.modify(dtoUpdate);
				if (result > 0)
					System.out.println(">> 성적 정보 수정 완료");
				System.out.println("---------------------------------------");
			} 
			else
			{
				System.out.println(">> 수정 대상을 찾지 못했습니다.");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				DBConn.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	
	// 성적 정보 삭제
	public void scoreDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("\n삭제할 학생의 학번 입력 : ");
			String value = sc.next();
			
			// 해당 학생 정보 학번으로 검색 후, 정보 출력
			dao.connection();
			ArrayList<ScoreDTO> lists = dao.searchLists("SID", value);
			
			if (lists.size() > 0)
			{
				System.out.println();
				System.out.println("학번   이름   국어   영어   수학   총점   평균   석차");
				
				for (ScoreDTO obj : lists)
				{
					System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.2f  %5d\n"
							, obj.getSid(), obj.getName()
							, obj.getKor(), obj.getEng(), obj.getMat()
							, obj.getTot(), obj.getAvg(), obj.getRank());
				}
				
				System.out.println();
				System.out.print("정말 삭제하시겠습니까?[Y/N] : ");
				String answer = sc.next();
				if (answer.equalsIgnoreCase("y"))
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
						System.out.println(">> 성적 정보 삭제 완료");
				}	
				else
					System.out.println(">> 삭제가 취소되었습니다.");	
			} 
			else
			{
				System.out.println(">> 삭제 대상을 찾지 못했습니다.");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				DBConn.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
}






