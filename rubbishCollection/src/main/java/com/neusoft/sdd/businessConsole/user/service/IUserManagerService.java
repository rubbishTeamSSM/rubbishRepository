package com.neusoft.sdd.businessConsole.user.service;

import java.sql.Timestamp;
import java.util.Map;

import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.user.model.User;

public interface IUserManagerService {


	Grid<User> getUsers(User userPage);

	void addNewUser(Map<String,Object> param) throws Exception;

	String[] getUserAdditionMenu(String USER_CODE);
	
	void changeUserAdditionMenu(String USER_CODE, String[] MENU_CODE,String CREATED_BY,Timestamp start,Timestamp end);

	User getUserByUserCode(String USER_CODE);

	void updateUser(User user);

	void modifyUserPwd(String USER_CODE, String pwd, String UPDATED_BY);

	//void deleteUser(String USER_CODES, String UPDATED_BY);

	JsonResult changeUserDepartment(Map<String,Object> param);

	Map<String, String> validUser(String USER_ACCNT);

    String getUserGws(String USER_CODE);

    void updateUserStations(String USER_CODE, String[] gwds, String CREATED_BY);
    //修改/重置密码
    void setPassword(Map<String,Object> param) throws Exception;
    
    void deleteAccount(String USER_CODES,String UUIDS,String UPDATED_BY);
    
    Map<String,Object> getAccountMessage(Map<String,Object> param);
    
    void updateAccount(Map<String,Object> param);
    
    Grid<Map<String,String>> getAccount(Map<String,String> param);
    
    Grid<Map<String, String>> getArea(Map<String, Object> param);
    
    Tree getStationTree(String stationRoot,String AREA_CODE);
    
    String[] getUserstation(String USER_CODE,String AREA_CODE);
    
    void updateUserStation(String USER_CODE, String POST_CODE, String YHMC,String AREA_CODE);
    
    String getStationRoot(String AREA_CODE);
    
    int getAreaDepart(String USER_CODE);

	Grid<Map<String, String>> getAllProject(Map<String, String> param);

	JsonResult updateUserProject(Map<String, Object> param);
}
