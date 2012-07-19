package kojh.spring.board;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kojh.db.beans.BoardBean;
import kojh.spring.mappers.BoardMapper;

@Component
public class BoardService
{
	@Autowired
	private BoardMapper boardMapper;

	/*
	// @Autowired 로 처리했으므로 setter 불필요 
	public void setBoardMapper(BoardMapper boardMapper) 
	{
		this.boardMapper = boardMapper;
	}
	*/

	public ArrayList<BoardBean> getList(int nStartPage, String dbsearch,int list_num)
	{
		return this.boardMapper.getList(nStartPage, dbsearch, list_num);
		
		//ArrayList<BoardBean> arryListInfoBeanRtn = new ArrayList<BoardBean> ();
		//return arryListInfoBeanRtn;
	}
	
	/*
	public  BoardBean doSomeBusinessStuff(String userId) 
	{
		return this.boardMapper.getUser(userId);
	}
	*/
}
