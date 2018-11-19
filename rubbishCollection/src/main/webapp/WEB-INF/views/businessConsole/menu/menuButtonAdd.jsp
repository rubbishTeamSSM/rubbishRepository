<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script src="${ct}/plugin/arithmetic.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<title>新增菜单按钮</title>
</head>
  
<body>
    <div class="easyui-panel" data-options="fit:true,border:false">
		<form id="menuBtnForm" style="margin: 20px 40px;">
			<table>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>按钮名称：</td>
					<td><input type="text" name="button_name" class="easyui-validatebox" data-options="required:true,validType:['length[1,40]']"/></td>
				</tr>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>&nbsp;&nbsp;&nbsp;</font>执行方法：</td>
					<td>
						<input type="text" name="button_url" class="easyui-validatebox" data-options="required:false,validType:['length[1,50]']"/>
					</td>
				</tr>
				<tr>
					<td><font color='red' size='2' style='vertical-align: middle;'>&nbsp;&nbsp;&nbsp;</font>图标样式：</td>
					<td><input type="text" name="button_style" class="easyui-validatebox" data-options="validType:['length[0,50]']"/></td>
				</tr>
				
				<tr>
					<td align="right"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>排序：</td>
					<td><input type="text" name="sort_no" class="easyui-numberbox" data-options="required:true,validType:['length[1,9]']"/></td>
				</tr>
				
				
				<tr>
					<td align="right"><font color='red' size='2' style='vertical-align: middle;'>&nbsp;&nbsp;&nbsp;</font>备注：</td>
					<td><input type="text" name="remark" class="easyui-validatebox" data-options="validType:['length[0,500]']"/></td>
				</tr>
				
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
function saveData($dialog, $grid, $pjq,parent_button_code,menu_code,level){
	var form = $("#menuBtnForm");
	var isValid = $(form).form('validate');
	if(isValid){
		var btnInfo = ns.serializeObject(form);
		btnInfo.menu_code = menu_code;//菜单代码
		btnInfo.parent_button_code = parent_button_code;//上级按钮代码
		btnInfo.level = Number(level).add(1);//层次
    	$.ajax({
    		url : '${ct}/businessConsole/menuBtn/addMenuBtn.do',
    		type : 'post',
    		data : btnInfo,
    		dataType : 'json',
    		beforeSend : function(){
	       		parent.$.messager.progress({
	       			title : '新增',
	       			text : '保存中。。。。'
	       		});
	       	},
    		error : function() {
    			//关闭进度条
				parent.$.messager.progress('close');
    			parent.$.messager.alert('错误', '系统异常！', 'error');
    		},
    		success : function() {
    			//关闭进度条
				parent.$.messager.progress('close');
    			parent.$.messager.alert('提示', '新增成功！', 'info',
    				function(){
    					$grid.tree({
                        	url:'${ct}/businessConsole/menuBtn/getMenuBtnList.do?data='+menu_code,
	                    }); 
    					$dialog.dialog('destroy');//关闭窗口
    				}
    			);
    		}
    	});
	}
}
</script>
</html>
