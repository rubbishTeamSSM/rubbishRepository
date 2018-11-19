var filetext = "";
var upload_url = "";
function uploadCallBack(result)
{
	filetext=result[0].WJDM;
	upload_url=result[0].URL;
	var html = '<img alt="" src="'+result[0].URL+'" id="image">';
	$("#imgDiv").html(html);
    $("#PIC_URL").val(upload_url);
}

function addAccountsInfor($addAccountDialog,$accountGrid,$pjq){
		var form= $("#addAccountForm");
        var isValid = $(form).form('validate');
        var data = ns.serializeObject(form);
        data.FILETEXT = filetext;
        var PIC_URL = $("#PIC_URL").val();//获取PIC_URL
        if("" == PIC_URL){
        parent.$.messager.alert('提示', '请选择上传文件', 'error');
        }else{
        if(isValid){
        parent.$.messager.progress({
     	  title : '新增账号',
     	  text : '数据新增中,请勿关闭窗口........'
     	}); 
           $.ajax({
             type :'post',
             url  :basePath+'/homeRentalController/insertAccountInfo.do',
             data : {'data':JSON.stringify(data)},
             async:false,
             dataType : 'json',
             success : function(result) {
            	parent.$.messager.progress('close');
             	if("0"==result.msg){
             		 parent.$.messager.alert('提示', '保存成功', 'info');
             		 $addAccountDialog.dialog('destroy');
             		 $accountGrid.datagrid('reload');
             		//关闭进度条
				     parent.window[index-1].addAccountDialog.dialog('destroy'); 
             	}else if("1"==result.msg){
            		 parent.$.messager.alert('提示', '账号已存在', 'error');
            		/* $addAccountDialog.dialog('destroy');
            		 $accountGrid.datagrid('reload');*/
            		//关闭进度条
				     parent.window[index-1].addAccountDialog.dialog('destroy'); 
            	}
             },
             error : function(){
            	//关闭进度条
			     parent.window[index-1].addAccountDialog.dialog('destroy'); 
			     parent.$.messager.alert('提示', '系统异常', 'error');
             }
          });
        }}
        
	}
