package com.neusoft.sdd.businessConsole.user.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.department.dao.DepartmentDAO;
import com.neusoft.sdd.businessConsole.ftp.service.IFtpService;
import com.neusoft.sdd.businessConsole.menu.dao.MenuDAO;
import com.neusoft.sdd.businessConsole.role.dao.IRoleDao;
import com.neusoft.sdd.businessConsole.station.dao.StationDAO;
import com.neusoft.sdd.businessConsole.station.model.Station;
import com.neusoft.sdd.businessConsole.user.dao.IUserManagerDAO;
import com.neusoft.sdd.businessConsole.user.model.User;
import com.neusoft.sdd.businessConsole.user.service.IUserManagerService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;
import com.neusoft.sdd.util.commonUtil.SHACoder;
import com.neusoft.sdd.util.commonUtil.StringUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

//@Transactional
@Service
public class UserManagerServiceImpl extends BaseService implements IUserManagerService {
	
	@Autowired
	private IUserManagerDAO userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private DepartmentDAO departmentDao;
	
	@Autowired
	private StationDAO stationDao;
	
	@Autowired
	private MenuDAO menuDao;

	@Autowired
	private IFtpService ftpService;

	/**
	 * 获取用户列表
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public Grid<User> getUsers(User userPage) {
	    
		Grid<User> grid = new Grid<User>();
		PageHelper.startPage(userPage.getPage(), userPage.getRows());
		List<User> list = userDao.getUsers(userPage);

		grid.setRows(list);
		grid.setTotal((int)new PageInfo<User>(list).getTotal());
		return grid;
	}

	/**
	 * 新增用户
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-1
	 * @throws Exception 
	 */
	@Override
	public void addNewUser(Map<String,Object> param) throws Exception {
	    //uuid
		//自动生成用户代码
		String USER_CODE = StringUtil.getCommonCode(2);//生成城市编码的方法
	    param.put("USER_CODE", USER_CODE);
		//生成UUID
		String uuid = UUIDUtil.uuidStr();
		param.put("UUID", uuid);
		int newSortNum;//排序号
		//如果排序号为空，则查出库中数据中序号最大的			
		Map<String,Object> sortNumResult = userDao.getMaximumSortNo();
		if(null==sortNumResult){
			newSortNum = 1;//如果库中没有数据，则给当前插入的城市信息的排序号赋值为1
		}else{
			int sortNo = Integer.parseInt(sortNumResult.get("SORT_NO").toString());
			newSortNum = sortNo+1;//最大的序号加1
		}			
	   param.put("SORT_NO", newSortNum);
	   //密码加密
	   if(param.containsKey("USER_PWD"))
	   {
		   param.put("USER_PWD", SHACoder.encodeSHA256(param.get("USER_PWD").toString()));
	   }else
	   {
		   param.put("USER_PWD", SHACoder.encodeSHA256("123456"));
	   }
	   if(null!=String.valueOf(param.get("FILETEXT"))&&!"".equals(param.get("FILETEXT")))
	       ftpService.UpdateZfbjAndSjuuid(String.valueOf(param.get("FILETEXT")), "0",uuid);
	   //新增
	   userDao.addNewUser(param);
	  
	  //this.insertUserDeprat(String.valueOf(param.get("UUID")),String.valueOf(param.get("DEPT_CODE")), String.valueOf(param.get("YHMC")));
	  /* //用户部门新增
	   if(param.com)
	   String[] DEPT_CODES=String.valueOf(param.get("DEPT_CODE")).split(",");
	   //新增部门
	   this.addUserDeparts(USER_CODE, DEPT_CODES, String.valueOf(param.get("YHMC")));*/
	   
	   //this.insertUserDeprat(param);
       
	}
	
	/**
	 * 新增用户默认角色
	 */
	/*private void insertUserDefaultRole(String USER_CODE, String ROLE_CODE,String CREATED_BY) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("USER_CODE", USER_CODE);
		params.put("ROLE_CODE", ROLE_CODE);
		params.put("CREATED_BY", CREATED_BY);
		params.put("UUID", UUIDUtil.uuidStr());
		params.put("DEL_FLAG", GlobalConstant.ZF_BJ_N);
		//roleDao.insertUserDefaultRole(params);
	}*/

