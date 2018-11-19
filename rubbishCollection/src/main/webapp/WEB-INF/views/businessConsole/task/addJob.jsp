<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flag=request.getParameter("flag");
String UUID=request.getParameter("UUID");
String cSEXj=request.getParameter("cSEXj");
%>
<html>
<head>
<script type="text/javascript">
var flag = "<%=flag%>";
var UUID = "<%=UUID%>";
var cSEXj="<%=cSEXj%>";
var EXEC_CLASS_CODE = '';
</script>
<link rel="stylesheet" type="text/css" href="${ct }/css/table.css" />
<script type="text/javascript" >
var path = "${ct}/";
$(document).ready(function(){
	if(flag=='1'){
		EXEC_CLASS_CODE = $("#HIDDEN_EXEC_CLASS_CODE").val();
	}
	initJOB_EXEC_CLASS();
	initSelect();
});

function initSelect(){
	if(flag=='1'){
		$("#EXEC_CLASS_NAME").combogrid("setValue",$("#HIDDEN_EXEC_CLASS_NAME").val()); 
		//$("#EXEC_FLAG").combobox('setValue',$("#HIDDEN_SELECT").val());
	}
}
function initJOB_EXEC_CLASS(){
	$('#EXEC_CLASS_NAME').combogrid({
		panelWidth : 450,
		idField : 'EXEC_CLASS_CODE',
		textField : 'EXEC_CLASS_NAME',
		url : path + 'businessConsole/jobInfo/findJobExec.do?cSEXj=' + cSEXj +'&&EXEC_CLASS_CODE=' + EXEC_CLASS_CODE,
		onSelect : function(data) {
			var select = $('#EXEC_CLASS_NAME').combogrid('grid').datagrid('getSelected');
			$("#EXEC_CLASS").val(select.EXEC_CLASS);
			$("#EXEC_CLASS_CODE").val(select.EXEC_CLASS_CODE);
			$("#HIDDEN_EXEC_CLASS_CODE").val(select.EXEC_CLASS_CODE); 
		},
		columns : [ [ {
			field : 'EXEC_CLASS_CODE',
			title : '执行类代码',
			width : 100
		}, {
			field : 'EXEC_CLASS_NAME',
			title : '执行类名称',
			width : 100
		}, {
			field : 'EXEC_CLASS',
			title : '执行类',
			width : 210
		} ] ]
	});
}

function setCronExpress(){
	var sjDialog = parent.ns.modalDialog({
		title : '时间表达式设置',
		width : 700,
		height : 400,
		resizable : true,
		url : path + 'businessConsole/jobInfo/cronExpress.do',
		buttons : [ {
			text : '确认',
			iconCls : 'icon-save',
			handler : function() {
				var value = sjDialog.find('iframe').get(0).contentWindow
						.getExp(sjDialog, parent.$);
				var cron = value.split("---")[0];
				var sjdisplay = value.split("---")[1];
				var nexttime = value.split("---")[2];
				$("#CRON_EXPRESS").val(cron);
				$("#HIDDEN_TIME").val(sjdisplay);
				$('#NEXT_EXEC_DATE').datetimebox('setValue', nexttime);
			}
		} ]

	});
}


