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

	CompanyVO vo = (CompanyVO) request.getAttribute("vo");
	List<EmployeeVO> empList = (List<EmployeeVO>) request.getAttribute("list");
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
		
		<td style="font-family:돋음; font-size:12" colspan="2">
		<%=vo.getName()%>
		</td>
	</tr>

	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">주 소&nbsp;&nbsp;</div>
		</td>

		<td style="font-family:돋음; font-size:12" colspan="2">
			<%=vo.getAddress()%>
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
		<td colspan="3" style="height:1px;">
		</td>
	</tr>

	<tr align="center" valign="middle" bordercolor="#333333">
		<td style="font-family:Tahoma;font-size:8pt;" width="8%" height="26">
			<div align="center">번호</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="50%">
			<div align="center">이름</div>
		</td>

		<td style="font-family:Tahoma;font-size:8pt;" width="30%">
			<div align="center">직위/직급</div>
		</td>
	</tr>

	<%
		for(EmployeeVO employeeVO : empList)
		{
	%>
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23" style="font-family:Tahoma;font-size:10pt;">
			<%=employeeVO.getId()%>
		</td>
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><a href="./employeeInfo.do?employeeID=<%=employeeVO.getId()%>"><%=employeeVO.getName()%></a></div>
		</td>
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%=employeeVO.getRole()%></div>
		</td>
	</tr>
	<%
		}
	%>
	
	<tr bgcolor="cccccc">
		<td colspan="3" style="height:1px;"></td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle">
		<td colspan="5">
			<font size=2>
				<a href="./employeeForm.do?companyID=<%=vo.getId()%>">[추가]</a>&nbsp;&nbsp;
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