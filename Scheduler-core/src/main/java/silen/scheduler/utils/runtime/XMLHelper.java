package silen.scheduler.utils.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import silen.scheduler.core.oozieconf.Fork;
import silen.scheduler.core.oozieconf.Join;
import silen.scheduler.core.oozieconf.Kill;
import silen.scheduler.core.oozieconf.OAction;
import silen.scheduler.core.oozieconf.OConfigValue;
import silen.scheduler.core.oozieconf.OWorkFlow;
import silen.scheduler.core.oozieconf.Property;
import silen.scheduler.core.oozieconf.StartPoint;

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

			// OWorkFlow owf = parseOozieWorkFlow("oozie-job-define.xml");
			// testXMLEventReader("oozie-job-define.xml");
			parseOozieWorkFlow2("oozie-job-define22.xml");

			// System.out.println(owf);
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

					System.out.println(name);
					System.out.println(event.asStartElement().asCharacters());
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

				// break;

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

	@SuppressWarnings("unchecked")
	public static Element fixNamespace(Element element) {

		Iterator<Element> iter = element.elementIterator();

		while (iter.hasNext()) {

			Element ele = iter.next();

			System.out.println(ele.getNamespacePrefix());

			ele.remove(ele.getNamespace());

			System.out.println(ele.getNamespacePrefix());
			System.out.println(element.getName());
			System.out.println(element.asXML());

		}

		return element;

	}

	public static OConfigValue xml2OozieConfig(Properties xmlProperties)
			throws JsonParseException, JsonMappingException, IOException {

		List<Property> beanList = new ArrayList<Property>();
		Iterator<Entry<Object, Object>> ite = xmlProperties.entrySet()
				.iterator();
		while (ite.hasNext()) {

			Entry<Object, Object> entry = ite.next();
			Property pro = new Property();
			pro.setName(entry.getKey().toString());
			pro.setValue(entry.getValue().toString());
			beanList.add(pro);
		}
		OConfigValue ocv = new OConfigValue();
		ocv.setProperty(beanList.toArray(new Property[] {}));
		return ocv;

	}

	private static XmlMapper xmlMapper = new XmlMapper();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T xml2Bean(String xml, Class<T> t)
			throws JsonParseException, JsonMappingException, IOException {

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);

		XmlMapper mapper = new XmlMapper(module);
		return mapper.readValue(new StringReader(xml), t);

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

	public static Document getDocumentByInputStream(InputStream in) {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	@SuppressWarnings("unchecked")
	public static OWorkFlow parseOozieWorkFlow2(String filepath)
			throws Exception {

		try {

			Document document = getDocumentByInputStream(new FileInputStream(
					new File(filepath)));

			Element rootElement = document.getRootElement();

			Iterator<Element> iter = rootElement.elementIterator();

			OWorkFlow owf = new OWorkFlow();
			while (iter.hasNext()) {
				Element element = iter.next();

				System.out.println(element.getName());
				System.out.println(element.asXML());

				String tagname = element.getName();
				String xml = element.asXML();

				switch (tagname) {

				case "start":
					StartPoint startPoint = xml2Bean(xml, StartPoint.class);
					System.out.println(startPoint);
					owf.setStart(startPoint);
					break;
				case "fork":
					Fork fork = xml2Bean(xml, Fork.class);
					owf.addFork(fork);
					break;

				case "join":
					Join join = xml2Bean(xml, Join.class);
					owf.addJoin(join);
					break;
				case "action":
					OAction action = xml2Bean(xml, OAction.class);
					owf.addAction(action);
					break;
				case "kill":
					Kill kill = xml2Bean(xml, Kill.class);

					owf.setKill(kill);
					break;
				}
			}

			System.out.println(owf);
			return owf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static OWorkFlow parseOozieWorkFlow2(Element rootElement)
			throws Exception {

		try {

			Iterator<Element> iter = rootElement.elementIterator();

			OWorkFlow owf = new OWorkFlow();
			while (iter.hasNext()) {
				Element element = iter.next();
				System.out.println(element.getName());
				System.out.println(element.asXML());

				String tagname = element.getName();
				String xml = element.asXML();

				switch (tagname) {

				case "start":
					StartPoint startPoint = xml2Bean(xml, StartPoint.class);
					System.out.println(startPoint);
					owf.setStart(startPoint);
					break;
				case "fork":
					Fork fork = xml2Bean(xml, Fork.class);
					owf.addFork(fork);
					break;

				case "join":
					Join join = xml2Bean(xml, Join.class);
					owf.addJoin(join);
					break;
				case "action":
					OAction action = xml2Bean(xml, OAction.class);
					owf.addAction(action);
					break;
				case "kill":
					Kill kill = xml2Bean(xml, Kill.class);

					owf.setKill(kill);
					break;
				}
			}

			System.out.println(owf);
			return owf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
