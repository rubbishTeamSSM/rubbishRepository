<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/businessConsole/common/common.jsp"%>
</head>
<body>
	 <div  class="easyui-panel" data-options="fit:true,border:false" style="margin-top:20px">
		    				<form id="informationForm" style="margin: 10px 30px">
								<table class="table-ch-add" style="margin: 5px 10px">
									<tr>
										<td><label class="label-ch-w100">用户昵称：</label><input type="text" name="USER_NICK_NAME" id="USER_NICK_NAME" class="easyui-validatebox inp_w120" data-options="validType:['length[1,40]','unnormal']" style="height:24px;"/></td>
										<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>用户名称：</label><input type="text" name="USER_NAME" id="USER_NAME" class="easyui-validatebox inp_w120" data-options="required:true,validType:['length[1,40]','unnormal']" type="text" style="height:24px;" style="height:24px;"/></td>
										<%--<td rowspan="4">头像预览<div id="imgDiv" style="border: #DDDDDD 1px solid ; height: 130px;margin-right:5px;width: 100px;display: flex;justify-content: center;align-items: center; "></div></td>
									--%>
									</tr>
									<tr>
										<!-- <td><label class="label-ch-w100">用户代码：</label><input name="USER_CODE" id="USER_CODE" class="easyui-validatebox inp_w120" data-options="required:true,validType:['strlen[1,40]','unnormal']" type="text" style="height:24px;" readonly/></td> -->
										<td><label class="label-ch-w100">手机号码：</label><input type="text" name="PHONE" id="PHONE" class="easyui-validatebox inp_w120" data-options="required:false,validType:['mobile']" style="height:24px;"/></td>
										<td><label class="label-ch-w100">性别：</label><input name="SEX" id="SEX" class="easyui-combobox inp_w120" data-options="required:false,validType:['strlen[1,4]','unnormal']" type="text" style="height:24px;"/></td>
										
									</tr>
									<tr>
										
										<td><label class="label-ch-w100">IP地址：</label><input name="IP" id="IP" class="easyui-validatebox inp_w120" data-options="required:false,validType:['ipNo']" type="text" style="height:24px;"/></td>
										<td><label class="label-ch-w100"><font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font>管理员：</label><input name="ADMIN_FLAG" id="ADMIN_FLAG" class="easyui-combobox inp_w120" data-options="required:true,validType:['strlen[1,4]','unnormal']" type="text" style="height:24px;"/></td>
									</tr>
									<tr>
										
										<td><label class="label-ch-w100">身份证号：</label><input name="ID_CARD" id="ID_CARD" class="easyui-validatebox inp_w120" data-options="required:false,validType:['idCardNo']" type="text" style="height:24px;"/></td>
										<td><label class="label-ch-w100">固定电话：</label><input name="TEL" id="TEL" class="easyui-validatebox inp_w120" data-options="required:false,validType:['telephone']" type="text" style="height:24px;"/></td>
									</tr>
									<tr>								
										<td><label class="label-ch-w100">电子邮箱：</label><input type="text" name="EMAIL" id="EMAIL" class="easyui-validatebox inp_w120" data-options="required:false,validType:['length[1,40]','email']" style="height:24px;"/></td>
										<%--<td>
									       <div class="upfile" style="margin-left: -45px;">
											    <label class="label-ch-w140">上传头像：</label>
												<!-- <a id="upButton" onclick="getFile()" style="float: left;" class="easyui-linkbutton">点击上传</a>
												<input type="file" style="width: 260px !important;display:none" name="allFile" id="fileField" onchange="uploadFiles('fileField','httpCs','')"> -->
												 <div class="checkps">点击上传
						      						<input type="file" class="fileUploader" name="allFile" id="fileField" onchange="uploadFiles('fileField','wyApp','')">
				        						</div>
										   </div>
	          						 	</td>
	          						 	
									--%>
									</tr>
									<tr>
										<td colspan="4"><label class="label-ch-w100">备注：</label><textarea name="REMARK" id="REMARK" class="easyui-validatebox text-w360" data-options="required:false,validType:['length[0,1024]']" style="width:718px;height:100px;"/></textarea></td>
									</tr>
								</table>
							</form>
		</div>
<script>

var USER_CODE;//用户ID
var roleGrid;//角色Grid
$(function(){
	USER_CODE = '${USER_CODE}';
	initSex();//初始化性别
	//initDept();//初始化部门
	initAdminFlag();//初始化管理员标记
	initAccountMessage();//初始化用户信息
});
//初始化性别
function initSex(){
	$("#SEX").combobox({
        data:[{'ID':'0','VALUE':'女'},{'ID':'1','VALUE':'男'}],
        valueField:'ID',
        textField:'VALUE'
    });
}
//初始化管理员标记
function initAdminFlag(){
	$("#ADMIN_FLAG").combobox({
        data:[{'ID':'0','VALUE':'否'},{'ID':'1','VALUE':'是'}],
        valueField:'ID',
        textField:'VALUE'
    });
}
//初始化部门
function initDept(){
	var dept = {'code':'DEPT_CODE','name':'DEPT_NAME','table_name':'T_SYS_DEPT'};
	initCombo(dept,'DEPT_CODE',1,true);//调用公用combobox方法
}


