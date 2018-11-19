<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ct}/plugin/pagination/pagination.css">
</head>
<style>
.validatebox-text{
	width: 195px!important;
}
</style>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<form id="informationForm" style="margin: 40px 0px 40px -25px;">
			<table style="text-align:right;border-collapse:separate; border-spacing:25px;">
				<tr>
					<td><label class="label-ch-w140"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>新密码：</label><input name="PASSWORD" id='PASSWORD' class="easyui-validatebox inp_w120 inp_150" data-options="required:true,validType:['numEng','length[6,20]']" type="password"/></td>
				</tr>
				<tr>
					<td><label class="label-ch-w140"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>确认密码：</label><input type="password" id="PASSWORDNEW" name="PASSWORDNEW" style="width: 230px;" class="easyui-validatebox inp_w120 inp_150" validType="eqTo['#PASSWORD']" required="required" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	
<script>
$(".panel-switch li").bind("click",function(){
	$(".panel-switch li").removeClass("select");
	$(this).addClass("select");
	
	var num=$(this).attr("flag");
	$(".panel-result .panel-r").addClass("hidden");
	$(".panel-result .panel-r:eq("+parseInt(num)+")").removeClass("hidden");
});
var USER_CODE;
$(document).ready(function(){
	USER_CODE = '${param.USER_CODE}';//模块ID
});

/* $.extend($.fn.validatebox.defaults.rules, {
		passwd:{
		    validator:function(value){
		          //6到20位数字字母组合
		             var reg = /^[A-Za-z0-9]{6,20}$/;
		        	return reg.test(value);
		        },
		        message:"密码格式：6到20位数字字母组合."
		},
		eqTo:{
		     validator:function(value,param){
		        	return value==$(param[0]).val();
		        },
		        message:"密码输入不一致."
		}
	    
	}); */
	



function save($dialog,$grid,$pjq,user_code){
	var form= $("#informationForm");
	var isValid = $(form).form('validate');
	var data = ns.serializeObject(form);
	/* if(null != data.PASSWORD && '' != data.PASSWORD && null != data.PASSWORDNEW && '' != data.PASSWORDNEW){ */
	 if(isValid){
	    parent.$.messager.confirm('确认','确认修改密码?',function(r){
			if(r){
		   /*  if(data.PASSWORD == data.PASSWORDNEW){ */
			data.flag = "0";
			data.USER_CODE = user_code;
			$.ajax({
				type : 'post',
				url : '${ct}/businessConsole/user/setPassword.do',
				data :{'data':JSON.stringify(data)},
				dataType : 'json',
				success : function(result) {
					if(result.success){
						parent.$.messager.alert("提示", "修改成功", "info", function () {
		            		$dialog.dialog('destroy');//关闭窗口
							$grid.datagrid('reload');//刷新datagrid
		            	});
					}else{
						parent.$.messager.alert('提示','系统异常','error');
						$(".no-message").hide();
					}
				}
			});
		/* }else{
			parent.$.messager.alert('提示','两次密码输入不一致','error');
		} */
			 }
		});
	/* else{
		parent.$.messager.alert('提示','请输入密码','error');
	} */
	}
}




</script> 
</body>
</html>