package com.jfunit.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Action {
	public String INPUT = "input";
	public String SUCCESS = "success";
	public String ERROR = "error";
	
	//接收请求的方法
	public String execute(HttpServletRequest request,HttpServletResponse response)
	throws Exception;
}
