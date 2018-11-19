<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<style type="text/css">
	.has-adv-search{
		margin-top:10px;
		padding:5px 10px;
		border:1px solid #ddd;
		box-shadow:0 3px 3px #ddd;
		background:#fff;
	}

	.has-adv-search .easyui-linkbutton{
	font-size:12px;
	text-decoration:none;
	display:inline-block;
	zoom:1;
	height:24px;
	padding-right:18px;
	cursor:pointer;
	outline:none;
	border-radius:2px;
	border:0;
	}

	.has-adv-search .reload{
	background-color:#e3e3e3;
	color:#333;
	}
	
	.has-adv-search a.l-btn span.l-btn-left{
	display:inline-block;
	padding:4px 10px 4px 18px;
	line-height:16px;
	height:16px;
	border-radius:2px;
	}
	.datagrid-btable td div{
		height:20px !important;
		overflow:hidden;
	}
</style>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <!-- <div id="tcx" class="easyui-tabs" data-options="fit:true" > -->
    	<!-- <div title="登录日志" >
	    	<div class="easyui-layout" data-options="fit:true" >
	    		<div data-options="region:'north'" style="height:76px;">
	    		    <form id="searchFormLogin" class="has-adv-search">
						<table>
							<tr>
								<td>
									开始日期：<input class="easyui-datebox begindate" name="BEGINDATE" id="loginbegindate" editable="false"/>
									结束日期：<input class="easyui-datebox enddate" name="ENDDATE" id="loginenddate" editable="false"/>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadLoginData()">查询</a>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchFormLogin')">重置</a> 
								</td>
							</tr>
					    </table>
			        </form>
			    </div>
		        <div data-options="region:'center',border:false">
				     <table id="loginGrid"></table>
			    </div>
			</div>
		</div>		
		
		<div title="启动日志">
			<div class="easyui-layout" data-options="fit:true" >
	    		<div data-options="region:'north'" style="height:76px;">
				    <form id="searchFormStart" class="has-adv-search">
						<table>
							<tr>
								<td>
									开始日期：<input class="easyui-datebox begindate" name="BEGINDATE" id="startbegindate" editable="false"/>
									结束日期：<input class="easyui-datebox enddate" name="ENDDATE" id="startenddate" editable="false"/>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadStartData()">查询</a>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchFormStart')">重置</a> 
								</td>
							</tr>
					    </table>
			        </form>
			     </div>
		         <div data-options="region:'center',border:false">
				 <table id="startGrid"></table>
				 </div>
			</div>
		</div>
					
		<div title="店铺访问日志">
			<div class="easyui-layout" data-options="fit:true" >
	    		<div data-options="region:'north'" style="height:76px;">
				    <form id="searchFormShop" class="has-adv-search">
						<table>
							<tr>
								<td>
									开始日期：<input class="easyui-datebox begindate" name="BEGINDATE" id="shopbegindate" editable="false"/>
									结束日期：<input class="easyui-datebox enddate" name="ENDDATE" id="shopenddate" editable="false"/>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadShopData()">查询</a>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchFormShop')">重置</a> 
								</td>
							</tr>
					    </table>
			        </form>
			    </div>
		        <div data-options="region:'center',border:false">
				<table id="shopAccessGrid"></table>
				</div>
			</div>
		</div>
					
		<div title="商品访问日志">
			<div class="easyui-layout" data-options="fit:true" >
	    		<div data-options="region:'north'" style="height:76px;">
				    <form id="searchFormCommodity" class="has-adv-search">
						<table>
							<tr>
								<td>
									开始日期：<input class="easyui-datebox begindate" name="BEGINDATE" id="commoditybegindate" editable="false"/>
									结束日期：<input class="easyui-datebox enddate" name="ENDDATE" id="commodityenddate" editable="false"/>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadCommodityData()">查询</a>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchFormCommodity')">重置</a> 
								</td>
							</tr>
					    </table>
			        </form>
		        </div>
		        <div data-options="region:'center',border:false">
				<table id="commodityAccessGrid"></table>
				</div>
			</div>
		</div>  -->
		<!-- <div title="操作日志"> -->
			<div class="easyui-layout" data-options="fit:true" >
	    		<div data-options="region:'north'" style="height:76px;">
				    <form id="searchFormOperation" class="has-adv-search">
						<table>
							<tr>
								<td>
									开始日期：<input class="easyui-datebox begindate" name="BEGINDATE" id="operationbegindate" editable="false"/>
									结束日期：<input class="easyui-datebox enddate" name="ENDDATE" id="operationenddate" editable="false"/>
								</td>
								<td>类型：</td>
								<td><input type="text" name="OP_TYPE" id="OP_TYPE" class="easyui-combobox" data-options="
													valueField: 'label',
													textField: 'value',
													editable: false,
													panelHeight: 'auto',
													data: [{
														label: '1',
														value: '操作日志',
														selected: true 
													},{
														label: '0',
														value: '异常日志'
													}]"/>
								</td>
								<td>系统类型：</td>
								<td><input type="text" name="SYSTEM_FLAG" id="SYSTEM_FLAG" class="easyui-combobox" data-options="
													valueField: 'label',
													textField: 'value',
													editable: false,
													panelHeight: 'auto',
													data: [{
														label: '0',
														value: '系统后台',
														selected: true 
													},{
														label: '1',
														value: '商户后台'
													}]"/>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadOperationData()">查询</a>
								</td>
								<td width="100px">
									<a href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchFormOperation')">重置</a> 
								</td>
							</tr>
					    </table>
			        </form>
			    </div>
		        <div data-options="region:'center',border:false">
				<table id="operationAccessGrid"></table>
				</div>
			</div>
		<!-- </div>		
   	</div> --> 
