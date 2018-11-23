package com.web.clients.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * json数据格式化工具：http://tool.oschina.net/codeformat/json
 */
public class JsonToArrays {

	public static JSONArray JsonArrays(String filePath) throws IOException {
		String fileStr = FileUtils.readFileToString(new File(filePath), "utf-8");
		String jsonStr = StringEscapeUtils.unescapeCsv(fileStr);
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		JSONObject dataJson = jsonObject.getJSONObject("data");
		JSONArray jsonArrays = dataJson.getJSONArray("list");
		return jsonArrays;
	}

}
