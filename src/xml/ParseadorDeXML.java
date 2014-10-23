package xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
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
	
	public static final String CARPETA = "./XML";

	private Document documento;
	
	public ParseadorDeXML() {
		new File(CARPETA).mkdirs();
	}
	
	public boolean chequearSiExisteXML(String archivo) {
		return new File(archivo).isFile();
	}
	
	public void crearXML(String origen, String destino) {
		URL inputUrl = getClass().getResource(origen);
		File dest = new File(destino);
		try {
			FileUtils.copyURLToFile(inputUrl, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
