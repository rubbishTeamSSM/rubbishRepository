package com.neusoft.sdd.businessConsole.department.service;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.department.model.Department;

public interface DepartmentService {

	List<Department> getSystemDepartment(String pid);

	void addDepartment(Department dep);

	Tree getDepartmentTree(String departmentRoot);

	List<Map<String,String>> getUserDepartment(String USER_CODE);
	
	Department getDepartmentDet(String DEPT_CODE);

	void modifyDepartment(Department dep);

	JsonResult validDepartment(String DEPT_CODE);
	
	void delDepartment(Department dept);

}
