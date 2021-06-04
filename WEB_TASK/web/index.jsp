<%@ page import="vo.CompanyVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  if(session.getAttribute("isValid") == null)
  {
    out.println("<script>");
    out.println("location.href = '/login.do'");
    out.println("</script>");
  }

  List<CompanyVO> list = (List<CompanyVO>) request.getAttribute("list");
%>
<html>
  <head>
    <title>테스트</title>
  </head>
  <body>
    안녕하세요 <br />
    <%
      for(CompanyVO vo : list)
      {
    %>
    <h3>　번호 : <%=vo.getId()%></h3>
    <h3>회사명 : <%=vo.getName()%></h3>
    <h3>　주소 : <%=vo.getAddress()%></h3>
    <h3>　주석 : <%=vo.getComment()%></h3>
    <hr />
    <br />
    <%
      }
    %>
  </body>
</html>
