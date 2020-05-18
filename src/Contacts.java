import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Contacts {
	int id;
	String name, ipadress;
	String tmpname, tmpip;
	ArrayList<Contacts> contactlist = new ArrayList<Contacts>();

	public Contacts() {
		// contactlist.add(new Contacts(2,"Julius", "10.217.77.56"));
		// contactlist.add(new Contacts(1,"Thomas", "localhost"));
		// contactlist.add(new Contacts(3,"Bruh", "0.0.0.0"));
		// safecontacts();
		// loadentries();
	}

	public Contacts(int id, String name, String ipadress) {
		this.id = id;
		this.name = name;
		this.ipadress = ipadress;
	}

	public void safecontacts() {
		try {
			final JAXBContext context = JAXBContext.newInstance(Contacts.class);
			final Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// m.marshal(this, System.out);
			m.marshal(this, new File("contactlist.xml"));
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
		System.out.println("Kontakte Gespeichert.");
	}

	public void loadcontacts() {
		File fXmlFile = new File("contactlist.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("contactlist");
		System.out.println("----------------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
				System.out.println("name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("ip : " + eElement.getElementsByTagName("ipadress").item(0).getTextContent());
			}
		}
		System.out.println("Kontakte Geladen.");
	}

	public int loadentries() {
		File fXmlFile = new File("contactlist.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(doc.getElementsByTagName("contactlist").getLength());
		return doc.getElementsByTagName("contactlist").getLength();
	}

	public String[] loadcontact(int id) {
		File fXmlFile = new File("contactlist.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nList = doc.getElementsByTagName("contactlist");
		Node nNode = nList.item(id);

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			tmpname = eElement.getElementsByTagName("name").item(0).getTextContent();
			tmpip = eElement.getElementsByTagName("ipadress").item(0).getTextContent();
		}
		return new String[] { tmpname, tmpip };
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Contacts());
	}

}
