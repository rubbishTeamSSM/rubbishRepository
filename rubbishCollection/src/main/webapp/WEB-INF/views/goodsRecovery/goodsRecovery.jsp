<%--
  Created by IntelliJ IDEA.
  User: neusoft
  Date: 2018/8/7
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
    <title>Title</title>
</head>
<body onload="load();">
<div  class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true" id="cc">
        <div data-options="region:'north'" style="height:75px;">
            <form id="searchForm" class="has-adv-search">
                <table>
                    <tr>
                        <td>区域：</td><td><input id="REGION_CODE" name="REGION_CODE" class="easyui-combobox" style="height: 25px;"/></td>
                        <td>项目：</td><td><input id="PROJECT_CODE" name="PROJECT_CODE" class="easyui-combobox" style="height: 25px;"/></td>
                        <td>时间：</td><td><input name="start_time" id="start_time" class="easyui-datebox" style="height: 25px;"/></td>
                        <td>-</td><td><input name="end_time" id="end_time" class="easyui-datebox" style="height: 25px;"/></td>
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
            <table id="goodsGrid"></table>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        initAction('${param.menu_code}');//初始化按钮
        loadData();
        initRegionCombo();
        //initProjectCombo();
    });
    var bmGrid;
    function loadData(){
        var form= $("#searchForm");
        var data = ns.serializeObject(form);
        bmGrid = $('#goodsGrid').datagrid($.extend({
            singleSelect : false,
            url : url+'/goodsRecovery/getGoodsRecovery.do',
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
                    {field :'GOODS_NUMBER', title :'条形码', width : 50,sortable : true},
                    {field :'GOODS_CODE', title :'ID', width : 50,hidden:true},
                    {field :'GOODS_NAME', title :'商品名称', width : 75,sortable : true},
                    {field : 'GOODS_PRICE', title : '价格', width : 25,sortable : true},
                    {field : 'GOODS_BRAND', title : '品牌', width : 30,sortable : true},
                    {field :'AREA_NAME', title :'业主苑区', width : 50,sortable : true},
                    {field : 'BUILD_NAME', title : '业主楼栋', width : 50,sortable : true},
                    {field : 'ROOM_NAME', title : '业主房间', width : 50,sortable : true},
                    {field : 'RECOVER_TIME', title : '回收时间', width : 50,sortable : true}
                ]],
            toolbar : '#buttonContent'
        },ns.datagridOptions));
    }
    
    //查询
    function loadDataGrid(){
        var form= $("#searchForm");
        var data = ns.serializeObject(form);
        data={'data':JSON.stringify(data)};
        bmGrid.datagrid('load',data);
    }

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
    
    //导出
    function exportGoodsList(){
		var pageName="GoodsList.zip";
		var form= $("#searchForm");
		var data = ns.serializeObject(form);
		$.ajax({
			type : 'post',
    		url:url+'/goodsRecovery/getGoodsExportList.do',
  			data :{'data' : JSON.stringify(data)},
      		dataType : 'json',
      		success : function(data) {
     			if (data.success) {
					window.location.href = url+'/roomInfoMgr/downloadExcel.do?filepath='+ data.msg+'&pageName='+pageName;
         		} else {
             		$.messager.alert('提示', data.msg, 'error');
         		}
   			}
		});
	}	
</script>
</body>
</html>