</div>
<script>
/* var loginGrid;//登录日志grid
var startGrid;//启动日志grid
var shopAccessGrid;//商铺访问日志grid
var commodityAccessGrid;//商品访问日志grid */
var operationAccessGrid;//操作日志grid

$(document).ready(function(){
	/* 设置查询的默认时间 */
	$('.begindate').datebox('setValue',time(1));
	$('.enddate').datebox('setValue',time(2));
	
	loadOperationData();//操作日志访问
	
	/* $('#tcx').tabs({
		    border:false,
		    onSelect:function(title,index){
				if('0'==index){
				    loadLoginData();//登录日志
				}else if('1'==index){
				    loadStartData();//启动日志
				}else if('2'==index){
				    loadShopData();//店铺访问
				}else if('3'==index){
				    loadCommodityData();//商品访问
				}else if('4'==index){
				    loadOperationData();//操作日志访问
				}
		    }
	    }); */

});

//加载查询登录日志信息内容
function loadLoginData(){
	var form= $("#searchFormLogin");
	var data = ns.serializeObject(form);
	var startTime = $('#loginbegindate').datebox('getValue');
   	var endTime = $('#loginenddate').datebox('getValue');
   
   	if(startTime!="" && endTime !=""){
   		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    	if(start > end){
    		parent.$.messager.alert("错误",'开始时间不能大于结束时间',"error");
    		return;
    	}
   	}
	
	loginGrid = $('#loginGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/logManage/LogMgrController/getLoginLog.do',
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
				    {field :'UUID',hidden : true},
				 	{field :'LOGIN_ACCNT', title :'登录账号', width : 50, height :100, sortable : true},
				 	{field :'OPERA_NAME', title :'操作类型名称', width : 50, height :100, sortable : true},
				 	{field :'DETAIL', title :'详细信息', width : 50, height :100, sortable : true},
				 	{field :'IP', title :'操作人IP', width : 50, height :100, sortable : true},
				 	{field :'CREATED_BY', title :'操作创建人 ', width : 50, height :100, sortable : true},
				 	{field :'CREATED_TIME', title : '创建时间', width : 50, height :100, sortable : true}
				]],
				toolbar : '#buttonContent',
                onDblClickRow: function (index, row) {
					loginLogDetails(row);
		        }
            },ns.datagridOptions));
}

