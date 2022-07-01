package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class Process
{
	// 주요 속성 구성
	private MemberDAO dao;
	
	// 생성자 정의
	public Process()
	{
		dao = new MemberDAO();
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------	
	
	// 1. 직원 정보 입력
	public void empInsert()
	{
		try
		{	
			dao.connection();
			
			Scanner sc = new Scanner(System.in);
			
			// 예외처리 다 추가해야함
			System.out.println("직원 정보 입력 ----------------------------------------------------------------");
			System.out.print("이름 : ");
			String emp_name = sc.next();
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsadate = sc.next();
			System.out.print("지역(");	dao.printList("city"); 	System.out.print(") : ");
			String city_name = sc.next();
			System.out.print("전화번호 : ");
			String tel = sc.next();
			System.out.print("부서(");	dao.printList("buseo");	System.out.print(") : ");
			String buseo_name = sc.next();
			System.out.print("직위(");	dao.printList("jikwi");	System.out.print(") : ");
			String jikwi_name = sc.next();
			System.out.print("기본급(최소 ");	dao.printList_Basicpay(jikwi_name);		System.out.print(") : ");
			int basicpay = sc.nextInt();
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			System.out.println();
				
			MemberDTO dto = new MemberDTO();
			
			dto.setEmp_name(emp_name);
			dto.setSsn(ssn);
			dto.setIbsadate(ibsadate);
			dto.setCity_name(city_name);
			dto.setTel(tel);
			dto.setBuseo_name(buseo_name);
			dto.setJikwi_name(jikwi_name);
			dto.setBasicpay(basicpay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			
			if (result > 0) 
				System.out.println(">> 직원 정보 입력 완료~!!!");
			System.out.println("-----------------------------------------------------------------직원 정보 입력");
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
//----------------------------------------------------------------------------------------------------------------------------------------------	
	
	// 2. 직원 전체 출력(1.사번정렬  2.이름정렬  3.부서정렬  4.직위정렬  5.급여내림차순정렬)
	public void empSelectAll()
	{  
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("1. 사번 정렬");                   
		System.out.println("2. 이름 정렬");
		System.out.println("3. 부서 정렬");
		System.out.println("4. 직위 정렬");
		System.out.println("5. 급여 내림차순 정렬	");
		System.out.print(">> 선택(1~5, -1 메인) : ");	
		String menus = sc.next();
		
		try
		{	
			int menu = Integer.parseInt(menus);
			
			// 메인으로
			if (menu == -1)
				return;
			
			if (menu >= 1 && menu <= 5)
			{
				dao.connection();
				
				System.out.println();
				System.out.printf("전체 인원 : %d명\n", dao.count());
				System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
				
				for (MemberDTO obj : dao.lists(menu))
				{
					System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
							, obj.getEmp_id(), obj.getEmp_name(), obj.getSsn(), obj.getIbsadate()
							, obj.getCity_name(), obj.getTel(), obj.getBuseo_name(), obj.getJikwi_name()
							, obj.getBasicpay(), obj.getSudang(), obj.getSalary());
				}
				
				dao.close();	
				
			} 
			else
			{
				System.out.println("!번호 입력 오류! 메인으로 돌아갑니다.");
				return;
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------	
	
	// 3. 직원 검색 출력(1.사번검색  2.이름검색  3.부서검색  4.직위검색)
	public void empSelectSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("1. 사번 검색");
		System.out.println("2. 이름 검색");
		System.out.println("3. 부서 검색");
		System.out.println("4. 직위 검색");
		System.out.print(">> 선택(1~4, -1 메인) : ");	
		String menus = sc.next();
		
		try
		{	
			ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
			int menu = Integer.parseInt(menus);
			
			// 메인으로
			if (menu == -1)
				return;
			
			if (menu >= 1 && menu <= 4)
			{
				dao.connection();
				
				if (menu == 1)
				{
					System.out.print("검색할 사번 입력 : ");
					int search = sc.nextInt();
					
					arrayList = dao.searchLists(search);
				}
				else if (menu == 2 || menu == 3 || menu == 4)
				{
					if (menu == 2)
						System.out.print("검색할 이름 입력 : ");
					else if (menu == 3)
						System.out.print("검색할 부서 입력 : ");
					else
						System.out.print("검색할 직위 입력 : ");
					
					String search = sc.next();
					
					arrayList = dao.searchLists(menu, search);
				}
				
				if (arrayList.size() > 0)
				{
					System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
					
					for (MemberDTO obj : arrayList)
					{
						System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
								, obj.getEmp_id(), obj.getEmp_name(), obj.getSsn(), obj.getIbsadate()
								, obj.getCity_name(), obj.getTel(), obj.getBuseo_name(), obj.getJikwi_name()
								, obj.getBasicpay(), obj.getSudang(), obj.getSalary());
					}
				} 
				else
				{
					System.out.println("검색 결과가 존재하지 않습니다.");
				}
				
				dao.close();
				
			} 
			else
			{
				System.out.println("!번호 입력 오류! 메인으로 돌아갑니다.");
				return;
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------	
	
	// 4. 직원 정보 수정
	public void empUpdate()
	{
		try
		{	
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 사번 입력 : ");
			int search = sc.nextInt();
			
			dao.connection();
			
			ArrayList<MemberDTO> arrayList = dao.searchLists(search);
			
			if (arrayList.size() > 0)
			{
				System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
				
				for (MemberDTO obj : arrayList)
				{
					System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
									 , obj.getEmp_id(), obj.getEmp_name(), obj.getSsn(), obj.getIbsadate()
									 , obj.getCity_name(), obj.getTel(), obj.getBuseo_name(), obj.getJikwi_name()
									 , obj.getBasicpay(), obj.getSudang(), obj.getSalary());
				}
				
				System.out.println();
				System.out.println("직원 정보 수정 ----------------------------------------------------------------");
				System.out.print("이름 : ");
				String emp_name = sc.next();
				System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				System.out.print("입사일(yyyy-mm-dd) : ");
				String ibsadate = sc.next();
				System.out.print("지역(");	dao.printList("city"); 	System.out.print(") : ");
				String city_name = sc.next();
				System.out.print("전화번호 : ");
				String tel = sc.next();
				System.out.print("부서(");	dao.printList("buseo");	System.out.print(") : ");
				String buseo_name = sc.next();
				System.out.print("직위(");	dao.printList("jikwi");	System.out.print(") : ");
				String jikwi_name = sc.next();
				System.out.print("기본급(최소 ");	dao.printList_Basicpay(jikwi_name);	System.out.print(") : ");
				int basicpay = sc.nextInt();
				System.out.print("수당 : ");
				int sudang = sc.nextInt();
					
				MemberDTO dto = new MemberDTO();
				
				dto.setEmp_name(emp_name);
				dto.setSsn(ssn);
				dto.setIbsadate(ibsadate);
				dto.setCity_name(city_name);
				dto.setTel(tel);
				dto.setBuseo_name(buseo_name);
				dto.setJikwi_name(jikwi_name);
				dto.setBasicpay(basicpay);
				dto.setSudang(sudang);
				dto.setEmp_id(search);
				
				int result = dao.modify(dto);
				
				if (result > 0)
				{
					System.out.println();
					System.out.println(">> 직원 정보 수정 완료~!!!");
				}
				System.out.println("-----------------------------------------------------------------직원 정보 수정");

			} 
			else
			{
				System.out.println("검색 결과가 존재하지 않습니다.");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------	
	
	// 5. 직원 정보 삭제
	public void empDelete()
	{
		try
		{	
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 사번 입력 : ");
			int search = sc.nextInt();
			
			dao.connection();
			
			ArrayList<MemberDTO> arrayList = dao.searchLists(search);
			
			if (arrayList.size() > 0)
			{
				System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
				
				for (MemberDTO obj : arrayList)
				{
					System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
									 , obj.getEmp_id(), obj.getEmp_name(), obj.getSsn(), obj.getIbsadate()
									 , obj.getCity_name(), obj.getTel(), obj.getBuseo_name(), obj.getJikwi_name()
									 , obj.getBasicpay(), obj.getSudang(), obj.getSalary());
				}
				
				System.out.print(">> 정말 삭제하시겠습니까?(Y/N) : ");
				String response = sc.next();
				
				if (response.equalsIgnoreCase("y"))
				{
					int result = dao.remove(search);
					
					if (result > 0)
						System.out.println(">> 직원 정보 수정 완료~!!!");
				}
				else
				{
					System.out.println("취소되었습니다.");
				}
			} 
			else
			{
				System.out.println("검색 결과가 존재하지 않습니다.");
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
