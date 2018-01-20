package com.mavenMVC.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;


/**
 * general methods: get data from http request
 * 
 * @author lizai
 */
public class RequestUtil {

	protected final static Log logger = LogFactory.getLog(RequestUtil.class);

	public static String getParameter(HttpServletRequest request,
			String paraName){
		String value = null;
		try {
            request.setCharacterEncoding("UTF-8");
			value = request.getParameter(paraName);
		} catch (Exception e) {
			logger.error(e);             e.printStackTrace();
		}
		return value;
	}

	public static int getIntPara(HttpServletRequest request, String name)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String str = RequestUtil.getParameter(request, name);
		if ((str != null) && (str.trim().length() > 0)) {
			int num = Integer.parseInt(str);
			return num;
		}
		return -1;
	}

	public static float getFloatPara(HttpServletRequest request, String name)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String str = RequestUtil.getParameter(request, name);
		if ((str != null) && (str != "")) {
			float num = Float.parseFloat(str);
			return num;
		}
		return -1;
	}

	public static long getLongPara(HttpServletRequest request, String name)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		long value = -1;
		String str = RequestUtil.getParameter(request, name);
		if (str != null) {
			value = Long.parseLong(str);
		}

		return value;
	}
	
	public static double getDoublePara(HttpServletRequest request, String name)
			throws Exception {
		double value = -1;
		String str = RequestUtil.getParameter(request, name);
		if (str != null) {
			value = Double.parseDouble(str);
		}
		return value;
	}
	
	public static long getUserId(HttpServletRequest request){
		return (Long) request.getSession().getAttribute("userId");
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址，
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，
	 * 取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
