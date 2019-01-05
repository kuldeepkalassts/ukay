package com.ukay.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SearchFlightAtExpedia {
	
	static Logger				log			= Logger.getLogger(SearchFlightAtExpedia.class.getName());
	public static final String	USER_AGENT	= "Java/1.6.0_26";

	
	
	//@formatter:off
	/**
	 * 
	 * @param fromDate: MM/DD/YYYY
	 * @param toDate: MM/DD/YYYY
	 * @param type: roundtrip / oneway
	 * @param source : IETA Codes i.e. NYC
	 * @param destination : IETA Codes i.e. DCA
	 * @param childs : 0
	 * @param adults : 1
	 * @param seniors: 0
	 * @param infant : Y or N
	 * @return
	 * @throws IOException 
	 */
	//@formatter:on

	public String searchFlightAtExpedia(String date, String source, String destination, int childs, int adults, int seniors, char infant) throws IOException {

		//@formatter:off

			String target = new String(
					"https://www.expedia.com/Flights-Search"
					+ "?flight-type=on"
					+ "&starDate=$date"
					+ "&mode=search"
					+ "&trip=oneway"
					+ "&leg1=from:$source,to:$destination,departure:$dateTANYT"
					+ "&passengers=children:$childs,adults:$adults,seniors:$seniors,infantinlap:$infant"
					);

			target = target.replaceAll("\\$date", date);
			target = target.replaceAll("\\$source", source);
			target = target.replaceAll("\\$destination", destination);
			target = target.replaceAll("\\$childs", "" + childs);
			target = target.replaceAll("\\$adults", "" + adults);
			target = target.replaceAll("\\$seniors", "" + seniors);
			target = target.replaceAll("\\$infant", "" + infant);

			return searchFlightAtExpedia(target);
		}
	
	
	//@formatter:off
		/**
		 * 
		 * @param fromDate: MM/DD/YYYY
		 * @param toDate: MM/DD/YYYY
		 * @param type: roundtrip / oneway
		 * @param source : IETA Codes i.e. NYC
		 * @param destination : IETA Codes i.e. DCA
		 * @param childs : 0
		 * @param adults : 1
		 * @param seniors: 0
		 * @param infant : Y or N
		 * @return
		 * @throws IOException 
		 */
		//@formatter:on
	
	
	public String searchFlightAtExpedia(String fromDate, String toDate,String source, String destination, int childs, int adults, int seniors,
			char infant) throws IOException {

		//@formatter:off

		String target = new String(
				"https://www.expedia.com/Flights-Search"
				+ "?flight-type=on"
				+ "&starDate=$from_date"
				+ "&endDate=$to_date"
				+ "&mode=search"
				+ "&trip=roundtrip"
				+ "&leg1=from:$source,to:$destination,departure:$from_dateTANYT"
				+ "&leg2=from:$destination,to:$source,departure:$to_dateTANYT"
				+ "&passengers=children:$childs,adults:$adults,seniors:$seniors,infantinlap:$infant"
				);
		//@formatter:on

		target = target.replaceAll("\\$from_date", fromDate);
		target = target.replaceAll("\\$to_date", toDate);
		target = target.replaceAll("\\$source", source);
		target = target.replaceAll("\\$destination", destination);
		target = target.replaceAll("\\$childs", "" + childs);
		target = target.replaceAll("\\$adults", "" + adults);
		target = target.replaceAll("\\$seniors", "" + seniors);
		target = target.replaceAll("\\$infant", "" + infant);

		return searchFlightAtExpedia(target);
	}

	private String searchFlightAtExpedia(String query) throws IOException {
		log.info("Scanning Site .. ");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Map<String, String>> flightResultCollection = new ArrayList<Map<String, String>>();

		Document doc = Jsoup.connect(query).userAgent(USER_AGENT).get();

		log.info("Found Title : " + doc.title());
		Elements FlightInformationListContainer = doc.getElementsByAttributeValue("data-test-id", "listing-summary");
		int resultCount = 0;
		for (Element single_fligh_container : FlightInformationListContainer) {
			Map<String, String> flightResult = new HashMap<String, String>();
			flightResult.put("departure-time", single_fligh_container.getElementsByAttributeValue("data-test-id", "departure-time").text());
			flightResult.put("arrival-time", single_fligh_container.getElementsByAttributeValue("data-test-id", "arrival-time").text());
			flightResult.put("duration", single_fligh_container.getElementsByAttributeValue("data-test-id", "duration").text());
			flightResult.put("stops", single_fligh_container.getElementsByAttribute("data-test-num-stops").text());
			flightResult.put("airline-name", single_fligh_container.getElementsByAttributeValue("data-test-id", "airline-name").text());
			flightResult.put("flight-info", single_fligh_container.getElementsByAttributeValue("data-test-id", "flight-info").text());
			flightResult.put("price-column", single_fligh_container.getElementsByAttributeValue("data-test-id", "price-column").text());
			flightResultCollection.add(resultCount++, flightResult);
		}
		log.info("Done !!! ");
		return gson.toJson(flightResultCollection);
	}

}
