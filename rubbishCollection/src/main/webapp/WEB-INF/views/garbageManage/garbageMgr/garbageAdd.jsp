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
				    <td>区域：</td><td><input id="REGION_CODE" name="REGION_CODE" class="easyui-combobox" style="height: 25px;"/></td>
                    <!-- <td>项目：</td><td><input id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" style="height: 25px;"/></td> -->
				    <td>项目：</td><td><input id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" type="text" style="height: 25px;"/></td>
					<td>苑区：</td><td><input id="AREA_CODE" name="AREA_CODE" class="easyui-combobox" type="text" style="height: 25px;"/></td>
					<!-- <td>
						<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadDataGrid()">查询</a>
						<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
					</td> -->
				</tr>
			</table>
			</form>
		</div>  
    	
    	
    	<div data-options="region:'west',border:true" style="overflow:auto;width: 250px;"  id="buildiv">
    		<table id="buildGrid"></table>
    	</div> 
    	<div data-options="region:'center',border:true" style="overflow:auto;width: 200px;" id="roomdiv">
    		<table id="roomGrid"></table>
    	</div> 
    	
    	<div data-options="region:'east',border:true" style="overflow:auto;width: 200px;" id="finaldiv">
    		<table id="FinalGrid"></table>
    	</div>
    	
        <div data-options="region:'south'" style="height:75px;">
        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectAllRoom()" style="margin-left:430px;margin-top:10px">全选</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectAllFinal()" style="margin-left:200px;margin-top:10px">全选</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteAllRoom()" style="margin-left:50px;margin-top:10px">清除</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="sendGarbage()" style="margin-left:50px;margin-top:10px">确定</a>   
        </div>
	    	
	</div>
</div>


<script>
$(document).ready(function(){
    initAction('${param.menu_code}');//初始化按钮 
    //initProject(); //初始化项目
    //initProjectCombo();
    initRegionCombo();
     $(".easyui-layout").layout('panel','west').panel("resize",{width:300});
  	 $(".easyui-layout").layout('panel','center').panel("resize",{width:300});
  	 $(".easyui-layout").layout('panel','center').panel("resize",{left:320});
  	 $(".easyui-layout").layout('panel','east').panel("resize",{width:280});
  	 $(".easyui-layout").layout('panel','east').panel("resize",{left:640});
    //loadData();
});
/* var isChangeAction = true;//是否有修改权限,默认没有 */

//初始化项目
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
        $("#PROJECT_CODE").combobox({
		onSelect:function(){
		 $("#AREA_CODE").combobox('clear');
		 initArea();
		 $("#AREA_CODE").combobox('loadData','');
		}			
	   });
    }
//项目初始化
/*     function initProjectCombo(){
        $('#PROJECT_CODE').combobox({
            type: "POST",
            url: url+'/goodsRecovery/getProject.do',
            valueField:'PROJECT_CODE',
            textField:'PROJECT_NAME',
            multiple:false, //设置为多选框
            onLoadSuccess: function () {
            },
        });
        $("#PROJECT_CODE").combobox({
		onSelect:function(){
		 $("#AREA_CODE").combobox('clear');
		 initArea();
		 $("#AREA_CODE").combobox('loadData','');
		}			
	});
    } */



/* function initProject(){
	
    var PROJECT_CODE = {'code':'PROJECT_CODE','name':'PROJECT_NAME','table_name':'T_CODE_PROJECT'};
	initCombo(PROJECT_CODE,'PROJECT_CODE',null,true);
	$("#PROJECT_CODE").combobox({
		onSelect:function(){
		 $("#AREA_CODE").combobox('clear');
		 initArea();
		 $("#AREA_CODE").combobox('loadData','');
		}			
	});
}
 */
