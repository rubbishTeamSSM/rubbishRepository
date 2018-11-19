package com.neusoft.sdd.combo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.combo.dao.IComboboxMgrDAO;
import com.neusoft.sdd.combo.service.IComboboxMgrService;

//@Transactional
@Service
public class ComboboxMgrServiceImpl  implements IComboboxMgrService {
	@Autowired
	private IComboboxMgrDAO comboboxMgrDAO;

	/**
	 * 字典表下拉查询
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2017-5-6
	 */
	@Override
	public List<Map<String, String>> getDictionaryList(
			Map<String, String> queryMap) {
		return comboboxMgrDAO.getDictionaryList(queryMap);
	}


	@Override
	public Grid<Map<String, String>> getCombogridList(
				Map<String, String> queryMap, String page, String rows) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
	    PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
	    grid.setRows(comboboxMgrDAO.getDictionaryList(queryMap));
	    grid.setTotal(comboboxMgrDAO.countDictionary(queryMap));
    	return grid;
	}

	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：验证下拉框列表数据是否存在
	 * 描述：略
	 * 作成者：封新芳
	 * 作成时间：2017-6-23
	 */
	@Override
	public int comboboxCheck(Map<String, String> param) {
		int count = 0;
		String ckValue = param.get("ck_value");
		String[] ck_values = ckValue.split(",");
		for(int i = 0;i < ck_values.length;i++){
			param.put("ck_value", ck_values[i]);
			count = comboboxMgrDAO.comboboxCheck(param);
			if(count == 0){
				return count;
			}
		}
		return count;
	}
	
	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：字段查重
	 * 描述：略
	 * 作成者：封新芳
	 * 作成时间：2017-11-22
	 */
	@Override
	public int duplicateCheck(Map<String, String> param) {
		return comboboxMgrDAO.duplicateCheck(param);
	}
	 
}
