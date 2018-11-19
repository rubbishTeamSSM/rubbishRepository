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
					<td><input type="text" name="role_name" class="easyui-validatebox" data-options="required:true,validType:['length[1,10]']"/></td>
				</tr>
				<tr>
					<td><label class="label-ch-w100">备注：</label></td>
					<td><input type="text" name="remark" class="easyui-validatebox" data-options="validType:['length[0,10]']"/></td>
				</tr>
			</table>
		</form>
	<script>

	function submitRole($roleDialog,$roleGrid,$pjq){
        var form= $("#newRoleForm");
        var isValid = $(form).form('validate');
        if(isValid){
            $.ajax({
                type : 'post',
                url : '${ct}/businessConsole/role/addNewRole.do',
                data :ns.serializeObject(form),
                dataType : 'json',
                success : function(result) {
                    if(result.success){
                    	$pjq.messager.alert('提示','新建成功','info', function(){
	                        $roleDialog.dialog('destroy');
	                        $roleGrid.datagrid('reload');
                    	});
                    }else{
                        $pjq.messager.alert('错误','创建角色失败','error');
                    }
                }
            });
        }
	}
	</script>
</div>
</body>
</html>