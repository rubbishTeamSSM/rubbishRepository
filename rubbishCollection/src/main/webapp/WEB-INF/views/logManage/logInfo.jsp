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

<body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<form id="detailForm" style="margin: 20px 40px;height:85%">
			<table
				style="text-align:right;border-collapse:separate; border-spacing:12px;margin:0 auto">
				<tr>
					<!-- <td>id：</td>
					<td><input type="text" style="width: 175px; height:22px;" id="ID"
						name="ID" class="easyui-validatebox"/>
					</td> -->
					<td>用户IP：</td>
					<td><input readonly="true" type="text" style="width: 175px; height:22px;" id="IP"
						name="IP" class="easyui-validatebox" />
					</td>
					
					<td>方法描述：</td>
					<td><input readonly="true" id="FUN_DESC" name="FUN_DESC" class="easyui-validatebox"
						style="width: 175px; height:22px;"></input>
					</td>
					
				</tr>
				
				
				


				<tr>
					<td>方法名：</td>
					<td><input readonly="true" style="width: 175px; height:22px;" type="text"
						id="FUN_NAME" name="FUN_NAME"
						class="easyui-validatebox" />
					</td>
					<td>操作类型：</td>
					<td><input readonly="true" type="text" style="width: 175px; height:22px;" id="OP_TYPE"
						name="OP_TYPE" class="easyui-validatebox" />
					</td>
					
				</tr>
				<tr>
					<td>创建时间：</td>
					<td><input readonly="true" style="width: 175px; height:22px;" type="text"
						id="CREATED_TIME" name="CREATED_TIME"
						class="easyui-validatebox" />
					</td>
					<td>操创建者：</td>
					<td><input readonly="true" type="text" style="width: 175px; height:22px;" id="CREATED_BY"
						name="CREATED_BY" class="easyui-validatebox" />
					</td>
					
				</tr>
				<tr>
					<td>请求参数：</td>
					<td colspan="3"><textarea readonly="true" id="FUN_PARAM" 	 cols="60" rows="10" 
						name="FUN_PARAM" ></textarea>
					</td>
				</tr>
				
				<tr>
					<td>操作信息：</td>
					<td colspan="3"><textarea readonly="true" cols="60" rows="10" 
						id="DETAIL" name="DETAIL" ></textarea>
					</td>
					
				</tr>
			</table>
			<input style="width: 175px;display:none" readonly type="text"
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
			datas.ID = logId;
			$.ajax({
				type : 'post',
				url : url + '/systemSetting/logMgr/getLogInfo.do',
				dataType : 'json',
				data : {'data' : JSON.stringify(datas)},
				success:function(rel) {
					$("#detailForm").form('load', rel);
				},
				error : function() {
					$.messager.alert('错误', '系统异常！', 'error');
				}
			});
		}
		
	</script>
</body>
</html>
