	//加载国家信息provinceId选中的省,cityId选中的市,areaId选中的区域
	function loadCountryList(countryId,provinceId,cityId,areaId){
		var loadFlag = 0;
	    var datas = {};
	    datas.code = 'COUNTRY_ID';
	    datas.name = 'COUNTRY_NAME';
	    datas.table_name = 'MI_BB_COUNTRY';
/*	    datas.where_key = 'CNODE_TYPE';
	    datas.where_value = '10380010';
	    datas.where_key1 = 'PNODE_ID';
	    datas.where_value1 = '-1';*/
	    if(countryId){
	    	datas.where_key2 = 'COUNTRY_ID';
		    datas.where_value2 = countryId;
	    }
	    $('#COUNTRY_ID').combogrid({
		    panelWidth:650,
		    panelHeight:320,
		    fitColumns:true,
		    pagination:true,
		    //editable:false,
	    	pageSize:10,
	    	pageList:[10,20,30,40,50],
		    queryParams: {'data' : JSON.stringify(datas)},
		    idField:'CODE',
		    textField:'NAME',
		    url : url+'/comboboxMgr/getCombogridList.do',  
		    columns:[[
		        {field:'CODE',title:'编码',width:100},
		        {field:'NAME',title:'名称',width:100}
		    ]], 
		    onChange :function(newV,oldV){
		    	if(newV == '') {
		    		countryId = '';
		    		provinceId = '';
		    		cityId = '';
		    		areaId = '';
		    	}
		    	var grids = $('#COUNTRY_ID').combogrid("grid").datagrid("getSelected");
		    	if(grids && grids.CODE != newV){
		    		$('#COUNTRY_ID').combogrid("setValue","");
		      		$('#COUNTRY_ID').combogrid("clear");
		    	}
		    	$('#PROVINCE_ID').combogrid("clear");
		    	$('#CITY_ID').combogrid("clear");
		    	$('#COUNTY_ID').combogrid("clear");
		    	loadProvinceList(newV);
		    	loadCityList('');
		    	loadAreaList('');
		    },onSelect:function(index, row){
				//加载省信息
            	loadProvinceList(row.CODE,provinceId,cityId,areaId);
			},onLoadSuccess:function(){
				if(0 == loadFlag){
					$('#COUNTRY_ID').combogrid("setValue", countryId);
				}
            },keyHandler: {  
                query: function(q) {  
                	if(countryId){
                    	datas.where_key2 = '';
                    }
                    datas.where_sql="("+datas.name+" LIKE '%"+q+"%' OR "+datas.code+" LIKE '%"+q+"%')";
                    $('#COUNTRY_ID').combogrid("grid").datagrid("reload", {'data' : JSON.stringify(datas)});  
                    $('#COUNTRY_ID').combogrid("setValue", q);
                    ++loadFlag;
                }  
            }
		});
	}

	//加载省信息countryCode:选中的国家值；provinceId修改页面选中的省,cityId修改页面选中的城市,areaId区域选中的值
	function loadProvinceList(countryCode,provinceId,cityId,areaId){
		var loadFlag = 0;
	    var datas = {};
	    datas.code = 'CNODE_ID';
	    datas.name = 'CNODE_NAME';
	    datas.table_name = 'MI_BB_CASCADE_NODE';
	    datas.where_key = 'CASCADE_ID';
	    datas.where_value = '392636A84D21A468E050A8C043FA66EA';
	    datas.where_key1 = 'PNODE_ID';
	    datas.where_value1 = countryCode;
	    if(provinceId){
	    	datas.where_key2 = 'CNODE_ID';
		    datas.where_value2 = provinceId;
	    }
	    $('#PROVINCE_ID').combogrid({
		    panelWidth:635,
		    panelHeight:320,
		    fitColumns:true,
		    pagination:true,
	    	pageSize:10,
	    	pageList:[10,20,30,40,50],
		    queryParams: {'data' : JSON.stringify(datas)},
		    idField:'CODE',
		    textField:'NAME',
		    url : url+'/comboboxMgr/getCombogridList.do',  
		    columns:[[
		        {field:'CODE',title:'编码',width:100},
		        {field:'NAME',title:'名称',width:100}
		    ]], 
		    onChange :function(newV,oldV){
		    	if(newV == '') {
		    		provinceId = '';
		    		cityId = '';
		    		areaId = '';
		    	}
		    	var grids = $('#PROVINCE_ID').combogrid("grid").datagrid("getSelected");
		    	if(grids && grids.CODE != newV){
		    		$('#PROVINCE_ID').combogrid("setValue","");
		    		$('#PROVINCE_ID').combogrid("clear");
		    	}
		    	$('#CITY_ID').combogrid("clear");
		    	$('#COUNTY_ID').combogrid("clear");
		    	loadCityList(newV);
		    	loadAreaList('');
		    },onSelect:function(index, row){
				//加载市信息
				loadCityList(row.CODE,cityId,areaId);
			},onLoadSuccess:function(){
				if(0 == loadFlag){
					$('#PROVINCE_ID').combogrid("setValue", provinceId);  
				}	
            	 
            },keyHandler: {  
                query: function(q) {
                	//动态搜索  
                    if(provinceId){
                    	datas.where_key2 = '';
                    }
                    datas.where_sql="("+datas.name+" LIKE '%"+q+"%' OR "+datas.code+" LIKE '%"+q+"%')";
                    $('#PROVINCE_ID').combogrid("grid").datagrid("reload", {'data' : JSON.stringify(datas)});  
                    $('#PROVINCE_ID').combogrid("setValue", q);  
                    ++loadFlag;
                }  
            }
		});
	}
	
	//加载城市列表provinceCode:选中的省份值；cityId修改页面选中的城市,areaId区域选中的值
	function loadCityList(provinceCode,cityId,areaId){
		var loadFlag = 0;
		var datas = {};
	    datas.code = 'CNODE_ID';
	    datas.name = 'CNODE_NAME';
	    datas.table_name = 'MI_BB_CASCADE_NODE';
	    datas.where_key = 'CASCADE_ID';
	    datas.where_value = '392636A84D21A468E050A8C043FA66EA';
	    datas.where_key1 = 'PNODE_ID';
	    datas.where_value1 = provinceCode;
	    if(cityId){
	    	datas.where_key2 = 'CNODE_ID';
		    datas.where_value2 = cityId;
	    }
	    
	    $('#CITY_ID').combogrid({
		    panelWidth:635,
		    panelHeight:320,
		    fitColumns:true,
		    pagination:true,
	    	pageSize:10,
	    	pageList:[10,20,30,40,50],
		    queryParams: {'data' : JSON.stringify(datas)},
		    idField:'CODE',
		    textField:'NAME',
		    url : url+'/comboboxMgr/getCombogridList.do',  
		    columns:[[
		        {field:'CODE',title:'编码',width:100},
		        {field:'NAME',title:'名称',width:100}
		    ]], 
		    onChange :function(newV,oldV){
		    	if(newV == '') {
		    		cityId = '';
		    		areaId = '';
		    	}
		    	var grids = $('#CITY_ID').combogrid("grid").datagrid("getSelected");
		    	if(grids && grids.CODE != newV){
		    		$('#CITY_ID').combogrid("setValue","");
		    		$('#CITY_ID').combogrid("clear");
		    	}
		    	$('#COUNTY_ID').combogrid("clear");
		    	loadAreaList(newV);
		    },onSelect:function(index, row){
            	//加载区县信息
				loadAreaList(row.CODE,areaId);
			},onLoadSuccess:function(){
				if(0 == loadFlag){
					$('#CITY_ID').combogrid("setValue", cityId);
				}
            },keyHandler: {  
		    	query: function(q) {
		    		//动态搜索  
                    if(cityId){
                    	datas.where_key2 = '';
                    }
                    datas.where_sql="("+datas.name+" LIKE '%"+q+"%' OR "+datas.code+" LIKE '%"+q+"%')";
                    $('#CITY_ID').combogrid("grid").datagrid("reload", {'data' : JSON.stringify(datas)});  
                    $('#CITY_ID').combogrid("setValue", q);
                    ++loadFlag;
                }  
            } 
		});
	}
	
	//加载区县cityCode:选中的城市值；areaId修改页面选中的区域
	function loadAreaList(cityCode,areaId){
		var loadFlag = 0;
		var datas = {};
	    datas.code = 'CNODE_ID';
	    datas.name = 'CNODE_NAME';
	    datas.table_name = 'MI_BB_CASCADE_NODE';
	    datas.where_key = 'CASCADE_ID';
	    datas.where_value = '392636A84D21A468E050A8C043FA66EA';
	    datas.where_key1 = 'PNODE_ID';
	    datas.where_value1 = cityCode;
	    if(areaId){
	    	datas.where_key2 = 'CNODE_ID';
		    datas.where_value2 = areaId;
	    }
	    
	    $('#COUNTY_ID').combogrid({
		    panelWidth:635,
		    panelHeight:320,
		    fitColumns:true,
		    pagination:true,
	    	pageSize:10,
	    	pageList:[10,20,30,40,50],
		    queryParams: {'data' : JSON.stringify(datas)},
		    idField:'CODE',
		    textField:'NAME',
		    url : url+'/comboboxMgr/getCombogridList.do',  
		    columns:[[
		        {field:'CODE',title:'编码',width:100},
		        {field:'NAME',title:'名称',width:100}
		    ]], 
		    onChange :function(newV,oldV){
		    	var grids = $('#COUNTY_ID').combogrid("grid").datagrid("getSelected");
		    	if(grids && grids.CODE != newV){
		    		$('#COUNTY_ID').combogrid("setValue","");
		    		$('#COUNTY_ID').combogrid("clear");
		    	}
		    },onLoadSuccess:function(){
		    	if(0 == loadFlag){
		    		$('#COUNTY_ID').combogrid("setValue", areaId);
		    	}
            },keyHandler: {  
		    	query: function(q) {
		    		//动态搜索  
                    if(areaId){
                    	datas.where_key2 = '';
                    }
                    datas.where_sql="("+datas.name+" LIKE '%"+q+"%' OR "+datas.code+" LIKE '%"+q+"%')";
                    $('#COUNTY_ID').combogrid("grid").datagrid("reload", {'data' : JSON.stringify(datas)});  
                    $('#COUNTY_ID').combogrid("setValue", q);
                    ++loadFlag;
                }  
            }
		});
	}