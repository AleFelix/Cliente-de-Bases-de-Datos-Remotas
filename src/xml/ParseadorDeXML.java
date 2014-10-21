package xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseadorDeXML {
	
	public static final String SERVIDOR = "servidor";
	public static final String PUERTO = "puerto";
	public static final String BASE_DE_DATOS = "base_de_datos";
	public static final String DIRECCION = "direccion";
	public static final String USUARIO = "usuario";
	public static final String PASSWORD = "password";

	private Document documento;

	public void parsearXML(String archivoXML) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			documento = db.parse(archivoXML);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String obtenerValor(String etiqueta) {
		Element elemento = documento.getDocumentElement();
		NodeList list = elemento.getElementsByTagName(etiqueta);
		return list.item(0).getTextContent();
	}
	
}
