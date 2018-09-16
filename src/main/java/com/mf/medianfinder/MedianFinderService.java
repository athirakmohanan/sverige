package com.mf.medianfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MedianFinderService {
	/**
	 * In this method, I have written the code to find the exchange rates during last year
	 * End point 'http://fx.modfin.se/2010-01-01/2010-01-05?symbols=usd,sek' is used to find out the exchange rates
	 * @param prevYearStartDate
	 * @param prevYearEndDate
	 * @return median value of Double type
	 */
	public double findMedian(String prevYearStartDate, String prevYearEndDate) {
		
		String output = "";
		List<Exchange> exchangeList = new ArrayList<Exchange>();
		double medianValue = 0.0;

		try {
			URL url = new URL("http://fx.modfin.se/" + prevYearStartDate + "/" + prevYearEndDate + "?symbols=usd,sek");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Transaction failed with HTTP error code : " + connection.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((output = br.readLine()) != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				exchangeList = objectMapper.readValue(output, new TypeReference<List<Exchange>>() {
				});
			}

			Exchange[] exchangeObjectArray = (Exchange[]) exchangeList.toArray(new Exchange[exchangeList.size()]);

			CalculateMedian calculateMedian = new CalculateMedian();

			medianValue = calculateMedian.findMedian(exchangeObjectArray);
			connection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return medianValue;
	}

}
