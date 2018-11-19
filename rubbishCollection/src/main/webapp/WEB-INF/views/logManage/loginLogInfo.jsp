<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ct}/plugin/pagination/pagination.css">
</head>
<style>
	#detailForm table tr td{
		width:90px;
		padding:10px 5px;
		text-align:left;
	}
</style>
<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<form id="detailForm" style="height:90%">
			<table
				style="text-align:right;border-collapse:collapse; border-spacing:12px;margin:0 auto">
				<tr>
					<!-- <td>id：</td>
					<td><input type="text" style="width: 175px; height:22px;" id="ID"
						name="ID" class="easyui-validatebox"/>
					</td> -->
					<td>登录账号：</td>
					<td><input readonly="true" type="text" style="width: 150px; height:22px;" id="LOGIN_ACCNT"
						name="LOGIN_ACCNT" class="easyui-validatebox" />
					</td>
					
					<td>操作类型名称：</td>
					<td><input readonly="true" id="OPERA_NAME" name="OPERA_NAME" class="easyui-validatebox"
						style="width: 150px; height:22px;"></input>
					</td>
					
				</tr>
				
				<tr>
					<td>详细信息：</td>
					<td><input readonly="true" style="width: 150px; height:22px;" type="text"
						id="DETAIL" name="DETAIL"
						class="easyui-validatebox" />
					</td>
					<td>操作人IP：</td>
					<td><input readonly="true" type="text" style="width: 150px; height:22px;" id="IP"
						name="IP" class="easyui-validatebox" />
					</td>
				</tr>
				
				<tr>
					<td>操作创建人：</td>
					<td><input readonly="true" type="text" style="width: 150px; height:22px;" id="CREATED_BY"
						name="CREATED_BY" class="easyui-validatebox" />
					</td>
					<td>创建时间：</td>
					<td><input readonly="true" style="width: 150px; height:22px;" type="text"
						id="CREATED_TIME" name="CREATED_TIME"
						class="easyui-validatebox" />
					</td>
				</tr>
			</table>
			<input style="width: 150px;display:none" readonly type="text"
				id="LOG_ID" name="LOG_ID" class="easyui-validatebox" />
		</form>
	</div>
	<script type="text/javascript">
		var logId = '${param.data}';
		
		$(document).ready(function() {
			details();
		});

		function details() {
			var datas = {};
			datas.UUID = logId;
			$.ajax({
				type : 'post',
				url : url + '/logManage/LogMgrController/getLoginLogInfo.do',
				dataType : 'json',
				data : {
					'data' : JSON.stringify(datas)
				},
				success:function(rel) {
					$("#detailForm").form('load', rel[0]);//返回结果是list
				},
				error : function() {
					$.messager.alert('错误', '系统异常！', 'error');
				}
			});
		}
		
	</script>
</body>
</html>
