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
		<div data-options="region:'north'" style="height:75px;">
			 <form id="searchForm" class="has-adv-search">
			<table>
				<tr>
					<!-- <td>区域：</td>
					<td>
					<select style="width:150px;height:24px;"  id="REGION_CODE" name="REGION_CODE" class="easyui-combobox" data-options=""></select>
             		</td>
             		
					<td>项目：</td>
					<td>
					<select style="width:150px;height:24px;"  id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" data-options=""></select>
             		</td> -->
             		    <td>区域：</td><td><input id="REGION_CODE" name="REGION_CODE" class="easyui-combobox" style="height: 25px;"/></td>
                        <td>项目：</td><td><input id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" style="height: 25px;"/></td>
             		
             		
					<td>年份：</td>
					<td>
					<%--<select style="width:120px;height:24px;"  id="YEAR" name="YEAR" class="easyui-combobox" data-options=""></select>
             		--%>
             		<input name="YEAR" id="YEAR" type="text" style="height: 25px;"/>
             		</td>
					<td>月份：</td><td><input name="MONTH" id="MONTH" type="text" style="height: 25px;"/></td>
					<td>
						<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadDataGrid()">查询</a>
						<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm');initRegionCombo();">重置</a> 
					</td>
				</tr>
			</table>
			</form>
		</div>  
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
    
    initRegionCombo();
    //initProjectCombo();
    
    initYear();//初始化年
    initMonth();//初始化月
    loadData();//加载数据
});


//区域初始化
    function initRegionCombo(){
         $('#REGION_CODE').empty();
         $('#REGION_CODE').combobox({
            type: "POST",
            url: url+'/goodsRecovery/getRegion.do',
            valueField:'REGION_CODE',
            textField:'REGION_NAME',
            multiple:false, //设置为多选框
            onLoadSuccess: function () {
            }
        });
        
        $("#REGION_CODE").combobox({
			onSelect:function(){
			 	$("#PROJECT_CODE").combobox('clear');
				projectCombo();
				$("#PROJECT_CODE").combobox('loadData','');
			}			
		});
    }
    
    //项目级联
    function projectCombo(){
    	var REGION_CODE = $("#REGION_CODE").combobox('getValue');
        $('#PROJECT_CODE').empty();
        $('#PROJECT_CODE').combobox({
            type: "POST",
            url: url+'/goodsRecovery/getProjectCombobox.do?REGION_CODE='+REGION_CODE,
            valueField:'PROJECT_CODE',
            textField:'PROJECT_NAME',
            multiple:false, //设置为多选框
            onLoadSuccess: function () {
            }
        });
    }
    
    //项目初始化
    function initProjectCombo(){
        $('#PROJECT_CODE').combobox({
            type: "POST",
            url: url+'/goodsRecovery/getProject.do',
            valueField:'PROJECT_CODE',
            textField:'PROJECT_NAME',
            multiple:false, //设置为多选框
            onLoadSuccess: function () {
            }
        });
    }

/* //区域
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
}  */
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
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
	roomGrid = $('#roomGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/garbage/garbageMgrController/getGarbageInfoGrid.do',
				queryParams: {'data' : JSON.stringify(data)},
				fitColumns:true,
				fit:true,
				remoteSort : false,
				toolbar : '#buttonContent',
				rowStyler:function(index,row){
		          if (index%2==1){
		              return 'background-color:#f3f4f6;';
		          }
				},
                columns : [
				[
				    {field :'UUID', title :'UUID', width : 50,hidden:true},
				 	{field :'GARBAGE_BAGE_CODE', title :'垃圾袋编号', width : 50,sortable : true},
				 	{field :'AREA_NAME', title :'苑区', width : 50,sortable : true},
				 	{field :'BUILD_NAME', title :'楼栋', width : 50,sortable : true},
				 	{field :'ROOM_NAME', title : '房间号', width : 50,sortable : true},
				 	/* {field :'RECOVER_STATUS', title : '回收状态', width : 50,sortable : true}, */
				 /* 	{field :'SEND_TIME', title :'发放时间', width : 30}, */
				 	{field :'RECOVER_TIME', title : '回收时间', width : 50,sortable : true}	
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
//下载模板
function downDemo(){
	window.location.href=url+"/file/roomExcel.xlsx";
}
//导出
function exportGarBageInfo(){
	var pageName="垃圾袋回收信息.zip";
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
	$.ajax({
		type : 'post',
    	url:url+'/garbage/garbageMgrController/getGarbageInfoListForExport.do',
  		data :{'data' : JSON.stringify(data)},
      	dataType : 'json',
      	success : function(data) {
     		if (data.success) {
				window.location.href = url+'/garbage/garbageMgrController/downloadExcel.do?filepath='+ data.msg+'&pageName='+pageName;
         	} else {
             	$.messager.alert('提示', data.msg, 'error');
         	}
   		}
	});
}     
</script> 
</body>
</html>