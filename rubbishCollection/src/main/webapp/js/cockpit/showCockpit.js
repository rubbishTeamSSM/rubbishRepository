	//获取当前日期
	Date.prototype.Format = function (fmt) { //author: meizz 
			var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
	    	};
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
	};
	var currentdate = new Date().Format("yyyy-MM-dd");
   /*var mydate = new Date();
   var reg = new RegExp("/","g");
   var currentdate = mydate.toLocaleDateString().replace(reg,"-");*/
	var PAGE1=1;//页数
	var PAGE2=1;//页数
	var PAGE3=1;//页数
	var PAGE4=1;//页数
	var PAGE5=1;//页数
	var PAGE6=1;//页数
	var PAGE7=1;//页数
$(document).ready(function(){
   var groupFlag = 0;
   $(".cockpit-word").html(title);
   var groupFlag = 0;
   
   if("0" == flag){
	   $("#part-one").show();
	   initUserRoom(currentdate,groupFlag);//增长用户和房间折线图
   }
   if("1" == flag){
	   $("#part-two").show();
	   initUserRegist();//今日各项目增加情况圆形图
   }
   if("2" == flag){
	   $("#part-three").show();
	   initAreaRoom(PAGE1);//各项目下载情况
   }
   if("3" == flag){
	   $("#part-four").show();
	   initProjectActiveUser(PAGE2);//本日各项目活跃用户数
   }
   if("4" == flag){
	   $("#part-five").show();
	   initUserActive(currentdate,groupFlag);//活跃用户数增长情况
   }
   if("5" == flag){
	   $("#part-six").show();
	   initPvUv(currentdate);//本周活跃用户趋势
   }
   if("6" == flag){
	   $("#part-seven").show();
	   initProjectPv(PAGE3);//本日各项目使用PV
   }
   if("7" == flag){
	   $("#part-eight").show();
	   initUserRoomDept(currentdate,groupFlag);//子公司下增长用户和房间折线图
   }
   if("8" == flag){
	   $("#part-nine").show();
	   initUserRegistDept();//子公司下今日各项目增加情况圆形图
   }
   if("9" == flag){
	   $("#part-ten").show();
	   initAreaRoomDept(PAGE4);//子公司下总房间和绑定房间柱状图
   }
   if("10" == flag){
	   $("#part-eleven").show();
	   initUserActiveDept(currentdate,groupFlag);//子公司下活跃用户用户数  折线图
   }
   if("11" == flag){
	   $("#part-twelve").show();
	   initProjectPvDept(PAGE5);//本日各项目使用占比
   }
   if("12" == flag){
	   $("#part-thirteen").show();
	   initUserRoomArea(currentdate,groupFlag);//小区下增长用户和房间折线图
   }
   if("13" == flag){
	   $("#part-fourteen").show();
	   initAreaRoomArea(PAGE6);//小区下总房间和绑定房间柱状图
   }
   if("14" == flag){
	   $("#part-fifteen").show();
	   initUserActiveArea(currentdate,groupFlag);//小区下活跃用户用户数  折线图
   }
   if("15" == flag){
	   $("#part-sixteen").show();
	   initProjectPvArea(PAGE7);//小区下本日各项目使用PV
   }
});
 var userRoomRel = {};
 //增长用户和房间折线图
 function initUserRoom(date,groupFlag){
 		var data = {};
	  	data.SIGN = "0";
    	data.END_DATE = date;
    	
    	if(0==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
    		data.NUMBER = "7";
    	}else if(1==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
    		data.NUMBER = "49";
    	}else if(2==groupFlag){
    		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
    		data.NUMBER = "210";
    	}
    	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRight").hide();
					}else{
						$("#userRoomRight").show();
					}
					userRoomRel = data;
					userRoomRel.groupFlag = groupFlag;
					createUserRoomData(data);
				}
			}
		});
	}
	
	function createUserRoomData(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
	    $.each(data,function(i,v){
          category.push({
              "label" : v.DATE
          });
	    }); 
		var tmpD = {};
		var tmpE = {};
		var dd = []; 
		var ee = [];
		tmpD.seriesname = "用户数";
		tmpE.seriesname = "下载数";
		tmpE.color = '#00b3ec';
		tmpD.color = '#ffc900';
		$.each(data,function(i,v){
	       	dd.push({
		          "value" : v.OWNER_ADD_TOTAL
	        });
        }); 
       $.each(data,function(i,v){
       	ee.push({
	           "value" : v.BIND_ROOM_TOTAL
         });
       }) ;              
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		initUserRoomChart(chart);
	}
	
	function initUserRoomChart(chart) {
		//获取当前父元素的宽度
		var width = $("#part-one").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d
	      renderAt: 'chart-container',
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	           "caption": "",
	           "showvalues": "0",
	           "anchorRadius": "4",
	           "anchorBorderThickness": "2",
	           "theme" : "fint" 
	        },
	        "categories": [{ 
	        	"category": chart.category
	        }],
	        "dataset":  chart.dataset
	      }
	    });
		salesChart.render("RoomBrokenLine");
	}
	
	/* 日增 */
  	$("#userRoomDay").click(function(){
  		var dates = currentdate;
  		var groupFlag = 0;
  		initUserRoom(dates,groupFlag);
  	});
	
	/* 周增 */
  	$("#userRoomWeek").click(function(){
  		var dates = currentdate;
  		var groupFlag = 1;
  		initUserRoom(dates,groupFlag);
  	});
	
  	/* 月增 */
  	$("#userRoomMonth").click(function(){
  		var dates = currentdate;
  		var groupFlag = 2;
  		initUserRoom(dates,groupFlag);
  	});
  	
	/* 左分页 */
  	$("#userRoomLeft").click(function(){
  		if(0 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-1);
  		}else if(1 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-7);
  		}else if(2 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[0].DATE,-30);
  		}
  		var groupFlag = userRoomRel.groupFlag;
  		initUserRoom(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRight").click(function(){
  		if(0 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,7);
  		}else if(1 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,49);
  		}else if(2 == userRoomRel.groupFlag){
  			var dates = addDate(userRoomRel[6].DATE,210);
  		}
  		var groupFlag = userRoomRel.groupFlag;
  		initUserRoom(dates,groupFlag);
  	});
	
	 //今日各项目增加情况
    function initUserRegist(){
    	var data = {};
    	data.SIGN = "0";  
    	var BEGINDATE = $('#DATE_START').datebox('getValue');
  		var ENDDATE = $('#DATE_END').datebox('getValue');
  		
  		if(""!=BEGINDATE && "" != ENDDATE){
  			if(BEGINDATE>ENDDATE){
  	  			parent.$.messager.alert('错误', '结束时间不可小于开始时间', 'error');
  	  			return;
  	  		}
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}else if(""!=BEGINDATE && "" == ENDDATE){
  			data.FLAG = "2";
  			data.BEGINDATE = BEGINDATE;
  		}else if(""==BEGINDATE && "" != ENDDATE){
  			data.FLAG = "3";
  	  		data.ENDDATE = ENDDATE;
  		}
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoomBindTotal.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					var map = data.map;
					createCircularData(datas,map);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createCircularData(data,map){
	    var datas = [];
	     $.each(data,function(i,v){
               datas.push(
	               {
	               	"label" : v.AREA_NAME,
	               	"value" : v.ROOM_BIND_AREA
	               }
               );
         }) ;
      	initCircularChart(datas,map);
	}
	
	//加载圆饼图
	function initCircularChart(datas,map) {
		//获取当前父元素的宽度
		var width = $("#part-two").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		//获取绑定总数
		var allbind = map.ROOM_BIND_TOTAL;
		var bind = "共绑定"+allbind+"户";
		var revenueChart = new FusionCharts({
	        type: 'doughnut2d',
	        renderAt: 'chart-container',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart": {
	                "caption": "",
	                "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000,#A52A2A,#9400D3,#CD919E,#F08080,#7FFF00",
	                "bgColor": "#ffffff",
	                "showBorder": "0",
	                "use3DLighting": "0",
	                "showShadow": "0",
	                "enableSmartLabels": "0",
	                "startingAngle": "310",
	                "showLabels": "0",
	                "showPercentValues": "1",
	                "showLegend": "1",
	                "legendShadow": "0",
	                "legendBorderAlpha": "0",
	                "defaultCenterLabel": bind,
	                "centerLabel": "$label绑定$value户",
	                "centerLabelBold": "1",
	                "showTooltip": "1",
	                "decimals": "0",
	                "captionFontSize": "14",
	                "subcaptionFontSize": "14",
	                "centerLabelFontSize" : "15",
	                "defaultCenterLabelFontSize":"15",
	                "subcaptionFontBold": "0",
	                "toolTipColor": "#ffffff",
	                "toolTipBorderThickness": "0",
	                "toolTipBgColor": "#000000",
	                "toolTipBgAlpha": "80",
	                "toolTipBorderRadius": "2",
	                "toolTipPadding": "5",
	            },
	            "data":datas 
	        }
	    });
	    revenueChart.render("circularChart");
	
	}
	
	
	/* 左分页 */
  	$("#areaRoomLeft").click(function(){
  		PAGE1 = PAGE1 - 1;
  		initAreaRoom(PAGE1);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomRight").click(function(){
  		PAGE1 = PAGE1 + 1;
  		initAreaRoom(PAGE1);
  	});
  	
	//各项目下载情况
    function initAreaRoom(PAGE1){			
		var data = {}; 
		data.PAGE = PAGE1;
    	data.ROWS= 4;
		data.SIGN = "0"; 
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE1){
	       			$("#areaRoomRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE1){
	       			$("#areaRoomLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomLeft" ).css("display", "block"); 
	       		}
				 createChartData(data.rows);
				}
			}
		}); 
	}
	
	function createChartData(data) {
	   var chart = {};
	   var category =[];
	   //x轴坐标
	   var dataset = [];
       $.each(data,function(i,v){
              category.push({
                 "label" : v.AREA_NAME
            });
           }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; var ee = [];
	    tmpD.seriesname = "总用户数";
		tmpE.seriesname = "下载用户数";
		tmpE.color = '#CD661D';
		tmpD.color = '#00b1bd';
	  $.each(data,function(i,v){
         	dd.push({
          "value" : v.ALLROOM
          });
           ee.push({
           "value" : v.BINDROOM
           });
          }) ; 
		tmpD.data = dd;
           tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset;
		initChart(chart);
	}
	
	
    function initChart(chartdata) {
     //获取当前父元素的宽度
    	var width = $("#part-three").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var revenueChart = new FusionCharts({
			type: 'scrollColumn2d',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart" : {
				    "caption": "",
					"theme" : "fint"
				},
				"categories" : [ {
					"category" : chartdata.category
				} ],
	            "dataset" : chartdata.dataset
	        }
	    });
		 revenueChart.render("chartContainer");
	}
    
    //本日活跃用户分页
    /* 左分页 */
    $("#projectActiveLeft").click(function(){
  		PAGE2 = PAGE2 - 1;
  		initProjectActiveUser(PAGE2);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectActiveRight").click(function(){
  		PAGE2 = PAGE2 + 1;
  		initProjectActiveUser(PAGE2);
  	});
    
    //本日活跃用户数
	function initProjectActiveUser(PAGE2){
		var data = {};
		data.PAGE = PAGE2;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayActiveUsers.do',
			type : 'post',
			dataType : 'json',
			data :{'data':JSON.stringify(data)},
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE2){
	       			$("#projectActiveRight" ).css("display", "none"); 
	       		}else{
	       			$("#projectActiveRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE2){
	       			$("#projectActiveLeft" ).css("display", "none"); 
	       		}else{
	       			$("#projectActiveLeft" ).css("display", "block"); 
	       		}
				 createProjectActiveUser(data.rows);
				}
			}
		});
	}
	
	function createProjectActiveUser(data) {
	   var chart = {};
	   var category =[];
	   //x轴坐标
	   var dataset = [];		  
       $.each(data,function(i,v){
              category.push({
                 "label" : v.AREA_NAME
            });
        }) ;   
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
	     tmpD.seriesname = "用户数";
		 tmpE.seriesname = "活跃用户数";
		 tmpE.color = '#CD661D';
		 tmpD.color = '#00b1bd';
		 $.each(data,function(i,v){
          	dd.push({
	          "value" : v.EACH_ITEM_USERS
            });
            ee.push({
	          "value" : v.EACH_ITEM_ACTIVE_USERS
            });
		}); 
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset;
		initProjectActiveUserChart(chart);
	}

    function initProjectActiveUserChart(chartdata) {
    	//获取当前父元素的宽度
		var width = $("#part-four").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var revenueChart = new FusionCharts({
			type: 'scrollColumn2d',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart" : {
				    "caption": "",
					"theme" : "fint"
				},
				"categories" : [ {
					"category" : chartdata.category
				} ],
	            "dataset" : chartdata.dataset
	        }
		});
		revenueChart.render("ProjectActiveUser");
	}
    
    
    var userActiveRel = {};
	//活跃用户增长情况
	function initUserActive(date,groupFlag){
		 var data = {};
     	 data.SIGN = "0";
         data.END_DATE = date;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRight").hide();
					}else{
						$("#userActiveRight").show();
					}
					userActiveRel = data;
					userActiveRel.groupFlag = groupFlag;
					createUserData(data);
				}
			}
		});
	}
	
	function createUserData(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
	    tmpD.seriesname = "活跃用户量";
		tmpD.color = '#7dc432';
		$.each(data,function(i,v){
		   dd.push({
			  "value" : v.TODAY_ACTIVE_USERS
		   });
        });   
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		
      	initUserChart(chart);
	}
	
	function initUserChart(chart) {
		//获取当前父元素的宽度
		var width = $("#part-five").width() - 15;
		var height = $(".easyui-panel").height() - 85;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d   msline
	      renderAt: 'chart-container',
	      
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	        	
	           "caption": "",
	           "showNames":"0",
	           "rotateNames":"1",
               "showvalues": "0",
               "anchorRadius": "4",
               "anchorBorderThickness": "2",
               "theme" : "fint" 
	        },
	        "categories": [{ 
	        	"category": chart.category
	        }],
	        "dataset":  chart.dataset
	      }
	    });
	    salesChart.render("UserBrokenLine");
	}
	
	 //日增 
  	$("#userActiveDay").click(function(){
  		var dates = currentdate;
  		var groupFlag = 0;
  		initUserActive(dates,groupFlag);
  	});
	
	 //周增 
  	$("#userActiveWeek").click(function(){
  		var dates = currentdate;
  		var groupFlag = 1;
  		initUserActive(dates,groupFlag);
  	});
	
  	 //月增 
  	$("#userActiveMonth").click(function(){
  		var dates = currentdate;
  		var groupFlag = 2;
  		initUserActive(dates,groupFlag);
  	});
  	
	 //左分页 
  	$("#userActiveLeft").click(function(){
  		if(0 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-1);
  		}else if(1 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-7);
  		}else if(2 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[0].DATE,-30);
  		}
  		var groupFlag = userActiveRel.groupFlag;
  		initUserActive(dates,groupFlag);
  	});
  	
  	
  	 //右分页 
  	$("#userActiveRight").click(function(){
  		if(0 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,7);
  		}else if(1 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,49);
  		}else if(2 == userActiveRel.groupFlag){
  			var dates = addDate(userActiveRel[6].DATE,210);
  		}
  		var groupFlag = userActiveRel.groupFlag;
  		initUserActive(dates,groupFlag);
  	});
	
	
	
	var pvUvRel = {};
	//pv,uv增长情况
	function initPvUv(date){
	    var data = {};
        data.END_DATE = date;
        data.NUMBER = "7";
		$.ajax({
			url : url + '/cockpit/CockpitController/getPvUvDailyGrowth.do?',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			    
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#pvUvRight").hide();
					}else{
						$("#pvUvRight").show();
					}
					pvUvRel = data;
					createPvUvData(data);
				}
			}
		});
	}
	
	function createPvUvData(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; var ee = [];
	     tmpD.seriesname = "PV";
		 tmpE.seriesname = "UV";
		 tmpE.color = '#ffd200';
		 tmpD.color = '#fe006e';
		 $.each(data,function(i,v){
          	dd.push({
	          "value" : v.PV_TOTAL
           });
         }); 
         $.each(data,function(i,v){
        	ee.push({
          		"value" : v.UV_TOTAL
          	});
         });
                        
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		initPvUvChart(chart);
      	
	}
	
	function initPvUvChart(chart){
		//获取当前父元素的宽度
		var width = $("#part-six").width() - 10;
		var height = $(".easyui-panel").height() - 80;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d
	      renderAt: 'chart-container',
	      width: width,
	      height: '300',
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	          "caption": "PV、UV增长情况",
              "showvalues": "0",
              "anchorRadius": "4",
              "anchorBorderThickness": "2",
              "theme" : "fint" 
	        },
	        "categories": [
	        { "category": chart.category}
	        ],
	        "dataset":  chart.dataset
	      }
	    });
	    salesChart.render("PvUvBrokenLine");
	}
	
	 //pv、uv左分页 
  	$("#pvUvLeft").click(function(){
  		var dates = addDate(pvUvRel[0].DATE,-1);
  		initPvUv(dates);
  	});
  	
  	 //pv、uv右分页 
  	$("#pvUvRight").click(function(){
  		var dates = addDate(pvUvRel[6].DATE,7);
  		initPvUv(dates);
  	});
	
  	
  //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvLeft").click(function(){
  		PAGE3 = PAGE3 - 1;
  		initProjectPv(PAGE3);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvRight").click(function(){
  		PAGE3 = PAGE3 + 1;
  		initProjectPv(PAGE3);
  	});
	//本日各项目使用PV
	function initProjectPv(PAGE3){  
		var data = {};
		var BEGINDATE = $('#PV_DATE_START').datebox('getValue');
  		var ENDDATE = $('#PV_DATE_END').datebox('getValue');
  		data.PAGE = PAGE3;
    	data.ROWS= 4;
  		if(""!=BEGINDATE && "" != ENDDATE){
  			if(BEGINDATE>ENDDATE){
  	  			parent.$.messager.alert('错误', '结束时间不可小于开始时间', 'error');
  	  			return;
  	  		}
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}else if(""!=BEGINDATE || "" != ENDDATE){
  			data.FLAG = "1";
  			data.BEGINDATE = BEGINDATE;
  	  		data.ENDDATE = ENDDATE;
  		}
		
  		
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getEachItemDayPv.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE3){
		       			$("#projectPvRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE3){
		       			$("#projectPvLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvLeft" ).css("display", "block"); 
		       		}
				 	createProjectPv(data.rows);
				}
			}
		});
	}
	
	function createProjectPv(data) {
	   var chart = {};
	   var category =[];
	   //x轴坐标
	   var dataset = [];
       $.each(data,function(i,v){
         category.push({
             "label" : v.AREA_NAME
         });
       });  
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
	     tmpD.seriesname = "PV";
		 tmpD.color = '#FF0683';
		 $.each(data,function(i,v){
		     dd.push({
			    "value" : v.DAY_PV_TOTAL
		     });  
          }) ; 
		tmpD.data = dd;
		dataset.push(tmpD);
		chart.category = category;
		chart.dataset = dataset;
		initProjectPvChart(chart);
	}

    function initProjectPvChart(chartdata) {
    	//获取当前父元素的宽度
		var width = $("#part-seven").width() - 15;
		var height = $(".easyui-panel").height() - 85;
		var revenueChart = new FusionCharts({
			 type: 'scrollColumn2d',//scrollline2d
		     renderAt: 'chart-container',
		     width: width,
		     height: height,
		     dataFormat: 'json',
			 dataSource : {
				"chart" : {
					"theme" : "fint",
					"caption": "各项目PV"
			},
			categories : [{
				"category" : chartdata.category
			}],
			dataset : chartdata.dataset
			}
		});
		   revenueChart.render("ProjectPvHistogram");
	}

    /* ================ 子公司下 ================ */
    
    var userRoomRelDept = {};
    //增长用户和房间折线图
    function initUserRoomDept(date,groupFlag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.DEPT_CODE = dept_code;
       	
       	if(0==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
       		data.NUMBER = "210";
       	}
       	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRightDept").hide();
					}else{
						$("#userRoomRightDept").show();
					}
					userRoomRelDept = data;
					userRoomRelDept.groupFlag = groupFlag;
					createUserRoomDataDept(data);
				}
			}
		});
	}
	
	function createUserRoomDataDept(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
		tmpD.seriesname = "用户数";
		tmpE.seriesname = "下载数";
		tmpE.color = '#00b3ec';
		tmpD.color = '#ffc900';
		  $.each(data,function(i,v){
          	dd.push({
	          "value" : v.OWNER_ADD_TOTAL
           });
          }) ; 
          $.each(data,function(i,v){
          	ee.push({
	           "value" : v.BIND_ROOM_TOTAL
            });
          }) ;
                       
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		
      	initUserRoomChartDept(chart);
	}
	
	function initUserRoomChartDept(chart) {
		//获取当前父元素的宽度
		var width = $("#part-eight").width() - 10;
   		var height = $(".easyui-panel").height() - 100;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d
	      renderAt: 'chart-container',
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	          "caption": "",
              "showvalues": "0",
              "anchorRadius": "4",
              "anchorBorderThickness": "2",
              "theme" : "fint" 
	        },
	        "categories": [
	        { "category": chart.category}
	        ],
	        "dataset":  chart.dataset
	      }
	    });
		salesChart.render("RoomBrokenLineDept");
	}
  	
	/* 左分页 */
  	$("#userRoomLeftDept").click(function(){
  		if(0 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-1);
  		}else if(1 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-7);
  		}else if(2 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[0].DATE,-30);
  		}
  		var groupFlag = userRoomRelDept.groupFlag;
  		initUserRoomDept(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRightDept").click(function(){
  		if(0 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,7);
  		}else if(1 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,49);
  		}else if(2 == userRoomRelDept.groupFlag){
  			var dates = addDate(userRoomRelDept[6].DATE,210);
  		}
  		var groupFlag = userRoomRelDept.groupFlag;
  		initUserRoomDept(dates,groupFlag);
  	});
    
  	//子公司下今日各项目增加情况圆形图
  	function initUserRegistDept(){
    	var data = {};
    	data.SIGN = "1";  
    	data.DEPT_CODE = dept_code;
  		
		$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoomBindTotal.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					var map = data.map;
					createCircularDataDept(datas,map);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createCircularDataDept(data,map){
	    var datas = [];
	     $.each(data,function(i,v){
               datas.push(
	               {
	               	"label" : v.AREA_NAME,
	               	"value" : v.ROOM_BIND_AREA
	               }
               );
         }) ;
      	initCircularChartDept(datas,map);
	}
	
	//加载圆饼图
	function initCircularChartDept(datas,map) {
		//获取当前父元素的宽度
		var width = $("#part-nine").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		//获取绑定总数
		var allbind = map.ROOM_BIND_TOTAL;
		var bind = "今日共绑定"+allbind+"户";
		var revenueChart = new FusionCharts({
	        type: 'doughnut2d',
	        renderAt: 'chart-container',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart": {
	                "caption": "",
	                "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000,#A52A2A,#9400D3,#CD919E,#F08080,#7FFF00",
	                "bgColor": "#ffffff",
	                "showBorder": "0",
	                "use3DLighting": "0",
	                "showShadow": "0",
	                "enableSmartLabels": "0",
	                "startingAngle": "310",
	                "showLabels": "0",
	                "showPercentValues": "1",
	                "showLegend": "1",
	                "legendShadow": "0",
	                "legendBorderAlpha": "0",
	                "defaultCenterLabel": bind,
	                "centerLabel": "$label绑定$value户",
	                "centerLabelBold": "1",
	                "showTooltip": "1",
	                "decimals": "0",
	                "captionFontSize": "14",
	                "subcaptionFontSize": "14",
	                "defaultCenterLabelFontSize": "15",
	                "centerLabelFontSize": "15",
	                "subcaptionFontBold": "0",
	                "toolTipColor": "#ffffff",
	                "toolTipBorderThickness": "0",
	                "toolTipBgColor": "#000000",
	                "toolTipBgAlpha": "80",
	                "toolTipBorderRadius": "2",
	                "toolTipPadding": "5",
	            },
	            "data":datas 
	        }
	    });
	    revenueChart.render("circularChartDept");
	}
	
	//子公司下各小区所有房间和已绑定小区  分页
	/* 左分页 */
  	$("#areaRoomDeptLeft").click(function(){
  		PAGE4 = PAGE4 - 1;
  		initAreaRoomDept(PAGE4);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomDeptRight").click(function(){
  		PAGE4 = PAGE4 + 1;
  		initAreaRoomDept(PAGE4);
  	});
	//子公司下各小区所有房间和已绑定小区
    function initAreaRoomDept(PAGE4){			
    	var data = {};
    	data.SIGN = "1";
    	data.DEPT_CODE = dept_code;
    	data.PAGE = PAGE4;
    	data.ROWS= 4;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)}, 
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE4){
	       			$("#areaRoomDeptRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomDeptRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE4){
	       			$("#areaRoomDeptLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomDeptLeft" ).css("display", "block"); 
	       		}
				 createChartDataDept(data.rows);
				}
			}
		}); 
	}
	
	function createChartDataDept(data) {
	   var chart = {};
	   var category =[];
	   //x轴坐标
	   var dataset = [];
       $.each(data,function(i,v){
              category.push({
                 "label" : v.AREA_NAME
            });
           }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; var ee = [];
	    tmpD.seriesname = "总用户数";
		tmpE.seriesname = "下载用户数";
		tmpE.color = '#CD661D';
		tmpD.color = '#00b1bd';
	  $.each(data,function(i,v){
         	dd.push({
          "value" : v.ALLROOM
          });
           ee.push({
           "value" : v.BINDROOM
           });
          }) ; 
		tmpD.data = dd;
           tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset;
		initChartDept(chart);
	}
	
    function initChartDept(chartdata) {
     //获取当前父元素的宽度
    	var width = $("#part-ten").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var revenueChart = new FusionCharts({
			type: 'scrollColumn2d',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart" : {
				    "caption": "",
					"theme" : "fint"
				},
				"categories" : [ {
					"category" : chartdata.category
				} ],
	            "dataset" : chartdata.dataset
	        }
	    });
		 revenueChart.render("chartContainerDept");
	}
	
    var userActiveRelDept = {};
	//活跃用户增长情况
	function initUserActiveDept(date,groupFlag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.DEPT_CODE = dept_code;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRightDept").hide();
					}else{
						$("#userActiveRightDept").show();
					}
					userActiveRelDept = data;
					userActiveRelDept.groupFlag = groupFlag;
					createUserDataDept(data);
				}
			}
		});
	}
	
	function createUserDataDept(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
	    tmpD.seriesname = "活跃用户量";
		tmpD.color = '#7dc432';
		$.each(data,function(i,v){
		   dd.push({
			  "value" : v.TODAY_ACTIVE_USERS
		   });
        });   
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		
      	initUserChartDept(chart);
	}
	
	function initUserChartDept(chart) {
		//获取当前父元素的宽度
		var width = $("#part-eleven").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d   msline
	      renderAt: 'chart-container',
	      
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	        	
	           "caption": "",
	           "showNames":"0",
	           "rotateNames":"1",
               "showvalues": "0",
               "anchorRadius": "4",
               "anchorBorderThickness": "2",
               "theme" : "fint" 
	        },
	        "categories": [{ 
	        	"category": chart.category
	        }],
	        "dataset":  chart.dataset
	      }
	    });
	    salesChart.render("UserBrokenLineDept");
	}
	
	 //左分页 
  	$("#userActiveLeftDept").click(function(){
  		if(0 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-1);
  		}else if(1 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-7);
  		}else if(2 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[0].DATE,-30);
  		}
  		var groupFlag = userActiveRelDept.groupFlag;
  		initUserActiveDept(dates,groupFlag);
  	});
  	
  	
  	 //右分页 
  	$("#userActiveRightDept").click(function(){
  		if(0 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,7);
  		}else if(1 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,49);
  		}else if(2 == userActiveRelDept.groupFlag){
  			var dates = addDate(userActiveRelDept[6].DATE,210);
  		}
  		var groupFlag = userActiveRelDept.groupFlag;
  		initUserActiveDept(dates,groupFlag);
  	});
  			
  	
    //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvDeptLeft").click(function(){
  		PAGE5 = PAGE5 - 1;
  		initProjectPvDept(PAGE5);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvDeptRight").click(function(){
  		PAGE5 = PAGE5 + 1;
  		initProjectPvDept(PAGE5);
  	});
	//子公司下本日各项目使用PV
	function initProjectPvDept(PAGE5){  
		var data = {};
		data.DEPT_CODE = dept_code;
		data.PAGE = PAGE5;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayUseRatio.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE5){
		       			$("#projectPvDeptRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvDeptRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE5){
		       			$("#projectPvDeptLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvDeptLeft" ).css("display", "block"); 
		       		}
				 	createProjectPvDept(data.rows);
				}
			}
		});
	}
	
	function createProjectPvDept(data) {
		   var chart = {};
		   var category =[];
		   //x轴坐标
		   var dataset = [];
	       $.each(data,function(i,v){
	         category.push({
	             "label" : v.AREA_NAME
	         });
	       });  
			var tmpD = {};
	        var tmpE = {};
			var dd = []; 
			var ee = [];
		     tmpD.seriesname = "总用户数";
		     tmpE.seriesname = "本日用户数";
			 tmpD.color = '#CD661D';
			 tmpE.color = "#00b1bd";
			 $.each(data,function(i,v){
			     dd.push({
				    "value" : v.EACH_ITEM_USERS
			     });
			     ee.push({
				    "value" : v.EACH_ITEM_USE_USERS
				 });
	          }) ; 
			tmpD.data = dd;
			tmpE.data = ee;
			dataset.push(tmpD);
			dataset.push(tmpE);
			chart.category = category;
			chart.dataset = dataset;
			initProjectPvChartDept(chart);
		}

	    function initProjectPvChartDept(chartdata) {
	    	//获取当前父元素的宽度
			var width = $("#part-twelve").width() - 10;
			var height = $(".easyui-panel").height() - 85;
			var revenueChart = new FusionCharts({
				 type: 'scrollColumn2d',//scrollline2d
			     renderAt: 'chart-container',
			     width: width,
			     height: height,
			     dataFormat: 'json',
				 dataSource : {
					"chart" : {
						"theme" : "fint",
						"caption": ""
				},
				categories : [{
					"category" : chartdata.category
				}],
				dataset : chartdata.dataset
				}
			});
			   revenueChart.render("ProjectPvHistogramDept");
		}	
    
    
