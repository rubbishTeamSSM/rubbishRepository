package com.neusoft.sdd.businessConsole.department.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.department.dao.DepartmentDAO;
import com.neusoft.sdd.businessConsole.department.model.Department;
import com.neusoft.sdd.businessConsole.department.service.DepartmentService;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

//@Transactional
@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {

	@Autowired
	private DepartmentDAO departmentDao;

	/**
	 * 查询部门列表
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public List<Department> getSystemDepartment(String pid) {
		List<Department> depAll = departmentDao. getSystemDepartment(pid);
		//是否有子部门
		for (Department dep:depAll) {
			dep.setState(isHasChileDep(dep.getDEPT_CODE()));
		}
		return depAll;
	}

	/**
	 * 新增部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public void addDepartment(Department dep) {
		dep.setUUID(UUIDUtil.uuidStr());
		dep.setDEL_FLAG(GlobalConstant.ZF_BJ_N);
		
		//如果是根节点
		if(null == dep.getPARENT_DEPT_CODE()
		|| "".equals(dep.getPARENT_DEPT_CODE())){
			dep.setPARENT_DEPT_CODE("0");
			dep.setAUTH_CODE(dep.getDEPT_CODE());
		}else{
			dep.setAUTH_CODE(departmentDao.getAUTH_CODE(dep));
		}
		
		
		departmentDao.addDepartment(dep);
	}
	
	/**
	 * 是否有子部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	private String isHasChileDep(String depId){
		int count = departmentDao.departmentHasChildren(depId);
		if(count > 0){
			return "closed";
		}else{
			return "open";
		}
	}

	/**
	 * 部门tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public Tree getDepartmentTree(String departmentRoot) {
		 //获取所有菜单
        List<Department> allDeparts = departmentDao.getAllDepart();
        Tree rootTree = new Tree();
        initTree(rootTree,allDeparts,departmentRoot);
        return rootTree;
	}
	
	

	/**
	 * 递归所有部门生成部门tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	private void initTree(Tree rootTree, List<Department> allDeparts,
			String selfCode) {
		Iterator<Department> menuIterator = allDeparts.iterator();
		while (menuIterator.hasNext()) {
			Department tmp = menuIterator.next();
			// 初始化自身节点
			if (tmp.getDEPT_CODE().equals(selfCode)) {
				departToTree(tmp, rootTree,"DEPT");
				//初始化小区节点 ADD BY FXF 2018.01.02
				List<Map<String,String>> areaList = departmentDao.getUserAreaByDept(tmp.getDEPT_CODE());
				initAreaTree(rootTree,areaList);
			} else if (tmp.getPARENT_DEPT_CODE().equals(selfCode)) {
				// 初始化子节点
				Tree children = new Tree();
				departToTree(tmp, children,"DEPT");
				rootTree.getChildren().add(children);

				// 递归
				initTree(children, allDeparts, tmp.getDEPT_CODE());
			}
		}
	}
	
	/**
	 * 递归所有部门生成部门tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：封新芳
	  * 作成时间：2018-01-02
	 */
	private void initAreaTree(Tree rootTree, List<Map<String,String>> areaList) {
		Iterator<Map<String,String>> areaIterator = areaList.iterator();
		List<Tree> list = new ArrayList<Tree>();
		while (areaIterator.hasNext()) {
			Map<String,String> tmp = areaIterator.next();
			Tree areaTree = new Tree();
			areaTree.setId(tmp.get("AREA_CODE"));
			areaTree.setPid(tmp.get("DEPT_CODE"));
			areaTree.setIconCls("");
			areaTree.setText(tmp.get("AREA_NAME"));
			areaTree.setState("open");
			Tree attribute = new Tree();
	        attribute.setType("AREA");
	        areaTree.setAttributes(attribute);
	        list.add(areaTree);
		}
		rootTree.setChildren(list);
	}

	/**
	 * 部门对象转成tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	private void departToTree(Department dep, Tree tree,String type) {
		tree.setId(dep.getDEPT_CODE());
		tree.setPid(dep.getPARENT_DEPT_CODE());
		tree.setIconCls("");
		tree.setText(dep.getDEPT_NAME());
		tree.setState("open");
		
		DeptAttribute attribute = new DeptAttribute();
        attribute.setAUTH_CODE(dep.getAUTH_CODE());
        attribute.setType(type);
        tree.setAttributes(attribute);
	}

	/**
	 * 用户所有部门编号
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public List<Map<String,String>> getUserDepartment(String USER_CODE) {
		return departmentDao.getUserDepartment(USER_CODE);
	}

	/**
	 * 部门详情
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public Department getDepartmentDet(String DEPT_CODE) {
		return departmentDao.getDepartmentDet(DEPT_CODE);
	}

	/**
	 * 修改部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public void modifyDepartment(Department dep) {
		departmentDao.modifyDepartment(dep);
	}

	/**
	 * 校验部门是否存在
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public JsonResult validDepartment(String DEPT_CODE) {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		
		String msg = null;
		int DEPT_CODENum = departmentDao.isContainDEPT_CODE(DEPT_CODE);
		
		
		if(DEPT_CODENum > 0){
			result.setSuccess(false);
			msg = "部门代码已存在";
		}
		result.setMsg(msg);
		
		return result;
	}

	/**
	 * 删除部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public void delDepartment(Department dep) {
		String depIds = dep.getDEPT_CODE();
		if(null != depIds)
		{
			//删除用户关联的部门(t_xt_yh_bm)
			departmentDao.deleBmYh(depIds.split(","));
			
			//删除部门 (t_xt_bm)
			dep.setDEPT_CODES(depIds.split(","));
			departmentDao.deleBm(dep);
			
			//删除部门管理的岗位(t_xt_gw)
			departmentDao.deleDepGw(depIds.split(","));
			
			//删除管理管理的用户(t_xt_yh_gw)
			this.deleGwYh(depIds);
		}
		
	}
	
    private void deleGwYh(String depIds) {
        List<String> getBmGw = departmentDao.getBmGw(depIds.split(","));
        if (getBmGw.size() > 0){
            departmentDao.deleGwYh(getBmGw);
        }

  }
}
