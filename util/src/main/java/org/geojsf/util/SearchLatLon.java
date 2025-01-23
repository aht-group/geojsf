package org.geojsf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchLatLon
{
	public static double[] findLatLong(String country, String province, String city)
	{
		try
		{
			if(province.contains("N'Djamena")) {province = "N'Djamena";}
			country =  URLEncoder.encode(country, "UTF-8");
			province = URLEncoder.encode(province, "UTF-8");
			city = URLEncoder.encode(city, "UTF-8");
		}
		catch (IOException e) {return null;}

		String url = String.format("https://nominatim.openstreetmap.org/search?country=%s&state=%s&city=%s&format=json&limit=1",
                country, province, city);
		try
		{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(response.toString());
				if (rootNode.isArray() && rootNode.size() > 0)
				{
					JsonNode locationNode = rootNode.get(0);
					double lat = locationNode.get("lat").asDouble();
					double lon = locationNode.get("lon").asDouble();
					return new double[] { lat, lon };
				}
			}
		}
		catch (IOException e) {return null;}
		return null;
	}

	public static double[] findLatLongProvince(String country, String province)
	{
		try
		{
			if(province.contains("N'Djamena")) {province = "N'Djamena";}
			country =  URLEncoder.encode(country, "UTF-8");
			province = URLEncoder.encode(province, "UTF-8");
		}
		catch (IOException e) {return null;}

		String url = String.format("https://nominatim.openstreetmap.org/search?country=%s&state=%s&format=json&limit=1",
                country, province);
		try
		{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(response.toString());
				if (rootNode.isArray() && rootNode.size() > 0)
				{
					JsonNode locationNode = rootNode.get(0);
					double lat = locationNode.get("lat").asDouble();
					double lon = locationNode.get("lon").asDouble();
					return new double[] { lat, lon };
				}
			}
		}
		catch (IOException e) {return null;}
		return null;
	}
}
