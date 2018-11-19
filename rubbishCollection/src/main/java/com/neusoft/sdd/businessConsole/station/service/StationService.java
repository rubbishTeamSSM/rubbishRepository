package com.neusoft.sdd.businessConsole.station.service;

import java.util.Map;

import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;

public interface StationService {

	Grid<Map<String,String>> getStations(Map<String,String> param);

	JsonResult insertStation(Map<String,Object> param);

    JsonResult validStation(Map<String,Object> param);

    Map<String, Object> stationDetails(Map<String, Object> param);

    JsonResult modifyStation(Map<String,Object> param);
    
    JsonResult validService(Map<String,Object> param);

    JsonResult deleteStation(Map<String,Object> param);
}
