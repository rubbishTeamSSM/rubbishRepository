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
$(document).ready(function(){ 
       	$.ajax({
            type :'post',
            url  :basePath+'/homeRentalController/searchAccountById.do',
            data : {MEDI_CODE:MEDI_CODE},
            async:false,
            dataType : 'json',
            success : function(result) {      
        		$('#MEDI_NAME').val(result.MEDI_NAME);
        		$('#LINKMAN_NAME').val(result.LINKMAN_NAME);
        		$('#LINKMAN_PHONE').val(result.LINKMAN_PHONE);
        		$('#USER_ACCNT').val(result.USER_ACCNT);
        		$('#USER_ACCNTS').val(result.USER_ACCNT);
        		$('#USER_PWD').val(result.USER_PWD);
        		$("#PIC_URL").val(result.PIC_URL);
        		$("#UUID").val(result.UUID);
        		$("#IS_MEDI_CENTER").val(result.IS_MEDI_CENTER);
        		var html = '<img alt="" src="'+result.PIC_URL+'" id="image">';
        		$("#imgDiv").html(html);
            },
            error : function(){
            	$.messager.alert('提示', '系统异常', 'error');
            }
         });
    });
       
	function editAccounts($editAccoutDialog,$accountGrid,$pjq){
		var form= $("#editAccountForm");
        var isValid = $(form).form('validate');   
    	var data = ns.serializeObject(form);
    	var PIC_URL = $("#PIC_URL").val();//获取PIC_URL
    	var USER_ACCNT = $('#USER_ACCNT').val();
    	var USER_ACCNTS = $('#USER_ACCNTS').val();
    	data.FILETEXT = filetext;
    	 if(USER_ACCNT==USER_ACCNTS)
    	{
    		 $("#FLAG").val(1);
    	}else{
    		$("#FLAG").val(0);
    	}
    	 var FLAG =$('#FLAG').val();
         if("" == PIC_URL){
        	 parent.$.messager.alert('提示', '请选择上传文件', 'error');
         }else{
        if(isValid){
        parent.$.messager.progress({
           title : '修改账号',
           text : '数据修改中,请勿关闭窗口........'
           }); 
			$.ajax({
             type :'post',
             url  :basePath+'/homeRentalController/updateAccountInfo.do',
             data : {'data':JSON.stringify(data),MEDI_CODE:MEDI_CODE,FLAG:FLAG},
             async:false,
             dataType : 'json',
             success : function(result) {
            	parent.$.messager.progress('close');	
             	if("0"==result.msg){
             	     $pjq.messager.alert('提示','修改成功','info');
             		 $editAccoutDialog.dialog('destroy');
                     $accountGrid.datagrid('reload');
                   //关闭进度条
				     parent.window[index-1].addAccountDialog.dialog('destroy'); 
             	}else if("1"==result.msg){
             		$pjq.messager.alert('提示', '账号已存在', 'error');
             		/* $editAccoutDialog.dialog('destroy');
                     $accountGrid.datagrid('reload');*/
                   //关闭进度条
				     parent.window[index-1].addAccountDialog.dialog('destroy'); 
             	}
             },
             error : function(){
             	$pjq.messager.alert('提示', '系统异常', 'error');
             	//关闭进度条
			     parent.window[index-1].addAccountDialog.dialog('destroy'); 
             }
        	});
        }}
	}