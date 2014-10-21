package json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryManager {
	
	public static final String QUERY = "query";
	public static final String SQL = "sql";
	public static final String DATABASE = "database";
	public static final String COLS = "cols";
	public static final String ROWS = "rows";
	public static final String COLNAME = "colname";
	public static final String COL = "col";
	public static final String ROW = "row";
	public static final String ERROR = "error";
	
	public static String crearQuery(String bd, String sql) {
		Map valoresJSON = new LinkedHashMap();
		valoresJSON.put(DATABASE, bd);
		valoresJSON.put(SQL, sql);
		Map queryJSON = new LinkedHashMap();
		queryJSON.put(QUERY, valoresJSON);
		String textoJSON = JSONValue.toJSONString(queryJSON);
		return textoJSON;
	}

	public static String crearRespuesta(List<String> columnas, List<String> valores) {
		Map nombreColsJSON = new LinkedHashMap();
		for (int i = 0; i < columnas.size(); i++)
			nombreColsJSON.put(COLNAME + (i + 1), columnas.get(i));
		Map titulosJSON = new LinkedHashMap();
		titulosJSON.put(COLS, nombreColsJSON);
		int cantFilas = valores.size() / columnas.size();
		int desplazamiento;
		Map filasJSON = new LinkedHashMap();
		for (int i = 0; i < cantFilas; i++) {
			Map<String, String> columnasJSON = new LinkedHashMap<String, String>();
			for (int j = 0; j < columnas.size(); j++) {
				desplazamiento = columnas.size() * i;
				columnasJSON.put(COL + (j + 1),
						valores.get(j + desplazamiento));
			}
			filasJSON.put(ROW + (i + 1), columnasJSON);
		}
		titulosJSON.put(ROWS, filasJSON);
		Map queryJSON = new LinkedHashMap();
		queryJSON.put(QUERY, titulosJSON);
		String textoJSON = JSONValue.toJSONString(queryJSON);
		return textoJSON;
	}
	
	public static String crearError(String msgError) {
		Map errorJSON = new LinkedHashMap();
		errorJSON.put(ERROR, msgError);
		String textoJSON = JSONValue.toJSONString(errorJSON);
		return textoJSON;
	}
	
	public static boolean chequearError(String textoJSON) {
		boolean resultado = false;
		JSONParser parser = new JSONParser();
		try {
			Object objeto = parser.parse(textoJSON);
			JSONObject objetoJSON = (JSONObject) objeto;
			resultado = objetoJSON.containsKey(ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static List<String> decodificarError(String errorJSON) {
		List<String> resultado = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {
			Object objeto = parser.parse(errorJSON);
			JSONObject objetoJSON = (JSONObject) objeto;
			String error = (String) objetoJSON.get(ERROR);
			resultado.add(ERROR);
			resultado.add(error);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static List<String> decodificarQuery(String queryString) {
		List<String> resultado = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {
			Object objQuery = parser.parse(queryString);
			JSONObject queryJSON = (JSONObject) objQuery;
			JSONObject query = (JSONObject) queryJSON.get(QUERY);
			String db = (String) query.get(DATABASE);
			String sql = (String) query.get(SQL);
			resultado.add(db);
			resultado.add(sql);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static List<String> decodificarRespuesta(String queryRespuesta) {
		List<String> resultado = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		try {
			Object objQuery = parser.parse(queryRespuesta);
			JSONObject queryJSON = (JSONObject) objQuery;
			JSONObject query = (JSONObject) queryJSON.get(QUERY);
			JSONObject cols = (JSONObject) query.get(COLS);
			JSONObject rows = (JSONObject) query.get(ROWS);
			resultado.add(String.valueOf(cols.size()));
			for (int i = 1; i <= cols.size(); i++) {
				resultado.add((String)cols.get(COLNAME+i));
			}
			for (int i = 1; i <= rows.size(); i++) {
				JSONObject row = (JSONObject) rows.get(ROW+i);
				for (int j = 1; j <= row.size(); j++) {
					resultado.add((String)row.get(COL+j));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultado;
	}

}
