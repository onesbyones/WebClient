package com.web.clients.util;

import java.io.File;
import java.util.ArrayList;

public class FileForEach {
	static ArrayList<String> fileList;

	static {
		fileList = new ArrayList<>();
	}

	public static ArrayList<String> getFileList(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				System.out.println("空目录:" + path);
				return fileList;
			} else {
				for (File tempFile : files) {
					if (tempFile.isDirectory()) {
						System.out.println("文件夹目录:" + tempFile.getAbsolutePath());
						getFileList(tempFile.getAbsolutePath());
					} else {
						String jsonFile = tempFile.getAbsolutePath();
						System.out.println("文件:" + jsonFile);
						if (jsonFile.endsWith(".json")) {
							fileList.add(tempFile.getAbsolutePath());
						}
					}
				}
			}
		} else {
			System.out.println("不存在该目录:" + path);
		}
		return fileList;
	}
}
