package org.uscki.robot.bots;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;


public class Bucket implements Bot {

	public final static String HOST = "http://www.xelification.com/bucket.php"; 
	public final static String TEACH_PREFIX = "!leer";
	public final static String DELIMITER = "is";

	Pattern learnRegex = Pattern.compile("(?s)" + TEACH_PREFIX + ".+" + DELIMITER + ".+"); 

	/**
	 * Bot die communiceert met een server om te kijken of er een responds is op een specifieke input 
	 */
	public String ask(String input, String user) {
		try {
			URL url = new URL(HOST);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);//deze parameters zijn nodig om een post request te maken
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());		
			
			String pwData = "pw=" + URLEncoder.encode("1234", "UTF-8");//voor nu altijd nodig..
			out.writeBytes(pwData);
			
			if(learnRegex.matcher(input).matches()){
				int endKeyPosition = input.indexOf(DELIMITER);
				String key = input.substring(TEACH_PREFIX.length(), endKeyPosition).trim();
				int startValuePosition = endKeyPosition + DELIMITER.length();
				String value = input.substring(startValuePosition).trim();

				String cmdData = "&cmd=" + URLEncoder.encode("learn", "UTF-8");
				String keyData = "&key=" + URLEncoder.encode(key, "UTF-8");
				String valueData = "&value=" + URLEncoder.encode(value, "UTF-8");
				
				out.writeBytes(cmdData);
				out.writeBytes(keyData);
				out.writeBytes(valueData);
				out.flush();
				out.close();
				
				switch(conn.getResponseCode()){
				case HttpURLConnection.HTTP_OK://value overridden
					return "Ik zal het voortaan gebruiken in plaats van: " + getHttpOutput(conn);
				case HttpURLConnection.HTTP_CREATED://new key/value created
					return "Ik heb het onthouden!";
				default:
				case HttpURLConnection.HTTP_INTERNAL_ERROR://something went wrong
					return "Sorry er ging iets mis! :(";
				}
			}else{
				
				String cmdData = "&cmd=" + URLEncoder.encode("check", "UTF-8");
				String keyData = "&key=" + URLEncoder.encode(input, "UTF-8");
				
				out.writeBytes(cmdData);
				out.writeBytes(keyData);
				out.flush();
				out.close();
				
				switch(conn.getResponseCode()){
				case HttpURLConnection.HTTP_OK:
					return getHttpOutput(conn);
				default:
				case HttpURLConnection.HTTP_NO_CONTENT:
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					return null;
				}
			}
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	private static String getHttpOutput(HttpURLConnection conn) throws IOException{
		byte[] buffer = new byte[1024];
		InputStream is;

		is = conn.getInputStream();

		StringBuffer httpOutput = new StringBuffer();
		int bytesRead = -1;
		while((bytesRead = is.read(buffer)) != -1){
			httpOutput.append(new String(buffer, 0, bytesRead));
		}
		return httpOutput.toString();
	}
}
