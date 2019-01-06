package com.web.clients.util;

import java.util.Map;

import com.log.LogUtils;
import com.web.clients.ParamBean;

/**
 * 工具类
 * 
 * @author hqh
 *
 */
public class Util {

	/**
	 * 获取时间戳（15位）
	 * 
	 * @return long
	 */
	public static long getCurrentTimeMillis() {

		return System.currentTimeMillis();
	}

	/**
	 * 参数化输出日志
	 * 
	 * @param paramBean
	 */
	public static void printParamBean(ParamBean paramBean) {
		LogUtils.log("接口名称：" + paramBean.getDesc());
		LogUtils.log("请求Url：" + paramBean.getHost() + paramBean.getUri());
		LogUtils.log("请求方法：" + paramBean.getMethod());
		for (Map.Entry<String, String> temp : paramBean.getHeadersMap().entrySet()) {
			LogUtils.log("请求头，" + temp.getKey() + "：" + temp.getValue());
		}
		LogUtils.log("请求body：" + paramBean.getBody());
		if (null != paramBean.getResponse()) {
			LogUtils.log("响应body：" + paramBean.getDesc());
		}

	}

}
