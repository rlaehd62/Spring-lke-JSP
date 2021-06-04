<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>명함 관리 프로그램</title>
</head>
<body>
<script language="JavaScript" src="script.js"> </script>
<form name="loginform" method="post">
<center>
<table border=1>
	<tr>
		<td colspan="2" align=center>
			<b><font size=5>로그인 페이지</font></b>
		</td>
	</tr>
	<tr><td>아이디 : </td><td><input type="text" name="id"/></td></tr>
	<tr><td>비밀번호 : </td><td><input type="password" name="pw"/></td></tr>
	<tr>
		<td colspan="2" align=center>
			<input type="button" value="로그인" onClick="checkLogin()">
			<input type="button" value="회원가입" onClick="memberReg()">
		</td>
	</tr>
</table>
</center>
</form>
</body>
</html>