//初始化苑区
function initArea(){
   var PROJECT_CODE= $("#PROJECT_CODE").combobox('getValue');
   var AREA_CODE= {"code":"AREA_CODE","name":"AREA_NAME","table_name":"T_CODE_AREA","where_sql":" PROJECT_CODE= '" +PROJECT_CODE +"'"};
   initCombo(AREA_CODE,'AREA_CODE',null,true);
   $("#AREA_CODE").combobox({
		onSelect:function(){
		  loadBuildData();
		  $("#roomdiv").panel('close');
		   var item_room = $('#roomGrid').datagrid('getRows');  
             for (var i = item_room.length - 1; i >= 0; i--) {  
                 var index = $('#roomGrid').datagrid('getRowIndex', item_room[i]);  
                 $('#roomGrid').datagrid('deleteRow', index);  
             } 
	      $("#finaldiv").panel('close');
	      var item_final = $('#FinalGrid').datagrid('getRows');  
             for (var i = item_final.length - 1; i >= 0; i--) {  
                 var index = $('#FinalGrid').datagrid('getRowIndex', item_final[i]);  
                 $('#FinalGrid').datagrid('deleteRow', index);  
             }  
	      
		}			
	});
}

var bmGrid;
function loadBuildData(){
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
    bmGrid=$('#buildGrid').datagrid({
		url : url+'/garbage/garbageMgrController/getBuildByBuild.do',
		queryParams: {'data' : JSON.stringify(data)},
		fit : true,
		fitColumns:true,
		singleSelect : true,
		width:100,
		rowStyler:function(index,row){
	          if (index%2==1){
	              return 'background-color:#f3f4f6;';
	          }
			},
       columns : [
			[
			    {field :'BUILD_CODE', title :'BUILD_CODE', width : 50,hidden:true,align:'center'},
			 	{field :'BUILD_NAME', title :'楼座', width : 50,align:'center'},
			 	
			]],
	  onSelect:function(rowIndex, rowData){
	      var BUILD_CODE= rowData.BUILD_CODE;
	      loadRoomData(BUILD_CODE);
	  }
	});
}

var roomGrid;
function loadRoomData(BUILD_CODE){
	$("#roomdiv").panel('open');
	var data = {};
	data.BUILD_CODE=BUILD_CODE;
    roomGrid=$('#roomGrid').datagrid({
		url : url+'/garbage/garbageMgrController/getRoomByBuild.do',
		queryParams: {'data' : JSON.stringify(data)},
		fit : true,
		fitColumns:true,
		singleSelect : true,
		width:100,
		rowStyler:function(index,row){
	          if (index%2==1){
	              return 'background-color:#f3f4f6;';
	          }
			},
       columns : [
			[
			    {field :'ROOM_CODE', title :'ROOM_CODE', width : 50,hidden:true,align:'center'},
			 	{field :'ROOM_NAME', title :'房间', width : 50,align:'center'}	,
			 	{field :'ROOM_NAME_DETAIL', title :'房间详情', width : 50,align:'center', hidden:true}		
			]],
	  onLoadSuccess:function(data){
	  console.info(data);
	       if(data.total==0){
	          $("#roomdiv").panel('close');
	          $("#finaldiv").panel('close');
	          parent.$.messager.alert('提示','当前楼座下所有房间垃圾袋条形码已经生成完毕！请重新选择楼座!','info');
	      }
	  },
	  onSelect:function(rowIndex, rowData){
	      var ROOM_CODE= rowData.ROOM_CODE;
	      var ROOM_NAME_DETAIL= rowData.ROOM_NAME_DETAIL;
	      //初始化FinalGrai
	      initFinalData();
	      toRight(ROOM_CODE,ROOM_NAME_DETAIL);
	  },
	
	});
}

function selectAllRoom(){
	initFinalData();
 	var item_final = $('#FinalGrid').datagrid('getRows');  
    for (var i = item_final.length - 1; i >= 0; i--) {  
        var index = $('#FinalGrid').datagrid('getRowIndex', item_final[i]);  
        $('#FinalGrid').datagrid('deleteRow', index);  
    }  
    
	$("#roomGrid").datagrid('selectAll');
	var row = $("#roomGrid").datagrid('getSelections');
	for(var i = 0 ; i<row.length;i++){
		var ROOM_CODE= row[i].ROOM_CODE;
		var ROOM_NAME_DETAIL= row[i].ROOM_NAME_DETAIL;
	      //初始化FinalGrai
		toRight(ROOM_CODE,ROOM_NAME_DETAIL);
	}
	
}

function selectAllFinal(){
	$("#FinalGrid").datagrid('selectAll');
}

