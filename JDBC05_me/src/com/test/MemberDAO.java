package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO
{
	// 주요 속성 구성
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getconnection();
		
		return conn;
	}
	
	// 인원 수 
	public int count() throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
			result = rs.getInt("COUNT");
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 직원 정보 입력
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_EMP(EMP_ID, EMP_NAME, SSN, IBSADATE"
								+ ", CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
								+ " VALUES(EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD')"
								+ ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s'), '%s'"
								+ ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
								+ ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
								+ ", %d, %d)"
								, dto.getEmp_name(), dto.getSsn(), dto.getIbsadate()
								, dto.getCity_name(), dto.getTel(), dto.getBuseo_name()
								, dto.getJikwi_name(), dto.getBasicpay(), dto.getSudang());
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// 직원 전체 출력(1.사번정렬  2.이름정렬  3.부서정렬  4.직위정렬  5.급여내림차순정렬)
	public ArrayList<MemberDTO> lists(int menu) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		String sql = "";
		
		Statement stmt = conn.createStatement();
		
		if (menu == 1)		// 1.사번정렬
		{
			sql = "SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
					+ ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG"
					+ ", (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
					+ " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
					+ " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
					+ " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
					+ " ORDER BY EMP_ID";
		}
		else if (menu == 2)	// 2.이름정렬
		{
			sql = "SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
					+ ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG"
					+ ", (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
					+ " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
					+ " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
					+ " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
					+ " ORDER BY EMP_NAME";
		}
		else if (menu == 3)	// 3.부서정렬 
		{
			sql = "SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
					+ ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG"
					+ ", (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
					+ " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
					+ " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
					+ " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
					+ " ORDER BY BUSEO_NAME";
		}		
		else if (menu == 4)	// 4.직위정렬 
		{
			sql = "SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
					+ ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG"
					+ ", (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
					+ " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
					+ " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
					+ " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
					+ " ORDER BY JIKWI_NAME";
		}
		else if (menu == 5)	// 5.급여내림차순정렬 
		{
			sql = "SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
					+ ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG"
					+ ", (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
					+ " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
					+ " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
					+ " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
					+ " ORDER BY SALARY DESC";
		}
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmp_id(rs.getInt("EMP_ID"));
			dto.setEmp_name(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCity_name(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseo_name(rs.getString("BUSEO_NAME"));
			dto.setJikwi_name(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setSalary(rs.getInt("SALARY"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 직원 검색 출력(1.사번검색)
	public ArrayList<MemberDTO> searchLists(int search) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		
		// 1.사번검색
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, SALARY"
								 + " FROM"
								 + " (SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
								 + ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG, (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
								 + " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
								 + " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
								 + " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
								 + " ORDER BY EMP_ID)"
								 + " WHERE EMP_ID = %d", search);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmp_id(rs.getInt("EMP_ID"));
			dto.setEmp_name(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCity_name(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseo_name(rs.getString("BUSEO_NAME"));
			dto.setJikwi_name(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setSalary(rs.getInt("SALARY"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 직원 검색 출력(2.이름검색  3.부서검색  4.직위검색)
		public ArrayList<MemberDTO> searchLists(int menu, String search) throws SQLException
		{
			ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
			String sql = "";
			
			Statement stmt = conn.createStatement();
			
			if (menu == 2) 	 	// 2.이름검색
			{
				sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, SALARY"
								 + " FROM"
								 + " (SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
								 + ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG, (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
								 + " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
								 + " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
								 + " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
						 		 + " ORDER BY EMP_ID)"
						 		 + " WHERE EMP_NAME = '%s'", search);
			}
			else if (menu == 3)	// 3.부서검색
			{
				sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, SALARY"
								 + " FROM"
								 + " (SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
								 + ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG, (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
								 + " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
								 + " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
								 + " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
								 + " ORDER BY EMP_ID)"
								 + " WHERE BUSEO_NAME = '%s'", search);
			}
			else if (menu == 4) 	// 4.직위검색
			{
				sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, SALARY"
						 	 	 + " FROM"
						 	 	 + " (SELECT E.EMP_ID, E.EMP_NAME, E.SSN, TO_CHAR(E.IBSADATE, 'YYYY-MM-DD') AS IBSADATE, C.CITY_NAME, E.TEL"
						 	 	 + ", B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, E.SUDANG, (NVL(E.BASICPAY, 0) + NVL(E.SUDANG, 0)) AS SALARY"
						 	 	 + " FROM TBL_EMP E JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
						 	 	 + " JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
						 	 	 + " JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
						 	 	 + " ORDER BY EMP_ID)"
						 	 	 + " WHERE JIKWI_NAME = '%s'", search);
			}		
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				MemberDTO dto = new MemberDTO();
				
				dto.setEmp_id(rs.getInt("EMP_ID"));
				dto.setEmp_name(rs.getString("EMP_NAME"));
				dto.setSsn(rs.getString("SSN"));
				dto.setIbsadate(rs.getString("IBSADATE"));
				dto.setCity_name(rs.getString("CITY_NAME"));
				dto.setTel(rs.getString("TEL"));
				dto.setBuseo_name(rs.getString("BUSEO_NAME"));
				dto.setJikwi_name(rs.getString("JIKWI_NAME"));
				dto.setBasicpay(rs.getInt("BASICPAY"));
				dto.setSudang(rs.getInt("SUDANG"));
				dto.setSalary(rs.getInt("SALARY"));
				
				result.add(dto);
			}
			
			rs.close();
			stmt.close();
			
			return result;
		}
	
	// 직원 정보 수정(먼저 리스트 띄워주고 → 수정 진행)
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("UPDATE TBL_EMP"
								+ " SET EMP_NAME = '%s', SSN = '%s', IBSADATE = '%s'"
								+ ", CITY_ID = (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s')"
								+ ", TEL = '%s'"
								+ ", BUSEO_ID = (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
								+ ", JIKWI_ID = (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
								+ ", BASICPAY = %d, SUDANG = %d"
								+ " WHERE EMP_ID = %d"
								, dto.getEmp_name(), dto.getSsn(), dto.getIbsadate(), dto.getCity_name()
								, dto.getTel(), dto.getBuseo_name(), dto.getJikwi_name()
								, dto.getBasicpay(), dto.getSudang(), dto.getEmp_id());
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// 직원 정보 삭제(먼저 리스트 띄워주고 → 한 번 더 물어본 후에 삭제 진행)
	public int remove(int emp_id) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("DELETE TBL_EMP WHERE EMP_ID = %d", emp_id);
		
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		
		return result;
	}
	
	// Process에서 사용할 리스트 출력 메소드(지역,부서,직위)
	public void printList(String name) throws SQLException
	{
		String sql = "";	
		Statement stmt = conn.createStatement();
		
		if (name.equalsIgnoreCase("city"))
		{
			sql = "SELECT LISTAGG(CITY_NAME, '/') WITHIN GROUP(ORDER BY CITY_ID) AS LIST FROM TBL_CITY";
		}
		else if ((name.equalsIgnoreCase("buseo"))) 
		{
			sql = "SELECT LISTAGG(BUSEO_NAME, '/') WITHIN GROUP(ORDER BY BUSEO_ID) AS LIST FROM TBL_BUSEO";
		}
		else if ((name.equalsIgnoreCase("jikwi")))
		{
			sql = "SELECT LISTAGG(JIKWI_NAME, '/') WITHIN GROUP(ORDER BY JIKWI_ID) AS LIST FROM TBL_JIKWI";
		}
		
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next())
			System.out.print(rs.getString("LIST"));

		rs.close();
		stmt.close();
	}
	
	// Process에서 사용할 리스트 출력 메소드(최소 기본급)
	public void printList_Basicpay(String jikwi_name) throws SQLException
	{
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwi_name);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
			System.out.print(rs.getInt("MIN_BASICPAY"));
		
		rs.close();
		stmt.close();
	}
	
	// 데이터베이스 연결 종료
	public void close()
	{
		DBConn.close();
	}
}
