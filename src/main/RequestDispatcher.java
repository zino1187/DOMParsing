package main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RequestDispatcher {
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;
	URL url;
	
	public RequestDispatcher() {
		url=this.getClass().getClassLoader().getResource("config/config.xml");
		createDom();
	}

	public void createDom() {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(url.toURI()));
			System.out.println(getClassById("foodController"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public String getClassById(String id) {
		String className=null;
		document.getDocumentElement().normalize();
		//String root = document.getDocumentElement().getNodeName();// ��Ʈ ������Ʈ�� �̸��� ����
		
		NodeList nodeList =document.getElementsByTagName("beans");//ù��° depth�� ��� ��� ��ȯ
		
		for(int i=0;i<nodeList.getLength();i++) {
			Node firstNode =nodeList.item(i); //nodeList�� ù��° ��带 ���� �մϴ�.
			Element firstElement = (Element) firstNode; 
			
			NodeList list = firstElement.getElementsByTagName("bean");
			for(int a=0;a<list.getLength();a++) {
				Node node=list.item(a);
				if(node.getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
					className=node.getAttributes().getNamedItem("class").getNodeValue();
				}
			}
		}
		return className;		
	}
	
	public static void main(String[] args) {
		new RequestDispatcher();
	}

}
