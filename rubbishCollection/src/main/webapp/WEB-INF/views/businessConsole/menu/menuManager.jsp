<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/common.jsp"%>
</head>
<style>
	.panel-body{
		overflow:auto !important;
	}
</style>
<body>	
<div class="easyui-panel" data-options="fit:true,border:false" style="background:#fff;">

	<div class="easyui-layout" data-options="fit:true,border:false">  
	    <div data-options="region:'west',
	    				   title:'菜单树',
	    				   split:true,    				   
	    				   tools: [{  
						        iconCls:'icon-add', 
						        text:'新增', 
						        handler:function(){addMenu();}  
						    },{  
						        iconCls:'icon-edit', 
						        text:'修改',  
						        handler:function(){modifyMenu();}  
						    },{  
						        iconCls:'icon-remove', 
						         text:'删除', 
						        handler:function(){deleteMenu();}  
						    },{  
						        iconCls:''
						    }] 	    				   
	    				   " 
	    	style="width:200px;">    
	    	<ul id="allmenuTree"></ul>
	    </div>
	    
	    <div data-options="region:'center',title:'菜单信息'" style="padding:5px;">
	    	<div class="easyui-layout" data-options="fit:true,border:true">
		        <div data-options="region:'north'" style="height:80px">
		        	<form id="MenuInfoForm" >
						<table>
							<tr>
								<td>名称：</td>
								<td><input  type="text" name="MENU_NAME" class="easyui-validatebox"  disabled="disabled" style="height: 27px;width:154px;"/></td>
								<td>类型：</td>
								<td>
									<input id="TYPE" name="TYPE" class="easyui-combobox" data-options="width:150,panelHeight:'auto'" disabled="disabled" style="height: 27px;width:154px;"/>
								</td>
								<td>URL：</td><td><input type="text" name="MENU_URL" class="easyui-validatebox" disabled="disabled" style="height: 27px;width:154px;"/></td>
							</tr>
							<tr>
								<td>排序：</td><td><input type="text" name="SORT_NO" class="easyui-validatebox" disabled="disabled" style="height: 27px;width:154px;"/></td>
								<td>备注：</td><td><input type="text" name="REMARK" class="easyui-validatebox" disabled="disabled" style="height: 27px;width:154px;"/></td>
							</tr>
						</table>
					</form>
		        </div>
		        <div data-options="region:'center',title:'菜单按钮',
		        				   split:true,    				   
			    				   tools: [{  
								        iconCls:'icon-add', 
								        text:'新增', 
								        handler:function(){showAddMenuBtnDialog();}  
								    },{  
								        iconCls:'icon-edit', 
								        text:'修改',  
								        handler:function(){showModMenuBtnDialog();}  
								    },{  
								        iconCls:'icon-remove', 
								         text:'删除', 
								        handler:function(){deleteMenuBtn();}  
								    },{  
								        iconCls:''
								    }]">
	    			<ul id="btnTree"></ul>
		        </div>
		    </div>
	    </div>  
	</div>  
	
	
