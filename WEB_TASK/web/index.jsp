<%@ page import="vo.CompanyVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  if(session.getAttribute("isValid") == null)
  {
    out.println("<script>");
    out.println("location.href = '/loginForm.do'");
    out.println("</script>");
  }

  List<CompanyVO> list = (List<CompanyVO>) request.getAttribute("list");
  int size = list.size();
%>
<html>
  <head>
    <title>회사 목록</title>
  </head>
  <body>
  <table width=50% border="0" cellpadding="0" cellspacing="0">
    <tr align="center" valign="middle">
      <td colspan="4">회사 목록</td>
      <td align=right>
        <font size=2>회사 갯수 : <%=size%></font>
      </td>
    </tr>

    <tr align="center" valign="middle" bordercolor="#333333">
      <td style="font-family:Tahoma;font-size:8pt;" width="8%" height="26">
        <div align="center">번호</div>
      </td>
      <td style="font-family:Tahoma;font-size:8pt;" width="50%">
        <div align="center">이름</div>
      </td>
      <td style="font-family:Tahoma;font-size:8pt;" width="14%">
        <div align="center">주소</div>
      </td>
    </tr>

    <%
      for(CompanyVO vo : list)
      {
    %>
    <tr align="center" valign="middle" bordercolor="#333333"
        onmouseover="this.style.backgroundColor='F8F8F8'"
        onmouseout="this.style.backgroundColor=''">
      <td height="23" style="font-family:Tahoma;font-size:10pt;">
        <%=vo.getId()%>
      </td>
      <td style="font-family:Tahoma;font-size:10pt;">
        <div align="center"><a href="/companyInfo.do?companyID=<%=vo.getId()%>"><%=vo.getName()%></a></div>
      </td>
      <td style="font-family:Tahoma;font-size:10pt;">
        <div align="center"><%=vo.getAddress()%></div>
      </td>
    </tr>
    <%
      }
    %>
    <tr align="right">
      <td colspan="5">
        <a href="/companyForm.do">[회사 등록]</a>
      </td>
    </tr>
  </table>
  </body>
</html>
