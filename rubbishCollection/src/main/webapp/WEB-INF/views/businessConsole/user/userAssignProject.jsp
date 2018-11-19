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
    	<div data-options="region:'center',border:false" style="overflow:auto;">
    		</div>
    		<table id="dataGrid"></table>
    	</div> 
	</div>
</div>
<script>
var USER_CODE = '${param.USER_CODE}';
$(document).ready(function(){
    loadData();//加载数据
});

var roomGrid;
function loadData(){
	dataGrid = $('#dataGrid').datagrid({
                singleSelect : false,
                url : url+'/businessConsole/user/getAllProject.do',
				fitColumns:true,
				fit:true,
				remoteSort : false,
				rowStyler:function(index,row){
		          if (index%2==1){
		              return 'background-color:#f3f4f6;';
		          }
				},
                columns : [
				[
					{field :'fk', title :'fk', width : 50,checkbox:true},
				    {field :'PROJECT_CODE', title :'项目编码', width : 50},
				 	{field :'PROJECT_NAME', title :'项目名称', width : 50,sortable : true}
				]],
				onLoadSuccess :function (){
					var data={};
					data.USER_CODE = USER_CODE;
		   			//获取当前用户已有的项目
					$.ajax({
				         type : 'post',
				         url : url+'/goodsRecovery/getProject.do',
				         data :{'data' : JSON.stringify(data)},
				         dataType : 'json',
				         success : function(result) {
				             var rows = dataGrid.datagrid('getRows');
				             for(var i = 0 ; i<rows.length;i++){
				            	 for(var j = 0 ; j<result.length;j++){
				            		 if(rows[i].PROJECT_CODE == result[j].PROJECT_CODE){
				            			 $('#dataGrid').datagrid("selectRow",i);
				            		 }
				            	 }
				             }
				         }
				     });
			    }
            });
}
function submitUserProject($userDialog,$pjq,USER_CODE){
	var checkData = dataGrid.datagrid('getSelections');//选择的项目
	var data = {};
 	if(0== checkData.length){
 		 $pjq.messager.alert('错误','选择项目','error');
 		return;
 	}
 	data.PROJECT = checkData;
	data.USER_CODE = USER_CODE;
     $.ajax({
         type : 'post',
         url : '${ct}/businessConsole/user/updateUserProject.do',
         data :{'data' : JSON.stringify(data)},
         dataType : 'json',
         success : function(result) {
             if(result.success){
                 $userDialog.dialog('destroy');
                 $pjq.messager.alert('成功','设置成功','info');
             }else{
                 $pjq.messager.alert('错误','设置失败','error');
             }
         }
     });
}
</script> 
</body>
</html>