/* ================ 小区下 ================ */
    
    var userRoomRelArea = {};
    //增长用户和房间折线图
    function initUserRoomArea(date,groupFlag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.AREA_CODE = area_code;
       	if(0==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==groupFlag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindMonthTotal.do';
       		data.NUMBER = "210";
       	}
       	
		$.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userRoomRightArea").hide();
					}else{
						$("#userRoomRightArea").show();
					}
					userRoomRelArea = data;
					userRoomRelArea.groupFlag = groupFlag;
					createUserRoomDataArea(data);
				}
			}
		});
	}
	
	function createUserRoomDataArea(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
		tmpD.seriesname = "用户数";
		tmpE.seriesname = "下载数";
		tmpE.color = '#00b3ec';
		tmpD.color = '#ffc900';
		  $.each(data,function(i,v){
          	dd.push({
	          "value" : v.OWNER_ADD_TOTAL
           });
          }) ; 
          $.each(data,function(i,v){
          	ee.push({
	           "value" : v.BIND_ROOM_TOTAL
            });
          }) ;
                       
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		
      	initUserRoomChartArea(chart);
	}
	
	function initUserRoomChartArea(chart) {
		//获取当前父元素的宽度
		var width = $("#part-thirteen").width() - 10;
   		var height = $(".easyui-panel").height() - 85;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d
	      renderAt: 'chart-container',
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	          "caption": "",
              "showvalues": "0",
              "anchorRadius": "4",
              "anchorBorderThickness": "2",
              "theme" : "fint" 
	        },
	        "categories": [
	        { "category": chart.category}
	        ],
	        "dataset":  chart.dataset
	      }
	    });
		salesChart.render("RoomBrokenLineArea");
	}
  	
	/* 左分页 */
  	$("#userRoomLeftArea").click(function(){
  		if(0 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-1);
  		}else if(1 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-7);
  		}else if(2 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[0].DATE,-30);
  		}
  		var groupFlag = userRoomRelArea.groupFlag;
  		initUserRoomArea(dates,groupFlag);
  	});
  	
  	/* 右分页 */
  	$("#userRoomRightArea").click(function(){
  		if(0 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,7);
  		}else if(1 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,49);
  		}else if(2 == userRoomRelArea.groupFlag){
  			var dates = addDate(userRoomRelArea[6].DATE,210);
  		}
  		var groupFlag = userRoomRelArea.groupFlag;
  		initUserRoomArea(dates,groupFlag);
  	});
    
  	
  	
  //小区下各小区所有房间和已绑定小区 分页
  	/* 左分页 */
  	$("#areaRoomAreaLeft").click(function(){
  		PAGE6 = PAGE6 - 1;
  		initAreaRoomArea(PAGE6);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#areaRoomAreaRight").click(function(){
  		PAGE6 = PAGE6 + 1;
  		initAreaRoomArea(PAGE6);
  	});
  	
	//小区下各小区所有房间和已绑定小区
    function initAreaRoomArea(PAGE6){			
    	var data = {};
  	  	data.SIGN = "1";
       	data.AREA_CODE = area_code;
       	data.PAGE = PAGE6;
    	data.ROWS= 4;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				//加载到最后一页，去除加载更多按钮   
	       		var counts = data.total;
	       		reCount = Math.ceil(counts / 4);
	       		if(reCount <= PAGE6){
	       			$("#areaRoomAreaRight" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomAreaRight" ).css("display", "block"); 
	       		}
	       		if(1>=PAGE6){
	       			$("#areaRoomAreaLeft" ).css("display", "none"); 
	       		}else{
	       			$("#areaRoomAreaLeft" ).css("display", "block"); 
	       		}
				 createChartDataArea(data.rows);
				}
			}
		}); 
	}
	
	function createChartDataArea(data) {
	   var chart = {};
	   var category =[];
	   //x轴坐标
	   var dataset = [];
       $.each(data,function(i,v){
              category.push({
                 "label" : v.AREA_NAME
            });
           }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; var ee = [];
	    tmpD.seriesname = "总用户数";
		tmpE.seriesname = "下载用户数";
		tmpE.color = '#CD661D';
		tmpD.color = '#00b1bd';
	  $.each(data,function(i,v){
         	dd.push({
          "value" : v.ALLROOM
          });
           ee.push({
           "value" : v.BINDROOM
           });
          }) ; 
		tmpD.data = dd;
           tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset;
		initChartArea(chart);
	}
	
    function initChartArea(chartdata) {
     //获取当前父元素的宽度
    	var width = $("#part-fourteen").width() - 10;
		var height = $(".easyui-panel").height() - 85;
		var revenueChart = new FusionCharts({
			type: 'scrollColumn2d',
	        width: width,
	        height: height,
	        dataFormat: 'json',
	        dataSource: {
	            "chart" : {
				    "caption": "",
					"theme" : "fint"
				},
				"categories" : [ {
					"category" : chartdata.category
				} ],
	            "dataset" : chartdata.dataset
	        }
	    });
		 revenueChart.render("chartContainerArea");
	}
	
    var userActiveRelArea = {};
	//活跃用户增长情况
	function initUserActiveArea(date,groupFlag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.AREA_CODE = area_code;
         
		if(0==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==groupFlag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersMonthCounts.do';
			data.NUMBER = "210";
		}
         
		 $.ajax({
			url : passurl,
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					if(currentdate == data[6].DATE){
						$("#userActiveRightArea").hide();
					}else{
						$("#userActiveRightArea").show();
					}
					userActiveRelArea = data;
					userActiveRelArea.groupFlag = groupFlag;
					createUserDataArea(data);
				}
			}
		});
	}
	
	function createUserDataArea(data){
	    var chart = {};
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
		var tmpD = {};
        var tmpE = {};
		var dd = []; 
		var ee = [];
	    tmpD.seriesname = "活跃用户量";
		tmpD.color = '#7dc432';
		$.each(data,function(i,v){
		   dd.push({
			  "value" : v.TODAY_ACTIVE_USERS
		   });
        });   
		tmpD.data = dd;
        tmpE.data = ee;
		dataset.push(tmpD);
		dataset.push(tmpE);
		chart.category = category;
		chart.dataset = dataset; 
		
      	initUserChartArea(chart);
	}
	
	function initUserChartArea(chart) {
		//获取当前父元素的宽度
		var width = $("#part-fifteen").width() - 10;
		var height = $(".easyui-panel").height() - 80;
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d   msline
	      renderAt: 'chart-container',
	      
	      width: width,
	      height: height,
	      dataFormat: 'json',
	      dataSource: {
	        "chart": {
	        	
	           "caption": "",
	           "showNames":"0",
	           "rotateNames":"1",
               "showvalues": "0",
               "anchorRadius": "4",
               "anchorBorderThickness": "2",
               "theme" : "fint" 
	        },
	        "categories": [{ 
	        	"category": chart.category
	        }],
	        "dataset":  chart.dataset
	      }
	    });
	    salesChart.render("UserBrokenLineArea");
	}
		
	/* 左分页 */
  	$("#userActiveLeftArea").click(function(){
  		if(0 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-1);
  		}else if(1 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-7);
  		}else if(2 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[0].DATE,-30);
  		}
  		var groupFlag = userActiveRelArea.groupFlag;
  		initUserActiveArea(dates,groupFlag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userActiveRightArea").click(function(){
  		if(0 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,7);
  		}else if(1 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,49);
  		}else if(2 == userActiveRelArea.groupFlag){
  			var dates = addDate(userActiveRelArea[6].DATE,210);
  		}
  		var groupFlag = userActiveRelArea.groupFlag;
  		initUserActiveArea(dates,groupFlag);
  	});
	
  	
  	/* 左分页 */
    $("#projectPvAreaLeft").click(function(){
  		PAGE7 = PAGE7 - 1;
  		initProjectPvArea(PAGE7);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvAreaRight").click(function(){
  		PAGE7 = PAGE7 + 1;
  		initProjectPvArea(PAGE7);
  	});
  	
	//子公司下本日各项目使用占比
	function initProjectPvArea(PAGE7){  
		var data = {};
		data.AREA_CODE = area_code;
		data.PAGE = PAGE7;
    	data.ROWS= 4;
		$.ajax({
			url : url + '/cockpit/CockpitController/getItemDayUseRatio.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				
				if(null != data && "" != data){
					//加载到最后一页，去除加载更多按钮   
		       		var counts = data.total;
		       		reCount = Math.ceil(counts / 4);
		       		if(reCount <= PAGE7){
		       			$("#projectPvAreaRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvAreaRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE7){
		       			$("#projectPvAreaLeft" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvAreaLeft" ).css("display", "block"); 
		       		}
				 	createProjectPvArea(data.rows);
				}
			}
		});
	}

    function createProjectPvArea(data) {
		   var chart = {};
		   var category =[];
		   //x轴坐标
		   var dataset = [];
	       $.each(data,function(i,v){
	         category.push({
	             "label" : v.AREA_NAME
	         });
	       });  
			var tmpD = {};
	        var tmpE = {};
			var dd = []; 
			var ee = [];
		     tmpD.seriesname = "总用户数";
		     tmpE.seriesname = "本日用户数";
			 tmpD.color = '#CD661D';
			 tmpE.color = "#00b1bd";
			 $.each(data,function(i,v){
			     dd.push({
				    "value" : v.EACH_ITEM_USERS
			     });
			     ee.push({
				    "value" : v.EACH_ITEM_USE_USERS
				 });
	          }) ; 
			tmpD.data = dd;
			tmpE.data = ee;
			dataset.push(tmpD);
			dataset.push(tmpE);
			chart.category = category;
			chart.dataset = dataset;
			initProjectPvChartArea(chart);
		}

	    function initProjectPvChartArea(chartdata) {
	    	//获取当前父元素的宽度
			var width = $("#part-sixteen").width() - 10;
			var height = $(".easyui-panel").height() - 85;
			var revenueChart = new FusionCharts({
				 type: 'scrollColumn2d',//scrollline2d
			     renderAt: 'chart-container',
			     width: width,
			     height: height,
			     dataFormat: 'json',
				 dataSource : {
					"chart" : {
						"theme" : "fint",
						"caption": ""
				},
				categories : [{
					"category" : chartdata.category
				}],
				dataset : chartdata.dataset
				}
			});
			   revenueChart.render("ProjectPvHistogramArea");
		}
    
    
    /* =======计算日期========= */
	function addDate(date,days){ 
	   var now = date.split('-');
	   now = new Date(Number(now['0']),(Number(now['1'])-1),Number(now['2']));  
	   now.setDate(now.getDate() + days);
       var m=now.getMonth()+1; 
       return now.getFullYear()+'-'+m+'-'+now.getDate(); 
       
    } 