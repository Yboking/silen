package silen.scheduler.utils.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import silen.scheduler.core.oozieconf.OConfigValue;
import silen.scheduler.core.oozieconf.OWorkFlow;
import silen.scheduler.core.oozieconf.Property;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLHelper {
	public static void main(String[] args) {

		try {
			// String xml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>"
			// + "<root>" + "<jobname>wf001_time</jobname>" + "</root>";

			// OozieConfigValue res = xml2Bean(xml, OozieConfigValue.class);
			// OozieConfigValue ocv = xml2OozieConfig(new
			// File("oozie-job-config.xml"));
			//
			// System.out.println(ocv);

			OWorkFlow owf = parseOozieWorkFlow("oozie-job-define.xml");

			System.out.println(owf);
			// testXMLEventReader("Copy of oozie-job-define.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testXMLEventReader(String inputFile) {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader reader = factory
					.createXMLEventReader(new FileInputStream(inputFile));
			while (reader.hasNext()) {
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					String name = event.asStartElement().getName().toString();
					if ("Title".equals(name)) {
						System.out.println(reader.getElementText());
					}
				} else if (event.isCharacters()) {
					System.out.println(event.asCharacters().getData());
				} else if (event.isEndElement()) {

					System.out.println("end");
				} else if (event.isAttribute()) {

					System.out.println("attr");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("restriction")
	public static OWorkFlow parseOozieWorkFlow(String inputFile)
			throws Exception {

		XMLStreamReader reader = new XmlFactory().getXMLInputFactory()
				.createXMLStreamReader(new FileInputStream(inputFile));
		JacksonXmlModule module = new JacksonXmlModule();
		// to default to using "unwrapped" Lists:
		module.setDefaultUseWrapper(false);
		XmlMapper mapper = new XmlMapper(module);

		OWorkFlow wf = null;
		while (reader.hasNext()) {

			int point = reader.next();

			if (point == XMLStreamReader.START_ELEMENT) {

				System.out.println(reader.getName());
				wf = mapper.readValue(reader, OWorkFlow.class);

				break;

			}
		}

		reader.close();
		if (wf != null) {

			return wf;
		} else {
			throw new Exception("parse oozie workflow failed !");
		}
	}

	public static OConfigValue xml2OozieConfig(String filepath)
			throws JsonParseException, JsonMappingException, IOException {

		File file = new File(filepath);
		List<Property> beanList = xmlMapper.readValue(file,
				new TypeReference<List<Property>>() {
				});
		// xmlMapper.getTypeFactory().constructParametricType(List.class,
		// Bean.class);
		OConfigValue ocv = new OConfigValue();
		ocv.setProperty(beanList.toArray(new Property[] {}));
		return ocv;

	}

	private static XmlMapper xmlMapper = new XmlMapper();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T xml2Bean(String xml, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		return new XmlMapper().readValue(xml, clazz);

	}

	public static <T> void xml2Bean(File file, T t) throws JsonParseException,
			JsonMappingException, IOException {

		List<T> beanList = xmlMapper.readValue(file,
				new TypeReference<List<Property>>() {
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
