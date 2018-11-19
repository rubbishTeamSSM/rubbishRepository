package com.neusoft.sdd.businessConsole.station.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.station.dao.StationDAO;

import com.neusoft.sdd.businessConsole.station.service.StationService;
import com.neusoft.sdd.util.commonUtil.StringUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;


@Service
//@Transactional
public class StationServiceImpl implements StationService {

	@Autowired
	private StationDAO stationDao;
	
	/**
     *  查询岗位列表
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
	@Override
	public Grid<Map<String,String>> getStations(Map<String,String> param) {
		Grid<Map<String,String>> grid = new Grid<Map<String,String>>();
		String page = param.get("page").toString();
		String rows = param.get("rows").toString();
		PageHelper.startPage(Integer.valueOf(page), Integer.valueOf((rows)));
		List<Map<String,String>> list = stationDao.getStations(param);
		grid.setRows(list);
	    grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
     *  新增岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
	@Override
	public JsonResult insertStation(Map<String,Object> param) {
		JsonResult result = new JsonResult();
		param.put("POST_CODE", StringUtil.getCommonCode(2));
		param.put("UUID", UUIDUtil.uuidStr());		
		stationDao.insertStation(param);
		result.setSuccess(true);
		result.setMsg("操作成功！");       
		return result;
	}

	/**
     *  验证岗位名称
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
    @Override
    public JsonResult validStation(Map<String,Object> param) {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        int POST_NAMENum = stationDao.validStation(param);        
        if (POST_NAMENum > 0){
            result.setSuccess(false);
            result.setMsg("岗位名称已存在");
        }
        return result;
    }
    
    /**
     *  验证400客服
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
    @Override
    public JsonResult validService(Map<String,Object> param) {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        int ServiceNum = stationDao.validService(param);
        if (ServiceNum > 0){
            result.setSuccess(false);
            result.setMsg("400客服已存在");
        }
        return result;
    }

    /**
     *  查询岗位详情
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
    @Override
    public Map<String, Object> stationDetails(Map<String, Object> param) {
        return stationDao.stationDetails(param);
    }

    /**
     *  修改岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
    @Override
    public JsonResult modifyStation(Map<String, Object> param) {
       JsonResult result = new JsonResult();
       stationDao.modifyStation(param);
       result.setSuccess(true);
       result.setMsg("操作成功！");
       return result;
    }
    
    /**
     *  删除岗位
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：菅文达
      * 作成时间：2017-11-1
     */
	@Override
	public JsonResult deleteStation(Map<String, Object> param) {
		JsonResult result = new JsonResult();
		//如果岗位没有被关联过，则可以删除
		if(0 == stationDao.getAllUserPostCount(param)){
			stationDao.deleteStation(param);
			stationDao.deleteUserStation(param);
			stationDao.deleteAreaStation(param);
			result.setSuccess(true);
			result.setMsg("删除成功");
		}else{
			result.setSuccess(false);
			result.setMsg("删除失败，含有已经分配到用户的岗位！");
			return result;
		}
		return result;
	}

}
