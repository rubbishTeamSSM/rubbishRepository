<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String JOB_CODE=request.getParameter("JOB_CODE");
%>
<script type="text/javascript">
//var path = '${ct}';
var JOB_CODE = "<%=JOB_CODE%>";
</script>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
	<%@include file="../common/common.jsp"%>
<%-- 	<script type="text/javascript">
	var path = "${ct}/";
	var UUID = "<%=UUID%>";
	</script> --%>
	<script type="text/javascript">
			var jobInfoGrid;
			var path = '${ct}/';
			$(document).ready(function() {
				initTime();//初始化搜索框日期
				searchJobClass();
			});
			//初始化搜索框日期
			function initTime(){
				//设置开始时间默认为本月1号
				$('#START_TIME').datebox('setValue',time(1));
				//设置结束时间默认为当天日期
				$('#END_TIME').datebox('setValue',time(2));
			}
			function datagrid(gridName, method) {
				return $("#" + gridName).datagrid('method');
			}
			
			 function searchJobClass() {
				var form = $("#searchForm");
				var data = ns.serializeObject(form);
				data.JOB_CODE = JOB_CODE;
				if(data.START_TIME > data.END_TIME){
					parent.$.messager.alert('错误', '开始时间不可大于结束时间！', 'error');
					return false;
				}
				jobInfoGrid = $('#dataGrid').datagrid($.extend({
					url : path + 'businessConsole/jobInfo/findJobClass.do',
					singleSelect : true,
					//idField : 'UUID',
					checkOnSelect : true,
					fitColumns:true,
					queryParams : {
						'data' : JSON.stringify(data)
					},
					columns : [ [ {
						field : 'JOB_NAME',
						title : '任务名称',
						width : 200,
						sortable : true,
						formatter: fieldFormatter,
						align : 'center',
						sorter : function(one, two) {
							return one.localeCompare(two); //排序
						}
					}, {
						field : 'START_TIME',
						title : '执行开始时间',
						width : 200,
						sortable : true,
						align : 'center',
						sorter : function(one, two) {
							return one.localeCompare(two); //排序
						}
					}, {
						field : 'END_TIME',
						title : '执行结束时间',
						width : 200,
						sortable : true,
						align : 'center',
						sorter : function(one, two) {
							return one.localeCompare(two); //排序
						}
					}, {
						field : 'LOG_INFO',
						title : '日志信息',
						width : 200,
						sortable : true,
						sorter : function(one, two) {
							return one.localeCompare(two); //排序
						}
					} ] ]
				}, ns.datagridOptions));
			}
			//查询 
			function searchJobClasss(){
				var form = $("#searchForm");
				var data = ns.serializeObject(form);
				data.JOB_CODE = JOB_CODE;
				if(data.START_TIME>data.END_TIME){
					parent.$.messager.alert('错误', '结束时间不可小于开始时间', 'error');
					return;
				}
		        data={'data':JSON.stringify(data),'cSEXj' : 'jobInfo'};
		        jobInfoGrid.datagrid('load',data);
			}
	</script>
</head>
<div  class="easyui-panel" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<div data-options="region:'north'" style="height:73px;">
			<form id="searchForm" class="has-adv-search">
				<table>
					<tr>
						<td>执行开始时间：</td>
						<td><input type="text" name="START_TIME" id="START_TIME" editable="false" class="easyui-datebox"  style="width:160px"/></td>
						<td>执行结束时间：</td>
						<td><input type="text" name="END_TIME" id="END_TIME" editable="false" class="easyui-datebox"  style="width:160px"/></td>
						<td style="display: none">任务代码</td>
						<td style="display: none"><input type="text" name="JOB_CODE" class="easyui-validatebox"  style="width:150px" value="<%=JOB_CODE%>"/></td>
						<td>
							<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="searchJobClasss()">查询</a>
							<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>  
    	<div data-options="region:'center',border:false">
    		<table id="dataGrid"></table>
    	</div> 
	</div>
</div>
</body>
</html>