function deleteAllRoom(){
	var item_final = $('#FinalGrid').datagrid('getRows');  
    for (var i = item_final.length - 1; i >= 0; i--) {  
        var index = $('#FinalGrid').datagrid('getRowIndex', item_final[i]);  
        $('#FinalGrid').datagrid('deleteRow', index);  
    } 
}

var finalGrid;
function initFinalData(){
	$("#finaldiv").panel('open');
    finalGrid=$('#FinalGrid').datagrid({
		fit : true,
		fitColumns:true,
		width:100,
		rowStyler:function(index,row){
	          if (index%2==1){
	              return 'background-color:#f3f4f6;';
	          }
			},
		 columns : [
		[
		    {field :'ROOM_CODE', title :'ROOM_CODE', width : 50,hidden:true,align:'center'},
		 	{field :'ROOM_NAME_DETAIL', title :'发放房间', width : 50,align:'center'}		
		]],
	});
}

//添加
function toRight(ROOM_CODE,ROOM_NAME_DETAIL){
	//获取选中的账号信息
	//var rows = $('#roomGrid').datagrid('getSelections');
	
/* 	console.info(ROOM_CODE);
	console.info(ROOM_NAME_DETAIL); */
	/* if(!rows || 0 == rows.length){	
		$.messager.alert('提示','请选择一个房间','info');
		return;
	} */
	
	var custRows = $('#FinalGrid').datagrid('getRows');
	
	for(var i = 0; i < custRows.length; i++){
		/* for(var j = 0; j < rows.length; j++){ */
			if(ROOM_CODE == custRows[i].ROOM_CODE){
				$.messager.alert('提示','选中的房间已经存在','info');
				return ;
			}
		//}
	}
	
	//for(var i = 0; i < rows.length; i++){
	//新增一行
		$('#FinalGrid').datagrid('insertRow', {
			index : i,
			row : {
				ROOM_CODE : ROOM_CODE,
				ROOM_NAME_DETAIL : ROOM_NAME_DETAIL
			}
		});
	
	//}
/* 	$('#selectCustGrid').datagrid('clearSelections');//清除选中
	$('#selectCustGrid').datagrid('selectRow', 0);//选中一行 */
	
}

//移除
function toLeft(){
	//获取选中的数据
	var rows = $('#FinalGrid').datagrid('getSelections');
	
	if(0 == rows.length){
		$.messager.alert('提示','请选择一条要移除的数据','info');
	} else {
		for(var i = 0; i < rows.length; i++){
			var rowIndex = $('#FinalGrid').datagrid('getRowIndex', rows[i]);
			$('#FinalGrid').datagrid('deleteRow', rowIndex);
		}
	}
}

	//执行添加
	function sendGarbage(){
	   var account= $('#FinalGrid').datagrid('getSelections');//获取选中行
	    var roomList = [];//存放所有需要删除的用户id
	
	   	if(0!= account.length){
	   	    //执行添加
		   	for(var i = 0;i<account.length;i++){
				roomList.push(account[i].ROOM_CODE);
			}
			var data={};
			data.ROOM_CODES = roomList.join(',');
			parent.$.messager.progress({
    			title : '新增进度',
    			text : '数据新增中,请勿关闭窗口........'
    		});
			$.ajax({
						type : 'post',
						url : url+'/garbage/garbageMgrController/addGatbageCode.do',
						data :data,
						dataType : 'json',
						success : function(result) {
							if(result.success){
							    parent.$.messager.progress('close');	
								parent.$.messager.alert("提示", "垃圾袋创建成功", "info", function () {
								    var pageName="条形码信息.zip";
				            		$('#roomdiv').css("display","none");
				            		$('#finaldiv').css("display","none");
				            		$('#buildiv').css("display","none");
				            		window.location.href = url+'/garbage/garbageMgrController/downloadExcel.do?filepath='+ result.msg+'&pageName='+pageName;
				            	});
							}else{
								parent.$.messager.alert('提示','系统异常','error');
								$(".no-message").hide();
							}
						}
					});
	   	}else{
			parent.$.messager.alert('提示','请在【发放房间栏】中选择发放房间！','error');
		}
	}
	


   //查询
	function loadDataGrid(){	
		var form= $("#searchForm");
        var data = ns.serializeObject(form);
        data={'data':JSON.stringify(data)};
        bmGrid.datagrid('load',data);      
	}
	</script> 
	</body>
</html>