//加载查询启动日志信息内容
function loadStartData(){
	var form= $("#searchFormStart");
	var data = ns.serializeObject(form);
	var startTime = $('#startbegindate').datebox('getValue');
   	var endTime = $('#startenddate').datebox('getValue');
   	
   	if(startTime!="" && endTime !=""){
   		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    	if(start > end){
    		parent.$.messager.alert("错误",'开始时间不能大于结束时间',"error");
    		return;
    	}
   	}
	
	startGrid = $('#startGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/logManage/LogMgrController/getStartLog.do',
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
				 	{field :'UUID',hidden : true},
				 	{field :'CONSOLE_NAME', title :'后台应用名称', width : 50, height :100, sortable : true},
				 	{field :'OPERA_NAME', title :'操作类型名称', width : 50, height :100, sortable : true},
				 	{field :'IP', title :'操作人IP', width : 50, height :100, sortable : true},
				 	{field :'CREATED_BY', title :'操作创建人 ', width : 50, height :100, sortable : true},
				 	{field :'CREATED_TIME', title : '创建时间', width : 50, height :100, sortable : true}
				]],
		        toolbar : '#buttonContent1',
                onDblClickRow: function (index, row) {
					startLogDetails(row);
		        }
            },ns.datagridOptions));
}

//加载查询店铺访问日志信息内容
function loadShopData(){
	var form= $("#searchFormShop");
	var data = ns.serializeObject(form);
	var startTime = $('#shopbegindate').datebox('getValue');
   	var endTime = $('#shopenddate').datebox('getValue');
   	
   	if(startTime!="" && endTime !=""){
   		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    	if(start > end){
    		parent.$.messager.alert("错误",'开始时间不能大于结束时间',"error");
    		return;
    	}
   	}
	
	shopAccessGrid = $('#shopAccessGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/logManage/LogMgrController/getShopLog.do',
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
					{field :'UUID',hidden : true},
				 	{field :'AREA_CODE', title :'小区代码', width : 50,sortable : true},
				 	{field :'OWNER_CODE', title :'业主代码', width : 50,sortable : true},
				 	{field :'SELLER_NO', title :'卖家编码 ', width : 50,sortable : true},
				 	{field :'CREATED_BY', title :'操作创建人 ', width : 50, height :100, sortable : true},
				 	{field :'CREATED_TIME', title : '创建时间', width : 50, height :100, sortable : true}
				]],
		        toolbar : '#buttonContent1',
                onDblClickRow: function (index, row) {
					shopLogDetails(row);
		        }
            },ns.datagridOptions));
}

