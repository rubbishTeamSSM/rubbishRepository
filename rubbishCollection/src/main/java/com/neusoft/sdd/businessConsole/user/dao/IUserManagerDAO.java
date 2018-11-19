package com.neusoft.sdd.businessConsole.user.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.station.model.Station;
import com.neusoft.sdd.businessConsole.user.model.User;

public interface IUserManagerDAO {

	User getLoginUser(User user);

	List<User> getUsers(User userPage);

	int countUsers(User userPage);

	void addNewUser(Map<String,Object> param);

	String[] getUserAdditionMenu(String userId);

	void deleteUserAdditionMenu(String userId);

	void addUserAdditionMenu(Map<String, Object> params);

	User getUserByUSER_CODE(String USER_CODE);

	void updateUser(User user);

	void modifyUserPwd(Map<String, String> params);

	void deleteUsers(Map<String, Object> params);

	User isContainUSER_CODE(String USER_ACCNT);

	int isContainUSER_NAME(String USER_NAME);
	
	void setPassword(Map<String,Object> param);
	
	//void deleteAccount(String[] USER_CODEs);

	Map<String,Object> getAccountMessage(Map<String,Object> param);
	
	void updateAccount(Map<String,Object> param);
	
	List<Map<String,String>> getAccount(Map<String,String> param);
	
	int countAccount(Map<String,String> param);
	
	Map<String,Object> getMaximumSortNo();
	
	Map<String,Object> getMaximumSortNoInDept();
	
	List<Map<String,String>> getArea(Map<String, Object> param);
	
	List<Station> getAllStationByArea(String AREA_CODE);
	
	 String[] getUserstation(Map<String,Object> params);
	 
	void deleteUsersStation(Map<String,Object> params);
	
	void addUserStations(Map<String,Object> params);
	
	void updateUserStation(Map<String,Object> params);
	
	String getStationRoot(String AREA_CODE);
	
	 int getAreaDepart(String USER_CODE);

	List<Map<String, String>> getAllProject(Map<String, String> param);
	
	void deleteUserProject(Map<String, Object> param);

	void addUserProject(Map<String, Object> param);
}
