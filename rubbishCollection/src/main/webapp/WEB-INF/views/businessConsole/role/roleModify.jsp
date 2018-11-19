<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
		<form id="newRoleForm" style="margin: 20px 40px;">
			<table>
				<tr>
					<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>角色名：</label></td>
					<td><input type="text" name="role_name" value="${role.role_name}" class="easyui-validatebox" data-options="required:true,validType:['length[1,10]']"/></td>
				</tr>
				<tr>
					<td><label class="label-ch-w100">备注：</label></td>
					<td><input type="text" name="remark" value="${role.remark}" class="easyui-validatebox" data-options="validType:['length[0,10]']"/></td>
				</tr>
			</table>
		</form>
	<script>
	function submitRole($roleDialog,$roleGrid,$pjq,role_code){
		var role_code='${param.ROLE_CODE}';
        var form= $("#newRoleForm");
        var isValid = $(form).form('validate');
        if(isValid){
        	var data = ns.serializeObject(form);
        	data.role_code = role_code;
            $.ajax({
                type : 'post',
                url : '${ct}/businessConsole/role/updateRole.do',
                data :data,
                dataType : 'json',
                success : function(result) {
                    if(result.success){
                        $roleDialog.dialog('destroy');
                        $pjq.messager.alert('成功','修改角色成功','success');
                        $roleGrid.datagrid('reload');
                    }else{
                        $pjq.messager.alert('错误','修改角色失败','error');
                    }
                }
            });
        }
	}
	</script>
</div>
</body>
</html>