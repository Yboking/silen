package silen.scheduler.ui.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import silen.scheduler.ui.valuebean.OozieConfigValue;
import silen.scheduler.ui.valuebean.Property;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLHelper {
	public static void main(String[] args) {

		try {
			// String xml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>"
			// + "<root>" + "<jobname>wf001_time</jobname>" + "</root>";

			// OozieConfigValue res = xml2Bean(xml, OozieConfigValue.class);
			xml2Bean(new File("oozie-job-config.xml"), OozieConfigValue.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static XmlMapper xmlMapper = new XmlMapper();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T xml2Bean(String xml, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		return new XmlMapper().readValue(xml, clazz);

	}

	public static  <T> void xml2Bean(File file, T t)
			throws JsonParseException, JsonMappingException, IOException {

		List<T> beanList = xmlMapper.readValue(file,
				new TypeReference<List< Property>>() {
				});

		for (T c : beanList) {

			System.out.println(c);
		}

	}

	public static void xml2Json() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?><root><dog>dogname</dog><cat>catname</cat></root>";

		StringWriter w = new StringWriter();
		JsonParser jp;

		try {
			jp = xmlMapper.getFactory().createParser(xml);
			JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
			while (jp.nextToken() != null) {
				jg.copyCurrentEvent(jp);
			}
			jp.close();
			jg.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(w.toString());

	}

}
