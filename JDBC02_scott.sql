SELECT USER
FROM DUAL;
--==>> SCOTT


-- 기존 들어있던 데이터 제거
TRUNCATE TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER이(가) 잘렸습니다.


-- 시퀀스 생성
CREATE SEQUENCE MEMBERSEQ
NOCACHE;
--==>> Sequence MEMBERSEQ이(가) 생성되었습니다.


--○ 데이터 입력 쿼리문 구성
INSERT INTO TBL_MEMBER(SID, NAME, TEL)
VALUES(MEMBERSEQ.NEXTVAL, '이호석', '010-1111-1111');
--==>> 1 행 이(가) 삽입되었습니다.
--> 이클립스에서 가져다 쓸거라 한 줄로 구성
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, '이호석', '010-1111-1111')
;


--○ 인원 수 확인 쿼리문 구성
SELECT COUNT(*) AS COUNT
FROM TBL_MEMBER;
--==>> 1
--> 이클립스에서 가져다 쓸거라 한 줄로 구성
SELECT COUNT(*) AS COUNT FROM TBL_MEMBER
;


--○ 전체 리스트 조회 쿼리문 구성
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--> 한 줄 구성
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;
--==>> 1	이호석	010-1111-1111


--○ 커밋
COMMIT;
--==>> 커밋 완료.





