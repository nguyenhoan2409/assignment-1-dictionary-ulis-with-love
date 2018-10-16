/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: GoogleAPI						|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

package com.util;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleAPI {

	public String callTranslate (String langFrom, String langTo, String word) throws Exception {
		String url = "https://translate.googleapis.com/translate_a/single?" +
				"client=gtx&" +
				"sl=" + langFrom +
				"&tl=" + langTo +
				"&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		StringBuilder response;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		}

		return parseResult(response.toString());
	}

	public String parseResult (String inputJson) throws Exception {
		JSONArray jsonArray = (JSONArray)(new JSONArray(inputJson)).get(0);
		String res = "";
		for (int i = 0; i < jsonArray.length(); ++i) {
			if (jsonArray.get(i) == null) break;
			res += ((JSONArray)jsonArray.get(i)).get(0).toString();
		}
		return res;
	}

}