	/**
	 * 新增用户部门
	 */
	/*private void insertUserDeprat(Map<String,Object> param) {
		param.put("UUID", UUIDUtil.uuidStr());
		param.put("DEL_FLAG", 0);
		int newSortNum;//排序号
		//如果排序号为空，则查出库中数据中序号最大的			
		Map<String,Object> sortNumResult = userDao.getMaximumSortNo();
		if(null==sortNumResult){
			newSortNum = 1;//如果库中没有数据，则给当前插入的城市信息的排序号赋值为1
		}else{
			int sortNo = Integer.parseInt(sortNumResult.get("SORT_NO").toString());
			newSortNum = sortNo+1;//最大的序号加1
		}			
	     param.put("SORT_NO", newSortNum);
	     param.put("CREATED_BY", param.get("YHMC"));
		departmentDao.addUserDepart(param);
	}*/

	/**
	 * 获取用户额外菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2015-10-18
	 */
	@Override
	public String[] getUserAdditionMenu(String USER_CODE) {
		return userDao.getUserAdditionMenu(USER_CODE);
	}

	/**
	  * 修改用户额外菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2015-10-18
	 */
	@Override
	public void changeUserAdditionMenu(String USER_CODE, String[] MENU_CODE,String CREATED_BY,Timestamp start,Timestamp end) {
		userDao.deleteUserAdditionMenu(USER_CODE);
		this.addUserAdditionMenu(USER_CODE,MENU_CODE,CREATED_BY,start,end);
	}
	
