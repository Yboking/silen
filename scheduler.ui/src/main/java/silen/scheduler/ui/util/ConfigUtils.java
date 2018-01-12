package silen.scheduler.ui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigUtils {

	public static String reloadValue(String key) {

		Properties pro = new Properties();
		try {
			pro.load(new InputStreamReader(
					new FileInputStream("res.properties")));

			return pro.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

}
