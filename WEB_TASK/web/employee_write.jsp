<%@ page import="vo.CompanyVO" %>
<%@ page import="java.util.Objects" %>
<%@ page import="vo.EmployeeVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
	request.setCharacterEncoding("UTF-8");
	if(session.getAttribute("isValid") == null)
	{
		out.println("<script>");
		out.println("location.href = '/loginForm.do'");
		out.println("</script>");
	}

	int companyID = 0;
	EmployeeVO vo = (EmployeeVO) request.getAttribute("vo");
	if(Objects.isNull(vo)) companyID = Integer.parseInt(request.getParameter("companyID"));
%>

<html>
<head>
	<title>MVC 게시판</title>
	<script language="javascript">
	function addEmployee(companyID)
	{
		employeeForm.action = '/insertEmployee.do';
		employeeForm.encoding = "application/x-www-form-urlencoded";
		employeeForm.submit();
	}

	function updateEmployee(employeeID)
	{
		employeeForm.action = '/updateEmployee.do';
		employeeForm.encoding = "application/x-www-form-urlencoded";
		employeeForm.employeeID.value = employeeID;
		employeeForm.submit();
	}
	</script>
</head>
<body>
<form method="post" action="company_write.jsp" name="employeeForm">
<input type="hidden" name="employeeID"/>
<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td colspan="5">사원 등록</td>
	</tr>
	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">사원 이름</div>
		</td>
		<td>
			<%
				if(Objects.isNull(vo))
				{

			%>
			<input name="name" type="text" size="20" maxlength="20" value=""/>
			<%
				} else
				{
			%>
			<input name="name" type="text" size="20" maxlength="20" value="<%=vo.getName()%>"/>
			<%
				}
			%>
		</td>
	</tr>
	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">직위/직급</div>
		</td>
		<td>

			<%
				if(Objects.isNull(vo))
				{

			%>
			<input name="role" type="text" size="20" maxlength="20" value=""/>
			<%
				} else
				{
			%>
			<input name="role" type="text" size="20" maxlength="20" value="<%=vo.getRole()%>"/>
			<%
				}
			%>
		</td>
	</tr>

	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">소속 회사 (번호)</div>
		</td>
		<td>

			<%
				if(Objects.isNull(vo))
				{

			%>
			<input name="companyID" type="number" size="30" maxlength="30" value="<%=companyID%>"/>
			<%
			} else
			{
			%>
			<input name="companyID" type="number" size="30" maxlength="30" value="<%=vo.getCompany_id()%>"/>
			<%
				}
			%>
		</td>
	</tr>

	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center">주 석</div>
		</td>
		<td>
			<textarea name="comment" cols="65" rows="15"><%if(Objects.nonNull(vo)) {%><%=vo.getComment()%><%}%></textarea>
		</td>
	</tr>
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr align="center" valign="middle">
		<td colspan="5">
			<%
				if(Objects.isNull(vo))
				{

			%>
			<a href="javascript:addEmployee(<%=companyID%>)">[등록]</a>&nbsp;&nbsp;
			<%
				} else
				{
			%>
			<a href="javascript:updateEmployee(<%=vo.getId()%>)">[수정]</a>&nbsp;&nbsp;
			<%
				}
			%>
			<a href="javascript:history.go(-1)">[뒤로]</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>