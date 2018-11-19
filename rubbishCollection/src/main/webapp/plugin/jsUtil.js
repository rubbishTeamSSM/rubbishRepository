/**
 * 使panel和datagrid在加载时提示
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.fn.panel.defaults.loadingMessage = '加载中....';

/**
 *
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 
 * 例子如下，第二个参数可以是数组
 * 
 * datagrid.datagrid('removeEditor', 'cpwd');
 * 
 * datagrid.datagrid('addEditor', [ { field : 'ccreatedatetime', editor : { type : 'datetimebox', options : { editable : false } } }, { field : 'cmodifydatetime', editor : { type : 'datetimebox', options : { editable : false } } } ]);
 * 
 */
$.extend($.fn.datagrid.methods, {
 	//扩展datagrid，添加动态增加或删除Editor的方法, @author 孙宇
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	},
	//扩展datagrid，添加动态增加或删除formatter的方法, @author 周佳
	addFormatter : function(jq, param) {
		var e = $(jq).datagrid('getColumnOption', param.field);
		e.formatter = param.fun;
	},
	removeFormatter : function(jq, param) {
		var e = $(jq).datagrid('getColumnOption', param);
		e.formatter = null;
	},
 	//扩展datagrid，添加动态增加或删除toolbar的方法,@author 夏悸
	addToolbarItem: function(jq, items){  
		return jq.each(function(){  
			var toolbar = $(this).parent().prev("div.datagrid-toolbar");
			for(var i = 0;i<items.length;i++){
				var item = items[i];
				if(item === "-"){
					toolbar.append('<div class="datagrid-btn-separator"></div>');
				}else{
					var btn=$("<a href=\"javascript:void(0)\"></a>");
					btn[0].onclick=eval(item.handler||function(){});
					btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
				}
			}
			toolbar = null;
		});  
	},
	removeToolbarItem: function(jq, param){  
		return jq.each(function(){  
			var btns = $(this).parent().prev("div.datagrid-toolbar").children("a");
			var cbtn = null;
			if(typeof param == "number"){
				cbtn = btns.eq(param);
			}else if(typeof param == "string"){
				var text = null;
				btns.each(function(){
					text = $(this).data().linkbutton.options.text;
					if(text == param){
						cbtn = $(this);
						text = null;
						return;
					}
				});
			} 
			if(cbtn){
				var prev = cbtn.prev()[0];
				var next = cbtn.next()[0];
				if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
					$(prev).remove();
				}else if(next && next.nodeName == "DIV"){
					$(next).remove();
				}else if(prev && prev.nodeName == "DIV"){
					$(prev).remove();
				}
				cbtn.remove();	
				cbtn= null;
			}						
		});  
	} 				

});
$.extend($.fn.datagrid.defaults.editors.combobox, {
	    getValue : function(jq) {
	        var opts = $(jq).combobox('options');
	        if(opts.multiple){
	            var values = $(jq).combobox('getValues');
	            /*if(values.length>0){
	                if(values[0]==''||values[0]==' '){
	                    return values.join(',').substring(1);//新增的时候会把空白当成一个值了，去掉
	                }
	            }*/
	            return values.join(',');
	        }
	        else
	            return $(jq).combobox("getValue");
	    },
	    setValue : function(jq, value) {
	        var opts = $(jq).combobox('options');
	        if (value == null) {
				
			}else if(opts.multiple &&  value.indexOf(opts.separator)!=-1){//多选且不只一个值
	            var values = value.split(opts.separator);
	            $(jq).combobox("setValues", values);
	        }
	        else
	            $(jq).combobox("setValue", value);
	    }
	});
$.extend($.fn.datagrid.defaults.editors, {
	combogrid : {
		init : function(container, options) {
			var input = $('<inputtype="text" class="datagrid-editable-input">').appendTo(container);
			input.combogrid(options);
			return input;
		},
		destroy : function(target) {
			$(target).combogrid('destroy');
		},
		getValue : function(target) {
			return $(target).combogrid('getValue');
		},
		setValue : function(target, value) {
			$(target).combogrid('setValue', value);
		},
		resize : function(target, width) {
			$(target).combogrid('resize', width);
		}
	}
});
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + encodeURI(this['value']);
		} else {
			o[this['name']] = encodeURI(this['value']);
		}
	});
	return o;
};