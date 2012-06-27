package org.symagic.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class RecommandService {

	private String host = "localhost:8080";
	private String apikey;
	private String tenantid;

	public boolean view(String sessionID, String itemID,
			String itemDescription, String itemURL, String userName) {

		String url = "http://" + host + "/easyrec-web/api/1.0/json/view";

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("sessionid", sessionID);
		parameters.put("itemid", itemID);
		parameters.put("itemdescription", itemDescription);
		parameters.put("itemurl", itemURL);
		parameters.put("userid", userName);

		String reponse = this.sendGetRequest(url, parameters);

		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);
		if (object.containsKey("error"))
			return false;

		return true;
	}

	public boolean buy(String sessionID, String itemID, String itemDescription,
			String itemURL, String userName) {
		String url = "http://" + host + "/easyrec-web/api/1.0/json/buy";
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("sessionid", sessionID);
		parameters.put("itemid", itemID);
		parameters.put("itemdescription", itemDescription);
		parameters.put("itemurl", itemURL);
		parameters.put("userid", userName);

		String reponse = this.sendGetRequest(url, parameters);
		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);
		if (object.containsKey("error"))
			return false;

		return true;
	}

	public boolean rate(String sessionID, String rateValue, String itemID,
			String itemDescription, String itemURL, String userName) {
		String url = "http://" + host + "/easyrec-web/api/1.0/json/rate";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("sessionid", sessionID);
		parameters.put("itemid", itemID);
		parameters.put("ratingvalue", rateValue);
		parameters.put("itemdescription", itemDescription);
		parameters.put("itemurl", itemURL);
		parameters.put("userid", userName);

		String reponse = this.sendGetRequest(url, parameters);
		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);
		if (object.containsKey("error"))
			return false;

		return true;
	}
	

	public List<Integer> otherUsersAlsoBiewed(String itemID, String userName,
			Integer requireNumber) {
		List<Integer> result = new ArrayList<Integer>();

		String url = "http://" + host
				+ "/easyrec-web/api/1.0/json/otherusersalsoviewed";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("itemid", itemID);
		parameters.put("userid", userName);
		parameters.put("numberOfResults", requireNumber.toString());

		String reponse = this.sendGetRequest(url, parameters);

		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);

		JSONObject recommended = object.getJSONObject("recommendeditems");
		JSONArray items = recommended.getJSONArray("item");
		for (int i = 0; i < items.size(); i++) {
			JSONObject temp = (JSONObject) items.get(i);
			result.add(temp.getInt("id"));
		}

		return result;
	}

	
	public List<Integer> otherUsersAlsoBought(String itemID, String userName,
			Integer requireNumber) {
		List<Integer> result = new ArrayList<Integer>();

		String url = "http://" + host
				+ "/easyrec-web/api/1.0/json/otherusersalsobought";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("itemid", itemID);
		parameters.put("userid", userName);
		parameters.put("numberOfResults", requireNumber.toString());

		String reponse = this.sendGetRequest(url, parameters);

		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);

		JSONObject recommended = object.getJSONObject("recommendeditems");
		JSONArray items = recommended.getJSONArray("item");
		for (int i = 0; i < items.size(); i++) {
			JSONObject temp = (JSONObject) items.get(i);
			result.add(temp.getInt("id"));
		}

		return result;
	}
	
	
	
	public List<Integer> recommendationsForUser(String userName,
			Integer requireNumber){
		List<Integer> result = new ArrayList<Integer>();

		String url = "http://" + host
				+ "/easyrec-web/api/1.0/json/recommendationsforuser";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("apikey", apikey);
		parameters.put("tenantid", tenantid);
		parameters.put("userid", userName);
		parameters.put("numberOfResults", requireNumber.toString());

		String reponse = this.sendGetRequest(url, parameters);

		JSONObject object = (JSONObject) JSONSerializer.toJSON(reponse);

		JSONObject recommended = object.getJSONObject("recommendeditems");
		JSONArray items = recommended.getJSONArray("item");
		for (int i = 0; i < items.size(); i++) {
			JSONObject temp = (JSONObject) items.get(i);
			result.add(temp.getInt("id"));
		}

		return result;
	}

	/**
	 * 发送http请求
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	protected String sendGetRequest(String url, Map<String, String> parameters) {

		String result = null;
		String getURL = null;

		if (url.startsWith("http://")) {

			getURL = url;

			if (parameters != null && !parameters.isEmpty()) {
				getURL += '?';
				Iterator<Entry<String, String>> iterator = parameters
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					getURL += entry.getKey() + "=" + entry.getValue();
					if (iterator.hasNext())
						getURL += '&';
				}
			}

			try {
				URLConnection conn = (new URL(getURL)).openConnection();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuffer.append(line);
				}
				reader.close();
				result = stringBuffer.toString();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

		return result;

	}

}
