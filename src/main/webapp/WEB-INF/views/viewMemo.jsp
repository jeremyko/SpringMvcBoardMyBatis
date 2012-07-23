<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
<c:set var="boardInfo" value="${boardData}" />
--%>

<c:set var="str_aid" value="${memo_id}" />
<c:set var="str_c_page" value="${current_page}" />

<html>
<head>
<title>글보기</title>
</head>

 
<script language="javascript">

    function boardlist()
    {    	
    	<%-- 다음처럼 해도 동작
    	location.href='<%=request.getContextPath()%>/listSpecificPageWork?current_page=${current_page}';
    	--%>
    
    	location.href = '/SpringMvcBoardMyBatis/listSpecificPageWork?current_page=${current_page}';
    }
    
    function boardmodify() 
    {
        location.href='/SpringMvcBoardMyBatis/listSpecificPageWork_to_update?memo_id=${memo_id}&current_page=${current_page}';   
                
    }
    
    <%--
    function boarddelete() 
    {
        location.href = '/bbsModel2/delete.work?str_aid=<%=str_aid%>&str_c_page=<%=str_c_page%>';
    }
    
    
    --%>
</script>


<table cellspacing = 0 cellpadding = 5 border = 1 width=500>
    <tr><td><b>조회수</b></td><td> <c:out value="${boardData.getHits()}"/> </td></tr>
    <tr><td><b>이름 </b></td><td> <c:out value="${boardData.getName()}"/> </td></tr>
    <tr><td><b>이메일 </b></td><td> <c:out value="${boardData.getMail()}"/> </td></tr>    
    <tr><td><b>제목 </b></td><td> <c:out value="${boardData.getSubject()}"/> </td></tr>
    <tr><td><b>내용 </b></td><td width=350> <c:out value="${boardData.getMemo()}"/> </td></tr>
</table>
 
<table  cellspacing = 0 cellpadding = 0 border = 0 width=500>
    <tr><td>
        <input type=button value="수정" OnClick="javascript:boardmodify();">        
        
        <input type=button value="목록" OnClick="javascript:boardlist()"> 
        <input type=button value="삭제" OnClick="javascript:boarddelete();">
    </td></tr>
</table>

</html>

