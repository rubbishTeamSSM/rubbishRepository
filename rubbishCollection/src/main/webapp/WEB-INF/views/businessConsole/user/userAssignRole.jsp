<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
	<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
	<style>
	#userDefaultRoleForm li{
		list-style: none;
	}
	table.gridtable {
		font-family: verdana,arial,sans-serif;
		color:#333333;
		border-width: 1px;
		border-color: #AED0EA;
		border-collapse: collapse;
		padding-left: 20px;
	}
	table.gridtable th {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #AED0EA;
		background-color: #AED0EA;
	}
	table.gridtable td {
		border-width: 1px;
		padding: 4px 25px 4px 25px;
		border-style: solid;
		border-color: #AED0EA;
		background-color: #ffffff;
	}
	.panel-body {
	    overflow: scroll;
	    overflow-x: hidden;
    }
	</style>
	<script>
	
	$(document).ready(function(){	
		var rolesId = '${rolesId}';
		var rolesObj = JSON.parse(rolesId);
		 if(rolesObj.length > 0){
			 $.each(rolesObj,function(n,value) { 
				 var input = '#userDefaultRoleForm input[type="radio"][value=\"'+ value.role_code+'\"]';
				 $(input).attr("checked","checked");
				 //设置默认标记
				 if(value.default_flag == '1'){
					 var inputm = '#userDefaultRoleForm input[type="radio"][value=\"'+ value.role_code+'\"]';
					 $(inputm).attr("checked","checked");
				 }
			 });
		}
		
		
	});

    function radchange(obj){
    	var role_code = $(obj).attr('id');
    	var check = '#userDefaultRoleForm input[type="radio"][value=\"'+ role_code+'\"]';
    	var inputm = '#userDefaultRoleForm input[type="radio"][value=\"'+ role_code+'\"]';
    	if($(inputm).is(":checked")){
    		$(check).attr("checked","checked");
    	}
    	
    }

   /* function checkchange(obj){
    	var role_code = $(obj).attr('id');
    	var check = '#userDefaultRoleForm input[type="radio"][value=\"'+ role_code+'\"]';
    	var inputm = '#userDefaultRoleForm input[type="radio"][value=\"'+ role_code+'\"]';
    	if(!$(check).is(":checked") && $(inputm).is(":checked")){
    		$(check).attr("checked",true);
    	}
    	
    	if($(check).is(":checked") && !$(inputm).is(":checked"))
    	  $(check).attr("checked",false);
    } */

	function submitUser($userDialog,$pjq,USER_CODE){
		//判断是否有默认角色
		var defaultRole=$('input:radio[name="default"]:checked').val();
		if(defaultRole==null){
			$pjq.messager.alert('错误','选择默认角色','error');
     		return;
		}
		
		var ROLE_CODEs =[];
		$('#userDefaultRoleForm input[name="default"]:checked').each(function(n,item){	
			var tmp = {};
			tmp.role_code = $(item).val();
			
			 var inputm = '#userDefaultRoleForm input[name="default"][value=\"'+tmp.role_code+'\"]';
			 var c =  $(inputm).attr("checked");
			 if(c == 'checked'){
				 tmp.default_flag = '1';
			 }else{
				 tmp.default_flag = '0';
			 }
			ROLE_CODEs.push(tmp);
			
		});

		var data = {};
		data.ROLE_CODEs = JSON.stringify(ROLE_CODEs);
		data.user_code = USER_CODE;
     	if(ROLE_CODEs == ''){
     		 $pjq.messager.alert('错误','选择指派的角色','error');
     		return;
     	}
         $.ajax({
             type : 'post',
             url : '${ct}/businessConsole/user/updateUserRoles.do',
             data :data,
             dataType : 'json',
             success : function(result) {
                 if(result.success){
                     $userDialog.dialog('destroy');
                     $pjq.messager.alert('成功','设置成功','info');
                 }else{
                     $pjq.messager.alert('错误','设置失败','error');
                 }
             }
         });
	}
	</script>
</head>
<body>
	<div  class="easyui-panel" data-options="fit:true,border:false">
		<form id="userDefaultRoleForm" style="margin: 20px">
		
			<table class="gridtable" >
				<tr>
					<td>指派角色</td>
					<td>角色名称</td>
					<td>默认角色</td>
				</tr>
				<c:forEach items="${roles}" var="role">
					<tr>
					   <td><input id="${role.role_code}" type="radio" name="roleUser" disabled="disabled" value="${role.role_code}" onclick="checkchange(this)"/></td>
					   <td>${role.role_name}</td>
					   <td><input id="${role.role_code}" type="radio" name="default" value="${role.role_code}" onclick="radchange(this)"/></td>
				   </tr>
				 </c:forEach>
			</table>
		</form>
	</div>
</body>
</html>