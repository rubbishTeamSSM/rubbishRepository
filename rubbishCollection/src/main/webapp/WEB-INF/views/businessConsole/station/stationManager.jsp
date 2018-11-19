<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<style>
	select option{
		line-height:30px;
		height:30px;
		margin:5px auto;
		}
</style>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<div data-options="region:'north'" style="height:76px;">
			 <form id="searchForm" class="has-adv-search">
				<table>
					<tr>
						<td>岗位名称：</td>
						<td><input type="text" name="POST_NAME" id="POST_NAME" style="width:154px;height:25px;" class="easyui-validatebox" data-options="validType:['unnormal']"/></td>
						
						<td>品质部：</td>
						<td><select id="IS_QUALITY" name="IS_QUALITY" style="width:154px;height:25px;" editable="false">
											<option value="" style="line-height:10px;">全部</option>
											<option value="0">是</option>
											<option value="1">否</option>
						</select></td>
						
						<td>400客服：</td>
						<td><select id="IS_400SERVICE" name="IS_400SERVICE" style="width:154px;height:25px;" editable="false">
											<option value="">全部</option>
											<option value="0">是</option>
											<option value="1">否</option>
						</select></td>
						<td>
							<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="searchStation()">查询</a> 
							<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a>
						</td>
					</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'center',border:false" style="overflow:auto;">
				<div id="buttonContent">
					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="showAddDialog()">新增</a>
    				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="showModDialog()">修改</a>
    				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="drop()">删除</a> -->
    		</div>
	       	<table id="mainGrid"></table>
    	</div> 
	</div>
</div>
	<script>
		$(document).keydown(function(event){
		  switch(event.keyCode){
		     case 13:return false; 
		     }
		});
		var grid;
		$(document).ready(function(){
			initAction('${param.menu_code}');//初始化按钮
			loadData();
		});
		
		//查询
		function searchStation(){
			var form= $("#searchForm");
			var data = ns.serializeObject(form);
			data={'data':JSON.stringify(data)};
        	grid.datagrid('load',data); 
		};
		//获取列表
		function loadData(){			
			var form = $("#searchForm");
			var data = ns.serializeObject(form);
			grid = $('#mainGrid').datagrid($.extend({
				url : url+'/businessConsole/station/manangerStation.do',
				singleSelect : false,
				fitColumns:true,
				remoteSort : false,
				queryParams:{'data' : JSON.stringify(data)},
				rowStyler:function(index,row){
					if(index%2==1){
						return 'background-color:#f3f4f6;';	
					}
				},
				columns : [[
					{field : 'POST_CODE', title : 'POST_CODE', hidden : true,sortable : true},
				 	{field : 'POST_NAME', title : '岗位名称', width : 120,sortable : true,
				 	formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}},
				 	{field : 'POST_NAME_J', title : '岗位名称简', width : 120,sortable : true,
				 	formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}},
			        {field : 'IS_QUALITY', title : '是否品质部', width : 120,sortable : true,
				        formatter : function(value, row, index){
				       			if('1' == value){
				       				return '否';
				       			}else if('0' == value){
				       				return '是';
				       			}
				       		}},
			        {field : 'IS_400SERVICE', title : '是否400客服', width : 120,sortable : true,
			        	formatter : function(value, row, index){
				       			if('1' == value){
				       				return '否';
				       			}else if('0' == value){
				       				return '是';
				       			}
				       		}},
			        {field : 'CREATED_TIME', title : '创建时间', width : 120,sortable : true},
			        {field : 'CREATED_BY', title : '创建者', width : 120,sortable : true}
					]],
				toolbar : '#buttonContent'
			},ns.datagridOptions));				
		}
		
		//新增
		function showAddDialog(){
			var stationDialog = parent.ns.modalDialog({
				title : '新增岗位',
				width : 500,
				height : 400,
				resizable : true,
				url : url + '/businessConsole/station/toStationAdd.do',
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						stationDialog.find('iframe').get(0).contentWindow
								.submitStation(stationDialog, grid, parent.$);
					}
				}]
			});
		}
		
		function showModDialog() {
			var modi = grid.datagrid('getSelections');//选择的用户
			if (modi && 1 == modi.length) {
				var POST_CODE =modi[0].POST_CODE;
				var POST_NAME =modi[0].POST_NAME;
				var stationDialog = parent.ns.modalDialog({
					title : '修改岗位',
					width : 500,
					height : 400,
					resizable : true,
					url : url + '/businessConsole/station/toStationMod.do?POST_CODE='+ POST_CODE+'&POST_NAME='+POST_NAME,
					buttons : [ {
						text : '保存',
						iconCls : 'icon-save',
						handler : function() {
							stationDialog.find('iframe').get(0).contentWindow.submitTelephone(stationDialog, grid, parent.$);
						}
					}]	
				});
			}else {
				parent.$.messager.alert('错误', '请选择一条记录', 'error');
			}
		}
		
		function drop(){
			var dropi = grid.datagrid('getSelections');//获取选中行
			if(null != dropi && 0 != dropi.length){
				parent.$.messager.confirm('确认','确认删除?',function(r){
					if(r){
						var stationList = [];//存放所有需要删除的用户id
						for(var i = 0;i<dropi.length;i++){
							stationList.push(dropi[i].POST_CODE);
						}
						var data = {'POST_CODE':stationList};
						$.ajax({
							type : 'post',
							url : url+'/businessConsole/station/deleteStation.do',
							data :{'data':JSON.stringify(data)},
							dataType : 'json',
							success : function(result) {
								if(result.success){
									parent.$.messager.alert("提示", result.msg, "info", function () {
					            		$('#mainGrid').datagrid('reload');//刷新datagrid
					            	});
								}else{
									parent.$.messager.alert('提示',result.msg,'error');
									$(".no-message").hide();
								}
							}
						});
					}
				});
			}else {
				parent.$.messager.alert('错误', '请选择一条记录', 'error');
			}
		}
	</script>
  </body>
</html>