<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
	
	<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
	
	<script type="text/javascript">
			var path = "${ct}/";
			var jobInfoGrid;
			
			$(document).ready(function() {
				initAction('${param.menu_code}');//初始化按钮
				searchJobInfo();
			});
			function searchJobInfo() {
				var form = $("#searchForm");
				var data = ns.serializeObject(form);
				jobInfoGrid = $('#dataGrid').datagrid($.extend({
					url : path + 'businessConsole/jobInfo/getJobInfoList.do',
					remoteSort : false,
					singleSelect : true,
					fitColumns : true,
					queryParams : {
						'data' : JSON.stringify(data),
						'cSEXj' : 'jobInfo'
					},
					rowStyler:function(index,row){
			        	if (index%2==1){
			        		return 'background-color:#f3f4f6;';
			          	} 
					},
					columns : [ [ {
						field : 'JOB_NAME',
						title : '任务名称',
						formatter: fieldFormatter,
						width : 80,
						sortable : true,
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'EXEC_CLASS_NAME',
						title : '执行类名称',
						width : 80,
						sortable : true,
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'EXEC_CLASS',
						title : '执行类',
						width : 150,
						sortable : true,
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'CRON_EXPRESS',
						title : '时间表达式',
						width : 140,
						sortable : true,
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'NEXT_EXEC_DATE',
						title : '下次执行时间',
						width : 80,
						align : 'center',
						sortable : true,
						hidden : true,
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'DEL_FLAG',
						title : '启用状态',
						width : 60,
						sortable : true,
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return "已停用";
							} else if (value == "1") {
								return "已启用";
							} else {
								return "";
							}
						},
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}, {
						field : 'EXEC_FLAG',
						title : '运行状态',
						width : 60,
						sortable : true,
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return "未执行";
							} else if (value == "1") {
								return "执行中";
							} else {
								return "";
							}
						},
						sorter : function(one, two) {
							return one.localeCompare(two); // 排序
						}
					}/*
						 * ,{ field : '_operate', title : '操作', width : 60, sortable : true,
						 * align:'center', formatter: function(value,row,index){ return '<div
						 * style="text-alogn:center"><a style="text-decoration:none;"
						 * href="#" onclick="updateJob('+row+',0)">停止</a> <a
						 * style="text-decoration:none;" href="#"
						 * onclick="updateJob('+row+',1)">执行</a></div>'; }, sorter :
						 * function(one, two) { return one.localeCompare(two); //排序 } }
						 */
						] ],
						toolbar : '#buttonContent'
					}, ns.datagridOptions));
			 }
			//查询 
			function searchJobInfos(){
				var form = $("#searchForm");
				var data = ns.serializeObject(form);
		        data={'data':JSON.stringify(data),'cSEXj' : 'jobInfo'};
		        jobInfoGrid.datagrid('load',data);
			}
			//执行停止任务方法
			function updateJob(DEL_FLAG) {
				var select = jobInfoGrid.datagrid('getSelected');// 选择的记录
				if (null == select) {
					parent.$.messager.alert('错误', '请选择任务!', 'error');
				} else {
					parent.$.messager.confirm('确认', '确认操作?', function(r){
						if (r){
							updateJobState(select, DEL_FLAG);
						}
					});
				}
			}
			
			function updateJobState(select, DEL_FLAG) {
				var state = select.DEL_FLAG;
				var UUID = select.UUID;
				var JOB_NAME = encodeURI(encodeURI(select.JOB_NAME));
				var EXEC_CLASS = select.EXEC_CLASS;
				var CRON_EXPRESS = select.CRON_EXPRESS;
				$.ajax({
					type : 'post',
					url : path + 'businessConsole/jobInfo/getJobState.do?UUID=' + UUID,
					data : {},
					dataType : 'json',
					success : function(result) {
						var EXEC_FLAG = result.EXEC_FLAG; 
						if (state == '0' && DEL_FLAG == '0') {
							parent.$.messager.alert('错误', '任务已停用!', 'error');
							return false;
						} else if ((state == '1' && DEL_FLAG == '0') && EXEC_FLAG == '1') {
							parent.$.messager.alert('错误', '任务正在执行,不能停用!', 'error');
							$('#dataGrid').datagrid('clearSelections');
							$('#dataGrid').datagrid("reload");
							return false;
						} else if ((state == '1' && DEL_FLAG == '2') && EXEC_FLAG == '1') {
							parent.$.messager.alert('错误', '任务正在执行,不能作废!', 'error');
							$('#dataGrid').datagrid('clearSelections');
							$('#dataGrid').datagrid("reload");
							return false;
						} else if (state == '1' && DEL_FLAG == '1') {
							parent.$.messager.alert('错误', '任务已启用!', 'error');
							return false;
						} else {
							$.ajax({
								type : 'post',
								url : path + 'businessConsole/jobInfo/updateJobState.do?UUID=' + UUID
										+ "&DEL_FLAG=" + DEL_FLAG + "&JOB_NAME=" + JOB_NAME + "&EXEC_CLASS=" + EXEC_CLASS
										+ "&CRON_EXPRESS=" + CRON_EXPRESS,
								data : {},
								dataType : 'json',
								success : function(result) {
									if (result.success) {
										parent.$.messager.alert("成功", "操作成功！", "info", function (){
											$('#dataGrid').datagrid("reload");
											$('#dataGrid').datagrid('clearSelections');
										});
									} else {
										parent.$.messager.alert("成功", "操作失败！", "info", function (){
											$('#dataGrid').datagrid('clearSelections');
										});
									}
								}
							});
						}
			
					}
				});
			}
			
			
			function toDj(flag) {
				var url = "";
				if (flag == '1') {
					var select = jobInfoGrid.datagrid('getSelected');// 选择的记录
					if (select == null) {
						parent.$.messager.alert('错误', '请选择任务!', 'error');
						return false;
					} else {
						var UUID = select.UUID;
						url = path + 'businessConsole/jobInfo/toDj.do?UUID=' + UUID + "&flag=" + flag
								+ '&&cSEXj=jobInfo';
					}
				} else {
					url = path + 'businessConsole/jobInfo/toDj.do?flag=' + flag + '&&cSEXj=jobInfo';
				}
				var pbDialog = parent.ns.modalDialog({
					title : '任务登记',
					width : 800,
					height : 350,
					resizable : true,
					url : url,
					buttons : [ {
						text : '保存',
						iconCls : 'icon-save',
						handler : function() {
							pbDialog.find('iframe').get(0).contentWindow.submitpb(pbDialog,
									jobInfoGrid, parent.$);
							$('#dataGrid').datagrid('clearSelections');
						}
					} ]
			
				});
			}
			
			function findJobClass() {
				var select = jobInfoGrid.datagrid('getSelected');// 选择的记录
				if (null == select) {
					parent.$.messager.alert('错误', '请选择任务!', 'error');
					return false;
				}
				if (null != select) {
					var JOB_CODE = select.JOB_CODE;
					var url = path + 'businessConsole/jobInfo/toFindJobClass.do?JOB_CODE=' + JOB_CODE;
					var jlDialog = parent.ns.modalDialog({
						title : '执行记录查询',
						width : 900,
						height : 420,
						resizable : true,
						url : url
					});
				}
			}
			//登记
			function add(obj){
				toDj('0');
			}
			//修改
			function modify(obj){
				toDj('1');
			}
			//停用
			function disable(obj){
				updateJob('0');
			}
			//启用
			function enable(obj){
				updateJob('1');
			}
			//启用
			function cancel(obj){
				updateJob('2');
			}
	</script>
</head>

<div  class="easyui-panel" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<div data-options="region:'north'" style="height:73px;">
			<form id="searchForm" class="has-adv-search">
				<table>
					<tr>
						<td>任务名称：</td>
						<td><input type="text" class="easyui-validatebox" name="JOB_NAME" /></td>
						<td>执行类名称：</td>
						<td><input type="text" class="easyui-validatebox" name="EXEC_CLASS_NAME" /></td>
						<td>
							<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="searchJobInfos()">查询</a>
							<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
						</td>
					</tr>
				</table>
			</form>
		</div>  
    	<div data-options="region:'center',border:false">
    		<div id="buttonContent">
    			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toDj('0')">登记</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="toDj('1')">修改</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="updateJob('0')">停用</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="updateJob('1')">启用</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="updateJob('2')">作废</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-show'" onclick="findJobClass()">查看执行记录</a> -->
    		</div>
    		<table id="dataGrid"></table>
    	</div> 
	</div>
</div>