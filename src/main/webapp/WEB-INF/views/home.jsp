<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
	I made changes at home!!! hahaha!!! Git??
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
--%>




<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ page import="kojh.db.beans.BoardBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.FileInputStream"%>

<html>
<head>
<title>목록</title>
</head>

<%-- 
<c:set var="list" scope="session" value="boardList"/>

<%ArrayList<BoardBean> list = %><c:out value="${boardList }" /> <% ; %>
--%>

<%
    //불필요 경고 방지
    @SuppressWarnings("unchecked")
    //ArrayList<BoardBean> list = (ArrayList<BoardBean>) request.getAttribute("infoArryList");
    //String str_c_page = ((Integer) request.getAttribute("str_c_page")).toString();    
    //int total_cnt = ((Integer) request.getAttribute("totalCnt")).intValue();
    //int c_page = ((Integer) request.getAttribute("str_c_page")).intValue(); //현재 페이지 구분
    
    //test
    ArrayList<BoardBean> list = new ArrayList<BoardBean> ();
    String str_c_page = "1";
    int total_cnt = 0;
    int c_page = 1;
%>

<P>  The time on the server is ${serverTime}. </P>

<table cellspacing=1 width=700 border=0>
    <tr>
        <td>총 게시물수: <%=total_cnt%></td>
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
    <%
        for (int i = 0; i < list.size(); i++) {
        	BoardBean info = list.get(i);
    %>
    <tr>
        <td width=50><p align=center><%=info.getId()%></p></td>
        <td width=100><p align=center><%=info.getName()%></p></td>                
        <td width=320>
            <p align=center>
                <a href="/bbsModel2/view.work?str_aid=<%=info.getId()%>&str_c_page=<%=str_c_page%>" title="<%=info.getMemo() %>"> <%=info.getSubject() %>
            </p>
        </td>                      
        <td width=100><p align=center><%=info.getCreated_date()%></p></td>
        <td width=100><p align=center><%=info.getHits() %></p></td>
    </tr>
    <%
        }
    %>
    
    
    <%-- 다음의 페이지 처리하는 부분도 별도의 클래스에서 처리할수도 있겠다.. --%>

    <%              
        int list_num = 2; 
        
        //ServletContext ctx = getServletContext();
        //String paramValue = ctx.getInitParameter("list_num") ; 
        //System.out.print("paramValue: " + paramValue);
        //int list_num = Integer.parseInt(paramValue);
    
        int total_pages = 0;

        System.out.print("total_cnt:");
        System.out.print(total_cnt);
        System.out.print("\n");
        
        ///////////////////////////////////////////////////////////////
        //전체 페이지
        if ((total_cnt % list_num) == 0)
            total_pages = total_cnt / list_num;
        else
            total_pages = (total_cnt / list_num) + 1;
        
        System.out.print("t_page:");
        System.out.print(total_pages);
        System.out.print("\n");
        
        ///////////////////////////////////////////////////////////////
        //전체 블럭        
        
        // http://blog.naver.com/20klop/40004319618
        /////////////////////////////////////////////////////////////
        //context-param 사용 => web.xml에 저장된 값을 조회
        
        //ServletContext ctx = getServletContext();
        
        int block_num = 2;
        //paramValue = ctx.getInitParameter("block_num") ; 
        //System.out.print("paramValue: " + paramValue);
        //int block_num = Integer.parseInt(paramValue);
        
        /////////////////////////////////////////////////////////////
                        
        
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

        System.out.print("t_block:");
        System.out.print(total_blocks);
        System.out.print("\n");

        System.out.print("c_block:");
        System.out.print(c_block);
        System.out.print("\n");
    %>


</table>

<table cellspacing=1 width=700 border=1 >
    <tr>
        <td>
        
            <%
            //for (int i = (c_block - 1) * block_num + 1; i <= c_block * block_num && i <= total_pages; i++)
            for (int i = 1; i <= total_pages; i++) 
            {
            %>           
                <a href="/bbsModel2/list.work?str_c_page=<%=i%>" >                
                
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
        <td><input type=button value="글쓰기"  OnClick="window.location='/bbsModel2/show_write_form.work'">    </td>
        <td><form name=searchf method=post action="/bbsModel2/list.work"> 
            <p align=right><input type=text name=dbsearch size=50  maxlength=50>
            <input type=submit value="글찾기"></p>
        </td>
    </tr>
</table>
</html>