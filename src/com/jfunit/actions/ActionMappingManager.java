package com.jfunit.actions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ActionMappingManager {
	//保存所有Action的ActionMapping
	private static Map<String, ActionMapping> actionMappings = new HashMap<String, ActionMapping>();
	
	//加载Action配置文件
	public ActionMappingManager() {
	}
	
	public ActionMappingManager(String[] configureFileNames) {
		for (String configureFileName : configureFileNames) {
			init(configureFileName);//解析XML文件
		}
	}
	//加载解析XML配置文件
	public void init(String configureFileName){
		//判断文件是否为空
		try {
			if (configureFileName==null || configureFileName.isEmpty()) {
				throw new Exception("请检查配置文件");
			}
			//输入流用于加载系统的配置文件
			InputStream is = this.getClass().getResourceAsStream("/"+configureFileName);
			//读取XML文件
			Document doc = new SAXReader().read(is);
			//获取根节点
			Element root= doc.getRootElement();
			//获取Actions节点
			Element actions = (Element)root.elementIterator("actions").next();
			//遍历Action
			for (Iterator<Element> actionIt = actions.elementIterator("action"); actionIt.hasNext();) {
				//获取action元素，并奖其属性进行封装
				Element actionElement = actionIt.next();
				ActionMapping actionMapping = new ActionMapping();
				actionMapping.setName(actionElement.attributeValue("name"));
				actionMapping.setClassName(actionElement.attributeValue("class"));
				//遍历Action中的子元素result
				for (Iterator<Element> result = actionElement.elementIterator("result"); result.hasNext();) {
					Element resultElement = (Element) result.next();
					String resultName = resultElement.attributeValue("name");
					String resultValue = resultElement.getText();
					//判断数据正确性,数据是否为空
					if (null==resultName || "".equals(resultName) || resultName.isEmpty()) {
						resultName = "success";
					}
					System.out.println(resultName);
					System.out.println(resultValue);
					//将每个封装好的result添加到ActionMapping中
					actionMapping.addResult(resultName,resultValue);
				}
				
				//将ActionMapping存放到actionMappings中
				System.out.println(actionMapping.getName());
				actionMappings.put(actionMapping.getName(), actionMapping);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public   ActionMapping getActionMapping(String actionName) throws Exception{
		//根据Action获取Mapping
		ActionMapping actionMapping = null;
		
		//判断actionName是否为空，如果actionName为空，就是不用找ActionMapping,因为系统中没有该Action
		if (!(actionName==null||actionName.isEmpty())){
			actionMapping = this.actionMappings.get(actionName);
		}
		
		if (actionMapping==null) {
			throw new Exception("系统配置文件中找不到："+actionName+",请检查配置文件!");
		}
		
		return actionMapping;
	}

	
	
}
