/*===============================================
	MemberProcess.java
	- 콘솔 기반 서브 메뉴 입출력 전용 클래스
================================================*/
// 나중에 실무에 나가서는
// 우리가 지금 구성하고 있는 Process가 Service가 될 거임

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	// 주요 속성 구성
	private MemberDAO dao;
	
	// 생성자 정의
	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	// 직원 정보 입력 메소드 정의
	public void memberInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 지역 리스트 구성
			ArrayList<String> citys = dao.searchCity();
			// 동적인 거니까 StringBuilder 로 처리
			StringBuilder cityStr = new StringBuilder();
			for (String city : citys)
				cityStr.append(city + "/");	
			//--==>> "강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/"
		
			// 부서 리스트 구성
			ArrayList<String> buseos = dao.searchBuseo();
			StringBuilder buseoStr = new StringBuilder();
			for (String buseo : buseos)
				buseoStr.append(buseo + "/");
			//--==> "개발부/기획부/영업부/인사부/자재부/총무부/홍보부/"
			
			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			StringBuilder jikwiStr = new StringBuilder();
			for (String jikwi : jikwis)
				jikwiStr.append(jikwi + "/");
			//--==>> "사장/전무/상무/이사/부장/차장/과장/대리/사원/"
			
			// 사용자에게 보여지는 화면 처리
			System.out.println();
			System.out.println("직원 정보 입력 ----------------------------------------------------------------");
			System.out.print("이름 : ");
			String empName = sc.next();
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsaDate = sc.next();
			System.out.printf("지역(%s) : ", cityStr.toString());
			String cityName = sc.next();
			System.out.print("전화번호 : ");
			String tel = sc.next();
			System.out.printf("부서(%s) : ", buseoStr.toString());
			String buseoName = sc.next();
			System.out.printf("직위(%s) : ", jikwiStr.toString());
			String jikwiName = sc.next();
			System.out.printf("기본급(최소 %d원 이상) : ", dao.searchBasicPay(jikwiName));
			int basicPay = sc.nextInt();
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			System.out.println();
				
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityName(cityName);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicPay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			
			if (result > 0) 
				System.out.println(">> 직원 정보 입력 완료~!!!");
			System.out.println("-----------------------------------------------------------------직원 정보 입력");
			
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberInsert()
	
	
	// 직원 전체 출력 메소드 정의
	public void memberLists()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력(안내)
		System.out.println();
		System.out.println("1. 사번 정렬");     		// EMP_ID               
		System.out.println("2. 이름 정렬");				// EMP_NAME
		System.out.println("3. 부서 정렬");				// BUSEO_NAME
		System.out.println("4. 직위 정렬");				// JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬	");	// PAY DESC
		System.out.print(">> 선택(1~5, -1 메인) : ");	
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
					key = "EMP_ID";
					break;
				case 2:
					key = "EMP_NAME";
					break;
				case 3:
					key = "BUSEO_NAME";
					break;
				case 4:
					key = "JIKWI_NAME";
					break;
				case 5:
					key = "PAY DESC";
					break;	
			}
			
			// 데이터베이스 연결
			dao.connection();
			
			// 직원 리스트 출력
			System.out.println();
			System.out.printf("전체 인원 %d명\n", dao.memberCount());
			System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
			ArrayList<MemberDTO> memList = dao.lists(key);
			
			for (MemberDTO dto : memList)
			{
				System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
						, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
						, dto.getBasicPay(), dto.getSudang(), dto.getPay());
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberLists()
	
	
	// 직원 검색 출력
	public void memberSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 구성
		System.out.println();
		System.out.println("1. 사번 검색");		// EMP_ID
		System.out.println("2. 이름 검색");		// EMP_NAME
		System.out.println("3. 부서 검색");		// BUSEO_NAME
		System.out.println("4. 직위 검색");		// JIKWI_NAME
		System.out.print(">> 선택(1~4, -1 메인) : ");	
		String menuStr = sc.next();
		
		try
		{
			// 문자열로 받은 menuStr이 정수형태로 바꿀 수 없다면
			// NumberFormatException 발생하니까
			// 여기부터 try~catch 구문 안에 넣음
			int menu = Integer.parseInt(menuStr);
			
			if (menu == -1)
				return;
			
			String key = "";
			String value = "";
			
			switch (menu)
			{
				case 1:
					key = "EMP_ID";
					System.out.print("검색할 사번 입력 : ");
					value = sc.next();
					break;
				case 2:
					key = "EMP_NAME";
					System.out.print("검색할 이름 입력 : ");
					value = sc.next();
					break;
				case 3:
					key = "BUSEO_NAME";
					System.out.print("검색할 부서 입력 : ");
					value = sc.next();
					break;
				case 4:
					key = "JIKWI_NAME";
					System.out.print("검색할 직위 입력 : ");
					value = sc.next();
					break;
			}
			
			// 데이터베이스 연결
			dao.connection();
			
			// 검색 결과 출력
			System.out.println();
			System.out.printf("검색 인원 %d명\n", dao.memberCount(key, value));
			System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
			ArrayList<MemberDTO> memList = dao.searchLists(key, value);

			if (memList.size() > 0)
			{
				for (MemberDTO dto : memList)
				{
					System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
							, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
							, dto.getBasicPay(), dto.getSudang(), dto.getPay());
				}
				
				
			} 
			else
			{
				System.out.println("대상을 검색하지 못했습니다.");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberSearch()
	
	
	// 직원 정보 수정
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 수정할 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			// 검색메소드에서 검색
			ArrayList<MemberDTO> memList = dao.searchLists("EMP_ID", value);
			
			if(memList.size() > 0)
			{
				// 수정 대상을 찾은 경우
				// 지역 리스트 구성
				ArrayList<String> citys = dao.searchCity();
				StringBuilder cityStr = new StringBuilder();
				for (String city : citys)
					cityStr.append(city + "/");	
				//--==>> "강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/"
				String cities = cityStr.substring(0, cityStr.length()-1);
				
				// 부서 리스트 구성
				ArrayList<String> buseos = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
					buseoStr.append(buseo + "/");
				//--==> "개발부/기획부/영업부/인사부/자재부/총무부/홍보부/"
				
				// 직위 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
					jikwiStr.append(jikwi + "/");
				//--==>> "사장/전무/상무/이사/부장/차장/과장/대리/사원/"
				
				
				// 사용자에게 보여지는 화면 처리
				// 안 바꿀 값 똑같이 입력하게 하는 거 너무 불편함
				// '-' 입력하면 예전 값 그대로 쓴다는 뜻으로 처리하자.
				
				// 어차피 memList 에 값 하나 들어있음
				// memList에서 0번째 MemberDto 하나 꺼내서 가져옴
				MemberDTO mMember = memList.get(0);
				
				int mEmpId = mMember.getEmpId();
				String mEmpName = mMember.getEmpName();
				String mSsn = mMember.getSsn();
				String mIbsaDate = mMember.getIbsaDate();
				String mCityName = mMember.getCityName();
				String mTel = mMember.getTel();
				String mBuseoName = mMember.getBuseoName();
				String mJikwiName = mMember.getJikwiName();
				int mBasicPay = mMember.getBasicPay();
				int mSudang = mMember.getSudang();
				
				System.out.println();
				System.out.println("직원 정보 수정 ----------------------------------------------------------------");
				System.out.printf("기존 이름 : %s\n", mEmpName);
				System.out.print("수정 이름 : ");
				String empName = sc.next();
				if (empName.equals("-"))
					empName = mEmpName;
				
				System.out.printf("기존 주민등록번호(yymmdd-nnnnnnn) : %s\n", mSsn);
				System.out.print("수정 주민등록번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				if (ssn.equals("-"))
					ssn = mSsn;
				
				System.out.printf("기존 입사일(yyyy-mm-dd) : %s\n", mIbsaDate);
				System.out.print("수정 입사일(yyyy-mm-dd) : ");
				String ibsaDate = sc.next();
				if (ibsaDate.equals("-"))
					ibsaDate = mIbsaDate;
				
				System.out.printf("기존 지역 : %s\n", mCityName);
				// StringBuiler도 %s에 들어가서, 그냥 cityStr 로 해도 들어가고, 
				// toString() 사용해서 String 타입으로 바꿔서 넣어줘도 들어간다. 
				System.out.printf("수정 지역(%s) : ", cities);
				String cityName = sc.next();
				if (cityName.equals("-"))
					cityName = mCityName;
				
				System.out.printf("기존 전화번호 : %s\n", mTel);
				System.out.print("수정 전화번호 : ");
				String tel = sc.next();
				if (tel.equals("-"))
					tel = mTel;
				
				System.out.printf("기존 부서 : %s\n", mBuseoName);
				System.out.printf("수정 부서(%s) : ", buseoStr.toString());
				String buseoName = sc.next();
				if (buseoName.equals("-"))
					buseoName = mBuseoName;
				
				System.out.printf("기존 직위 : %s\n", mJikwiName);
				System.out.printf("수정 직위(%s) : ", jikwiStr);
				String jikwiName = sc.next();
				if (jikwiName.equals("-"))
					jikwiName = mJikwiName;
				
				System.out.printf("기존 기본급 : %d\n", mBasicPay); 
				System.out.printf("수정 기본급(최소 %d원 이상) : ", dao.searchBasicPay(jikwiName));
				String basicPayStr = sc.next();
				int basicPay = 0;
				if (basicPayStr.equals("-"))
					basicPay = mBasicPay;
				else
					basicPay = Integer.parseInt(basicPayStr);						
				
				System.out.printf("기존 수당 : %d\n", mSudang);
				System.out.print("수정 수당 : ");
				String sudangStr = sc.next();
				int sudang = 0;
				if (basicPayStr.equals("-"))
					sudang = mSudang;
				else
					sudang = Integer.parseInt(sudangStr);
				
				System.out.println();
				
				// 새로 입력받은(수정한) 내용을 통해 DTO 구성
				MemberDTO member = new MemberDTO();
				
				member.setEmpId(mEmpId);		// ★ check~!!! 꼭 들어가야 됨!!!
				member.setEmpName(empName);
				member.setSsn(ssn);
				member.setIbsaDate(ibsaDate);
				member.setCityName(cityName);
				member.setTel(tel);
				member.setBuseoName(buseoName);
				member.setJikwiName(jikwiName);
				member.setBasicPay(basicPay);
				member.setSudang(sudang);
				
				int result = dao.modify(member);
				
				if (result > 0) 
					System.out.println(">> 직원 정보 수정 완료~!!!");
				System.out.println("-----------------------------------------------------------------직원 정보 수정");	
			}
			else
				System.out.println("해당 직원이 존재하지 않습니다.");
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberUpdate()
	
	
	// 직원 정보 삭제
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("삭제할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			// 사용자가 입력한 직원의 사원번호에 대한 유효성 검사 가능~!!!
			
			// 직원 정보 확인 후 삭제여부 결정
			
			dao.connection();
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				System.out.println();
				System.out.println("사번  이름    주민번호        입사일      지역  전화번호       부서    직위  기본급   수당     급여");
				for (MemberDTO dto : members)
				{
					System.out.printf("%4d  %3s  %14s  %10s  %2s  %13s  %3s  %2s  %7d  %7d  %7d\n"
							, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
							, dto.getBasicPay(), dto.getSudang(), dto.getPay());
				}
				
				System.out.print("\n정말 삭제하시겠습니까?(Y/N) : ");
				String response = sc.next();
				if (response.equalsIgnoreCase("y"))
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
						System.out.println("직원 정보가 정상적으로 삭제되었습니다.");
				}
			}
			else
			{
				System.out.println("삭제 대상을 찾지 못했습니다.");
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} 
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberDelete()
	
}//end MemberProcess()










