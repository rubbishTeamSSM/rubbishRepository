var accountGrid;
	$(document).ready(function(){
		accountGrid = $('#accountGrid').datagrid($.extend({
	    url :basePath+'/homeRentalController/searchAccountInfo.do',
		remoteSort : false,			
		singleSelect : false,	
		queryParams:{},
		columns : [
			[
			    {field :'MEDI_CODE', title :'房产中介公司代码',hidden:true},   
				{field :'MEDI_NAME', title :'公司', width:250,
			      formatter : function(value, row, index){
		 			if(undefined !=value)
					{
						  return "<span title='"+value+"'>" + value + "</span>";
					}
	       		}
			    },
				{field :'LINKMAN_NAME', title :'联系人', width:150,
			       formatter : function(value, row, index){
				 	if(undefined !=value)
				   {
						 return "<span title='"+value+"'>" + value + "</span>";
					}
			     }},
				{field :'LINKMAN_PHONE', title :'联系人电话',width:162},
				{field :'USER_ACCNT', title :'账号', width:200,
				  formatter : function(value, row, index){
						if(undefined !=value)
				     {
						 return "<span title='"+value+"'>" + value + "</span>";
					}
			     }},
			    {field: 'PIC_URL',title: '附件',align:'center',width:335,
			     formatter:function(value,row,index){
			     /*return '<img  src="'+row.PIC_URL+'"/>'*/
	             return "<img style='width:150px;height:70px;' src=\""+row.PIC_URL+"\">";	
			     },
			    }]
		],
		toolbar:'#buttonContent'
		},ns.datagridOptions));
	});
	console.log(accountGrid);
	//查询账号信息
	function query(){
	    var form= $("#searchForm");
	    var data = ns.serializeObject(form);
        data={'data':JSON.stringify(data)};
        accountGrid.datagrid('load',data);	
	}
    //新增账号信息
	function addAccount(){
		var  addAccountDialog =  parent.ns.modalDialog({
	        title : '新增',
	        width : 445,
	        height :461,
	        resizable : true,
	        url : basePath+'/homeRentalController/showAddAccountInfo.do',
	        buttons : [
	           {
	       		text : '确认',
	       		iconCls : 'icon-save',
	       		handler : function() {
	       		addAccountDialog.find('iframe').get(0).contentWindow.addAccountsInfor(addAccountDialog,accountGrid,parent.$);
	          }
	      },{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
				addAccountDialog.dialog('close');
			}
			}]
		 });
	}
	//修改账号信息
	function editAccounts(){
		var checkObjs = accountGrid.datagrid('getSelections');
        if(checkObjs && 1 == checkObjs.length){
        	editAccount(checkObjs[0].MEDI_CODE,checkObjs[0].MEDI_NAME,checkObjs[0].LINKMAN_NAME,checkObjs[0].LINKMAN_PHONE,checkObjs[0].USER_ACCNT,checkObjs[0].USER_PWD,checkObjs[0].PIC_URL);
        }else{
            parent.$.messager.alert('错误', '请选择一条需要修改的数据', 'error');
        }
	}
	function editAccount(MEDI_CODE,MEDI_NAME,LINKMAN_NAME,LINKMAN_PHONE,USER_ACCNT,USER_PWD,PIC_URL){
		var editAccoutDialog =  parent.ns.modalDialog({
           title : '修改',
           width :404,
           height:457,
           resizable : true,
	       url : basePath+'/homeRentalController/showEditAccountInfo.do?MEDI_CODE='+MEDI_CODE,
           buttons : [{
               text : '确认',
               iconCls : 'icon-save',
               handler : function(){
            	editAccoutDialog.find('iframe').get(0).contentWindow.editAccounts(editAccoutDialog,accountGrid,parent.$);
               }
           },{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
				editAccoutDialog.dialog('close');
			}
			}]
       });
}
	function deleteAccounts(){
		var checkObjs = accountGrid.datagrid('getSelections');
        if(checkObjs && checkObjs.length > 0){
        	deleteAccount(checkObjs);
        }else{
            parent.$.messager.alert('错误', '请至少选择一条需要删除的数据', 'error');
        }
	}
	function deleteAccount(checkObjs){
		 parent.$.messager.confirm('删除','确认删除账号信息?',function(r){ 	
	    if (r){  	
  	     	var MeIdStr = [];
  	     	for(var i =0 ;i <checkObjs.length;i++ ){
  	     		MeIdStr.push(checkObjs[i].MEDI_CODE);
  	     	}
  	     	var data = {};
  	     	data.MeIdStr = MeIdStr.join(',');
  	     	 $.ajax({
  	                type : 'post',
  	                url : basePath+'/homeRentalController/delAccountInfo.do',
  	                data : data,
  	                dataType : 'json',
  	                success : function(result) {
  	                    if("0"==result.msg){
  	                    	parent.$.messager.alert('提示','删除账号成功','info');
  	                    	accountGrid.datagrid('reload');
  	                    }else{
  	                    	parent.$.messager.alert('错误','删除账号失败','error');
  	                    }
  	                }
  	            });
  	    } 
  	});
	}
