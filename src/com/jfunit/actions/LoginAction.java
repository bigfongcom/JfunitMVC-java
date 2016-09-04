package com.jfunit.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//访问数据库数据, 处理业务逻辑
		//最后返回的页面
		return "success";
	}

}
