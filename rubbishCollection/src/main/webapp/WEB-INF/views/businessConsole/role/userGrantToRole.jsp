<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',collapsed:false" style="height:76px;overflow: auto;">
			<form id="searchForm" style="margin-top: 10px;">
			<table>
				<tr>
					<td>用户名</td><td><input type="text" name="USER_NAME"/></td>
					<td>性别</td>
					<td>
						<select name="SEX">
							<option value="">全部</option>
							<option value="0">女</option>
							<option value="1">男</option>
						</select>
					</td>
					<td>
						<a  href="#" class="easyui-linkbutton search" data-options="iconCls:''" onclick="searchUser()">查询</a>
						<a  href="#" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="removeAll()">重置</a>
					</td>
					
				</tr>
				
			</table>
		</form>
			
		</div>  
    	<div data-options="region:'center'">  
        	<table id="userGrid"></table>
    	</div> 
	</div>
	
	
</div>
	<script>
    var userGrid;
	$(document).ready(function(){
		var form= $("#searchForm");
		var data = ns.serializeObject(form); 
        userGrid = $('#userGrid').datagrid($.extend({
		    	url:'${ct}/businessConsole/user/getAccount.do',
		    	queryParams : {'data' : JSON.stringify(data)},
		    	idField:'USER_CODE',
                fitColumns : true,
				columns:[
					[
		    		 	{field: 'USER_CODE', title: 'USER_CODE',hidden:true},
					 	{field :'USER_NAME', title :'用户名', width : 160},
						{field:'ADMIN_FLAG',title:'管理员',width:120,
								formatter:function(value,row,index){
									if(value =='1'){
										return '管理员';
									}else{
										return '非管理员';
									}
								}	
						},
						{field:'SEX',title:'性别',width:120,
								formatter:function(value,row,index){
									if(value =='1'){
										return '男';
									}else{
										return '女';
									}
								}	
						},
						{field:'PHONE',title:'手机号码',width:120}
					]
				],
                onLoadSuccess:function(data){

                    userGrid.datagrid("clearSelections");
                    var userid = userGrid.datagrid("getRows");//当前页用户
                    var ids = [];
                    for (var i = 0; i < userid.length; i++) {
                        ids.push(userid[i].USER_CODE);
                    }

                    $.post('${ct}/businessConsole/role/getRoleAssginUserCurrentPage.do', {
                        roleId : '${param.roleId}',
                        ids : ids.join(',')
                    }, function(result) {
                        if(result.success){
                            if(result.msg){
                                for(var i =0; i < result.msg.length;i++){
                                     userGrid.datagrid('selectRecord',result.msg[i]);
                                }
                            }
                        }
                    }, 'json');


                }

		    },ns.datagridOptions));
	});
	
	function submitUserToRole($dialog,$pjq){
        var userid = userGrid.datagrid("getRows");//当前页用户

        var selectUserid = userGrid.datagrid("getSelections");//选中的用户

        var currUserIds = [];//当前页的用户id
        var newUserIds =[];//选中的用户id
        for (var i = 0; i < userid.length; i++) {
            currUserIds.push(userid[i].USER_CODE);
        }

        for (var i = 0; i < selectUserid.length; i++) {
            newUserIds.push(selectUserid[i].USER_CODE);
        }

        if(newUserIds.length == 0){
            $pjq.messager.alert('提示', '请选择用户！', 'info');
            return;
        }
        $.post('${ct}/businessConsole/role/changeRoleUser.do', {
            roleId: '${param.roleId}',
            currUserIds: currUserIds.join(','),
            newUserIds: newUserIds.join(',')
        }, function (result) {
            if (result.success) {
                $dialog.dialog('destroy');
            } else {
                $pjq.messager.alert('提示', result.msg, 'error');
            }
            $pjq.messager.alert('提示', '授权成功！', 'info');
        }, 'json');
    }
	
	//搜索用户
	function searchUser(){
		var form= $("#searchForm");
		var data = ns.serializeObject(form); 
		userGrid.datagrid('load',{'data' : JSON.stringify(data)});
	}
	//重置
	function removeAll(){
		$('#searchForm').form('clear'); 
	}
	</script>
	</body>
	</html>