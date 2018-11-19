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
$(document).ready(function(){
	 var flag = 0;
	 getSummaryInfo();
	 initUserRoom(currentdate,flag);//增长用户和房间折线图
	 initAreaRoom(PAGE1);//初始化总房间和绑定房间柱状图
	 initUserActive(currentdate,flag);//活跃用户用户数  折线图
	 initProjectPv(PAGE2);//本日各项目使用PV
	 
	 initUserUv();//本日使用用户
	 initActiveUserProportion();//本日活跃用户
	 
	 getComplNoteInfo();//报修投诉发帖总计（闭合率完成率及时回复率）
	 initRepairCir();//报修圆圈图
     initComplaintCir();//投诉圆圈图
     initPostCir();//帖子圆圈图
     initTodayRepair(currentdate);
});
    
    //获取抬头信息
    function getSummaryInfo(){
    	var data = {};
    	data.SIGN = "1";
    	data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getSummaryInfo.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			error : function(){},
			success : function(data){
				$("#OWNER_REGIS_TOTAL").text(data.OWNER_REGIS_TOTAL);
				$("#OWNER_REGIS_TODAY").text(data.OWNER_REGIS_TODAY);
				$("#BIND_ROOM_TOTAL").text(data.BIND_ROOM_TOTAL);
				$("#BIND_ROOM_TODAY").text(data.ADD_BIND_ROOM_TODAY);
				$("#DOWNLOAD_RATE").text(data.DOWNLOAD_RATE);
				$("#LINK_GROWTH_RATE").text(data.LINK_GROWTH_RATE);
			}
		});
    }
    
    var userRoomRel = {};
    //增长用户和房间折线图
    function initUserRoom(date,flag){
    	var data = {};
  	  	data.SIGN = "1";
       	data.END_DATE = date;
       	data.AREA_CODE = area_code;
       	if(0==flag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindDayTotal.do';
       		data.NUMBER = "7";
       	}else if(1==flag){
       		passurl = url + '/cockpit/CockpitController/getRegisBindWeekTotal.do';
       		data.NUMBER = "49";
       	}else if(2==flag){
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
					userRoomRel.flag = flag;
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
		
      	initUserRoomChart(chart);
	}
	
	function initUserRoomChart(chart) {
		//获取当前父元素的宽度
		var width = $(".first-con").find("#first-cockpit-detail-con").width();
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d
	      renderAt: 'chart-container',
	      width: width,
	      height: '300',
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
		salesChart.render("RoomBrokenLine");
	}
	
	/* 日增 */
  	$("#userRoomDay").click(function(){
  		var dates = currentdate;
  		var flag = 0;
  		initUserRoom(dates,flag);
  	});
	
	/* 周增 */
  	$("#userRoomWeek").click(function(){
  		var dates = currentdate;
  		var flag = 1;
  		initUserRoom(dates,flag);
  	});
	
  	/* 月增 */
  	$("#userRoomMonth").click(function(){
  		var dates = currentdate;
  		var flag = 2;
  		initUserRoom(dates,flag);
  	});
  	
	/* 左分页 */
  	$("#userRoomLeft").click(function(){
  		if(0 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-1);
  		}else if(1 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-7);
  		}else if(2 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[0].DATE,-30);
  		}
  		var flag = userRoomRel.flag;
  		initUserRoom(dates,flag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userRoomRight").click(function(){
  		if(0 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,7);
  		}else if(1 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,49);
  		}else if(2 == userRoomRel.flag){
  			var dates = addDate(userRoomRel[6].DATE,210);
  		}
  		var flag = userRoomRel.flag;
  		initUserRoom(dates,flag);
  	});

	
  	
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
	//获取各小区所有房间和已绑定小区
    function initAreaRoom(PAGE1){		
    	var data = {};
    	data.PAGE = PAGE1;
    	data.ROWS= 4;
     	data.SIGN = "1";  
     	data.AREA_CODE = area_code;
		 $.ajax({
			url : url + '/cockpit/CockpitController/getAreaRoom.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
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
		var width = $(".third-con").find(".cockpit-detail-con").width();
		var revenueChart = new FusionCharts({
			type: 'scrollColumn2d',
	        width: width,
	        height: '300',
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
	
	    
    var userActiveRel = {};
	//活跃用户增长情况
	function initUserActive(date,flag){
		 var data = {};
     	 data.SIGN = "1";
         data.END_DATE = date;
         data.AREA_CODE = area_code;
         
		if(0==flag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersDayCounts.do';
			data.NUMBER = "7";
		}else if(1==flag){
			passurl = url + '/cockpit/CockpitController/getActiveUsersWeekCounts.do';
			data.NUMBER = "49";
		}else if(2==flag){
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
					userActiveRel.flag = flag;
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
		var width = $(".four-con").find(".cockpit-detail-con").width();
		var salesChart = new FusionCharts({
	      type: 'scrollline2d',//scrollline2d   msline
	      renderAt: 'chart-container',
	      
	      width: width,
	      height: '175',
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
	
	/* 日增 */
  	$("#userActiveDay").click(function(){
  		var dates = currentdate;
  		var flag = 0;
  		initUserActive(dates,flag);
  	});
	
	/* 周增 */
  	$("#userActiveWeek").click(function(){
  		var dates = currentdate;
  		var flag = 1;
  		initUserActive(dates,flag);
  	});
	
  	/* 月增 */
  	$("#userActiveMonth").click(function(){
  		var dates = currentdate;
  		var flag = 2;
  		initUserActive(dates,flag);
  	});
  	
	/* 左分页 */
  	$("#userActiveLeft").click(function(){
  		if(0 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-1);
  		}else if(1 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-7);
  		}else if(2 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[0].DATE,-30);
  		}
  		var flag = userActiveRel.flag;
  		initUserActive(dates,flag);
  	});
  	
  	
  	/* 右分页 */
  	$("#userActiveRight").click(function(){
  		if(0 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,7);
  		}else if(1 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,49);
  		}else if(2 == userActiveRel.flag){
  			var dates = addDate(userActiveRel[6].DATE,210);
  		}
  		var flag = userActiveRel.flag;
  		initUserActive(dates,flag);
  	});
	
  		
  	
    //本日各项目使用PV分页
  	/* 左分页 */
    $("#projectPvLeft").click(function(){
  		PAGE2 = PAGE2 - 1;
  		initProjectPv(PAGE2);
  		
  	});
  	
  	
  	/* 右分页 */
  	$("#projectPvRight").click(function(){
  		PAGE2 = PAGE2 + 1;
  		initProjectPv(PAGE2);
  	});
	//本日各项目使用占比
	function initProjectPv(PAGE2){  
		var data = {};
		
		data.AREA_CODE = area_code;
		data.PAGE = PAGE2;
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
		       		if(reCount <= PAGE2){
		       			$("#projectPvRight" ).css("display", "none"); 
		       		}else{
		       			$("#projectPvRight" ).css("display", "block"); 
		       		}
		       		if(1>=PAGE2){
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
		     tmpD.seriesname = "总用户数";
		     tmpE.seriesname = "本日用户数";
			 tmpD.color = '#00b1bd';
			 tmpE.color = "#CD661D";
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
			initProjectPvChart(chart);
		}

	    function initProjectPvChart(chartdata) {
	    	//获取当前父元素的宽度
			var width = $(".senevn-con").find(".cockpit-detail-con").width();
			var revenueChart = new FusionCharts({
				 type: 'scrollColumn2d',//scrollline2d
			     renderAt: 'chart-container',
			     width: width,
			     height: '175',
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
			   revenueChart.render("ProjectPvHistogram");
		}		
	
    
	//本日使用用户
	function initUserUv(){
		var data = {};
		data.FLAG = "1";
		data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getCpyOrAreaUserInfo.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
				
					var datas = data.list;
					$("#PV_TOTAL_DAY").text(data.PV_TOTAL_TODAY);// 使用用户 PV_TOTAL_TODAY
					$("#UV_TOTAL_DAY").text(data.GROWTH_DIFFERENCE);//今日同比昨天增加 GROWTH_DIFFERENCE
					
					createUserUv(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createUserUv(data){
      	initCircularUserUvChart(data);
	}
	
	//加载圆饼图
	function initCircularUserUvChart(data) {
		var revenueChart = new FusionCharts({
        type: 'doughnut2d',
        renderAt: 'chart-container',
        width: '300',
        height: '200',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "caption": "",
                "paletteColors": "#00c0c3,#f2f2f2",
                "bgColor": "#ffffff",
                "showBorder": "0",
                "use3DLighting": "0",
                "showShadow": "0",
                "enableSmartLabels": "0",
                "startingAngle": "310",
                "showLabels": "0",
                "showPercentValues": "1",
                //"showLegend": "1",
                "legendShadow": "0",
                "legendBorderAlpha": "0",
                //"defaultCenterLabel": "用户Uv占比",
                "centerLabel": "$label$value",
                "centerLabelBold": "1",
                "showTooltip": "0",
                "decimals": "0",
                "captionFontSize": "8",
                "subcaptionFontSize": "30",
                "subcaptionFontBold": "0" 
            },
            "data":[{
	               	"label" : "用户使用数",
	               	"value" : data.PV_TOTAL_TODAY//日uv
	               },
	               {
	               	"label" : "非用户使用数",
	               	"value" : data.NO_USER_TOTAL//日uv
	               }]
        }
    });
    revenueChart.render("UserUvProportion");
	}
	
	//本日活跃用户
    function initActiveUserProportion(){
    	var data = {};
		data.FLAG = "1";
		data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getCpyOrAreaActiveUserInfo.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					$("#TODAY_ACTIVE_USERS").text(data.UV_TOTAL_TODAY);//活跃用户数
					$("#CHAIN_GROWTH").text(data.GROWTH_DIFFERENCE);//今日同比昨日增加数
					
					createActiveUserProportion(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createActiveUserProportion(data){
      	initActiveUserProportionChart(data);
	}
	
	//加载圆饼图
	function initActiveUserProportionChart(data) {
	    
		var revenueChart = new FusionCharts({
        type: 'doughnut2d',
        renderAt: 'chart-container',
        width: '300',
        height: '200',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "caption": "",
                "paletteColors": "#00c0c3,#f2f2f2",
                "bgColor": "#ffffff",
                "showBorder": "0",
                "use3DLighting": "0",
                "showShadow": "0",
                "enableSmartLabels": "0",
                "startingAngle": "310",
                "showLabels": "0",
                "showPercentValues": "1",
                //"showLegend": "1",
                "legendShadow": "0",
                "legendBorderAlpha": "0",
                //"defaultCenterLabel": "活跃用户占比",
                "centerLabel": "$label$value",
                "centerLabelBold": "1",
                "showTooltip": "0",
                "decimals": "0",
                "captionFontSize": "8",
                "subcaptionFontSize": "30",
                "subcaptionFontBold": "0" 
            },
            "data":[{
	               	"label" : "活跃用户数",
	               	"value" : data.UV_TOTAL_TODAY//活跃用户数
	               },
	               {
	               	"label" : "非活跃用户数",
	               	"value" : data.NO_ACTIVE_USER_TOTAL//非活跃用户
	               }]
        }
    });
    revenueChart.render("ActiveUserProportion");
	}
	
	
	/* ================================================ */
	//投诉保修发帖的汇总信息
	function getComplNoteInfo(){
    	var data = {};
     	data.SIGN = "1";
     	data.AREA_CODE = area_code;
    	$.ajax({
			url : url + '/cockpit/CockpitController/getComplNoteInfo.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			error : function(){},
			success : function(data){
				$("#CPT_COMPL_TOTAL").text(data.CPT_COMPL_TOTAL);//今日投诉数
				$("#COMPL_EVAL_RATE").text(data.COMPL_EVAL_RATE);//投诉闭合率
				$("#COMPLETED_RATE").text(data.COMPLETED_RATE);//投诉完成率
				$("#REP_COMPL_TOTAL").text(data.REP_COMPL_TOTAL);//今日报修数
				$("#REP_COMPL_EVAL_RATE").text(data.REP_COMPL_EVAL_RATE);//报修闭合率
				$("#REP_COMPL_COMPLETED_RATE").text(data.REP_COMPL_COMPLETED_RATE);//报修完成率
				$("#REPLY_NOTE_TOTAL").text(data.REP_COMPL_TOTAL);//今日物业相关发帖数
				$("#NOTE_TIMELY_REPLY_RATE").text(data.NOTE_TIMELY_REPLY_RATE);//帖子及时回复率
				
				
			}
		});
    }
	
	
	 //本日报修
    function initRepairCir(){
    	var data = {};
     	data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getRepAnalysis.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					var datas = data.list;
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].COMPL_TYPE_NAME;
						html += "<span id='"+ repair[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						html += "</div>";
						
					}
					$("#repair").append(html); 				
					createRepairCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createRepairCir(data){	    
		var list = data.list;
		var map = data.map;
		var datas = [];
		$.each(list,function(i,v){
            datas.push(
	               {
	               	"label" : v.COMPL_TYPE_NAME,
	               	"value" : v.COMPL_TOTAL
	               }
            );
		}) ;
      	initRepairCirChart(datas,map);
	}
	
	//加载圆饼图
	function initRepairCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".nine-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var title = total+"条";
		var revenueChart = new FusionCharts({
        type: 'doughnut2d',
        renderAt: 'chart-container',
        width: width,
        height: '190',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "caption": "",
                "paletteColors": "#0075c2,#76EE00,#F4A460,#DC143C",
                "bgColor": "#ffffff",
                "showBorder": "0",
                "use3DLighting": "0",
                "showShadow": "0",
                "enableSmartLabels": "0",
                "startingAngle": "310",
                "showLabels": "0",
                "showPercentValues": "1",
                //"showLegend": "1",
                "legendShadow": "0",
                "legendBorderAlpha": "0",
                "defaultCenterLabel": title,
                "centerLabel": "$label$value条",
                "centerLabelBold": "1",
                "showTooltip": "0",
                "decimals": "0",
                "captionFontSize": "8",
                "subcaptionFontSize": "30",
                "subcaptionFontBold": "0" 
            },
            "data":datas
            
        }
    });
    revenueChart.render("repairCir");
	}
	
	 //本日投诉
    function initComplaintCir(){ 
    	var data = {};
     	data.AREA_CODE = area_code;
		$.ajax({
			url : url + '/cockpit/CockpitController/getCmptAnalysis.do',
			type : 'post',
			data :{'data':JSON.stringify(data)},
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].COMPL_TYPE_NAME;
						html += "<span id='"+ complaint[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						html += "</div>";
						
					}
					$("#complaint").append(html); 
					createComplaintCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createComplaintCir(data){
		var list = data.list;
		var map = data.map;
		var datas = [];
		$.each(list,function(i,v){
            datas.push(
	               {
	               	"label" : v.COMPL_TYPE_NAME,
	               	"value" : v.COMPL_TOTAL
	               }
            );
		}) ;
      	initComplaintCirChart(datas,map);
	}
	
	//加载圆饼图
	function initComplaintCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".ten-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var title = total+"条";
		var revenueChart = new FusionCharts({
        type: 'doughnut2d',
        renderAt: 'chart-container',
        width: width,
        height: '190',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "caption": "",
                "paletteColors": "#0075c2,#76EE00,#F4A460,#DC143C",
                "bgColor": "#ffffff",
                "showBorder": "0",
                "use3DLighting": "0",
                "showShadow": "0",
                "enableSmartLabels": "0",
                "startingAngle": "310",
                "showLabels": "0",
                "showPercentValues": "1",
                //"showLegend": "1",
                "legendShadow": "0",
                "legendBorderAlpha": "0",
                "defaultCenterLabel": title,
                "centerLabel": "$label$value条",
                "centerLabelBold": "1",
                "showTooltip": "0",
                "decimals": "0",
                "captionFontSize": "8",
                "subcaptionFontSize": "30",
                "subcaptionFontBold": "0" 
            },
            "data":datas
            
        }
    });
    revenueChart.render("complaintCir");
	}
	
	 //本日发帖
    function initPostCir(){
          var data = {};
     	  data.SIGN = "1";
      	  data.AREA_CODE = area_code;
      
		$.ajax({
			url : url + '/cockpit/CockpitController/getNoteAnalysis.do',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
				if(null != data && "" != data){
					
					var datas = data.list;
					
					var html="";
					for(i=0;i<datas.length;i++){
						html += "<div class='alock'>"+datas[i].NOTE_CAT_NAME;
						if("1"==datas[i].IS_ESTATE_FLAG){
							html += "<font color='red' size='2' style='vertical-align: middle;'>*&nbsp;</font><span id='"+ post[i] +"' style='font-size:5px;'>"+datas[i].COMPL_TOTAL+"</span>&nbsp;条 ";
						}else{
							html += "<span id='"+ post[i] +"' style='font-size:5px;'>"+datas[i].NOTE_TOTAL+"</span>&nbsp;条 ";
						}
						html += "</div>";
						
					}
					
					$("#post").append(html); 
					createPostCir(data);
				}
			}
		});
	}
	
	//创建饼图加载数据数组
	function createPostCir(data){
	    
		var list = data.list;
		var map = data.map;
		var datas = [];
		$.each(list,function(i,v){
            datas.push(
	               {
	               	"label" : v.NOTE_CAT_NAME,
	               	"value" : v.NOTE_TOTAL
	               }
            );
		}) ;
      	initPostCirChart(datas,map);
	}
	
	//加载圆饼图
	function initPostCirChart(datas,map) {
	    //获取当前父元素的宽度
		var width = $(".eleven-con").find(".cockpit-detail-con").width() * 0.65;
		//获取类别总数
		var total = map.TOTAL;
		var title = total+"条";
		var revenueChart = new FusionCharts({
	        type: 'doughnut2d',
	        renderAt: 'chart-container',
	        width: width,
	        height: '190',
	        dataFormat: 'json',
	        dataSource: {
	            "chart": {
	                "caption": "",
	                //"subCaption": "Last year",
	                //"numberPrefix": "$",
	                "paletteColors": "#0075c2,#76EE00,#F4A460,#DC143C,#FFFF00",
	                "bgColor": "#ffffff",
	                "showBorder": "0",
	                "use3DLighting": "0",
	                "showShadow": "0",
	                "enableSmartLabels": "0",
	                "startingAngle": "310",
	                "showLabels": "0",
	                "showPercentValues": "1",
	                //"showLegend": "1",
	                "legendShadow": "0",
	                "legendBorderAlpha": "0",
	                "defaultCenterLabel": title,
	                "centerLabel": "$label$value条",
	                "centerLabelBold": "1",
	                "showTooltip": "0",
	                "decimals": "0",
	                "captionFontSize": "8",
	                "subcaptionFontSize": "30",
	                "subcaptionFontBold": "0" 
	            },
	            "data":datas
	            
	        }
    	});
    	revenueChart.render("postCir");
	}
	
	//报修 发现 投诉折线图
	function initTodayRepair(date){
        var data = {};
     	data.SIGN = "1";
        data.END_DATE = date;
        data.AREA_CODE = area_code;
        
        data.NUMBER = "7";
		$.ajax({
			url : url + '/cockpit/CockpitController/getCmptRepNoteInfo.do?',
			data :{'data':JSON.stringify(data)},
			type : 'post',
			dataType : 'json',
			async : false,
			error : function(){},
			success : function(data){
			    
				if(null != data && "" != data){
				 
				 createTodayRepair(data);
				}
			}
		});
	
	}
	
	
	function createTodayRepair(data){
	    var chart = {};
	    //var ownerList = data.ownerList;
	    //var roomList = data.roomList;
	    var dataset = [];
	    var category =[];
        $.each(data,function(i,v){
             category.push({
                 "label" : v.DATE
             });
        }) ; 
			var tmpD = {};
            var tmpE = {};
            var tmpF = {};
			var dd = []; var ee = []; var ff = [];
		     tmpD.seriesname = "报修";
			 tmpE.seriesname = "投诉";
			 tmpF.seriesname = "发帖";
			 tmpE.color = '#fff01f';
			 tmpD.color = '#00bcc6';
			 tmpF.color = '#fe3772';
			  $.each(data,function(i,v){
			          	dd.push({
				          "value" : v.REP_TOTAL
			           });
              }) ; 
              $.each(data,function(i,v){
			          	ee.push({
				           "value" : v.CMP_TOTAL
			            });
              }) ;
              $.each(data,function(i,v){
			          	ff.push({
				           "value" : v.NOTE_TOTAL
			            });
              }) ;
                        
			tmpD.data = dd;
            tmpE.data = ee;
            tmpF.data = ff;
			dataset.push(tmpD);
			dataset.push(tmpE);
			dataset.push(tmpF);
		chart.category = category;
		chart.dataset = dataset; 

      	initTodayRepairChart(chart);
	}
	
	function initTodayRepairChart(chart){
		//获取当前父元素的宽度
		var width = $(".twelve-con").find(".cockpit-detail-con").width();
	    var salesChart = new FusionCharts({    //  msline
	        type: 'scrollline2d',
	        renderAt: 'chart-container',
	        id: 'myChart',
	        width: width,
	        height: '400',
	        dataFormat: 'json',
	        dataSource: {
	            "chart": {
	                "caption": "报修投诉发帖",
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
	     salesChart.render("repairComplaintPost");
	}
	
	//双击展示大图
	function showCockpit(flag,title){
		 
		var sendDialog = parent.ns.modalDialog({
			title : title,
			width :600,
			height : 500,
			resizable : true,
			url :url+'/cockpit/CockpitController/showCockpit.do?flag='+ flag +'&title='+encodeURI(encodeURI(title))+'&area_code='+area_code
		});
	}
	
	/* =======计算日期========= */
	function addDate(date,days){ 
	   var now = date.split('-');
	   now = new Date(Number(now['0']),(Number(now['1'])-1),Number(now['2']));  
	   now.setDate(now.getDate() + days);
       var m=now.getMonth()+1; 
       return now.getFullYear()+'-'+m+'-'+now.getDate(); 
       
    } 
