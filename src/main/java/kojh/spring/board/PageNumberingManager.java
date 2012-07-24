package kojh.spring.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageNumberingManager
{
	private static final PageNumberingManager pageNumberingManager = new PageNumberingManager();

	private static final Logger logger = LoggerFactory.getLogger(PageNumberingManager.class);
	
	private PageNumberingManager() 
	{
		
	}
	
	public  static PageNumberingManager getInstance()
	{
		return pageNumberingManager ;
	}
	
	public int getTotalPage(int total_cnt, int rowsPerPage)
	{
		logger.info("getTotalPage called!!");
		
		int total_pages = 0;
		
		if ((total_cnt % rowsPerPage) == 0)
            total_pages = total_cnt / rowsPerPage;
        else
            total_pages = (total_cnt / rowsPerPage) + 1;
		
		logger.info("getTotalPage return total_pages="+total_pages);
		return total_pages;		
	}
	
	//게시판의 block처리 추가 필요 (이전/다음 블럭 버튼 처리)
	public int getPageBlock(int curPage, int pagePerBlock)
	{
		int block = 0;
		
		if ((curPage % pagePerBlock) == 0) 
		{
		   block = curPage / pagePerBlock;
		} 
		else 
		{
		   block = curPage / pagePerBlock + 1;
		}
		return block;
	}
	
	// block 에 속한 첫번째 페이지 계산
	public int getFirstPageInBlock(int block, int pagePerBlock)
	{		 
		return ( ( block - 1 ) * pagePerBlock + 1 ) ;
	}

	// block 에 속한 마지막 페이지 계산	
	public int getLastPageInBlock(int block, int pagePerBlock)
	{
		return ( block * pagePerBlock);
	}
	
	/*
	 // 현재 block > 1 면 [이전]링크를 만들고 firstPage - 1 페이지로 링크
	int prevPage = 0;
	if(block > 1) {
	  prevPage = firstPage - 1;
	%>
	 <a href="list.jsp?curPage=<%=prevPage %>">[이전]</a>
	<%
	}
	
	// 루프문을 이용해서 첫번째 페이지부터 마지막 페이지까지 링크를 만든다. 
	for(int i = firstPage+1; i <= lastPage; i++) {
	%>
	    <a href="list.jsp?curPage=<%=i %>">[<%=i %>]</a>    	
	<%
	// block < totalBlock 면 [다음]링크를 만들고  lastPage + 1 를 페이지번호로 링크 
	
	if(block < totalBlock) {
	  int nextPage = lastPage + 1;
	%>
	  <a href="list.jsp?curPage=<%=nextPage %>">[다음]</a>
	<%
	} 
	*/
}
