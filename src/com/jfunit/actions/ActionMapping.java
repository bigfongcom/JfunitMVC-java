package com.jfunit.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionMapping {
	private String name;
	private String className;
	private Map<String, String>  resultMap = new HashMap<String, String>();
	
	//获得返回页面
	public String getResult(String resultName) {
		return resultMap.get(resultName);
	}
	
	//添加页面关联关系
	public void addResult(String resultName,String result) {
		resultMap.put(resultName, result);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}
	
}
