<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
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
			<input type="hidden" name="button_code" value="${param.BTN_CODE }">
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
var btn_code = '${param.BTN_CODE}';
var menu_code = '${param.MENU_CODE}';
$(function(){
	getMenuBtnDetail();
});

// 获取按钮详情
function getMenuBtnDetail(){
	var btnInfo = {'button_code' : btn_code};
   	$.ajax({
   		url : '${ct}/businessConsole/menuBtn/getMenuBtnDetail.do',
   		type : 'post',
   		data : btnInfo,
   		dataType : 'json',
   		error : function() {
   			parent.$.messager.alert('错误', '系统异常！', 'error');
   		},
   		success : function(data) {
   			var form = $("#menuBtnForm");
   			$(form).form('clear');
   			$(form).form('load', data);
   		}
   	});
}

// 保存
function saveData($dialog, $grid, $pjq){
	var form = $("#menuBtnForm");
	var isValid = $(form).form('validate');
	if(isValid){
		
    	$.ajax({
    		url : '${ct}/businessConsole/menuBtn/updateMenuBtn.do',
    		type : 'post',
    		data : ns.serializeObject(form),
    		dataType : 'json',
    		error : function() {
    			parent.$.messager.alert('错误', '系统异常！', 'error');
    		},
    		success : function() {
    			parent.$.messager.alert('提示', '修改成功！', 'info',
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