</div>
	<script>
    var allmenuTree;
    var btnTree;
    
    $(document).ready(function(){
    	initType();//初始化类型
    	//initMenuBtn();//初始化菜单按钮
    	$(".layout-button-left").hide();
        allmenuTree =$('#allmenuTree').tree({
            url:'${ct}/businessConsole/menu/getAllMenu.do',
            onSelect:function(node){
            	menuInfo(node.id);//加载菜单详细信息
        		loadMenuBtn(node);//加载菜单的按钮信息
            }
        });
    });
    function initType(){
		var data=[{'CODE':'1','TEXT':'菜单'},{'CODE':'2','TEXT':'目录'}];
		$('#TYPE').combobox({
			data:data,
			valueField:'CODE',
			textField:'TEXT',
			panelHeight:'100'
 		});
	}
    // 加载菜单按钮树
    function loadMenuBtn(node){
    	btnTree =$('#btnTree').tree({
            url:'${ct}/businessConsole/menuBtn/getMenuBtnList.do?data='+node.id
        });
    }
    
    // 菜单详情
    function menuInfo(menuId){	
    	var $form = $('#MenuInfoForm');
    	$($form).form('clear');
    	//加上['&fresh=' + Math.random()]，解决IE下ajax缓存问题
    	$($form).form('load','${ct}/businessConsole/menu/menuDetails.do?MENU_CODE='+menuId+'&fresh=' + Math.random() );
    }
    
    // 添加菜单
    function  addMenu(){	
    	var select = allmenuTree.tree('getSelected');//获取选中的行	
		var dialog = parent.ns.modalDialog({
            title:'新增菜单',
            url:'${ct}/businessConsole/menu/menuAdd.do',
            width :380,
            height : 400,
            buttons:[{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){
                    dialog.find('iframe').get(0).contentWindow.submitMenu(dialog,allmenuTree,parent.$,select,$('#btnTree'),$('#MenuInfoForm'));
                }
            }]

	    });
    }
    // 修改菜单
    function modifyMenu(){
    	var select = allmenuTree.tree('getSelected');//获取选中的行
		if(select){
			 var dialog = parent.ns.modalDialog({
                    title:'修改菜单',
                    url:'${ct}/businessConsole/menu/menuModify.do?menuId=' + select.id,
                    width :380,
                    height : 400,
                    buttons:[{
                        text:'保存',
                        iconCls:'icon-save',
                        handler:function(){
                            dialog.find('iframe').get(0).contentWindow.submitMenu(dialog,allmenuTree,parent.$,$('#btnTree'),$('#MenuInfoForm'));
                        }
                    }]
                });
		}else{
			parent.$.messager.alert('错误','请选择操作的菜单','error');
		}
    }
    
    // 删除菜单
    function deleteMenu(){
    	var select = allmenuTree.tree('getSelected');//获取选中的行
		if(select){
			parent.$.messager.confirm('确认', '确认删除?', function(r){
				if (r){
					var child = allmenuTree.tree('getChildren',select.target);
					var str = select.id;
					if(child){
						for(var i =0;i<child.length;i++){
							str +=",";
							str += child[i].id;
						}
					}
					var parents = allmenuTree.tree('getParent',select.target);
					var data = {};
					if(parents){
						data.parents = parents.id;
					}else{
						data.parents = '0';
					}
					data.menuId = str;
					
					$.ajax({
						type : 'post',
						url : '${ct}/businessConsole/menu/deleteMenu.do',
						data :data,
						dataType : 'json',
						success : function(result) {
			    			parent.$.messager.alert('提示', '删除成功！', 'info',
	    	    				function(){
	    	    					//重新加载
	    	    					allmenuTree.tree({
										url:'${ct}/businessConsole/menu/getAllMenu.do'
									});
									$('#btnTree').tree('loadData',[]);
									$('#MenuInfoForm').form('clear');
	    	    				}
	    	    			);
						},
						error :function(){
							parent.$.messager.alert('错误', '删除失败', 'error');
						}
					});
				}
			});
		}else{
			parent.$.messager.alert('错误','请选择操作的菜单','error');
		}
    }
	
	// 初始化菜单按钮
	function initMenuBtn(node){
		
		btnGrid = $('#btnGrid').datagrid($.extend({
		    	url : '',
		    	remoteSort : false,			
				singleSelect : false,
				fitColums : true,
				columns : [
					[
				        {field : 'sort_no', title : '序号', width : 60},
					 	{field : 'button_name', title :'按钮名称', width : 100},
				        {field : 'button_url', title :'执行方法', width : 120},
				        {field : 'button_style', title : '图标样式', width : 100},
				        {field : 'remark', title : '备注', width : 190}
					]
				],
				toolbar : [{
					text : '新增',
					iconCls : 'icon-add',
					handler : function(){
						showAddMenuBtnDialog();
					}
				},{
					text : '修改',
					iconCls : 'icon-edit',
					handler : function(){	
						var rows = btnGrid.datagrid('getSelections');
						
                        if(rows && rows.length > 0){
                        	showModMenuBtnDialog(rows[0]);
                        }else{
                            parent.$.messager.alert('错误', '请选择一个按钮！', 'error');
                        }
					}
				},{
					text : '删除',
					iconCls : 'icon-remove',
					handler : function(){
						var rows = btnGrid.datagrid('getSelections');
                        if(rows && rows.length > 0){
                        	deleteMenuBtn(rows);
                        }else{
                            parent.$.messager.alert('错误', '请至少选择一个按钮！', 'error');
                        }
					}
				},{
					text : '设置子菜单',
					iconCls : 'icon-add',
					handler : function(){
						var rows = btnGrid.datagrid('getSelections');
                        if(rows && 1 == rows.length){
                        	setMenuBtn(rows);
                        }else{
                            parent.$.messager.alert('错误', '一次只能给一个按钮设置子菜单', 'error');
                        }
					}
				}]

		    },ns.datagridOptions));
	}
	
	// 添加菜单按钮
	function showAddMenuBtnDialog(){
		var select = allmenuTree.tree('getSelected');//获取选中的菜单
		if(!select){// 为选中
			parent.$.messager.alert('错误', '请选择一个菜单！', 'error');
			return;
		}
		var node = btnTree.tree('getSelected');//获取选中按钮
		//var type = node.attributes.TYPE;//菜单类型
		if(!node){// 为选中
			node = {'id':'0','attributes':{'level':'0'}};
		}
		 var addBtnDialog =  parent.ns.modalDialog({
           title : '新增按钮',
           width : 400,
           height : 300,
           resizable : true,
           url : '${ct}/businessConsole/menuBtn/showAddMenuBtn.do',
           buttons : [{
               text : '保存',
               iconCls : 'icon-save',
               handler : function(){
                   addBtnDialog.find('iframe').get(0).contentWindow.saveData(addBtnDialog,btnTree,parent.$,node.id,select.id,node.attributes.level);
               }
           }]

       });
	}
	
	// 修改菜单按钮
	function showModMenuBtnDialog(){
		 var select = allmenuTree.tree('getSelected');//获取选中的菜单
		 if(select){
		 	var node = btnTree.tree('getSelected');//获取选中按钮
			 	if(!node){// 为选中
				parent.$.messager.alert('错误', '请选择一个按钮！', 'error');
				return;
			 }
			 var modBtnDialog =  parent.ns.modalDialog({
	           title : '修改按钮',
	           width : 400,
	           height : 300,
	           resizable : true,
	           url : '${ct}/businessConsole/menuBtn/showModMenuBtn.do?BTN_CODE=' + node.id + '&&MENU_CODE=' + select.id,
	           buttons : [{
	               text : '保存',
	               iconCls : 'icon-save',
	               handler : function(){
	                   modBtnDialog.find('iframe').get(0).contentWindow.saveData(modBtnDialog,btnTree,parent.$);
	               }
	           }]
	
	       });
		 }else{
		 	parent.$.messager.alert('错误', '请先选择一个菜单!', 'error');
			return;
		 }
	}
	
	// 删除菜单按钮
	function deleteMenuBtn(){
		var select = allmenuTree.tree('getSelected');//获取选中的菜单
		if(select){
			var node = btnTree.tree('getSelected');//获取选中按钮
			if(!node){// 为选中
				parent.$.messager.alert('错误', '请选择一个按钮！', 'error');
				return;
			}
			var obj = {};
			var arr = [];
			arr.push(node.id);
			
			obj.button_code = arr.join(",");
			
			parent.$.messager.confirm('确认','是否删除选中数据？',function(r){  
	    	    if(r) {
	    	    	$.ajax({
	    	    		url : '${ct}/businessConsole/menuBtn/deleteMenuBtn.do',
	    	    		type : 'post',
	    	    		data : obj,
	    	    		dataType : 'json',
	    	    		error : function() {
	    	    			parent.$.messager.alert('错误', '系统异常！', 'error');
	    	    		},
	    	    		success : function() {
	    	    			parent.$.messager.alert('提示', '删除成功！', 'info',
	    	    				function(){
	    	    					//重新加载
	    	    					btnTree.tree({
			                        	url:'${ct}/businessConsole/menuBtn/getMenuBtnList.do?data='+select.id,
				                    });
	    	    				}
	    	    			);
	    	    		}
	    	    	});
	    	    }
			});
		}else{
			parent.$.messager.alert('错误', '请先选择一个菜单!', 'error');
			return;
		}
	}
	//设置按钮子菜单
	function setMenuBtn(rows){
		var node = allmenuTree.tree('getSelected');//获取选中菜单
		var title = "设置按钮子菜单";
	    var urls = url +"/businessConsole/menuBtn/showSetMenuBtn.do?MODULE_ID="+modId;
	    var icon = "";
	    parent.openNewTab(title, urls, icon);
		//console.info(rows);
	}
	</script>
</body>
</html>