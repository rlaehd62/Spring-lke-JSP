<%@ page import="vo.CompanyVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	if(session.getAttribute("isValid") == null)
	{
		out.println("<script>");
		out.println("location.href = '/loginForm.do'");
		out.println("</script>");
	}

	CompanyVO vo = (CompanyVO) request.getAttribute("vo");
%>

<html>
<head>
	<title>회사 정보</title>
</head>

<body>
<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td colspan="5">회사 정보</td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">이 름&nbsp;&nbsp;</div>
		</td>
		
		<td style="font-family:돋음; font-size:12">
		<%=vo.getName()%>
		</td>
	</tr>

	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">주 소&nbsp;&nbsp;</div>
		</td>

		<td style="font-family:돋음; font-size:12">
			<%=vo.getAddress()%>
		</td>
	</tr>
	
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center">주 석</div>
		</td>
		<td style="font-family:돋음; font-size:12">
			<table border=0 width=490 height=250 style="table-layout:fixed">
				<tr>
					<td valign=top style="font-family:돋음; font-size:12">
					<%=vo.getComment().replaceAll("\n", "<br>") %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;"></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle">
		<td colspan="5">
			<font size=2>
				<a href="./companyForm.do?companyID=<%=vo.getId()%>">[수정]</a>&nbsp;&nbsp;
				<a href="./deleteCompany.do?companyID=<%=vo.getId()%>">[삭제]</a>&nbsp;&nbsp;
				<a href="./companyList.do">[목록]</a>&nbsp;&nbsp;
			</font>
		</td>
	</tr>
</table>
<!-- 게시판 수정 -->
</body>
</html>