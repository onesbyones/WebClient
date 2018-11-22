/**
* json串格式化工具：http://tool.oschina.net/codeformat/json
*/
publci class JsonToArrays {

  public static JSONArray JsonArrays(String filePath){
    String fileStr = FileUtils.readFileToString(new File(filePath), "utf-8");
    String jsonStr = StringEscapeUtils.unescapeCsv(fileStr);
    JSONObject jsonObject = JSONObject.fromObject(jsonStr);
    JSONObject dataJson = jsonObject.getJSONObject("data");
    JSONArray jsonArrays = dataJson.getJSONArray("list");
    return jsonArrays;
  }

}
