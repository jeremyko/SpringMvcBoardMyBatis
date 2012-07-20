// http://www.mybatis.org/core/ko/java-api.html
// http://loianegroner.com/2011/02/getting-started-with-ibatis-mybatis-annotations/

package kojh.spring.mappers;

import java.util.ArrayList;



import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import kojh.db.beans.BoardBean;

public interface  BoardMapper
{
	//조회할 범위가 지정된다.
	/*
	 --page1
	 5          <--- rownum =1
	 4          <--- rownum =2
	 --page2
	 3          <--- rownum =3
	 2          <--- rownum =4
	 --page3
	 1          <--- rownum =5
	  
	 * 2 page 조회
	 startRowNum = total_cnt - (nStartPage - 1) * list_num; 
	 3           = 5         - ((2-1)           * 2 ) 
				
	SELECT RNUM,ID,SUBJECT,NAME,TO_CHAR(CREATED_DATE, 'yyyy/mm/dd hh:mi:ss'), MAIL,MEMO,HITS FROM (
	    SELECT RNUM, ID,SUBJECT,NAME, CREATED_DATE, MAIL,MEMO,HITS FROM (
	    	SELECT ROWNUM RNUM, ID,SUBJECT,NAME, CREATED_DATE, MAIL,MEMO,HITS FROM SPRING_BOARD  ORDER BY ID DESC
	    ) WHERE RNUM <= 3 (startRowNum)
	) WHERE RNUM <= 2 (list_num)
	
	// http://www.javaservice.net/~java/bbs/read.cgi?m=etc&b=dbms&c=r_p&n=1040698828&p=10&s=t
	SELECT * FROM (
		SELECT	/ *+ INDEX(articles 정렬하고 싶은 인덱스명) * /  
				article_id, title,date ceil( rownum / 한화면당 갯수 ) as page
				FROM articles
		WHERE 조건들....
	) WHERE page = 출력하고 싶은 페이지번호
	
	SELECT ID,SUBJECT,NAME,TO_CHAR(CREATED_DATE,'yyyy/mm/dd hh:mi:ss'), MAIL,MEMO,HITS
	FROM	
	(
		SELECT	/ *+ INDEX_DESC(A SYS_C007223) * / 
	        	ID,SUBJECT,NAME, CREATED_DATE, MAIL,MEMO,HITS, ceil( rownum / 2 ) as page
		FROM 	SPRING_BOARD	A
	) WHERE page = 2	
	*/
	
	final String SELECT_ALL = "SELECT ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO,HITS from SPRING_BOARD ORDER BY ID DESC";
	
	final String SELECT_BY_ID = "SELECT ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO,HITS from SPRING_BOARD WHERE ID=#{id}";
	
	final String SELECT_CNT_BY_SUBJECT = "SELECT count(1) FROM SPRING_BOARD WHERE SUBJECT LIKE '%#{likeThis}%'";
	
	final String SELECT_CNT_ALL = "SELECT count(1) FROM SPRING_BOARD";
	
	final String INSERT = "INSERT INTO SPRING_BOARD (ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO) " +
		"VALUES( SEQ_ID.NEXTVAL,#{subject}, #{name}, SYSDATE, #{mail}, #{memo})";
	//BoardBean 의 속성들과 도일한 이름으로 #{mail} 등을 지정해야한다.
			
	@Select(SELECT_ALL)
	@Results(value = {
	        @Result(property="id", column="ID"),
	        @Result(property="subject", column="SUBJECT"),
	        @Result(property="name", column="NAME"),
	        @Result(property="created_date", column="CREATED_DATE"),
	        @Result(property="mail", column="MAIL"),
	        @Result(property="memo", column="MEMO"),
	        @Result(property="hits", column="HITS")
	    })
	ArrayList<BoardBean> getList(int nStartPage, String dbsearch,int list_num);
	
	
	@Select(SELECT_BY_ID)
	@Results(value = {
	        @Result(property="id", column="ID"),
	        @Result(property="subject", column="SUBJECT"),
	        @Result(property="name", column="NAME"),
	        @Result(property="created_date", column="CREATED_DATE"),
	        @Result(property="mail", column="MAIL"),
	        @Result(property="memo", column="MEMO"),
	        @Result(property="hits", column="HITS")
	    })
	BoardBean getSpecificRow(@Param("id") String id);
	
		
	// 전체 글 갯수를 조회
	@Select(SELECT_CNT_ALL)
	int getTotalCnt();
	
	// 해당 주제의 관련글 갯수를 조회
	@Select(SELECT_CNT_BY_SUBJECT)
	int getTotalCntBySubject(@Param("likeThis") String includingThis);
		
	@Insert(INSERT)
	//@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertBoard (BoardBean boardBean);
	
	
}
