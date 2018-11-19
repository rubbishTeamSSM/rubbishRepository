package com.neusoft.sdd.businessConsole.department.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.department.model.Department;

public interface DepartmentDAO {

	List<Department> getSystemDepartment(String pid);

	void addDepartment(Department dep);

	int departmentHasChildren(String depId);

	List<Department> getAllDepart();

	void deleteUserSDep(String[] USER_CODEs);

	List<Map<String,String>> getUserDepartment(String USER_CODE);

	void addUserDepart(Map<String,Object> params);

	void addUserDeparts(Map<String,Object> params);

	Department getDepartmentDet(String DEPT_CODE);

	void modifyDepartment(Department dep);

	int isContainDEPT_CODE(String DEPT_CODE);

    void deleBmYh(String[] depIds);

    void deleBm(Department dep);

    void deleDepGw(String[] depIds);

    void deleGwYh(List<String> depIds);
    
    List<String> getBmGw(String[] depIds);

	String getAUTH_CODE(Department dep);
	
	List<Map<String,String>> getUserAreaByDept(String deptCode);
	
	void deleteUserPostByData(Map<String,Object> param);
	
	void deleteUserAreaByData(Map<String,Object> param);
}
