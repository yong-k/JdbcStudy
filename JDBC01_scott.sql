SELECT USER
FROM DUAL;
--==>> SCOTT


DROP TABLE TBL_MEMBER PURGE;
--==>> Table TBL_MEMBER이(가) 삭제되었습니다.


--○ 실습 테이블 생성
CREATE TABLE TBL_MEMBER
( SID   NUMBER
, NAME  VARCHAR2(30)
, TEL   VARCHAR2(60)
, CONSTRAINT MEMBER_SID_PK PRIMARY KEY(SID)
);
--==>> Table TBL_MEMBER이(가) 생성되었습니다.


--○ 샘플 데이터 입력
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(1, '홍길동', '010-1111-1111');
--==>> 1 행 이(가) 삽입되었습니다.


--○ 확인
SELECT *
FROM TBL_MEMBER;
--==>> 1	홍길동	010-1111-1111


--○ 커밋
COMMIT;
--==>> 커밋 완료.


-- INSERT, UPDATE, DELETE 쿼리문 작성후에,커밋해주지 않은 상태에서
-- (=오라클에서 트랜잭션 처리가 끝나지 않은 상태에서) 
-- 자바로 처리하게 되면데이터 액션 처리가 이루어지지 않는다.


--○ 자바에서 Test003.java 실행 후 다시 확인
SELECT *
FROM TBL_MEMBER;
--==>> 
/*
1	홍길동	010-1111-1111
2	김정용	010-2731-3153
*/


--○ 자바에서 Test004.java 실행 후 다시 확인
SELECT *
FROM TBL_MEMBER;
--==>>
/*
1	홍길동	010-1111-1111
2	김정용	010-2731-3153
3	김정용	010-3333-3333
4	오이삭	010-4444-4444
5	김태형	010-5555-5555
*/


--○ 조회 쿼리문 준비
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
-- 이클립스로 데려갈 때는 
--> 한 줄 구성
--> 세미콜론은 밑으로 탈락시킨 다음 가져갈 거임
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;

-- LINE 71 데리고 이클립스 ㄱㄱ





