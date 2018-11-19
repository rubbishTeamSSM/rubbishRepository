<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
		<form id="userDefaultRoleForm" style="margin: 20px 40px;">
			<table>
				
				<tr>
					<c:choose>
						<c:when test="${not empty roles}">	
							<td>默认角色：</td>
							<td>
								<select name="role_code" class="easyui-combobox" data-options="width:176,panelHeight:'auto'" >
									<c:forEach items="${roles}" var="role">
										<option value="${role.role_code}">${role.role_name}</option>
									</c:forEach>
								</select>
							</td>
						
						</c:when>
						
						<c:otherwise>
							<p style="color:red;">用户尚未分配角色，无法设置默认角色！<br/>请先为用户分配角色！</p>
						
						</c:otherwise>
					
					</c:choose>
					
					
				</tr>
			
			</table>
		</form>
	<script>
	
	$(document).ready(function(){	
		
		
	});

	function submitUserDefaultRole($userDialog,$pjq){
		
		<c:if test="${empty roles}">
		//没有角色
		 $pjq.messager.alert('提示','用户尚未分配角色，无法设置默认角色！','error');
		 return;
		</c:if>
        var form= $("#userDefaultRoleForm");
        
        var isValid = $(form).form('validate');
        if(isValid){
            $.ajax({
                type : 'post',
                url : '${ct}/businessConsole/user/setUserDefaultRole.do?user_code=${param.user_code}',
                data :ns.serializeObject(form),
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
	}
	</script>
</div>
</body>
</html>