package kojh.spring.board;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
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
		System.out.println("boardMapper.getList() 호출!![nStartPage="+nStartPage +",dbsearch="+dbsearch + ",list_num="+list_num+"]");
		return this.boardMapper.getList(nStartPage, dbsearch, list_num);		
	}
	
	public BoardBean getSpecificRow(String id)
	{
		System.out.println("boardMapper.getSpecificRow() 호출!!");
		return this.boardMapper.getSpecificRow(id);
	}
	
	public int getTotalCnt(String search)
	{
		int nCnt = 0;
		if(null == search )
		{
			System.out.println("boardMapper.getTotalCnt() 호출!!");
			nCnt= this.boardMapper.getTotalCnt();
		}
		else
		{
			System.out.println("boardMapper.getTotalCntBySubject() 호출!!");			
			nCnt= this.boardMapper.getTotalCntBySubject(search) ;			
		}
		System.out.println("nCnt="+nCnt);
		return nCnt;		
	}
	
	public void insertBoard (BoardBean boardBean)
	{
		boardMapper.insertBoard(boardBean);
	}
	
}
