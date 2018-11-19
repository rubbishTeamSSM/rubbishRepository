<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<!-- <div data-options="region:'north'" style="height:75px;">
		 <form id="searchForm" class="has-adv-search">
			<table>
				<tr>
					<td>区域：</td>
					<td>
					<select style="width:150px;height:24px;"  id="REGION_CODE" name="REGION_CODE" class="easyui-combobox" data-options=""></select>
             		</td>
             		
					<td>项目：</td>
					<td>
					<select style="width:150px;height:24px;"  id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" data-options=""></select>
             		</td>
             		
             		
					<td>年份：</td>
					<td>
					<select style="width:120px;height:24px;"  id="YEAR" name="YEAR" class="easyui-combobox" data-options=""></select>
             		
             		<input name="YEAR" id="YEAR" type="text" style="height: 25px;"/>
             		</td>
					<td>月份：</td><td><input name="MONTH" id="MONTH" type="text" style="height: 25px;"/></td>
					<td>
						<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadDataGrid()">查询</a>
						<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
					</td>
				</tr>
			</table> 
			</form> 
		</div>   -->
    	<div data-options="region:'center',border:false" style="overflow:auto;">
    		<div id="buttonContent" style="height: 35px">
    		</div>
    		<table id="roomGrid"></table>
    	</div> 
	</div>
</div>
<script>
$(document).ready(function(){
    initAction('${param.menu_code}');//初始化按钮 
    //initRegionCombo();//初始化区域
    //initYear();//初始化年
    //initMonth();//初始化月
    loadData();//加载数据
});

//区域
function initRegionCombo(){
	var REGION_CODE = {'code':' REGION_CODE','name':'REGION_NAME','table_name':'T_CODE_REGION'};
	initCombo(REGION_CODE,'REGION_CODE',null,true);
	$("#REGION_CODE").combobox({
		onSelect:function(){
		 $("#PROJECT_CODE").combobox('clear');
		 initProjectCombo();
		 $("#PROJECT_CODE").combobox('loadData','');
		}			
	});
} 

//项目
function initProjectCombo(){
	var REGION_CODE= $("#REGION_CODE").combobox('getValue');
	var PROJECT_CODE = {"code":" PROJECT_CODE","name":"PROJECT_NAME","table_name":"T_CODE_PROJECT","where_sql":" REGION_CODE= '" +REGION_CODE +"'"};
	initCombo(PROJECT_CODE,'PROJECT_CODE',null,true);
} 
var yearDate = [{"value":"2018","text":"2018"}, {"value":"2019","text":"2019"},
                {"value":"2020","text":"2020"}, {"value":"2021","text":"2021"}];
//初始化年
function initYear(){
	var nowDate = new Date();
    var year = nowDate.getFullYear();//获取当前年份
	$("#YEAR").combobox({
		data:yearDate,
		valueField:'value',
		textField:'text',
		panelHeight:'auto',
	    onLoadSuccess:function(data){
	    	$("#YEAR").combobox("setValue",year);
		}
	});
}
var monthDate = [{"value":"1","text":"1"}, {"value":"2","text":"2"}, {"value":"3","text":"3"}, {"value":"4","text":"4"}, 
				 {"value":"5","text":"5"}, {"value":"6","text":"6"}, {"value":"7","text":"7"}, {"value":"8","text":"8"}, 
				 {"value":"9","text":"9"}, {"value":"10","text":"10"}, {"value":"11","text":"11"}, {"value":"12","text":"12"}];
//初始化月
function initMonth(){
	var nowDate = new Date();
    var month = nowDate.getMonth()+1;//获取当前月份
	$("#MONTH").combobox({
		data:monthDate,
		valueField:'value',
		textField:'text',
		panelHeight:'auto',
	    onLoadSuccess:function(data){
	    	$("#MONTH").combobox("setValue",month);
		}
	});
}
/* var isChangeAction = true;//是否有修改权限,默认没有 */
var roomGrid;
function loadData(){
/* 	var form= $("#searchForm");
	var data = ns.serializeObject(form); */
	roomGrid = $('#roomGrid').datagrid($.extend({
                url : url+'/garbage/garbageMgrController/getGarbageRecord.do',
				fitColumns:true,
				fit:true,
				remoteSort : false,
				singleSelect : true,
				rowStyler:function(index,row){
		          if (index%2==1){
		              return 'background-color:#f3f4f6;';
		          }
				},
                columns : [
				[
				    {field :'UUID', title :'UUID', width : 50,hidden:true},
				    {field :'fk', title :'fk', width : 50,sortable : true,checkbox : true},
				 	{field :'CREATED_TIME', title :'日期', width : 50,sortable : true},
				 	{field :'AREA_NAME', title :'苑区', width : 50,sortable : true},
				 	{field :'BATCH', title :'批次号', width : 50,sortable : true,}
				]],
				toolbar : '#buttonContent'
            },ns.datagridOptions));
}
//查询
function loadDataGrid(){	
	var form= $("#searchForm");
    var data = ns.serializeObject(form);
    data={'data':JSON.stringify(data)};
    roomGrid.datagrid('load',data);      
}
//导出
function exportGarBageBatchInfo(){
	var account= $('#roomGrid').datagrid('getSelections');
	if(1 == account.length){
		var pageName="第"+account[0].BATCH+"批次垃圾袋条形码.zip";
		var form= $("#searchForm");
		var data = ns.serializeObject(form);
		parent.$.messager.progress({
    			title : '导出进度',
    			text : '数据导出中,请勿关闭窗口........'
    	});
		$.ajax({
			type : 'post',
	    	url:url+'/garbage/garbageMgrController/getGarbageBatchForExport.do?batch='+account[0].BATCH,
	  		data :{'data' : JSON.stringify(data)},
	      	dataType : 'json',
	      	success : function(result) {
	      	  parent.$.messager.progress('close');	
	     		if (result.success) {
					window.location.href = url+'/garbage/garbageMgrController/downloadExcel.do?filepath='+ result.msg+'&pageName='+pageName;
	         	} else {
	             	$.messager.alert('提示', '导出失败!', 'error');
	         	}
	   		}
		});
	}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	
}

//详情
 function showBatchInfo(){
 var account= $('#roomGrid').datagrid('getSelections');
	if(1 == account.length){
	   var userDialog =  parent.ns.modalDialog({
	    title : '批次详情',
        width : 800,
        height : 600,
        resizable : true,
        url : url+'/garbage/garbageMgrController/showBatchInfo.do?batch='+account[0].BATCH,
        buttons : [{
            text : '返回',
            iconCls : 'icon-cancel',
            handler : function(){
                //userDialog.find('iframe').get(0).contentWindow.save(userDialog,bmGrid,parent.$,account[0].USER_CODE);
                  userDialog.dialog('destroy');
            }
        }]
	   });
	}else{
	  parent.$.messager.alert('提示','请选择一条数据','error');
	}
 }
</script> 
</body>
</html>