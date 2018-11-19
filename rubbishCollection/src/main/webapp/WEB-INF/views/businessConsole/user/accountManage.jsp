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
					<td>用户账号：</td><td><input name="USER_ACCNT" type="text" style="height: 25px;"/></td>
					<td>用户名称：</td><td><input name="USER_NAME" type="text" style="height: 25px;"/></td>
					<td>用户昵称：</td><td><input name="USER_NICK_NAME" id="USER_NICK_NAME" type="text" style="height: 25px;"/></td>
					<td>
						<a  href="javascript:void(0)" class="easyui-linkbutton search" data-options="iconCls:''" onclick="loadDataGrid()">查询</a>
						<a  href="javascript:void(0)" class="easyui-linkbutton reload" data-options="iconCls:''" onclick="resetForm('searchForm')">重置</a> 
					</td>
				</tr>
			</table>
			</form>
		</div>  
    	<div data-options="region:'center',border:false" style="overflow:auto;">
    		<div id="buttonContent" style="height: 35px">
    		    <!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="showAddUserDialog()">用户新增</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="showModifyDialog()">修改信息</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="drop()">删除用户</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="changePassword()">修改密码</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-menu'" onclick="resetPassword()">重置密码</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="userAssignRole()">指派角色</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="showUserAssignStationDialog()">设置岗位</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="setUserDepartment()">设置部门</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="setUserDefaultRole()">设置默认角色</a>
    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="setUserAdditionMenu()">设置额外菜单</a> -->
    		</div>
    		<table id="userGrid"></table>
    	</div> 
	</div>
