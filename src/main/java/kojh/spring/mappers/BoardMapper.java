// http://www.mybatis.org/core/ko/java-api.html
// http://loianegroner.com/2011/02/getting-started-with-ibatis-mybatis-annotations/

package kojh.spring.mappers;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import kojh.db.beans.BoardBean;

public interface  BoardMapper
{			
	final String SELECT_PAGE = "SELECT * FROM (	SELECT	ID,SUBJECT,NAME, CREATED_DATE, MAIL,MEMO,HITS, ceil( rownum / #{rowsPerPage} ) as page "+
			"FROM SPRING_BOARD  ORDER BY ID DESC ) WHERE page = #{page}";
	
	final String SELECT_BY_ID = "SELECT ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO,HITS from SPRING_BOARD WHERE ID=#{id}";
	
	//  '%#{searchThis}%' 로 사용시 에러! java.sql.SQLException: 부적합한 열 인덱스
	final String SELECT_CNT_BY_SUBJECT = "SELECT COUNT(1) FROM SPRING_BOARD WHERE SUBJECT LIKE '%'||'${searchThis}'||'%'";
		
	final String SELECT_ROWS_BY_SUBJECT = "SELECT * FROM (SELECT ID,SUBJECT,NAME, CREATED_DATE, MAIL,MEMO,HITS, "+
			"ceil( rownum / #{rowsPerPage}) as page FROM SPRING_BOARD  WHERE SUBJECT LIKE '%'||'${likeThis}'||'%' ORDER BY ID DESC ) WHERE page = #{page}";
		
	final String SELECT_CNT_ALL = "SELECT count(1) FROM SPRING_BOARD";
	
	final String INSERT = "INSERT INTO SPRING_BOARD (ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO) " +
		"VALUES( SEQ_ID.NEXTVAL,#{subject}, #{name}, SYSDATE, #{mail}, #{memo})";
	
	final String UPDATE_BY_ID = "UPDATE SPRING_BOARD SET SUBJECT= #{subject},MAIL= #{mail},MEMO= #{memo} WHERE ID= #{id}";
	   
	final String DELETE_BY_ID= "DELETE FROM SPRING_BOARD WHERE ID=#{id}";
	
	
	//BoardBean 의 속성들과 동일한 이름으로 #{mail} 등을 지정해야한다.			
	@Select(SELECT_PAGE)
	@Results(value = {
	        @Result(property="id", column="ID"),
	        @Result(property="subject", column="SUBJECT"),
	        @Result(property="name", column="NAME"),
	        @Result(property="created_date", column="CREATED_DATE"),
	        @Result(property="mail", column="MAIL"),
	        @Result(property="memo", column="MEMO"),
	        @Result(property="hits", column="HITS")
	    })
	ArrayList<BoardBean> getList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage);
	
	
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
	int getTotalCntBySubject(@Param("searchThis") String includingThis);
	
	//해당 주제의 관련글 조회
	@Select(SELECT_ROWS_BY_SUBJECT)
	@Results(value = {
	        @Result(property="id", column="ID"),
	        @Result(property="subject", column="SUBJECT"),
	        @Result(property="name", column="NAME"),
	        @Result(property="created_date", column="CREATED_DATE"),
	        @Result(property="mail", column="MAIL"),
	        @Result(property="memo", column="MEMO"),
	        @Result(property="hits", column="HITS")
	    })
	public ArrayList<BoardBean> getSearchedList(@Param("page") int page, 
												@Param("rowsPerPage") int rowsPerPage, 
												@Param("likeThis") String strSearchThis);
	
	@Insert(INSERT)
	//@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertBoard (BoardBean boardBean);
		
	@Update(UPDATE_BY_ID)
	void updateBoard (@Param("id") int id,@Param("subject") String subject, @Param("mail") String mail,@Param("memo") String memo);	
	
	@Delete(DELETE_BY_ID)
	void deleteSpecificRow(@Param("id") int id);
	
}
