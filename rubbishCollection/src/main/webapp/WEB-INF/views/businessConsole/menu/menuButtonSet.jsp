<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body class="body-list">
<div  class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 10px;">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<div data-options="region:'north'" style="height:70px;">
			 <form id="searchForm" class="has-adv-search">
			<table>
				<tr>
					<td>子公司：</td><td><input name="DEPT_CODE" id="DEPT_CODE" type="text" style="height: 25px;"/></td>
					<td>小区：</td><td><input name="AREA_CODE" id="AREA_CODE" type="text" style="height: 25px;"/></td>
					<td>业主姓名：</td><td><input name="OWNER_NAME" type="text" style="height: 25px;"/></td>
					<td>
						<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadData()">查询</a>
						<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
					</td>
				</tr>
				<tr class="tr-search hidden">
					<td>业主手机：</td><td><input name="OWNER_PHONE" id="OWNER_PHONE" type="text" style="height: 25px;"/></td>
					<td>访客车牌号：</td><td><input name="VEHICLE_NO" id="VEHICLE_NO" type="text" style="height: 25px;"/></td>
					<td>访客手机号：</td><td><input name="VISITOR_PHONE" id="VISITOR_PHONE" type="text" style="height: 25px;"/></td>
				</tr>
			</table>
			<div class="adv-search adv-search-down"><a href="javascript:showAdvSearch('down',125)"><span>高级搜索</span><img src="${ct}/images/adv-down.png" alt="高级搜索"/></a></div>
			<div class="adv-search adv-search-up hidden"><a href="javascript:showAdvSearch('up',75)"><span>收起</span><img src="${ct}/images/adv-up.png" alt="高级搜索"/></a></div>
			</form>
		</div>  
    	<div data-options="region:'center',border:false">
    		<div id="buttonContent">
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="examine(0)">审批通过</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="examine(1)">审批不通过</a>
    		</div>
    		<table id="visitorGrid"></table>
    	</div> 
	</div>
</div>
<script>
$(document).ready(function(){
    loadData();
    initDeptCode();//初始化子公司combobox
    initAreaCode();//初始化小区combobox
});
//初始化子公司combobox
function initDeptCode(){
	var deptCode = {'code':'DEPT_CODE','name':'DEPT_NAME','table_name':'T_SYS_DEPT'};
	initCombo(deptCode,'DEPT_CODE',null,true);
}
//初始化小区combobox
function initAreaCode(){
	var areaCode = {'code':'AREA_CODE','name':'AREA_NAME','table_name':'T_CODE_SYNC_AREA'};
	initCombo(areaCode,'AREA_CODE',null,true);
}
var visitorGrid;
function loadData(){
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
	visitorGrid = $('#visitorGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/visitorInfoController/getVisitorMsg.do',
				queryParams: {'data' : JSON.stringify(data)},
				fitColumns:true,
				remoteSort : false,
				rowStyler:function(index,row){
		          if (index%2==1){
		              return 'background-color:#f3f4f6;';
		          }
				},
                columns : [
				[
				 	{field :'OWNER_NAME', title :'业主姓名', width : 50,sortable : true},
				 	{field :'OWNER_PHONE', title :'业主手机号', width : 50,sortable : true},
				 	{field :'APPROVAL_STATUS', title :'审批状态', width : 50,sortable : true,
				 		formatter : function(value, row, index){
			       			if('0' == value){
			       				return '待审批';
			       			}else{
			       				return '已审批';
			       			}
			       		}
				 	},
				 	{field : 'APPROVAL_RESULT', title : '审批结论', width : 50,sortable : true,
				 		formatter : function(value, row, index){
			       			if('1' == value){
			       				return '审批通过';
			       			}else if('2' == value){
			       				return '审批不通过';
			       			}
			       		}
				 	},
				 	{field : 'VEHICLE_ENTER_STATUS', title : '进入状态', width : 50,sortable : true,
				 		formatter : function(value, row, index){
			       			if(null != row.VEHICLE_OUT_TIME){
			       				return '已离开';
			       			}else if(null != row.VEHICLE_ENTER_TIME){
			       				return '已进入';
			       			}else{
			       				return '未进入';
			       			}
			       		}
				 	},
				 	{field : 'VISITOR_NAME', title : '访客姓名', width : 50,sortable : true},
				 	{field : 'VISITOR_PHONE', title : '访客手机号', width : 50,sortable : true},
				 	{field : 'VEHICLE_NO', title : '访客车牌号', width : 50,sortable : true},
			        {field :'REASON', title :'理由', width : 80,sortable : true},
			        {field : 'OPEN_DOOR_TIME', title : '门禁开启时间', width : 50,
			       		formatter : function(value, row, index){
			       			if(1==value){
			       				return '是';
			       			}else{
			       				return '否';
			       			}
			       		},sortable : true	
			        },
			        {field : 'VEHICLE_ENTER_TIME', title : '车辆进入时间', width : 80,sortable : true},
			        {field : 'VEHICLE_OUT_TIME', title : '车辆离开时间', width : 80,sortable : true}
				]],
				toolbar : '#buttonContent',
                onDblClickRow: function (index, row) {
					//modify(row);
		        }
            },ns.datagridOptions));
}
//审批通过与不通过
function examine(flag){
	//获取选中行
	var visitor= $('#visitorGrid').datagrid('getSelections');
	if(null != visitor && 0 != visitor.length){
		for(var i = 0;i < visitor.length;i ++){
			if('0' != visitor[i].APPROVAL_STATUS){
				parent.$.messager.alert('提示','只有待审批状态才可以审批','error');
				return;
			}
		}
		parent.$.messager.confirm('确认','确认审批访客信息?',function(r){
			if(r){
				var APPROVAL_RESULT;
				//flag为0表示审批通过；1表示审批不通过
				if('0' == flag){
					APPROVAL_RESULT = '1';
				}else if('1' == flag){
					APPROVAL_RESULT = '2';
				}else{
					return;
				}
				var data = {'APPROVAL_RESULT':APPROVAL_RESULT,'VISITORS':visitor};
				$.ajax({
					type : 'post',
			       	url : '${ct}/visitorInfoController/updateApproval.do',
			       	data :{'data':JSON.stringify(data)},
			       	dataType : 'json',
			       	async: false,
			       	success : function(result) {
			       		if(result.success){
							parent.$.messager.alert("提示", "审批成功", "info", function () {
			            		$('#visitorGrid').datagrid('reload');//刷新datagrid
			            	});
						}else{
							parent.$.messager.alert('提示','系统异常','error');
						}
					}
				});
			}
		});
	}else{
		parent.$.messager.alert('错误','请选择需要审批的访客信息','error');
	}
}
</script> 
</body>
</html>