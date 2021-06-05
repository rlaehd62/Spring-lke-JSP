<%@ page import="vo.CompanyVO" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
	request.setCharacterEncoding("UTF-8");
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
	<title>MVC 게시판</title>
	<script language="javascript">
	function addCompany()
	{
		companyForm.action = '/insertCompany.do';
		companyForm.encoding = "application/x-www-form-urlencoded";
		companyForm.submit();
	}

	function updateCompany(companyID)
	{
		companyForm.action = '/updateCompany.do';
		companyForm.encoding = "application/x-www-form-urlencoded";
		companyForm.companyID.value = companyID;
		companyForm.submit();
	}
	</script>
</head>
<body>
<form method="post" action="company_write.jsp" name="companyForm">
<input type="hidden" name="companyID"/>
<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td colspan="5">회사 등록</td>
	</tr>
	<tr>
		<td style="font-family:돋음; font-size:12" height="16">
			<div align="center">회사 이름</div>
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
			<div align="center">회사 주소</div>
		</td>
		<td>

			<%
				if(Objects.isNull(vo))
				{

			%>
			<input name="address" type="text" size="30" maxlength="30" value=""/>
			<%
				} else
				{
			%>
			<input name="address" type="text" size="30" maxlength="30" value="<%=vo.getAddress()%>"/>
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
			<a href="javascript:addCompany()">[등록]</a>&nbsp;&nbsp;
			<%
				} else
				{
			%>
			<a href="javascript:updateCompany(<%=vo.getId()%>)">[수정]</a>&nbsp;&nbsp;
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