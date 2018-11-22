package com.web.clients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.ParseException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreateHttp {

	private ParamBean paramBean;

	public CreateHttp(JSONArray jsonArrays) throws ParseException, IOException {

		for (Object object : jsonArrays) {
			this.paramBean = new ParamBean();// 不清楚是否会导致jvm内存溢出
			JSONObject jsonObject = (JSONObject) object;
			this.paramBean.setDesc(jsonObject.getString("describe"));
			this.paramBean.setHost(jsonObject.getString("host"));
			this.paramBean.setUri(jsonObject.getString("uri"));
			HashMap<String, String> headersMap = new HashMap<>();
			JSONObject headersJsonObject = jsonObject.getJSONObject("headers");
			Set<String> headersSet = headersJsonObject.keySet();
			for (String tempStr : headersSet) {
				headersMap.put(tempStr, headersJsonObject.getString(tempStr));
			}
			this.paramBean.setHeadersMap(headersMap);
			this.paramBean.setBody(jsonObject.getString("body"));
			this.paramBean.setMethod(jsonObject.getString("method"));
			this.paramBean.setResponse(jsonObject.getString("response"));
			this.paramBean.setExpectValue(jsonObject.getString("expectValue"));
			this.paramBean.setActualVlaue(jsonObject.getString("actualVlaue"));
			this.paramBean.setVariable(jsonObject.getString("variable"));
			this.paramBean.setSql(jsonObject.getString("sql"));

			// 创建请求 -->@todo: 可以对响应做变量提取、结果校验
			String response = ResponseDone.doneRespone(this.paramBean);
			this.paramBean.setResponse("创建请求 -->@todo: 可以对响应做变量提取、结果校验" + this.paramBean.toString());
		}

	}

	public ParamBean getParamBean() {
		return paramBean;
	}

}
