package com.neusoft.sdd.businessConsole.station.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.station.model.Station;

public interface StationDAO {

	/*List<Station> getStations(Station pageStation);*/

	/*void insertStation(Station station);*/

	

    /*int validStation(String POST_CODE);*/

   /* Station stationDetails(String POST_CODE);

    void modifyStation(Station station);*/

    void deleGwYh(String[] POST_CODEs);

    void deleGw(String[] POST_CODEs);

    List<Station> getBmStations(String DEPT_CODE);

    String[] getUserGws(String USER_CODE);

    void addUserStations(Map<String,Object> params);
    
    
    List<Map<String,String>> getStations(Map<String,String> param);

	void insertStation(Map<String,Object> param);
	
	void deleteUsersSta(String[] USER_CODEs);

	//void deleteUsersSta(String[] USER_CODEs);

    int validStation(Map<String,Object> param);
    
    Map<String, Object> stationDetails(Map<String, Object> param);

    void modifyStation(Map<String, Object> param);
    
    int validService(Map<String,Object> param);

    //List<Station> getBmStations(String DEPT_CODE);

    //String[] getUserGws(String USER_CODE);

    //void addUserStations(Map<String,Object> params);
    
    void deleteStation(Map<String,Object> param);
    
    void deleteUserStation(Map<String,Object> param);
    
    void deleteAreaStation(Map<String,Object> param);
    
    int getAllUserPostCount(Map<String,Object> param);
}