//加载查询商品访问日志信息内容
function loadCommodityData(){
	var form= $("#searchFormCommodity");
	var data = ns.serializeObject(form);
	var startTime = $('#commoditybegindate').datebox('getValue');
   	var endTime = $('#commodityenddate').datebox('getValue');
   	
   	if(startTime!="" && endTime !=""){
   		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    	if(start > end){
    		parent.$.messager.alert("错误",'开始时间不能大于结束时间',"error");
    		return;
    	}
   	}
	
	commodityAccessGrid = $('#commodityAccessGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/logManage/LogMgrController/getCommodityLog.do',
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
					{field :'UUID',hidden : true},
					{field :'AREA_CODE', title :'小区代码', width : 50,sortable : true},
				 	{field :'OWNER_CODE', title :'业主代码', width : 50,sortable : true},
				 	{field :'SKU_NO', title :'SKU编码', width : 50,sortable : true},
				 	{field :'SKU_NAME', title :'SKU名称', width : 50,sortable : true},
				 	{field :'GOODS_NO', title :'商品编码', width : 50,sortable : true},
				 	{field :'GOODS_NAME', title : '商品名称', width : 50,sortable : true},
				 	{field :'SELLER_NO', title :'卖家编码', width : 50,sortable : true},
				 	{field :'SELLER_NAME', title : '卖家名称', width : 50,sortable : true},
				 	{field :'CREATED_BY', title :'操作创建人 ', width : 45, height :100, sortable : true},
				 	{field :'CREATED_TIME', title : '创建时间', width : 55, height :100, sortable : true}
				]],
		        toolbar : '#buttonContent1',
                onDblClickRow: function (index, row) {
					commodityLogDetails(row);
		        }
            },ns.datagridOptions));
}
//加载操作日志
function loadOperationData(){
	var form= $("#searchFormOperation");
	var data = ns.serializeObject(form);
	var startTime = $('#operationbegindate').datebox('getValue');
   	var endTime = $('#operationenddate').datebox('getValue');
   	
   	if(startTime!="" && endTime !=""){
   		var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    	if(start > end){
    		parent.$.messager.alert("错误",'开始时间不能大于结束时间',"error");
    		return;
    	}
   	}
	
	operationAccessGrid = $('#operationAccessGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/systemSetting/logMgr/getLogList.do',
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
					{field :'ID',hidden : true},
					{field :'IP', title :'用户IP', width : 50,sortable : true,
					formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'FUN_DESC', title :'方法描述', width : 50,sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'OP_TYPE', title :'操作类型', width : 50,sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'FUN_NAME', title :'方法名', width : 50,sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'FUN_PARAM', title :'请求参数', width : 50,sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'DETAIL', title : '操作信息', width : 50,sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}},
				 	{field :'SYSTEM_FLAG', title :'系统标记', width : 50,sortable : true},
				 	{field :'CREATED_TIME', title : '创建时间', width : 55, height :100, sortable : true,
				 	formatter : function(value, row, index){
			 			if(undefined !=value){
							return "<span title='"+value+"'>" + value + "</span>";
						}
		       		}}
				]],
		        toolbar : '#buttonContent1',
                onDblClickRow: function (index, row) {
					operationDetails(row);
		        }
            },ns.datagridOptions));
}

// 跳转到登陆日志详情页面
function loginLogDetails(row) {
	var LOGID = row.UUID;
	var logDialog = parent.ns.modalDialog({
		title : '登陆日志信息',
		width : 600,
		height : 300,
		resizable : true,
		url : url + '/logManage/LogMgrController/loginLogInfo.do?data=' + LOGID,
	});
}

// 跳转到启动日志详情页面
function startLogDetails(row) {
	var LOGID = row.UUID;
	var logDialog = parent.ns.modalDialog({
		title : '启动日志信息',
		width : 600,
		height : 300,
		resizable : true,
		url : url + '/logManage/LogMgrController/startLogInfo.do?data=' + LOGID,
	});
}

// 跳商铺访问日志详情页面
function shopLogDetails(row) {
	var LOGID = row.UUID;
	var logDialog = parent.ns.modalDialog({
		title : '商铺访问日志信息',
		width : 600,
		height : 300,
		resizable : true,
		url : url + '/logManage/LogMgrController/shopLogInfo.do?data=' + LOGID,
	});
}

// 跳转到商品访问日志详情页面
function commodityLogDetails(row) {
	var LOGID = row.UUID;
	var logDialog = parent.ns.modalDialog({
		title : '商品访问日志信息',
		width : 600,
		height : 400,
		resizable : true,
		url : url + '/logManage/LogMgrController/commodityLogInfo.do?data=' + LOGID,
	});
}
// 跳转到操作日志详情页面
function operationDetails(row) {
	var OPERID = row.ID;
	var logDialog = parent.ns.modalDialog({
		title : '操作日志信息',
		width : 800,
		height : 600,
		resizable : true,
		url : url + '/systemSetting/logMgr/toLogInfo.do?data=' + OPERID,
	});
}

</script> 
</body>
</html>