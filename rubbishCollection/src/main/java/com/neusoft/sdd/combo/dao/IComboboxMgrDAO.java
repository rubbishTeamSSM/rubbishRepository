package com.neusoft.sdd.combo.dao;

import java.util.List;
import java.util.Map;


public interface IComboboxMgrDAO {

	List<Map<String, String>> getDictionaryList(Map<String, String> queryMap);

	int countDictionary(Map<String, String> queryMap);
	
	int comboboxCheck(Map<String, String> param);
	
	int duplicateCheck(Map<String, String> param);
}
