<%@ page import="vo.CompanyVO" %>
<%@ page import="vo.EmployeeVO" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	if(session.getAttribute("isValid") == null)
	{
		out.println("<script>");
		out.println("location.href = '/loginForm.do'");
		out.println("</script>");
	}

	EmployeeVO vo = (EmployeeVO) request.getAttribute("vo");
%>

<html>
<head>
	<title>회사 정보</title>
</head>

<body>
<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td colspan="5">사원 정보</td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">이 름&nbsp;&nbsp;</div>
		</td>
		
		<td style="font-family:돋음; font-size:12" colspan="2">
		<%=vo.getName()%>
		</td>
	</tr>

	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">직 급&nbsp;&nbsp;</div>
		</td>

		<td style="font-family:돋음; font-size:12" colspan="2">
			<%=vo.getRole()%>
		</td>
	</tr>
	
	<tr bgcolor="cccccc">
		<td colspan="3" style="height:1px;">
		</td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center">주 석</div>
		</td>
		<td style="font-family:돋음; font-size:12" colspan="2">
			<table border=0 width=490 height=100 style="table-layout:fixed">
				<tr>
					<td valign=top style="font-family:돋음; font-size:12">
					<%=vo.getComment().replaceAll("\n", "<br>") %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr bgcolor="cccccc">
		<td colspan="3" style="height:1px;"></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle">
		<td colspan="5">
			<font size=2>
				<a href="./employeeForm.do?employeeID=<%=vo.getId()%>">[수정]</a>&nbsp;&nbsp;
				<a href="./deleteEmployee.do?employeeID=<%=vo.getId()%>">[삭제]</a>&nbsp;&nbsp;
				<a href="./companyInfo.do?companyID=<%=vo.getCompany_id()%>">[목록]</a>&nbsp;&nbsp;
			</font>
		</td>
	</tr>
</table>
<!-- 게시판 수정 -->
</body>
</html>