//
var uuid;
//初始化用户信息
function initAccountMessage(){
	var data = {'USER_CODE':USER_CODE};
	$.ajax({
			type : 'post',
        	url : url+'/businessConsole/user/getAccountMessage.do',
        	dataType : 'json',
        	data : {'data':JSON.stringify(data)},
        	success : function(result) {
        	    upLoadUrl=result.accountMessage.PIC_URL;
        	    uuid=result.accountMessage.UUID;
        	    if(upLoadUrl)
        	    {
        	      var html = '<img alt="" src="'+upLoadUrl+'" id="image" style=" height: 100%;width: 100%;" onclick="download($(this))">';
		          $("#imgDiv").html(html);
		          $('#fileField').val("");
        	    }
		        
        		$('#informationForm').form('load',result.accountMessage);//回填form表单
			}
		});
}



//保存
function save($modifyDialog,$bmGrid,$pjq){
	parent.$.messager.confirm('确认','确认修改？',function(r){
		if(r){
			var form= $("#informationForm");
			var data = ns.serializeObject(form);//获取用户基本信息
			data.PIC_URL=upLoadUrl;
			data.FILETEXT = filetext;
			data.USER_CODE=USER_CODE;
			data.UUID=uuid;
			var isValid = $(form).form('validate');
			if(isValid){
				$.ajax({
			          type : 'post',
			          url : url+'/businessConsole/user/updateAccountManage.do',
			          dataType : 'json',
			          async : false,
			          data : {'data':JSON.stringify(data)},
			          success : function(data) {
						if(data.success){ 
							$pjq.messager.alert("提示", "修改成功", "info", function () {
			                $modifyDialog.dialog('destroy');
                            $bmGrid.datagrid('reload');
			            	});
						}
			          },
			          error: function(){
			          	  $pjq.messager.alert('错误', '系统异常！', 'error');
			          }
			    });
			}
		}
	});
	
}


    var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
	function IdCardValidate(idCard) { 
	    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
	    if (idCard.length == 15) {   
	        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
	    } else if (idCard.length == 18) {   
	        var a_idCard = idCard.split("");                // 得到身份证数组   
	        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
	            return true;   
	        }else {   
	            return false;   
	        }   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	 * 判断身份证号码为18位时最后的验证位是否正确  
	 * @param a_idCard 身份证号码数组  
	 * @return  
	 */  
	function isTrueValidateCodeBy18IdCard(a_idCard) {   
	    var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += Wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == ValideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	  * 验证18位数身份证号码中的生日是否是有效生日  
	  * @param idCard 18位书身份证字符串  
	  * @return  
	  */  
	function isValidityBrithBy18IdCard(idCard18){   
	    var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }   
	}   
	  /**  
	   * 验证15位数身份证号码中的生日是否是有效生日  
	   * @param idCard15 15位书身份证字符串  
	   * @return  
	   */  
	  function isValidityBrithBy15IdCard(idCard15){   
	      var year =  idCard15.substring(6,8);   
	      var month = idCard15.substring(8,10);   
	      var day = idCard15.substring(10,12);   
	      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	      if(temp_date.getYear()!=parseFloat(year)   
	              ||temp_date.getMonth()!=parseFloat(month)-1   
	              ||temp_date.getDate()!=parseFloat(day)){   
	                return false;   
	        }else{   
	            return true;   
	        }   
	  }   
	//去掉字符串头尾空格   
	function trim(str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
	    mobile:{
	        validator:function(value){
	        	var reg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	        	return value.length == 11 && reg.test(value);
	        },
	        message:"请正确填写的手机号码."
	   },telephone:{
		    validator:function(value){
			        //电话号码格式010-12345678或者0510-86997732
			         var reg = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;;
			        return reg.test(value);
		        },
		        message:"请正确填写的电话号码."
		},idCardNo:{
		    validator:function(value){
		        	return IdCardValidate(value);
		        },
		        message:"请正确输入的身份证号码."
		},ipNo:{
		    validator:function(value){
		          //6到20位数字字母组合
		             var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                     return reg.test(value);
		        },
		        message:"请输入正确的ip"
		}
	    
	});
	 //上传附件
	var filetext = '';
	var upLoadUrl = '';
	var imgIndex = 0;
	function uploadCallBack(result)
	{
		filetext=result[0].WJDM;
		upLoadUrl=result[0].URL;
		var html = '<img alt="" src="'+upLoadUrl+'" id="image" style=" height: 100%;width: 100%;">';
		$("#imgDiv").html(html);
	    $('#fileField').val("");
	}
	
	function getFile(){
	    $('#fileField').click();
    }
    
   /*  function download(obj){
         var wjlj = obj.attr("src");
         console.info(wjlj);
         window.location.href=url+'/download.tool?wjlj='+wjlj;
    } */
</script> 
</body>
</html>