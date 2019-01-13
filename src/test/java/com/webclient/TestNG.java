package com.webclient;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.ParseException;
import org.testng.annotations.Test;

import com.business.web.api.WebApi;
import com.log.LogUtils;

import net.sf.json.JSONObject;

public class TestNG {

	private WebApi webApi;

	@Test(priority = 1)
	public void test1() throws ParseException, IOException {
		this.webApi = new WebApi();
	}

	@Test(priority = 2)
	public void test2() throws ParseException, IOException {

		LogUtils.log("******************发布房源******************");
		String responseStr = this.webApi.uploadPicture();
		JSONObject responseJson = JSONObject.fromObject(responseStr);
		String errorCode = responseJson.getString("errorCode");
		String successCode = responseJson.getString("success");
		assertEquals(errorCode, "0");
		assertEquals(successCode, "true");
	}

}
