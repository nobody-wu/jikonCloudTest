package cn.jikon.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AppConfigerFile {
	public static Map<String, String> parseConfigFile(String configFile) {
		Map<String, String> result = new HashMap<String, String>();

		int a = configFile.lastIndexOf("/");
		String dirName = configFile.substring(0, a + 1);
		String baseName = configFile.substring(a + 1);

		String fileName = dirName + baseName;
		String content = getConfigFileContent(fileName);
		if (content == null || content.length() < 1) {
			return result;
		}

		String[] contentLines = content.split("\\n");
		for (int j = 0; j < contentLines.length; j++) {
			String line = contentLines[j].trim();
			if (line.length() < 1) {
				continue;
			}
			if (line.substring(0, 1).equals("#")) {
				continue;
			}
			a = line.indexOf("=");
			if (a <= 0) {
				continue;
			}

			String name = line.substring(0, a).trim();
			if (name.length() < 1) {
				continue;
			}

			String value = "";
			if (line.length() - 1 > a) {
				value = line.substring(a + 1).trim();
			}
			result.put(name, value);
		}

		return result;
	}

	private static String getConfigFileContent(String configFile) {
		byte[] data = null;
		try {
			data = getResource(configFile);
			if (data == null) {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		return new String(data);
	}

	/**
	 * 获取资源数据
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static byte[] getResource(final String path) throws IOException {
		InputStream inputStream = AppConfigerLoader.class.getResourceAsStream(path);
		if (inputStream == null) {
			String path2 = "/resources" + path; // resources目录, 兼容jar包
			inputStream = AppConfigerLoader.class.getResourceAsStream(path2);
			if (inputStream == null) {
				return null;
			}
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int read = 0;

		try {
			for (;;) {
				read = inputStream.read(buffer);
				if (read < 1) {
					break;
				}
				outputStream.write(buffer, 0, read);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return outputStream.toByteArray();
	}
}
