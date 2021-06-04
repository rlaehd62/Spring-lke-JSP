<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>회원관리 시스템 회원가입 페이지</title>
</head>
<body>
<script language="JavaScript" src="script.js"> </script>
<form name="joinform" action="joinProcess.jsp" method="post">
<center>
<table border=1>
	<tr>
		<td colspan="2" align=center>
			<b><font size=5>회원가입 페이지</font></b>
		</td>
	</tr>
	<tr><td>아이디 : </td><td><input type="text" name="id"/><input type="button" value="ID중복확인" onClick="idCheck(this.form.id.value)"></td></tr>
	<tr><td>비밀번호 : </td><td><input type="password" name="pass"/></td></tr>
	<tr><td>이름 : </td><td><input type="text" name="name"/></td></tr>
	<tr><td>나이 : </td><td><input type="text" name="age" maxlength=2 size=5/></td></tr>
	<tr>
		<td>성별 : </td>
		<td>
			<input type="radio" name="gender" value="남" checked/>남자
			<input type="radio" name="gender" value="여"/>여자
		</td>
	</tr>
	<tr><td>이메일 주소 : </td><td><input type="text" name="email" size=30/></td></tr>
	<tr>
		<td colspan="2" align=center>
			<input type="button" value="이전으로" onClick="prevPage()">
			<input type="button" value="회원가입" onClick="inputCheck()">
			<input type="reset" value="다시작성">
		</td>
	</tr>
</table>
</center>
</form>
</body>
</html>
