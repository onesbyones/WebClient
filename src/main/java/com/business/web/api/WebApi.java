package com.business.web.api;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.business.web.param.UploadPictureParam;
import com.business.web.param.WebLoginParam;
import com.log.LogUtils;
import com.web.clients.HttpClientAdapter;
import com.web.clients.ParamBean;
import com.web.clients.util.BeanUtil;
import com.web.clients.util.Util;

/**
 * C端相关API
 * 
 * @author hqh
 *
 */
public class WebApi {
	private String cookie;

	public String getCookie() {
		return this.cookie;
	}

	public WebApi() throws ParseException, IOException {
		// 调用登录API，初始化cookie
		login_web();
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String login_web() throws ParseException, IOException {
		WebLoginParam webLoginParam = new WebLoginParam();
		ParamBean paramBean = new ParamBean();
		BeanUtil.copyProperties(webLoginParam, paramBean);

		HttpClientAdapter httpClientAdapter = new HttpClientAdapter(paramBean);
		CloseableHttpResponse response = httpClientAdapter.executeHttp(paramBean);

		HttpEntity resEntity = response.getEntity();
		String responseStr = EntityUtils.toString(resEntity);
		if (null != responseStr || "" != responseStr || !responseStr.isEmpty()) {
			paramBean.setResponse(responseStr);
		}
		Util.printParamBean(paramBean);
		Header[] resHeader = response.getAllHeaders();
		for (Header header : resHeader) {
			LogUtils.log("响应头, " + header.getName() + ": " + header.getValue());
			if ("Set-Cookie".equalsIgnoreCase(header.getName()) && header.getValue().contains("ZF_COOKIE")) {
				this.cookie = header.getValue();
			}
		}
		LogUtils.log("响应状态码： " + String.valueOf(response.getStatusLine().getStatusCode()));
		return responseStr;
	}

	/**
	 * 上传单张图片
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String uploadPicture() throws ParseException, IOException {
		UploadPictureParam uploadPictureParam = new UploadPictureParam();
		ParamBean paramBean = new ParamBean();
		BeanUtil.copyProperties(uploadPictureParam, paramBean);
		paramBean.getHeadersMap().put("cookie", this.cookie);
		HttpClientAdapter httpClientAdapter = new HttpClientAdapter(paramBean);
		CloseableHttpResponse response = httpClientAdapter.executeHttp(paramBean);
		HttpEntity resEntity = response.getEntity();
		String responseStr = EntityUtils.toString(resEntity);
		if (null != responseStr || "" != responseStr || !responseStr.isEmpty()) {
			paramBean.setResponse(responseStr);
		}
		Util.printParamBean(paramBean);
		LogUtils.log("响应状态码： " + String.valueOf(response.getStatusLine().getStatusCode()));
		return responseStr;

	}

}