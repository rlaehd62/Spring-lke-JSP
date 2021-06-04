function checkLogin(){
	if(document.loginform.id.value==""){
		alert("아이디를 입력해 주세요.");
		document.loginform.id.focus();
		return;
	}
	if(document.loginform.pw.value==""){
		alert("비밀번호를 입력해 주세요.");
		document.loginform.pw.focus();
		return;
	}
	document.loginform.action = '/login.do';
	document.loginform.submit();
}

function memberReg(){
	document.location="joinForm.do";
}

function prevPage() {
	history.go(-1);
}

function inputCheck(){
	if(document.joinform.id.value==""){
		alert("아이디를 입력해 주세요.");
		document.joinform.id.focus();
		return;
	}
	if(document.joinform.pass.value==""){
		alert("비밀번호를 입력해 주세요.");
		document.joinform.pass.focus();
		return;
	}
	if(document.joinform.name.value==""){
		alert("이름을 확인해 주세요");
		document.joinform.name.focus();
		return;
	}
	if(document.joinform.age.value==""){
		alert("나이를 입력해 주세요.");
		document.joinform.age.focus();
		return;
	}
	if(document.joinform.email.value==""){
		alert("메일주소를 입력해 주세요.");
		document.joinform.email.focus();
		return;
	}

	document.joinform.action = '/join.do';
	document.joinform.submit();
}

function idCheck(id){
	if(id == ""){
		alert("아이디를 입력해 주세요.");
		document.joinform.id.focus();
	} else {
		url = "/verifyID.do?id=" + id;
		window.open(url, "post", "width=450,height=250");
	}
}

function win_close() {
	self.close();
}