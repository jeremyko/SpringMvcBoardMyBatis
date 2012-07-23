
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ page session="false" %>

<%@ page import="kojh.db.beans.BoardBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="kojh.spring.board.PageNumberingManager"%>

<html>
<head>
<title>목록</title>
</head>

<%--
<c:set var="list" value="${searchedList}" />
--%>

<c:set var="total_cnt" value="${totalCnt}" />
<c:set var="searchStr" value="${searchStr}" />

<%
    //ArrayList<BoardBean> list = (ArrayList<BoardBean>) pageContext.getAttribute("list") ; //jstl in code
    int total_cnt = ((Integer)(pageContext.getAttribute("total_cnt"))).intValue()  ;
    String searchStr =(String) pageContext.getAttribute("searchStr");
    String str_c_page = "1";    
    int c_page = 1;
%>


<table cellspacing=1 width=700 border=0>
    <tr>
        <td>총 게시물수: <c:out value="${totalCnt}"/></td>
        <td><p align=right> 페이지:<%=c_page%> 
        </td>
    </tr>
</table>

<table cellspacing=1 width=700 border=1>
    <tr>
        <td width=50><p align=center>번호</p>
        </td>
        <td width=100><p align=center>이름</p>
        </td>
        <td width=320><p align=center>제목</p>
        </td>
        <td width=100><p align=center>등록일</p>
        </td>
        <td width=100><p align=center>조회수</p>
        </td>
    </tr>
   
    <c:forEach var="board" items="${searchedList}">
        <tr>
        <td width=50><p align=center>${board.getId()}</p></td>
        <td width=100><p align=center>${board.getName()}</p></td>                
        <td width=320>
            <p align=center>
                <a href="/SpringMvcBoardMyBatis/viewWork?memo_id=${board.getId()}&current_page=<%=str_c_page%>" title="${board.getMemo()}"><c:out value="${board.getSubject()}"/>
            </p>
        </td>                      
        <td width=100><p align=center><c:out value="${board.getCreated_date()}"/></p></td>
        <td width=100><p align=center><c:out value="${board.getHits()}"/></p></td>
    </tr>
    </c:forEach>
    
    
    
    <%-- 다음의 페이지 처리하는 부분도 별도의 클래스에서 처리할수도 있겠다.. --%>

    <%                  
        int rowsPerPage = 2;
        
        ///////////////////////////////////////////////////////////////
        //전체 페이지
        int total_pages = PageNumberingManager.getInstance().getTotalPage(total_cnt, rowsPerPage) ;
        
        int block_num = 2;
        int total_blocks = total_pages / block_num;

        if (total_pages % block_num != 0) // 2%5 = 2
        {
            System.out.print("total_blocks++ !");
            total_blocks++;
        }

        int c_block = c_page / block_num;

        if (c_page % block_num != 0)
        {
            c_block++;
        }
        System.out.print("total_blocks++ !"+total_blocks);
    %>


</table>

<table cellspacing=1 width=700 border=1 >
    <tr>
        <td>        
            <%
            for (int i = 1; i <= total_pages; i++) 
            {
            %>           
                <a href="/SpringMvcBoardMyBatis/listSearchedSpecificPageWork?pageForView=<%=i%>&searchStr=<c:out value="${searchStr}"/>" >                
                
                <%
                if (c_page == i)
                    out.print("<b>");
                %> 
                [<%=i%>]
                <%
                if (c_page == i)
                    out.print("</b>");
                %>            
            <%
            }
            %>        
        </td>
    </tr>
</table>
<table width=700>
    <tr>
        <td><input type=button value="전체 목록으로 돌아가기"  OnClick="window.location='/SpringMvcBoardMyBatis/'">    </td>        
    </tr>
</table>
</html>