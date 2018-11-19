<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
	<div id="buttonContent"></div>
    <table id="roleGrid"></table>
    <script>
        var roleGrid;
        $(document).ready(function(){
        	initAction('${param.menu_code}');//初始化按钮
        	
            roleGrid = $('#roleGrid').datagrid($.extend({
                singleSelect : false,
                //idField:'ROLE_CODE',
                fitColumns : true,
                url : '${ct}/businessConsole/role/manageRole.do',
                /* frozenColumns : [
                    [
                        {field : 'UUID', title : 'UUID', hidden : true},
                        {field : 'ROLE_CODE', title : 'ROLE_CODE', checkbox : true}
                    ]
                ], */
                rowStyler:function(index,row){
		        	if (index%2==1){
		        		return 'background-color:#f3f4f6;';
		          	} 
				},
                columns : [
                    [
                        {field : 'role_code', hidden:true},
                        {field : 'role_name', title : '名称', width : 120},
                        {field : 'remark', title : '备注', width : 120}
                       
                    ]
                ],
                toolbar : "#buttonContent"
            },ns.datagridOptions));
        });


        function showAddRoleDialog(obj){
        	//alert($(obj).attr('button_code'));
            var roleDialog =  parent.ns.modalDialog({
                title : '新增角色',
                width : 410,
                height : 200,
                url : '${ct}/businessConsole/role/roleAdd.do',
                buttons : [{
                    text : '保存',
                    iconCls : 'icon-save',
                    handler : function(){
                        roleDialog.find('iframe').get(0).contentWindow.submitRole(roleDialog,roleGrid,parent.$);
                    }
                }]
            });
        }
        
        function showModifyRoleDialog(obj){
			var select = roleGrid.datagrid('getSelections');//选择的角色
			if(!select || 1 != select.length){
			    parent.$.messager.alert('错误', '请选择一个角色', 'error');
			    return;
			}
			var roleDialog =  parent.ns.modalDialog({
                 title : '编辑角色',
                 width : 410,
                 height : 200,
                 url : '${ct}/businessConsole/role/modifyRole.do?ROLE_CODE='+select[0].role_code,
                 buttons : [{
                     text : '保存',
                     iconCls : 'icon-save',
                     handler : function(){
                         roleDialog.find('iframe').get(0).contentWindow.submitRole(roleDialog,roleGrid,parent.$,select.role_code);
                     }
                 }]
            });
        }


        function showMenuGrantToRoleDialog(obj){
        	var selectRole = roleGrid.datagrid('getSelections');//选择的角色
                    	
			if(!selectRole || 1 != selectRole.length){
			    parent.$.messager.alert('错误', '请选择一个角色', 'error');
			    return;
			}
            var menuToRoleDialog =  parent.ns.modalDialog({
                title : '菜单授权--' + selectRole[0].role_name,
                width : 670,
                height : 400,
                url : '${ct}/businessConsole/role/menuGrantToRole.do?roleId='+selectRole[0].role_code,
                buttons : [{
                    text : '授权',
                    iconCls : 'icon-save',
                    handler : function(){
                        menuToRoleDialog.find('iframe').get(0).contentWindow.submitMenuToRole(menuToRoleDialog,parent.$);
                    }
                }]

            });
        }

        function showUserGrantToRoleDialog(obj){
        	var selectRole = roleGrid.datagrid('getSelections');//选择的角色
                    	
			if(!selectRole || 1 != selectRole.length){
			    parent.$.messager.alert('错误', '请选择一个角色', 'error');
			    return;
			}
            var userToRoleDialog =  parent.ns.modalDialog({
                title : '用户授权--' + selectRole[0].role_name,
                width : 860,
                height : 580,
                url : '${ct}/businessConsole/role/userGrantToRole.do?roleId='+selectRole[0].role_code,
                buttons : [{
                    text : '授权',
                    iconCls : 'icon-save',
                    handler : function(){
                        userToRoleDialog.find('iframe').get(0).contentWindow.submitUserToRole(userToRoleDialog,parent.$);
                    }
                }]

            });
        }
        
        function deleteRoles(obj){
       		var checkRoles = roleGrid.datagrid('getSelections');//选择的角色
                    	
            if(!checkRoles || 0 == checkRoles.length){
                parent.$.messager.alert('错误', '请选择一个角色', 'error');
                return;
            }
    		parent.$.messager.confirm('确认','确认删除角色?',function(r){  
        	    if (r){  
        	     	var roleDms = [];
        	     	for(var i =0 ;i <checkRoles.length;i++ ){
        	    		
        	     		roleDms.push(checkRoles[i].role_code);
        	     	}
        	     	
        	     	var data = {};
        	     	data.roleDms = roleDms.join(',');
        	     	
					$.ajax({
       	                type : 'post',
       	                url : '${ct}/businessConsole/role/deleteRoles.do',
       	                data :data,
       	                dataType : 'json',
       	                success : function(result) {
       	                    if(result.success){
       	                    	roleGrid.datagrid('clearSelections').datagrid('clearChecked');
       	                    	roleGrid.datagrid('reload');
       	                    	parent.$.messager.alert('成功','删除用户成功','info');
       	                    }else{
       	                       parent.$.messager.alert('错误','删除用户失败','error');
       	                    }
       	                },
       	                error : function() {
							parent.$.messager.alert('提示', '系统异常', 'error');
						}
					});
        	    }  
        	}); 
        }
        
    </script>
</div>
</body>
</html>