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
}
