// http://www.mybatis.org/core/ko/java-api.html
// http://loianegroner.com/2011/02/getting-started-with-ibatis-mybatis-annotations/

package kojh.spring.mappers;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import kojh.db.beans.BoardBean;

public interface  BoardMapper
{
	final String SELECT_ALL = "SELECT ID,SUBJECT,NAME,CREATED_DATE,MAIL,MEMO,HITS from SPRING_BOARD";
	
	@Select(SELECT_ALL)
	@Results(value = {
	        @Result(property="id", column="ID"),
	        @Result(property="subject", column="SUBJECT"),
	        @Result(property="name", column="NAME"),
	        @Result(property="date", column="CREATED_DATE"),
	        @Result(property="mail", column="MAIL"),
	        @Result(property="memo", column="MEMO"),
	        @Result(property="hits", column="HITS")
	    })
	ArrayList<BoardBean> getList(int nStartPage, String dbsearch,int list_num);
	
	//import org.apache.ibatis.annotations.Param;
	//@Select("select ID,SUBJECT,NAME,DATE,MAIL,MEMO,HITS from SPRING_BOARD WHERE id = #{userId}")
	//BoardBean getUserList(@Param("userId") String userId);
	
}
