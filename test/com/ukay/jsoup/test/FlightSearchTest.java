package com.ukay.jsoup.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.ukay.jsoup.SearchFlightAtExpedia;

class FlightSearchTest {

	@Test
	void test() throws IOException {
		SearchFlightAtExpedia test = new SearchFlightAtExpedia();
		System.out.println(test.searchFlightAtExpedia("02/20/2019", "02/25/2019", "NYC", "DCA", 0, 1, 0, 'N'));
		System.out.println(test.searchFlightAtExpedia("02/20/2019", "NYC", "DCA", 0, 1, 0, 'N'));
	}

}