</div>
<script>
$(document).ready(function(){
    initAction('${param.menu_code}');//初始化按钮 
    loadData();
});
/* var isChangeAction = true;//是否有修改权限,默认没有 */
var bmGrid;
function loadData(){
	var form= $("#searchForm");
	var data = ns.serializeObject(form);
	bmGrid = $('#userGrid').datagrid($.extend({
                singleSelect : false,
                url : url+'/businessConsole/user/getAccount.do',
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
				 	{field :'USER_CODE', title :'用户代码', width : 50,hidden:true},
				 	{field :'USER_ACCNT', title :'用户账号', width : 50,sortable : true,
			 			 formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}	
			 	
				 	},
				 	{field :'USER_NICK_NAME', title :'用户昵称', width : 50,sortable : true,
				 		 formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}
				 	},
				 	{field : 'USER_NAME', title : '用户名称', width : 50,sortable : true,
					 	 formatter : function(value, row, index){
				 				if(undefined !=value)
								  	{
								    	return "<span title='"+value+"'>" + value + "</span>";
								  	}
			       			}
				 	},
				 	{field : 'ID_CARD', title : '身份证号码', width : 50,sortable : true,
				 	   formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}
				 	},
				 	{field :'SEX', title :'性别', width : 30,
			       		formatter : function(value, row, index){
			       			if(value =='1'){
			       				return '男';
			       			}else{
			       				return '女';
			       			}
			       		},sortable : true	
			        },
				 	{field : 'TEL', title : '固定电话', width : 50,sortable : true},
				 	{field : 'PHONE', title : '手机号码', width : 50,sortable : true},
				 	{field : 'EMAIL', title : '电子邮件', width : 50,sortable : true,
				 	    formatter : function(value, row, index){
			 				if(undefined !=value)
							  	{
							    	return "<span title='"+value+"'>" + value + "</span>";
							  	}
		       			}
				 	},
				 /* 	{field : 'DEPT_NAME', title : '部门名称', width : 30,sortable : true}, */
				 	{field :'SPECIAL_FLAG', title :'是否内部人员', width : 50,
			       		formatter : function(value, row, index){
			       			if(value =='0'){
			       				return '是';
			       			}else if(value =='1'){
			       				return '否';
			       			}
			       		},sortable : true	
			        },
			        {field : 'IP', title : 'IP地址', width : 70,sortable : true}/* ,
				 	{field : 'CREATED_TIME', title : '创建时间', width : 70,sortable : true},
				 	{field : 'CREATED_BY', title : '创建者', width : 30,sortable : true} */
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
    var userGrid;
	//修改密码
	function changePassword(){
		var account= $('#userGrid').datagrid('getSelections');
		if(1 == account.length){
			var userDialog =  parent.ns.modalDialog({
	           title : '修改密码',
	           width : 450,
	           height : 310,
	           resizable : true,
	           url : url+'/businessConsole/user/showChangePassword.do?USER_CODE='+account[0].USER_CODE,
	           buttons : [{
	               text : '保存',
	               iconCls : 'icon-save',
	               handler : function(){
		                 userDialog.find('iframe').get(0).contentWindow.save(userDialog,bmGrid,parent.$,account[0].USER_CODE);
	               }
	           }]
	       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//重置密码
	function resetPassword(){
		parent.$.messager.confirm('确认','确认重置密码?',function(r){
			if(r){
				var account= $('#userGrid').datagrid('getSelections');//获取选中行
				if(1 == account.length){
					var data = {'flag':'1','USER_CODE':account[0].USER_CODE};
					$.ajax({
						type : 'post',
						url : url+'/businessConsole/user/setPassword.do',
						data :{'data':JSON.stringify(data)},
						dataType : 'json',
						success : function(result) {
							if(result.success){
								parent.$.messager.alert("提示", "重置成功", "info", function () {
				            		$('#userGrid').datagrid('reload');//刷新datagrid
				            	});
							}else{
								parent.$.messager.alert('提示','系统异常','error');
								$(".no-message").hide();
							}
						}
					});
				}else{
					parent.$.messager.alert('提示','请选择一条数据','error');
				}
			}
		});
	}
	//删除
	function drop(){
		var users= $('#userGrid').datagrid('getSelections');//获取选中行
		if(null != users && 0 != users.length){
			parent.$.messager.confirm('确认','确认删除用户?',function(r){
				if(r){
					var userList = [];//存放所有需要删除的用户id
					for(var i = 0;i<users.length;i++){
						userList.push(users[i].USER_CODE);
					}
					var uuidList = [];//存放所有需要删除的用户uuid
					for(var i = 0;i<users.length;i++){
						uuidList.push(users[i].UUID);
					}
					var data={};
					data.USER_CODES = userList.join(',');
					data.UUIDS=uuidList.join(',');
					$.ajax({
						type : 'post',
						url : url+'/businessConsole/user/deleteAccount.do',
						data :data,
						dataType : 'json',
						success : function(result) {
							if(result.success){
								parent.$.messager.alert("提示", "删除成功", "info", function () {
				            		$('#userGrid').datagrid('reload');//刷新datagrid
				            	});
							}else{
								parent.$.messager.alert('提示','系统异常','error');
								$(".no-message").hide();
							}
						}
					});
				}
			});
			
		}else{
			parent.$.messager.alert('错误','请选择需要删除的用户','error');
		}
	}
	
/* 	//修改用户信息
	function showModDialog(row){
		if(isChangeAction){
			var users= $('#userGrid').datagrid('getSelections');//获取选中行
			if(null != users && 0 != users.length){
				if(1 == users.length){
					var title="修改账号";
				    var basepath=url+"/businessConsole/user/toAccountMgrModify.do?USER_CODE="+users[0].USER_CODE+"&&modId="+MODULE_ID;
				    var icon="";
		    		parent.openNewTab(title,basepath,icon);
				}else{
					parent.$.messager.alert('错误','只能选择一条数据','error');
				}
			}else if(null != row){
				var title="修改用户";
			    var basepath=url+"/businessConsole/use/toAccountMgrModify.do?USER_CODE="+row.USER_CODE+"&&modId="+MODULE_ID;
			    var icon="";
	    		parent.openNewTab(title,basepath,icon);
			}else{
				parent.$.messager.alert('错误','请选择用户','error');
			}
		}else{
			parent.$.messager.alert('提示','您没有修改权限','error');
			$(".no-message").hide();
		}
	} */
	//用户新增
	function showAddUserDialog(){
	   var userDialog =  parent.ns.modalDialog({
           title : '新增用户',
           width : 965,
           height : 530,
           resizable : true,
           url : '${ct}/businessConsole/user/userAdd.do',
           buttons : [{
               text : '保存',
               iconCls : 'icon-save',
               handler : function(){
                   userDialog.find('iframe').get(0).contentWindow.submitUser(userDialog,bmGrid,parent.$);
               }
           }]

       });
	}
	//修改用户信息
	function  showModifyDialog(){
	    var userinfo= $('#userGrid').datagrid('getSelections');
		if(1 == userinfo.length){
			var userDialog =  parent.ns.modalDialog({
	           title : '修改用户信息',
	           width : 960,
	           height : 500,
	           resizable : true,
	           url : url+'/businessConsole/user/toAccountMgrModify.do?USER_CODE='+userinfo[0].USER_CODE,
	           buttons : [{
	               text : '保存',
	               iconCls : 'icon-save',
	               handler : function(){
	                   userDialog.find('iframe').get(0).contentWindow.save(userDialog,bmGrid,parent.$);
	               }
	           }]
	       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//指派角色
	function userAssignRole(){
	        var checkUser= $('#userGrid').datagrid('getSelections');
		    if(1 == checkUser.length){
			var userDialog =  parent.ns.modalDialog({
	           title : '指派角色',
	           width : 430,
	           height : 400,
	           resizable : true,
	           url : url+'/businessConsole/user/showUserAssignRole.do?USER_CODE='+checkUser[0].USER_CODE,
	           buttons : [{
	               text : '保存',
	               iconCls : 'icon-save',
	               handler : function(){
	                   userDialog.find('iframe').get(0).contentWindow.submitUser(userDialog,parent.$,checkUser[0].USER_CODE);
	               }
	           }]
	       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}

    //设置默认角色
    function setUserDefaultRole(){
	        var checkUser= $('#userGrid').datagrid('getSelections');
		    if(1 == checkUser.length){
				var UserDefaultRoleDialog =  parent.ns.modalDialog({
		           title : '默认角色--' + checkUser[0].USER_NAME,
		           width : 340,
                   height : 300,
		           url : url+'/businessConsole/user/UserDefaultRole.do?user_code='+checkUser[0].USER_CODE,
                   buttons : [{
                     text : '分配',
                     iconCls : 'icon-save',
                     handler : function(){
                	 UserDefaultRoleDialog.find('iframe').get(0).contentWindow.submitUserDefaultRole(UserDefaultRoleDialog,parent.$);
                 }
             }]

		       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//设置额外菜单
    function setUserAdditionMenu(){
	        var checkUser= $('#userGrid').datagrid('getSelections');
		    if(1 == checkUser.length){
				var UserAdditionMenuDialog =  parent.ns.modalDialog({
		           title : '额外菜单--' + checkUser[0].USER_NAME,
		           width : 340,
		           height : 500,
		           fitColumns : true,
		           url : url+'/businessConsole/user/UserAdditionMenu.do?user_code='+checkUser[0].USER_CODE,
                   buttons : [{
                     text : '分配',
                     iconCls : 'icon-save',
                     handler : function(){
                	  UserAdditionMenuDialog.find('iframe').get(0).contentWindow.submitUserAdditionMenu(UserAdditionMenuDialog,parent.$);
                 }
             }]

		       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//设置岗位
    function showUserAssignStationDialog(){
	        var checkUser= $('#userGrid').datagrid('getSelections');
		    if(1 == checkUser.length){
		         $.ajax({
    	                type : 'post',
    	                url : '${ct}/businessConsole/user/getAreaDepart.do?USER_CODE='+checkUser[0].USER_CODE,
    	                dataType : 'json',
    	                success : function(result) {
    	                    if("0"!=result){
    	                        var userDialog =  parent.ns.modalDialog({
					                 title : '分配岗位',
					                 width : 805,
					                 height : 630,
					                 resizable : true,
					                 url : '${ct}/businessConsole/user/showAssginStation.do?user_code='+checkUser[0].USER_CODE,
					                 buttons : [{
						                text : '保存',
						                iconCls : 'icon-save',
						                handler : function(){
					                         userDialog.find('iframe').get(0).contentWindow.submitStation(userDialog,parent.$);
					                }
					            }]
					        });                   			
    	                    }else{
    	                       parent.$.messager.alert('提示','当前用户未配置部门，请先配置部门！','info');
    	                    }
    	                }
    	            }); 	
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//设置部门
	function setUserDepartment(){
	        var checkUser= $('#userGrid').datagrid('getSelections');
		    if(1 == checkUser.length){
				var UserDepartmentDialog =  parent.ns.modalDialog({
		           title : '部门设置--' + checkUser[0].USER_NAME,
		           width : 400,
		           height : 500,
		           fitColumns : true,
		           url : url+'/businessConsole/user/UserAssignDepartment.do?user_code='+checkUser[0].USER_CODE,
                   buttons : [{
                     text : '保存',
                     iconCls : 'icon-save',
                     handler : function(){
                	   UserDepartmentDialog.find('iframe').get(0).contentWindow.submitUserDepartment(UserDepartmentDialog,parent.$);
                 }
             }]

		       });
		}else{
			parent.$.messager.alert('提示','请选择一条数据','error');
		}
	}
	//设置项目
	function userAssignProject(){
        var checkUser= $('#userGrid').datagrid('getSelections');
	    if(1 == checkUser.length){
			var userDialog =  parent.ns.modalDialog({
	           title : '设置项目',
	           width : 430,
	           height : 400,
	           resizable : true,
	           url : url+'/businessConsole/user/showUserAssignProject.do?USER_CODE='+checkUser[0].USER_CODE,
	           buttons : [{
	               text : '保存',
	               iconCls : 'icon-save',
	               handler : function(){
	                   userDialog.find('iframe').get(0).contentWindow.submitUserProject(userDialog,parent.$,checkUser[0].USER_CODE);
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