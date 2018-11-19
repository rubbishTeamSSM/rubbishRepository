<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
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
         background:url("${ct}/images/login/login2bg.png") no-repeat center;
         position:relative;
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
     	width:100%
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
     #UserLoginForm p span.login_title{
    	display:inline-block;
    	width:60px;
    	height:38px;
    	line-height:38px;
     	text-align:center;
     	background-color:#00aea0;
     	color:#fff;
     	border-top-left-radius:5px;
     	border-bottom-left-radius:5px;
     }
     #UserLoginForm p input.login_code{
     	position:absolute;
     	right:25px;
     	display:inline-block;
    	width:110px;
    	height:38px;
    	line-height:38px;
     	text-align:center;
     	background-color:#00aea0;
     	border:1px solid #00aea0;
     	color:#fff;
     	border-top-right-radius:5px;
     	border-bottom-right-radius:5px;
     	cursor:pointer;
     }
     #UserLoginForm input.easyui-validatebox{
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
			<div class="login">
				<div class="loginUser">
					<div class="logo">
						<img src="${ct}/images/login/logo.png" alt="" />
					</div>
					<form id="UserLoginForm">
						<p>
							<span class="login_title">手机号</span>
							<input type="text"  name="USER_ACCNT" id="USER_ACCNT" placeholder="手机号" class="easyui-validatebox"
								 data-options="required:true,validType:'multipleCheck'"/>
						<input type="text" id="mobilePhone" name="mobilePhone" style="display: none;"/>
						<input type="text" id="code" name="code" style="display: none;"/>
						</p>
						<p>
							<span class="login_title">验证码</span>
							<input type="text" name="code" id="USER_PWD" placeholder="验证码" class="easyui-validatebox"
								 data-options="required:true" style="width:252px;border-radius:0;"/>
							<input type="button" class="login_code" value="获取验证码" onclick="getCode(this)"/>
						</p>
						<div class="login_btn">
							<a href="javascript:void(0);" class="login-btn" onclick="userLogin()">登录</a>
							<a href="javascript:void(0);" class="reset-btn" onclick="resetLogin()">重置</a>
							<a href="javascript:void(0);" class="tel-login" onclick="changeLogin()">账号密码登录</a>
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
	   var wait=60;
	   var USER_ACCNT = $("#USER_ACCNT").val();
	   var USER_PWD =$("#USER_PWD").val();
	 $.extend($.fn.validatebox.defaults.rules,{    
         multipleCheck: {    
            validator: function(USER_ACCNT){
                var re = /^0?1[3|4|5|7|8][0-9]\d{8}$/;//手机号
                if(re.test(USER_ACCNT)){
                    return true; 
                }
            },  
           message: '请输入正确的手机格式'    
        }
    });  
	//重置
	function resetLogin(){
		$('#UserLoginForm').form('clear'); 
		$(".login_code").attr("disabled","false");   
		$(".login_code").val("获取验证码");   
	    wait = 0;
	}
	//获取验证码
    function getCode(o){
    var UserLoginForm = $('#UserLoginForm');
     var USER_ACCNT = $("#USER_ACCNT").val();
     
     if(!USER_ACCNT){
     $.messager.alert('错误','请输入手机号 ', 'error');
     return ;
     }else{
     var data ={};
     data.USER_ACCNT = USER_ACCNT;
     $.ajax({
			type : 'post',
			url : '${ct}/businessConsole/login/getLoginCode.do',
			data :{'data':JSON.stringify(data)},
			async:false,
            dataType : 'json',
			success : function(result) {
			 if("success"==result.msg){
			   $('#mobilePhone').val(result.mobile);
			   $('#code').val(result.note);
			   time(o);
			 }
		   }
		});
    }
      }
	//获取验证码
/* 	function getCode(o){
		//随机生成6位验证码传给后台发送
		var num=""; 
		for(var i=0;i<6;i++) 
		{ 
			num+=Math.floor(Math.random()*10); 
		}
		var phone = $("input[name=USER_ACCNT]").val();
		//调用ajax方法
		
		//发送成功之后，调用倒计时
		time(o);
		
	} */
	
	
	function time(o) {
	  if (wait == 0) {
	   o.removeAttribute("disabled");   
	   o.value="获取验证码";
	   wait = 60;
	  } else { 
	   o.setAttribute("disabled", true);
	   o.value="重新发送(" + wait + ")";
	   wait--;
	   setTimeout(function() {
	    time(o);
	   },
	   1000);
	  }
	 }
	
	//手机号登录
	function changeLogin(){
		window.location.href="${ct}/businessConsole/login/login.do";
	}

	function userLogin() {
		var USER_ACCNT = $("#USER_ACCNT").val();
		var USER_PWD = $("#USER_PWD").val();
		var code = $("#code").val();
	    if(!USER_ACCNT || !USER_PWD){
         parent.$.messager.alert('错误','请输入手机号或验证码', 'error');
         return ;
     }else{
     	 if(code != USER_PWD){
     	 	parent.$.messager.alert('错误','验证码错误', 'error');
         	return ;
     	 }
         var UserLoginForm = $('#UserLoginForm');
     		$.ajax({
			type : 'post',
			url : '${ct}/businessConsole/login/getLoginUser.do',
			data : ns.serializeObject(UserLoginForm),
			dataType : 'json',
			success : function(result) {
				if(result.success){
					location.href="${ct}/businessConsole/login/showViews.do";
				}else{
					 parent.$.messager.alert('错误','用户信息错误', 'error');
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				 parent.$.messager.alert('错误','系统异常!', 'error');
			}
		});}}
</script>
</html>