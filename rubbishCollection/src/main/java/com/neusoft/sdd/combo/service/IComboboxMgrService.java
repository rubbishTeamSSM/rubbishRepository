package com.neusoft.sdd.combo.service;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.base.model.easyui.Grid;

public interface IComboboxMgrService {

	List<Map<String, String>> getDictionaryList(Map<String, String> queryMap);

	Grid<Map<String, String>> getCombogridList(Map<String, String> queryMap,
			String page, String rows);
	
	int comboboxCheck(Map<String, String> param);
	
	int duplicateCheck(Map<String, String> param);
}
