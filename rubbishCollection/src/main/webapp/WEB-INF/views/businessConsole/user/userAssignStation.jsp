<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<style>
	.layout-panel-center{
		width:463px!important; 
		height: 480px!important;
	}
	.layout-panel-center{
		top: 60px!important; 
	}
	.datagrid-view{
		/*height: 345px!important;*/
		 overflow:auto!important; 
	}
	.pagination table {
	    float: left!important;
	}
	
	.panel-body{
		overflow:hidden!important;
	}
	.panel{
		width: 490px!important;
		float: left!important;
	}
	.panel-body-noborder {
		width: 480px!important;
	}
	
	/* .layout{
		width: 470px!important;
	} */
</style>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false" style="padding:10px">
	<div class="easyui-layout" data-options="fit:true" id="cc">
		<div data-options="region:'north'" style="height: 80px!important;">
			<form id="searchForm" class="has-adv-search" style="padding: 0 18px;width: 440px;">
				<table>
					<tr>
						<td>小区名称：</td><td><input name="AREA_NAME" id="AREA_NAME" type="text" style="height: 25px;"/></td>
						<td>
							<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadData()">查询</a>
							<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
						</td>
					</tr>
				</table>
			</form>
		</div>  
    	<div data-options="region:'center',border:false">
    		<table id="datagrid"></table>
    	</div> 
	</div>
</div>
		
		
		
	  	<div class="leftMenuIn" style="width: 284px;margin-left: 32px;height: 455px;float: right;margin: 70px 10px 20px 10px;border: 1px #ddd solid;">
			<div class="m-titleIn" style="background-color: #E7E9E8;height: 17px;color: #000000;font-size: 12px;padding: 5px;">岗位目录</div>
			<!-- 管理菜单树 -->
			<ul id="categoryTree" class="easyui-tree" style="height: 420px;overflow:auto;margin-top: 5px;"></ul>
		</div>
 
 
<script>
var USER_CODE = '${USER_CODE}';
$(document).ready(function(){
	loadData();
});

var bmGrid;
var AREA_CODE='';
function loadData(){
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
	bmGrid = $('#datagrid').datagrid($.extend({
    	url : url+'/businessConsole/user/getArea.do?USER_CODE='+USER_CODE,
		queryParams: {'data' : JSON.stringify(data)},
		width:200,
		fitColumns:true,
		remoteSort : false,
		singleSelect: true,
		rowStyler:function(index,row){
          if (index%2==1){
              return 'background-color:#f3f4f6;';
          }
		},
        columns : [[
		 	{field : 'AREA_CODE', title : 'AREA_CODE', hidden : true},
		 	{field :'AREA_NAME', title :'小区名称', width : 100}
		]],
		onClickRow:function(rowIndex, rowData)
		{	
			AREA_CODE=rowData.AREA_CODE;
			showAssgin(AREA_CODE);
		}
	},ns.datagridOptions));
}
 

/**
 * 获取岗位树
 */
var categoryTree;
function showAssgin(AREA_CODE){
	categoryTree =$('#categoryTree').tree({
        url:url+'/businessConsole/user/getStationTree.do?AREA_CODE='+AREA_CODE,
        checkbox:true,
        cascadeCheck:false,
        onLoadSuccess:function(node,data){
        if(""==data)
        { 
          parent.$.messager.alert('提示', '该小区下没有配置岗位', 'info');
          return;
        }
         $(this).find('span.tree-checkbox').unbind().click(function () {
          $('#categoryTree').tree('select', $(this).parent());
          return false;
         });
           $.post(url+'/businessConsole/user/getUserstation.do?AREA_CODE='+AREA_CODE+'&USER_CODE='+USER_CODE,function(result){
            	if(result.success){
                    if(result.msg){
                        for(var i =0; i < result.msg.length;i++){
                            var node = categoryTree.tree('find',result.msg[i]);
                            if(node){
                            	categoryTree.tree('check', node.target);
                            }
                        }
                    }
                }

            },'json');
        },
        onSelect:function(node)
        {
            var cknodes = $('#categoryTree').tree("getChecked");
	        for (var i = 0; i < cknodes.length; i++) {
	          /* if(cknodes[i].id!=''&&node.id!='')
	          { */
	             if (cknodes[i].id != node.id) {
	              $('#categoryTree').tree("uncheck", cknodes[i].target);
	             }
	          /* } */
	        }
	          
	        if (node.checked) {
	          $('#categoryTree').tree('uncheck', node.target);
	 
	        } else {
	          $('#categoryTree').tree('check', node.target);
	 
	        }
        }

    });
};
/**
 * 授权
 */
function submitStation($dialog,$pjq){
    var checkednodes = categoryTree.tree('getChecked');
    //var indeterminatenodes = categoryTree.tree('getChecked', 'indeterminate');
   /*  var ids = [];
    for (var i = 0; i < checkednodes.length; i++) {
        ids.push(checkednodes[i].id);
    } */
   /*  for (var i = 0; i < indeterminatenodes.length; i++) {
        ids.push(indeterminatenodes[i].id);
    } */
    /* if(checkednodes.length>1){
        $pjq.messager.alert('提示', '至多只能选择一个岗位！', 'info');
        return;
    } */
  /*   if(checkednodes.length==0){
        $pjq.messager.alert('提示', '请选择一个岗位！', 'info');
        return;
    } */
    var char='';
   if(checkednodes[0]){
       char=checkednodes[0].id;
   }
   $.post(url+'/businessConsole/user/updateUserStation.do', {
	   	USER_CODE : USER_CODE,
        POST_CODE : char,
        AREA_CODE : AREA_CODE
    }, function(result) {
        if (result.success) {
            $pjq.messager.alert('提示', '设置成功！', 'info');
            //$dialog.dialog('destroy');
        } else {
            $pjq.messager.alert('提示', '设置失败！', 'error');
        }
       
    }, 'json');
}
     
    </script>
  </body>
</html>
