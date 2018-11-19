<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
		<form id="newMenuForm" style="margin: 20px 40px;">
			<table>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>名称：</td><td><input type="text" name="MENU_NAME" class="easyui-validatebox" data-options="required:true,validType:['length[1,200]']" style="height: 28px;width:154px;"/></td>
				</tr>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>类型：</td>
					<td>
						<input id="TYPE" name="TYPE" class="easyui-combobox" data-options="required:true,editable:false" style="height: 28px;width:154px;">
					</td>
				</tr>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>&nbsp;&nbsp;&nbsp;</font>URL：</td><td><input type="text" name="MENU_URL" class="easyui-validatebox" data-options="validType:['length[0,200]']" style="height: 28px;width:154px;"/></td>
				</tr>
				
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>排序：</td><td><input type="text" name="SORT_NO" class="easyui-numberbox" data-options="required:true,validType:['length[1,9]']" style="height: 28px;width:154px;"/></td>
				</tr>
				
				
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>&nbsp;&nbsp;&nbsp;</font>备注：</td><td><input type="text" name="REMARK" class="easyui-validatebox" data-options="validType:['length[0,500]']" style="height: 28px;width:154px;"/></td>
				</tr>
			</table>
		</form>
	<script>
	$(document).ready(function(){
		initType();//初始化类型
	});
	function initType(){
		var data=[{'CODE':'1','TEXT':'菜单'},{'CODE':'2','TEXT':'目录'}];
		$('#TYPE').combobox({
			data:data,
			valueField:'CODE',
			textField:'TEXT',
			panelHeight:'100'
 		});
	}
	function submitMenu($dialog,$allmenuTree,$pjq,$checked,$btnTree,$form){
        var form= $("#newMenuForm");
        var isValid = $(form).form('validate');
        if(isValid){
            var menu = ns.serializeObject(form);
            
            if($checked)
            {
	            menu.PARENT_MENU_CODE = $checked.id;//设置父节点
	            menu.PARENT_AUTH_CODE = $checked.attributes.AUTH_CODE;//上级权限范围
	            menu.LEVEL =parseInt($checked.attributes.LEVEL)+1;//菜单层次
            }
            
            $.ajax({
                type : 'post',
                url : '${ct}/businessConsole/menu/addNewMenu.do',
                data :menu,
                dataType : 'json',
                beforeSend : function(){
		       		parent.$.messager.progress({
		       			title : '新增',
		       			text : '保存中。。。。'
		       		});
		       	},
                success : function(result) {
                	//关闭进度条
					parent.$.messager.progress('close');
                	parent.$.messager.alert('提示', '新增成功！', 'info',
	    				function(){
	    					$allmenuTree.tree({
		                         url:'${ct}/businessConsole/menu/getAllMenu.do'
		                    }); 
		                    if($checked){
		                    	$btnTree.tree('loadData',[]);
		                    };
		                    $form.form('clear');
		                    $dialog.dialog('destroy');
	    				}
	    			);
                },
				error :function(){
					//关闭进度条
					parent.$.messager.progress('close');
					$pjq.messager.alert('错误', '创建菜单失败', 'error');
				}
            });

        }
	}
	</script>
</div>
</body>
</html>