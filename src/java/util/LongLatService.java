/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 *
 * @author Mariella
 */
public class LongLatService {
    //<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAITDKVsmqWFfcBXcEszV0ZMkCj9tJTbns"></script>
    private static final String GEOCODE_REQUEST_URL 
            = "http://maps.googleapis.com/maps/api/geocode/json?output=xml";
    private static HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    
    

    public String obtenerDistrito(double latitud, double  longitud) {
        try {
            StringBuilder urlBuilder = new StringBuilder(GEOCODE_REQUEST_URL);
            urlBuilder.append("&latlng=").append(latitud);
            urlBuilder.append(",").append(longitud);
            urlBuilder.append("&sensor=true");
            
 
            final GetMethod getMethod = new GetMethod(urlBuilder.toString());
            try {
                httpClient.executeMethod(getMethod);
                Reader reader = new InputStreamReader(getMethod.getResponseBodyAsStream(), getMethod.getResponseCharSet());
                 
                int data = reader.read();
                char[] buffer = new char[1024];
                Writer writer = new StringWriter();
                while ((data = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, data);
                }
                String result = writer.toString();
                // Texto que vamos a buscar
                String sTextoBuscado = "address_components";
                // Contador de ocurrencias 
                int contador = 0;
                while (result.indexOf(sTextoBuscado) > -1) {
                  if(contador==2) break;  
                  result = result.substring(result.indexOf(
                          sTextoBuscado)+sTextoBuscado.length(),result.length());
                  contador++; 
                }
                int route= result.indexOf("route");
                
                sTextoBuscado = "short_name";
                result = result.substring(result.indexOf(
                          sTextoBuscado)+sTextoBuscado.length(),result.length());
                if(route>1){
                    result = result.substring(result.indexOf(
                          sTextoBuscado)+sTextoBuscado.length(),result.length());
                    result = result.substring(result.indexOf(
                          sTextoBuscado)+sTextoBuscado.length(),result.length());
                    
                }
                
                result = result.substring(result.indexOf(
                          "\"")+"\"".length(),result.length());
                result = result.substring(result.indexOf(
                          "\"")+"\"".length(),result.length());
                
                int coma= result.indexOf("\"");
                String distrito = result.substring(0,coma);
                System.out.println (distrito);
                return distrito;
                // al segundo addres_component
                // si encuentro route busco dos mas short_name, sino esta en el primer shortname 
                 
            } finally {
                getMethod.releaseConnection();
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
    return null;
    }
    
}
