SELECT USER
FROM DUAL;
--==>> SCOTT


SELECT *
FROM TBL_MEMBER
ORDER BY SID;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
*/


--○ 데이터 입력 쿼리문 구성
INSERT INTO TBL_MEMBER(SID, NAME, TEL)
VALUES(MEMBERSEQ.NEXTVAL, '김상기', '010-4444-4444');
--> 한 줄 구성
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, '김상기', '010-4444-4444')
;
--==>> 1 행 이(가) 삽입되었습니다.


--○ 확인
SELECT *
FROM TBL_MEMBER
ORDER BY SID;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
4	김상기	010-4444-4444
*/


--○ 커밋
COMMIT;
--==>> 커밋 완료.


--○ 데이터 전체 조회 쿼리문 구성
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--> 한 줄 구성
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
4	김상기	010-4444-4444
*/


SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
4	김상기	010-4444-4444
5	한충희	010-5555-5555
*/


SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
4	김상기	010-4444-4444
5	한충희	010-5555-5555
6	박현수	010-6666-6666
*/


SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--==>>
/*
1	이호석	010-1111-1111
2	오이삭	010-2222-2222
3	임소민	010-3333-3333
4	김상기	010-4444-4444
5	한충희	010-5555-5555
6	박현수	010-6666-6666
30	정은정	010-3030-3030
31	김정용	010-3131-3131
*/


--------------------------------------------------------------------------------
DESC TBL_SCORE;
SELECT SID, NAME, KOR, ENG, MAT
     , (KOR+ENG+MAT) AS TOT
     , ROUND((KOR+ENG+MAT)/3, 2) AS AVG
     , RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK
FROM TBL_SCORE;

--○ 필요 쿼리문 작성
-- 뷰 생성
CREATE OR REPLACE VIEW VIEW_SCORE
AS
SELECT SID, NAME, KOR, ENG, MAT
     , (KOR+ENG+MAT) AS TOT
     , ROUND((KOR+ENG+MAT)/3, 2) AS AVG
     , RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK
FROM TBL_SCORE
ORDER BY SID;

-- 시퀀스의 다음 번호 조회
SELECT LAST_NUMBER AS NEXTSID
FROM USER_SEQUENCES
WHERE SEQUENCE_NAME = 'SCORESEQ';
--> 한 줄 구성
SELECT LAST_NUMBER AS NEXTSID FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'SCORESEQ'
;

-- 전체 학생 수 출력
SELECT COUNT(*) AS COUNT FROM TBL_SCORE
;

-- 검색 학생 수 출력
--1.학번
SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE SID = 1
;
--2.이름
SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE NAME = '김정용'
;
--3.석차
SELECT COUNT(*) AS COUNT FROM VIEW_SCORE WHERE RANK = 1
;

-- 성적 정보 입력
INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, ?, ?, ?, ?)
;

-- 성적 전체 조회
--(학번/이름/석차 정렬)
SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK FROM VIEW_SCORE 
--ORDER BY SID
--ORDER BY NAME
--ORDER BY RANK
;

-- 성적 검색 조회
--(학번/이름/석차 검색)
SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK FROM VIEW_SCORE WHERE SID = 1
;
--WHERE NAME = '김정용'
--WHERE RANK = 1

-- 성적 정보 수정
UPDATE TBL_SCORE SET NAME = ?, kor = ?, eng = ?, mat = ? WHERE SID = ?
;

-- 성적 정보 삭제
DELETE TBL_SCORE WHERE SID = ?
;

commit;

SELECT *
FROM VIEW_SCORE;




