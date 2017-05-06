package cn.jikon.common.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AppConfigerLoader {
	private static String[] args = null;

	private static Map<String, Map<String, String>> configers = new Hashtable<String, Map<String, String>>();

	public static String getConfig(String fieldName) {
		return getConfig(fieldName, "");
	}

	public static String getConfig(String fieldName, String defaultValue) {
		return getConfigByFile(Constants.APPLICATION_CONFIG, fieldName, defaultValue);
	}

	public static Map<String, String> getConfigs() {
		return getConfigsByFile(Constants.APPLICATION_CONFIG);
	}

	public static String getConfigByFile(String configFile, String configName) {
		return getConfigByFile(configFile, configName, "");
	}

	public static String getConfigByFile(String configFile, String configName, String defaultValue) {
		Map<String, String> configs = getConfigsByFile(configFile);
		if (!configs.containsKey(configName)) {
			return defaultValue;
		}
		String result = configs.get(configName);
		if (null == result || result.length() < 1) {
			return defaultValue;
		}
		return result;
	}

	public static Map<String, String> getConfigsByFile(String configFile) {
		if (configers.containsKey(configFile)) {
			return configers.get(configFile);
		}
		synchronized (configers) {
			if (configers.containsKey(configFile)) {
				return configers.get(configFile);
			}
			Map<String, String> configItems = AppConfigerFile.parseConfigFile(configFile);
			Map<String, String> configItems2 = getConfigsByArgs();
			if (configItems2 != null) {
				for (String name : configItems2.keySet()) {
					String value = configItems2.get(name);
					configItems.put(name, value); // 覆盖文件里的配置
				}
			}
			configers.put(configFile, configItems);
			return configItems;
		}
	}

	public static synchronized void setArgs(String[] args) {
		AppConfigerLoader.args = args;
	}

	private static Map<String, String> getConfigsByArgs() {
		if (args == null || args.length < 1) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		for (String arg : args) {
			if (arg.length() < 4) {
				continue;
			}
			if (!arg.substring(0, 2).equals("--")) {
				continue;
			}
			arg = arg.substring(2);
			int a = arg.indexOf("=");
			if (a == 0) {
				continue;
			}
			String name = null;
			String value = null;
			if (a < 0) {
				name = arg;
			} else {
				name = arg.substring(0, a);
				try {
					value = arg.substring(a + 1);
				} catch (Exception e) {

				}
			}
			if (value == null) {
				value = "";
			}
			result.put(name, value);
		}
		return result;
	}

}