	private void addUserAdditionMenu(String USER_CODE, String[] MENU_CODE,
			String CREATED_BY, Timestamp start, Timestamp end) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USER_CODE", USER_CODE);
		params.put("MENU_CODE", MENU_CODE);
		params.put("CREATED_BY", CREATED_BY);
		params.put("start", start);
		params.put("end", end);
		userDao.addUserAdditionMenu(params);
	
	}

	/**
	 * 查询用户
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public User getUserByUserCode(String USER_CODE) {
		return userDao.getUserByUSER_CODE(USER_CODE);
	}

	/**
	 * 修改用户
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	/**
	 * 修改用户密码
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public void modifyUserPwd(String USER_CODE, String pwd, String UPDATED_BY) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("USER_CODE", USER_CODE);
		params.put("PWD", pwd);
		params.put("UPDATED_BY", UPDATED_BY);
		userDao.modifyUserPwd(params);
	}
	/**
	 * 修改用户部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public JsonResult changeUserDepartment(Map<String,Object> param) {
		JsonResult result = new JsonResult();
		//删除现有部门
		departmentDao.deleteUserSDep(String.valueOf(param.get("USER_CODE")).split(","));
		//删除用户岗位不在选中小区中的数据
		departmentDao.deleteUserPostByData(param);
		//删除小区用户中间表
		departmentDao.deleteUserAreaByData(param);
		//新增部门
		List<Map<String, String>> deptList = null;
		if(!StringUtil.isNullOrEmpty(String.valueOf(param.get("deptArrs")))){
			deptList = new Gson().fromJson(String.valueOf(param.get("deptArrs")), new TypeToken<List<Map<String, String>>>() {}.getType());
		}
		if(null != deptList && 0 < deptList.size()){
			param.put("deptList", deptList);		
			departmentDao.addUserDeparts(param);
		}
		result.setSuccess(true);
		result.setMsg("操作成功！");
		return result;
	}
	
	
	/**
	 * 校验用户是否存在
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@Override
	public Map<String, String> validUser(String USER_ACCNT) {
		
		User user = userDao.isContainUSER_CODE(USER_ACCNT);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result_code", "0");//返回码
		
		// 用户代码已存在
		if(null != user){
			if(null != user.getDEL_FLAG() && !"".equals(user.getDEL_FLAG())){
				// 用户代码为未作废
				if("0".equals(user.getDEL_FLAG())) {
					result.put("result_code", "1");//返回码
					result.put("result_msg", "用户账号已存在！");//返回信息
				} else if("1".equals(user.getDEL_FLAG())) {// 用户代码已作废
					result.put("result_code", "2");//返回码
					result.put("result_msg", "用户账号已存在，是否覆盖？");//信息
					result.put("USER_CODE", user.getUSER_CODE());
				}
			}
		}
		
		return result;
	}

    /**
     * 获取用户岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-28
     */
    @Override
    public String getUserGws(String USER_CODE) {
        String[] userGws = stationDao.getUserGws(USER_CODE);
        
        return GsonUtil.toJson(userGws);
    }

    /**
     * 修改用户岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-28
     */
    @Override
    public void updateUserStations(String USER_CODE, String[] gwds, String CREATED_BY) {
        //删除现有岗位
        stationDao.deleteUsersSta(USER_CODE.split(","));
        //新增岗位
        this.addUserStations(USER_CODE,gwds,CREATED_BY);
    }
    
    private void addUserStations(String USER_CODE, String[] gwds, String CREATED_BY) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("USER_CODE",USER_CODE);
        params.put("gwds", gwds);
        params.put("CREATED_BY",CREATED_BY);
        stationDao.addUserStations(params);
    }
    
    
    /**
	 * 部门：软件开发事业部
	 * 功能：修改/重置密码
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-11
	 */
	@Override
	public void setPassword(Map<String, Object> param) throws Exception{
		if("0".equals(param.get("flag"))){
			param.put("PASSWORD", SHACoder.encodeSHA256(param.get("PASSWORD").toString()));
		}else{
			param.put("PASSWORD", SHACoder.encodeSHA256("123456"));
		}
		userDao.setPassword(param);
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：删除账号
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@Override
	public void deleteAccount(String USER_CODES,String UUIDS,String UPDATED_BY) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("UPDATED_BY", UPDATED_BY);
		params.put("USER_CODES", USER_CODES.split(","));
		//删除用户
		userDao.deleteUsers(params);
		//删除用户角色
		roleDao.deleteUserSRoles(USER_CODES.split(","));
		//删除用户部门
		departmentDao.deleteUserSDep(USER_CODES.split(","));
		//删除用户岗位
		stationDao.deleteUsersSta(USER_CODES.split(","));
		//删除用户额外菜单
		menuDao.deleteUsersAdditionMenu(USER_CODES.split(","));
		/*//图片作废
		for(int i=0;i<UUIDS.split(",").length;i++)
		{
			List<Xtjzmx> oldjzmx = ftpService.getXtjzmxBySjuuid(String.valueOf(UUIDS.split(",")[i]));
			for (int j = 0; j < oldjzmx.size(); j++) {
			ftpService.updateWjzfbj(String.valueOf(UUIDS.split(",")[i]),oldjzmx.get(i).getWj_dm(), "1");
			}	
		}*/
		
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取账号修改信息
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@Override
	public Map<String,Object> getAccountMessage(Map<String, Object> param) {
		Map<String,Object> accountMessage = new HashMap<String,Object>();
		accountMessage.put("accountMessage", userDao.getAccountMessage(param));
		return accountMessage;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：修改账号
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@Override
	public void updateAccount(Map<String, Object> param) {
		/*if(null!=String.valueOf(param.get("FILETEXT"))&&!"".equals(param.get("FILETEXT")))
		{
			List<Xtjzmx> oldjzmx = ftpService.getXtjzmxBySjuuid(String.valueOf(param.get("UUID")));
			for (int i = 0; i < oldjzmx.size(); i++) {
				ftpService.updateWjzfbj(String.valueOf(param.get("UUID")),oldjzmx.get(i).getWj_dm(), "1");
			}
			//更新标题图片的作废标记
			ftpService.UpdateZfbjAndSjuuid(String.valueOf(param.get("FILETEXT")),"0" ,String.valueOf(param.get("UUID")));
		}*/
		

		userDao.updateAccount(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取账号列表
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@Override
	public Grid<Map<String, String>> getAccount(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
		String page = param.get("page").toString();
		String rows = param.get("rows").toString();
		PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(rows));
		List<Map<String,String>> list = userDao.getAccount(param);
		grid.setRows(list);
		grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取小区列表
	 * 描述：略
	 * 作成者：尹帅
	 * 作成时间：2017-11-2
	 */
	@Override
	public Grid<Map<String, String>> getArea(Map<String, Object> param) {
		Grid<Map<String,String>> grid = new Grid<Map<String,String>>();
		String page = param.get("page").toString();
		String rows = param.get("rows").toString();
		PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(rows));
		List<Map<String,String>> list = userDao.getArea(param);
		grid.setRows(list);
		grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	/**
	  * 岗位tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@Override
	public Tree getStationTree(String stationRoot,String AREA_CODE) {
		//获取所有菜单
        List<Station> allDeparts = userDao.getAllStationByArea(AREA_CODE);
        Tree rootTree = new Tree();
        initTree(rootTree,allDeparts,stationRoot);
        return rootTree;
	}
	/**
	 * 递归所有小区岗位生成tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	private void initTree(Tree rootTree, List<Station> allDeparts,
			String selfCode) {
		Iterator<Station> menuIterator = allDeparts.iterator();
		while (menuIterator.hasNext()) {
			Station tmp = menuIterator.next();
			// 初始化自身节点
			if (tmp.getPOST_CODE().equals(selfCode)) {
				stationToTree(tmp, rootTree);

			} else if (tmp.getPARENT_POST_CODE().equals(selfCode)) {
				// 初始化子节点
				Tree children = new Tree();
				stationToTree(tmp, children);
				rootTree.getChildren().add(children);

				// 递归
				initTree(children, allDeparts, tmp.getPOST_CODE());
			}
		}
	}

	/**
	 * 岗位对象转成tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	private void stationToTree(Station dep, Tree tree) {
		tree.setId(dep.getPOST_CODE());
		tree.setPid(dep.getPARENT_POST_CODE());
		tree.setIconCls("");
		tree.setText(dep.getPOST_NAME());
		tree.setState("open");
		
		/*DeptAttribute attribute = new DeptAttribute();
        attribute.setAUTH_CODE(dep.getAUTH_CODE());
        tree.setAttributes(attribute);*/
	}
	
	/**
	 * 用户所有岗位编号
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@Override
	public String[] getUserstation(String USER_CODE,String AREA_CODE) {
		Map<String,Object> params = new HashMap<String,Object>();
		 params.put("USER_CODE", USER_CODE);
		 params.put("AREA_CODE", AREA_CODE);
		return userDao.getUserstation(params);
	}
	
	/**
     * 修改用户岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：尹帅
      * 作成时间：2017-11-2
     */
	@Override
	public void updateUserStation(String USER_CODE, String POST_CODE,
			String YHMC,String AREA_CODE) {
		//删除现有岗位
		 Map<String,Object> params = new HashMap<String,Object>();
		 params.put("USER_CODE", USER_CODE);
		 params.put("AREA_CODE", AREA_CODE);
		userDao.deleteUsersStation(params);
		if(!StringUtil.isNullOrEmpty(POST_CODE))
		{
			//新增岗位
	        this.addUserStation(USER_CODE,POST_CODE,YHMC,AREA_CODE);
	        /*//更新用户表中的岗位信息
	        params.put("POST_CODE", POST_CODE);
	        params.put("YHMC", YHMC);
	        userDao.updateUserStation(params);*/
	        
		}
	}
	
	 private void addUserStation(String USER_CODE,String POST_CODE, String YHMC,String AREA_CODE ) {
	        Map<String,Object> params = new HashMap<String,Object>();
	        params.put("USER_CODE",USER_CODE);
	        params.put("POST_CODE", POST_CODE);
	        params.put("YHMC",YHMC);
	        params.put("AREA_CODE", AREA_CODE);
	        //生成UUID
			String uuid = UUIDUtil.uuidStr();
			params.put("UUID", uuid);
			userDao.addUserStations(params);
	    }
	 /**
	   * 获取岗位的跟代码
	   * 部门：软件开发事业部	 
	   * 描述：略
	   * 作成者：尹帅
	   * 作成时间：2017-11-2
	   */
	@Override
	public String getStationRoot(String AREA_CODE) {
		return userDao.getStationRoot(AREA_CODE);
	}
	/**
	  * 查找用户所在的部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@Override
	public int getAreaDepart(String USER_CODE) {
		return userDao.getAreaDepart(USER_CODE);
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：所有项目信息
	 * 描述：略
	 * 作成者：盛婷婷
	 * 作成时间：2018-08-10
	 */
	@Override
	public Grid<Map<String, String>> getAllProject(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		List<Map<String, String>> list = userDao.getAllProject(param);

		grid.setRows(list);
		return grid;
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：修改项目信息
	 * 描述：略
	 * 作成者：盛婷婷
	 * 作成时间：2018-08-10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JsonResult updateUserProject(Map<String,Object> param) {
		JsonResult result = new JsonResult();
		//删除用户项目中间表
		userDao.deleteUserProject(param);
		//新增部门
		List<Map<String, String>> projectList = (List<Map<String, String>>)param.get("PROJECT");
		if(null != projectList && 0 < projectList.size()){
			for (int i = 0; i < projectList.size(); i++) {
				projectList.get(i).put("UUID", UUIDUtil.uuidStr());
			}
			param.put("projectList", projectList);		
			userDao.addUserProject(param);
		}
		result.setSuccess(true);
		result.setMsg("操作成功！");
		return result;
	}
}
