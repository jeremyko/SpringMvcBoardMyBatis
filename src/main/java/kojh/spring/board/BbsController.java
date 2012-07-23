package kojh.spring.board;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import kojh.db.beans.BoardBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BbsController
{
	@Autowired
	BoardService boardService;
			
	/*
	 Spring 3 이후부터는 Bean Validation에 대한 표준을 정의한 JSR-303 Spec.을 지원하고 있다. 
	 Validation은 선언적인 형태와 프로그램적인 형태로 구분할 수 있으며 Hibernate Validator와 같은 
	 JSR-303 Spec.을 구현한 구현체를 연계하여 처리된다.	 
	 즉, Hibernate 추가해서 Validation 처리
	*/
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/show_write_form", method = RequestMethod.GET)
	public String show_write_form( Model model) 
	{
		logger.info("show_write_form called!!");
				
		// 객체를 전달해서 값을 얻어와야 함!!!		
		model.addAttribute("boardBeanObjToWrite", new BoardBean());
					
		return "writeBoard";		
	}
		
	//test---> OK!!
	/*
	@RequestMapping( value = "/DoWriteBoard" ,method = RequestMethod.POST)
	//public String PostWork( @ModelAttribute("boardBeanObjToWrite") BoardBean boardBeanObj, Model model) //OK!! 
	public String PostWork( BoardBean boardBeanObjToWrite, Model model) //OK Too!!
	{		
		logger.info("PostWork called!!");
		logger.info("memo=["+boardBeanObjToWrite.getMemo()+"]");
		return "PostWork";
	}
	*/
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/DoWriteBoard", method = RequestMethod.POST)
	public String DoWriteBoard( BoardBean boardBeanObjToWrite, Model model) // 이것도 동작하고 위처럼 @ModelAttribute 사용해도 됨
	{
		logger.info("DoWriteBoard called!!");
		logger.info("memo=["+boardBeanObjToWrite.getMemo()+"]");
						
		//저장!!
		boardService.insertBoard(boardBeanObjToWrite);
				
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ko"));		
		//String formattedDate = dateFormat.format(date);		
		//model.addAttribute("serverTime", formattedDate );
		
		//목록을 조회후 저장 시킴.
		model.addAttribute("totalCnt", new Integer(boardService.getTotalCnt()) ); //Integer objects
		model.addAttribute("boardList", boardService.getList(1, 2) ); // 1 page 내용을 출력(2개 rows)
			
		return "home";		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//개별 목록 조회
	@RequestMapping(value = "/viewWork", method = RequestMethod.GET)
	public String viewWork	( 
								@RequestParam("memo_id") String memo_id,
								@RequestParam("current_page") String current_page,
								 Model model
							) 
	{
		logger.info("viewWork called!!");
		logger.info("memo_id=["+ memo_id+"] current_page=["+current_page+"]");
			
		model.addAttribute("memo_id", memo_id ); 
		model.addAttribute("current_page", current_page ); 
		model.addAttribute("boardData", boardService.getSpecificRow(memo_id) ); 
		
			
		return "viewMemo";		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 특정 페이지에서 작업중 목록으로 나올경우, 이전 페이지 번호를 참조해서 해당 페이지 출력
	@RequestMapping(value = "/listSpecificPageWork", method = RequestMethod.GET)
	public String listSpecificPageWork	(								
								@RequestParam("current_page") String pageForView,
								Model model
							) 
	{
		logger.info("listSpecificPageWork called!!");
		logger.info("current_page=["+pageForView+"]");
				 
		model.addAttribute("totalCnt", new Integer(boardService.getTotalCnt()) );
		model.addAttribute("current_page", pageForView ); 
		model.addAttribute("boardList", boardService.getList( Integer.parseInt(pageForView), 2)); 
					
		return "listSpecificPage";		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 특정 페이지 수정을 위한 내용 출력
	@RequestMapping(value = "/listSpecificPageWork_to_update", method = RequestMethod.GET)
	public String listSpecificPageWork_to_update	(	
								@RequestParam("memo_id") String memo_id,
								@RequestParam("current_page") String current_page,
								Model model
							) 
	{
		logger.info("listSpecificPageWork_to_update called!!");
		logger.info("memo_id=["+ memo_id+"] current_page=["+current_page+"]");
			
		model.addAttribute("memo_id", memo_id ); 
		model.addAttribute("current_page", current_page ); 
		model.addAttribute("boardData", boardService.getSpecificRow(memo_id) ); 
		
			
		return "viewMemoForUpdate";		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//개별 row 업데이트
	@RequestMapping(value = "/DoUpdateBoard", method = RequestMethod.POST)
	public String DoUpdateBoard( 
			                    BoardBean boardBeanObjToUpdate, 
			                    @RequestParam("memo_id") int memo_id, //String,int 둘다 작동됨
			                    @RequestParam("current_page") String current_page,
								Model model) 
	{
		logger.info("DoUpdateBoard called!!");
		logger.info("listSpecificPageWork_to_update called!!");
		
		//boardBeanObjToUpdate.getId() 가 0이다! 값을 설정하지 않았기 때문이다. 대신,memo_id 를 이용하자(그런데 String ?) 
		logger.info("memo_id=["+ memo_id+""+"/"+boardBeanObjToUpdate.getId()+"] current_page=["+current_page+"]");
		logger.info("memo=["+boardBeanObjToUpdate.getMemo()+"]");						
				
		boardBeanObjToUpdate.setId(memo_id); // 약간의 꼼수...
		
		boardService.updateBoard(	boardBeanObjToUpdate	);				
		
		model.addAttribute("totalCnt", new Integer(boardService.getTotalCnt()) );
		model.addAttribute("current_page", current_page ); 
		model.addAttribute("boardList", boardService.getList( Integer.parseInt(current_page), 2));
			
		return "listSpecificPage";		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 삭제 DeleteSpecificRow
	@RequestMapping(value = "/DeleteSpecificRow", method = RequestMethod.GET)
	public String DeleteSpecificRow(@RequestParam("memo_id") int memo_id,
									@RequestParam("current_page") String current_page, 
									Model model)
	{
		logger.info("DeleteSpecificRow called!!");
		logger.info("memo_id=[" + memo_id + "] current_page=[" + current_page + "]");

		boardService.deleteRow(memo_id);
		
		//다시 페이지를 조회한다.
		model.addAttribute("totalCnt", new Integer(boardService.getTotalCnt()) );
		model.addAttribute("current_page", current_page);
		model.addAttribute("boardList", boardService.getList( Integer.parseInt(current_page), 2));

		return "listSpecificPage";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////	
	@RequestMapping(value = "/searchWithSubject", method = RequestMethod.POST)
	public String searchWithSubject	(	@RequestParam("searchStr") String searchStr,										
										Model model	) 
	{
		/*
		logger.info("listSpecificPageWork_to_update called!!");
		logger.info("searchStr=["+ searchStr+"]");
				 
		model.addAttribute("totalCnt", new Integer(boardService.getTotalCntBySubject(searchStr)) );		
		model.addAttribute("searchedList", boardService.getSearchedList( 1,2, searchStr) ); //처음에는 1 페이지만 보여줌
		model.addAttribute("searchStr", searchStr );
					
		return "listSearchedPage";
		*/		
		
		//redirection...
		return listSearchedSpecificPageWork("1", searchStr, model);//처음에는 1 페이지만 보여줌
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 검색된 상태에서 특정 페이지로 이동하기
	@RequestMapping(value = "/listSearchedSpecificPageWork", method = RequestMethod.GET)
	public String listSearchedSpecificPageWork	(	@RequestParam("pageForView") String pageForView,
													@RequestParam("searchStr") String searchStr,	
													Model model ) 
	{
		logger.info("listSearchedSpecificPageWork called!!");
		logger.info("pageForView=["+pageForView+"]");
		logger.info("searchStr=["+searchStr+"]");
				 
		model.addAttribute("totalCnt", new Integer( boardService.getTotalCntBySubject(searchStr) ) );		
		model.addAttribute("searchedList", boardService.getSearchedList(Integer.parseInt(pageForView),2, searchStr) );
		model.addAttribute("pageForView", Integer.parseInt(pageForView) );
		model.addAttribute("searchStr", searchStr );
					
		return "listSearchedPage";		
	}
	
			
}