// 保存方法
function submitpb($pbDialog,$jcpbGrid,$pjq){	

	var form = $("#newJobInfoForm");
	var data = ns.serializeObject(form);
	var isValid = $(form).form('validate');
	
	if (isValid) {
		var data = ns.serializeObject(form);
		$.ajax({
			type : 'post',
			url : path + 'businessConsole/jobInfo/addJobInfo.do?flag=' + flag + "&UUID="
					+ $("#HIDDEN_TEXT").val() + "&display="
					+ $("#HIDDEN_TIME").val() + "&HIDDEN_EXEC_CLASS_NAME="
					+ encodeURI(encodeURI($("#HIDDEN_EXEC_CLASS_NAME").val())) + "&HIDDEN_EXEC_CLASS_CODE="
					+ $("#HIDDEN_EXEC_CLASS_CODE").val() + "&HIDDEN_JOB_NAME="
					+ encodeURI(encodeURI($("#HIDDEN_JOB_NAME").val())) + "&EXEC_CLASS="
					+ $("#EXEC_CLASS").val(),
			data : data,
			dataType : 'json',
			success : function(result) {
				parent.$.messager.alert("成功", "操作成功！", "info", function (){
					$pbDialog.dialog('close');
					$jcpbGrid.datagrid('reload');
				});
			},
			error : function(){
				$pjq.messager.alert('错误', '登记失败', 'error');
			}
		});
	}
}	
</script>
</head>
<body >
<div  class="easyui-panel" data-options="fit:true,border:false" >
		<form id="newJobInfoForm" style="margin: 20px 60px;">
			<table class="gridtable" >
			    <tr>
				   <td colspan="1" align="right"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>任务名称：</td>
				   <td colspan="2">
				   <input type="text" id="JOB_NAME" name="JOB_NAME" class="easyui-validatebox"  data-options="required:true,validType:['length[1,50]']" value="${jobInfo.JOB_NAME}"/></td>
				   <td colspan="1" align="right"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>执行类名称：</td>
				   <td>
				   		<input id="EXEC_CLASS_NAME" name="EXEC_CLASS_NAME" class="easyui-combogrid" data-options="required:true,editable:false">  
				   </td>
				   <td style="display: none">
				   		<input type="text" id="EXEC_CLASS_CODE" name="EXEC_CLASS_CODE" type="text" />
				   </td>
				  <%--  <td colspan="2" style= "margin-left: 20px">
				   <input type="text" id="EXEC_CLASS_NAME" name="EXEC_CLASS_NAME" class="easyui-validatebox"  data-options="required:true" value="${jobInfo.EXEC_CLASS_NAME}"/>
				   </td> --%>
				</tr>
				<tr>
				  <td colspan="1" align="right">执行类：</td>
				  <td style= "margin-left: 20px" colspan="2">
				   <input type="text" id="EXEC_CLASS" name="EXEC_CLASS" class="easyui-validatebox"  data-options="required:true" value="${jobInfo.EXEC_CLASS}" disabled="disabled"/>
				  </td>
				  <td colspan="1" align="right"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>时间表达式：</td>
				    <td style="margin-left: 10px" colspan="2"> 
				        <input id="HIDDEN_TEXT" type="hidden" value="${jobInfo.UUID}"/>
						<input id="HIDDEN_SELECT" type="hidden" value="${jobInfo.EXEC_FLAG}"/>
						<input id="HIDDEN_EXEC_CLASS_NAME" type="hidden" value="${jobInfo.EXEC_CLASS_NAME}"/>
						<input id="HIDDEN_EXEC_CLASS_CODE" type="hidden" value="${jobInfo.EXEC_CLASS_CODE}"/>
						<input id="HIDDEN_JOB_NAME" type="hidden" value="${jobInfo.JOB_NAME}"/>
						<input id="HIDDEN_TIME" type="hidden" />
					     <input type="text" id="CRON_EXPRESS" name="CRON_EXPRESS" class="easyui-validatebox" readonly="readonly"  data-options="required:true" value="${jobInfo.CRON_EXPRESS}" />
					     <a  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  plain="true"  onclick="setCronExpress()"></a>
				    </td>
				</tr>	
				<tr style="display: none;">
					<td colspan="1" >下次执行时间：</td>
				  	<td colspan="2" ><input type="text" class="easyui-datetimebox" id="NEXT_EXEC_DATE" name="NEXT_EXEC_DATE"  value="${jobInfo.NEXT_EXEC_DATE}" disabled="disabled"/></td>
					<td colspan="1">是否执行：</td>
				    <td style= "margin-left: 20px" colspan="2">
				    <select class="easyui-combobox" name="EXEC_FLAG" id="EXEC_FLAG" style="width:154px;" >
				    	<option value="0" selected="selected">未执行</option>	
				    	<option value="1">执行中</option>
			        </select>
				   </td>		
				</tr>		    
			</table>
		</form>
</div>
</body>
<html>
  