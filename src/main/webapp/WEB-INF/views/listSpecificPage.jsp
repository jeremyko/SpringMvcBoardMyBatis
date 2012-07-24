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

<c:set var="current_page" value="${current_page}" />
<c:set var="total_cnt" value="${totalCnt}" />

<%
    int c_page = Integer.parseInt( (String)  (pageContext.getAttribute("current_page") ))  ;
    pageContext.setAttribute("c_page",c_page);              
%>

<table cellspacing=1 width=700 border=0>
    <tr>
        <td>총 게시물수: <c:out value="${totalCnt}"/></td>
        <td><p align=right> 페이지:<c:out value="${current_page}"/> 
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
   
    <c:forEach var="board" items="${boardList}">
        <tr>
        <td width=50><p align=center>${board.getId()}</p></td>
        <td width=100><p align=center>${board.getName()}</p></td>                
        <td width=320>
            <p align=center>
                <a href="/SpringMvcBoardMyBatis/viewWork?memo_id=${board.getId()}&current_page=<c:out value="${current_page}"/>&searchStr=None" title="${board.getMemo()}"><c:out value="${board.getSubject()}"/>
            </p>
        </td>                      
        <td width=100><p align=center><c:out value="${board.getCreated_date()}"/></p></td>
        <td width=100><p align=center><c:out value="${board.getHits()}"/></p></td>
    </tr>
    </c:forEach>
                
    <%              
        int rowsPerPage = 2;        
        ///////////////////////////////////////////////////////////////
        int total_cnt = ((Integer)(pageContext.getAttribute("total_cnt"))).intValue()  ; 
        
        //전체 페이지
        int total_pages = PageNumberingManager.getInstance().getTotalPage(total_cnt, rowsPerPage) ;        
        //http://answers.google.com/answers/threadview/id/371241.html
        pageContext.setAttribute("t_pages",total_pages);        
        
        ///////////////////////////////////////////////////////////////
        //전체 블럭
        // http://blog.naver.com/20klop/40004319618
        //int block_num = 2;
        //context-param 사용 => web.xml에 저장된 값을 조회
        //ServletContext ctx = getServletContext();       
        //paramValue = ctx.getInitParameter("block_num") ; 
        //System.out.print("paramValue: " + paramValue);
        //int block_num = Integer.parseInt(paramValue);        
        /////////////////////////////////////////////////////////////        
    %>


</table>

<table cellspacing=1 width=700 border=1 >
    <tr>
        <td>                
        <c:forEach var="i" begin="1" end="${t_pages}">            
            <a href="/SpringMvcBoardMyBatis/listSpecificPageWork?current_page=${i}" >
            [
            <c:if test="${i == c_page}" > <b> </c:if>
            ${i}    
            <c:if test="${i == c_page}" > </b> </c:if>
            ]
        </c:forEach>            
        </td>
    </tr>
</table>

<table width=700>
    <tr>
        <td><input type=button value="글쓰기"  OnClick="window.location='/SpringMvcBoardMyBatis/show_write_form'">    </td>
        <td><form name=searchf method=post action="/SpringMvcBoardMyBatis/searchWithSubject"> 
            <p align=right><input type=text name=searchStr size=50  maxlength=50>
            <input type=submit value="글찾기"></p>
        </td>
    </tr>
</table>
</html>