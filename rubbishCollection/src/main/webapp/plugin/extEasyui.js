/**
 * 扩展easyui
 */

/**
 * 名称空间
 */
var ns = ns || {};

/**
 * 模态窗口
 */
ns.modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 640,
		height : 480,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);

	opts.modal = true;

	if (options.url) {
		opts.content = '<iframe id="" src="'
				+ options.url
				+ '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';

	}

	return $('<div/>').dialog(opts);
};


$.extend($.fn.validatebox.defaults.rules, {
	eqTo : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '不相同'
	},
	validataChar:{
		    validator:function(value){
			        var reg = /^[A-Za-z0-9_]+$/;
			        return reg.test(value);
		        },
		        message:"只能输入数字、字母和下划线."
	},
	mobile:{
        validator:function(value){
        	var reg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        	return value.length == 11 && reg.test(value);
        },
        message:"请正确填写的手机号码."
	},
	email:{
	    validator:function(value){
	    	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	        return reg.test(value);
	    },
	    message:"请正确填写邮箱."
   },
   IDcard:{
	   validator:function(value){
	    	var reg = /^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/;
	        return reg.test(value);
	    },
	    message:"请正确填写身份证."
   },
   postalcode:{ //邮政编码
	   validator:function(value){
	    	var reg = /^[1-9]\d{5}$/;
	        return reg.test(value);
	    },
	    message:"请正确填写邮政编码."
   },
   phone:{ //手机固定电话
	   validator:function(value){
		   var reg=/^((\d{7,8})|(\d{4}|\d{3})-?(\d{7,8})|(\d{4}|\d{3})-?(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$|(^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[8|9])\d{8}$)/;
	        return reg.test(value);
	    },
	    message:"请填写正确的电话号码."
   },english: {// 验证英语
       validator: function (value) {
           return /^[A-Za-z]+$/i.test(value);
       },
       message: "请填写英文"
   },faxno: {// 验证传真
       validator: function (value) {
    	   return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?(\-\d{1,4})?$/i.test(value);
       },
       message: "请填写正确的传真号码"
   },tel: {// 固定电话校验
       validator: function (value) {
           return /^((\d{3,4}\-)|)\d{7,8}(|([-\u8f6c]{1}\d{1,5}))$/i.test(value);
       },
       message: "请填写正确的电话号码"
   },www: {// 网址校验
       validator: function (value) {
           return /^((https?|ftp|news):\/\/)?([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/i.test(value);
       },
       message: "请填写正确的网址"
   },number:{
	    validator:function(value){
	        var reg = /^\d+$/;
	        return reg.test(value);
        },
        message:"只能输入数字"
   },numEng:{
	    validator:function(value){
	        var reg = /^[0-9a-zA-Z]+$/;
	        return reg.test(value);
       },
       message:"只能输入数字或英文"
  },
	strlen : {  
        validator : function(value, param) {  
            this.message = '长度{0}~{1}之间';  
            var len = stringLength($.trim(value));  
           
            if (param) {  
                for (var i = 0; i < param.length; i++) {  
                    this.message = this.message.replace(new RegExp(  
                                    "\\{" + i + "\\}", "g"), param[i]);  
                }  
            }  
            return len >= param[0] && len <= param[1];  
        },  
        message : '长度 {0}~{1}之间'  
    },
    englishName:{
	    validator:function(value){
		        var reg = /^[\s\.\,0-9a-zA-Z_-]+$/;
		        return reg.test(value);
	        },
	        message:"只能输入数字、字母、下划线、点、逗号和空格."
    },
    cash:{
    	validator:function(value){
    		if(value.indexOf(",")>=0){
    			var reg = /^\S{1,13}$/;
    	        return reg.test(value);
    		}else{
    			var reg = /^\S{1,10}$/;
    	        return reg.test(value);
    		}
        },
        message:"输入金额长度必须为1-10位"
    },
    numberCheck:{
	    validator:function(value){
		        var reg = /^\+?[1-9]\d*$/;
		        return reg.test(value);
	        },
	        message:"只能输入正整数."
    },
    numCheck:{
	    validator:function(value){
		        var reg =  /^([1-9]\d*|[0]{1,1})$/;
		        return reg.test(value);
	        },
	        message:"只能输入正整数或零."
    },
    gpsCheckChina:{
	    validator:function(value){
		        var reg = /^.*[\u4e00-\u9fa5]+.*$/;
		        return !reg.test(value);
	        },
	        message:"请输入正确的GPS格式."
    },
    gpsCheck:{
	    validator:function(value){
		        var reg = /^.*[!@#$%^&*()]+.*$/;
		        return !reg.test(value);
	        },
	        message:"请输入正确的GPS格式."
    },
    moneyCheck:{
	    validator:function(value){
		        var reg = /^(([1-9]\d*)|((([0])|([1-9]\d*))\.[0-9]{1,2}))$/;
		        return reg.test(value);
	        },
	        message:"满减格式不正确."
    },
    workTimeCheck:{
	    validator:function(value){
		        var reg = /^([0-1]{1}\d|2[0-3]):([0-5]\d)$/;
		        return reg.test(value);
	        },
	        message:"时间格式不正确,格式:01:01"
    },
    phoneCheck:{
	    validator:function(value){
		        var telreg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}$/;
		        var mobilereg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
		        if((telreg.test(value))||(mobilereg.test(value))){
		        	return true;
		        }
	        },
	        message:"紧急电话格式不正确."
    }
});

//计算字符长度，中文两个字符，英文一个字符
function stringLength(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
}

ns.datagridOptions = {
		fit:true,
    	//fitColumns:true,
    	border:false,
    	pagination:true,
    	pageSize:10,
    	pageList:[50,100,200,500],
    	sortName:'name',
    	sortOrder:'asc',
    	checkOnSelect:true,
    	selectOnCheck:true,
    	nowrap:false
};

ns.getSelectOrCheck = function(datagrid){
	 var  select = datagrid.datagrid('getSelected');//选择
	 var Check = datagrid.datagrid('getChecked');
	 
	 var result = null;

        if(select){
        	result = select;
        }else{
           if(Check && Check.length > 0){
        	   result = Check;
           }
        }
        
        return result;
};

function fixWidth(percent){
	//去掉左侧菜单宽度
	return (document.body.clientWidth - 160) * percent;
}

$.fn.combobox.defaults.filter = function(q, row){  
    var opts = $(this).combobox('options');
    var v = row[opts.textField].toUpperCase();
    return v.indexOf(q.toUpperCase()) >= 0;  
};


//datagrid 时间控件编辑器扩展
$.extend($.fn.datagrid.defaults.editors, {
	datetimebox : {  
		init : function(container, options) {  
		    var box = $('<input />').appendTo(container);  
			box.datetimebox(options);  
			return box;  
		},  
		getValue : function(target) {  
		    return $(target).datetimebox('getValue');  
		},  
		setValue : function(target, value) {  
		    $(target).datetimebox('setValue', value);  
		},  
		resize : function(target, width) {  
		    var box = $(target);  
		    box.datetimebox('resize', width);  
		},  
		destroy : function(target) {  
		    $(target).datetimebox('destroy');  
	    }  
	}  
});
// 时间格式化
Date.prototype.format = function (format) {
    /*
    * eg:format="yyyy-MM-dd hh:mm:ss";
    */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};