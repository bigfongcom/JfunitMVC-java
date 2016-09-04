package com.jfunit.actions;

public class ActionManager {
	public static Action createAction(String className){
		//生产class对像
		Class clazz = null;
		
		try {
			//判断当前线程是否有该action线程运行
			clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (clazz==null) {
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//产生Action实例
		Action action = null;
		try {
			action = (Action)clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return action;
	}
}
