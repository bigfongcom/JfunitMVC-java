package com.jfunit.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfunit.actions.Action;
import com.jfunit.actions.ActionManager;
import com.jfunit.actions.ActionMapping;
import com.jfunit.actions.ActionMappingManager;

public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActionMappingManager actionMappingManager = null;
	
	public ActionServlet() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//根据actionName获取一个ActionMapping(通过ActionMappingManager获取ActionMapping)
		try {
			ActionMapping actionMapping = actionMappingManager.getActionMapping(this.getActionName(req));
			//获取className
			Action action = ActionManager.createAction(actionMapping.getClassName());
			//执行业务逻辑
			String resultName = action.execute(req, resp);
			
			//根据resultName获取result
			String result = actionMapping.getResult(resultName);
			
			//转向结果显示页面
			if (result==null||result.isEmpty()) {
				resp.sendError(404,"未配置Action对应的result元素!");
				return;
			}
			//重定向结果页面
			resp.sendRedirect(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private String getActionName(HttpServletRequest request) {
		// 获取URI
		String uri = request.getRequestURI();
		//获取上下文路径
		String contextPath = request.getContextPath();
		//截取上下文件路径，获取ActionPath
		String actionPath = uri.substring(contextPath.length());
		//获取ActionName
		String actionName = actionPath.substring(1,actionPath.lastIndexOf('.')).trim();
		return actionName;
	}
	
	//webserver自动加载框架配置文件
	@Override
	public void init(ServletConfig config) throws ServletException {
		//读取配置信息
		String configStr = config.getInitParameter("config");
		//可以读取多个配置文件
		String[] fileNames = null;
		
		//判断配置文件是否已配置
		if (configStr==null || configStr.isEmpty()) {
			fileNames = new String[]{"jfunit.xml"};
		}else{
			fileNames = configStr.split(",");
		}
		System.out.println("------------");
		System.out.println(Arrays.toString(fileNames));
		//根据配置文件产生actionMappingManager
		this.actionMappingManager = new ActionMappingManager(fileNames);
	}
}
