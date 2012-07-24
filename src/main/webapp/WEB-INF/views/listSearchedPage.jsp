
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


<c:set var="total_cnt" value="${totalCnt}" />
<c:set var="searchString" value="${searchStr}" />
<c:set var="pageForView" value="${pageForView}" />

<%
    int total_cnt = ((Integer)(pageContext.getAttribute("total_cnt"))).intValue()  ;
    String searchStr =(String) pageContext.getAttribute("searchString");
    int rowsPerPage = 2;   
    int total_pages = PageNumberingManager.getInstance().getTotalPage(total_cnt, rowsPerPage) ;    
    pageContext.setAttribute("t_pages",total_pages);
%>


<table cellspacing=1 width=700 border=0>
    <tr>
        <td>총 게시물수: <c:out value="${totalCnt}"/></td>
        <td><p align=right> 페이지:<c:out value="${t_pages}"/> 
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
                <a href="/SpringMvcBoardMyBatis/viewWork?memo_id=${board.getId()}&current_page=${pageForView}&searchStr=${searchStr}" title="${board.getMemo()}"><c:out value="${board.getSubject()}"/>
            </p>
        </td>                      
        <td width=100><p align=center><c:out value="${board.getCreated_date()}"/></p></td>
        <td width=100><p align=center><c:out value="${board.getHits()}"/></p></td>
    </tr>
    </c:forEach>
</table>

<table cellspacing=1 width=700 border=1 >
    <tr>
        <td>        
        <c:forEach var="i" begin="1" end="${t_pages}">            
            <a href="/SpringMvcBoardMyBatis/listSearchedSpecificPageWork?pageForView=${i}&searchStr=<c:out value="${searchStr}"/>" >
            [
            <c:if test="${i == pageForView}" > <b> </c:if>
            ${i}    
            <c:if test="${i == pageForView}" > </b> </c:if>
            ]
        </c:forEach>            
        </td>
    </tr>
</table>
<table width=700>
    <tr>
        <td><input type=button value="전체 목록으로 돌아가기"  OnClick="window.location='/SpringMvcBoardMyBatis/'">    </td>        
    </tr>
</table>
</html>