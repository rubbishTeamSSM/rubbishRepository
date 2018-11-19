<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="/WEB-INF/views/businessConsole/common/commonIndex.jsp"%>
<style>
	 *{
         margin:0;
         padding:0;
     }
     body,html{
         width:100%;
         height:100%;
         overflow-y:hidden;
         font-family:"微软雅黑";
     }
     .bg{
     	width:100%;
        height:100%;
     }
     .loginContent{
         width:100%;
         height:100%;
         background:url("${ct}/images/login/loginbg.png") no-repeat center;
         position:relative;
     }
     .login_top{
     	position:fixed;
     	left:65%;
     	top:5%;
     	margin-left:-300px;
     
     }
     .login{
     	padding:8px;
     	width:584px;
     	height: 394px;
     	background:#fff;
     	position:fixed;
     	left:50%;
     	top:50%;
     	margin-left:-300px;
     	margin-top:-210px;
     	border-radius:10px;
     	opacity:.9;
     
     }
     .loginUser{
     	width:584px;
     	height:394px;
     	border-radius:10px;
     }
     .logo{
     	width:80%;
     	height:75px;
     	border-radius:10px;
     	text-align:center;
     	margin:0 auto;
     	margin-top:50px;
     }
     .logo img{
     	width:312px;
     	height:28px;
     }
     #UserLoginForm{
     	width: 80%;
     	height:214px;
     	padding-top:18px;
     	padding-left:10px;
     	margin:0 auto;
     }
     #UserLoginForm p{
     	position:relative;
     	padding:0;
     	margin-bottom:30px;
     	height:40px;
     }
     #UserLoginForm p span{
    	display:inline-block;
    	width:60px;
    	height:38px;
     	text-align:center;
     	background-color:#00aea0;
     	border-top-left-radius:5px;
     	border-bottom-left-radius:5px;
     }
     #UserLoginForm input{
     	position: absolute;
     	left:60px;
     	top:0px;
     	width:365px;
     	height:36px;
     	padding-left:20px;
     	border:1px solid #00aea0;
     	border-top-right-radius:5px;
     	border-bottom-right-radius:5px;
     }
     #UserLoginForm .button{
     	width: 162px;
     	background:#3fcc6c;
     	font-size:16px;
     	color:#fff;
     	padding:0;
     	border:0;
     	font-weight:bold;
     	cursor:pointer;
     }

     .loginFooter{
         width:100%;
         height:7%;
         background:#298bd6;
         line-height:50px;
         font-size:14px;
         color:#fff;
         text-align:center;
     }
     .login_btn{
     	text-align:left;
     }
     .login_btn .login-btn{
     	display:inline-block;
     	width:120px;
     	height:35px;
     	line-height:35px;
     	background-color:#00aea0;
     	color:#fff;
     	text-align:center;
     	border-radius:5px;
     	margin-right:10px;
     }
     .login_btn .reset-btn{
     	display:inline-block;
     	width:120px;
     	height:33px;
     	line-height:33px;
     	background-color:#fff;
     	color:#444;
     	text-align:center;
     	border-radius:5px;
     	border:1px solid #00aea0;
     	margin-right:60px;
     }
     .login_btn .tel-login{
     	color:#00806f;
     }

</style>
</head>
<body>
   <div class="bg">
		<div class="loginContent">
			<div class="login_top">
				<img src="${ct}/images/login/logo_top.png" alt="" />
			</div>
			<div class="login">
				<div class="loginUser">
					<div class="logo">
						<img src="${ct}/images/login/logo.png" alt="" />
					</div>
					<form id="UserLoginForm">
						<p>
							<span>
								<img src="${ct}/images/login/user.png" style="margin-top:8px"/>
							</span>
							<input type="text" name="USER_ACCNT" id="USER_ACCNT" placeholder="用户名" class="easyui-validatebox"
								 data-options="validType:['length[1,40]']"/>
					       <input type="text" id="mobilePhone" name="mobilePhone" style="display: none;"/>
						  <input type="text" id="code" name="code" style="display: none;"/>
							
						</p>
						<p>
							<span>
								<img src="${ct}/images/login/locked.png" style="margin-top:8px"/>
							</span>
							<input type="password" name="USER_PWD" id="USER_PWD" placeholder="密码" class="easyui-validatebox"
								 data-options="validType:['length[1,20]']"/>
						</p>
						<div class="login_btn">
							<a href="javascript:void(0);" class="login-btn" onclick="userLogin()" style="margin-left: 98px;">登录</a>
							<a href="javascript:void(0);" class="reset-btn" onclick="resetLogin()">重置</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
	<script type="text/javascript">
	$(document).keydown(function(e){
		if(13 == e.keyCode){
			userLogin();
		}
	});

	//重置
	function resetLogin(){
		$('#UserLoginForm').form('clear'); 
	}
	
	//手机号登录
	function changeLogin(){
		window.location.href="${ct}/businessConsole/login/loginMobile.do";
	}

	function userLogin() {
		var UserLoginForm = $('#UserLoginForm');
	    var isValid = $(UserLoginForm).form('validate');
	    var USER_ACCNT = $("#USER_ACCNT").val();
		var USER_PWD = $("#USER_PWD").val();
		if(!USER_ACCNT ||!USER_PWD){
		  $.messager.alert('错误','请输入用户名或密码', 'error');
		  return ;
		 }
		if(isValid){
		$.ajax({
			type : 'post',
			url : '${ct}/businessConsole/login/getLoginUser.do',
			data : ns.serializeObject(UserLoginForm),
			dataType : 'json',
			success : function(result) {
				if(result.success){
					location.href="${ct}/businessConsole/login/showViews.do";
				}else{
					$.messager.alert('错误',result.msg, 'error');
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert('错误','系统异常!', 'error');
			}
		});
	}
	}
	
</script>
</html>