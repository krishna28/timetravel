package com.gamesys.timetravel.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.gamesys.timetravel.TimeTravelApplication;
import com.gamesys.timetravel.domain.TimeTravel;
import com.gamesys.timetravel.domain.TimeTravelId;
import com.gamesys.timetravel.services.TimeTravelService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimeTravelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeTravelControllerTest {

	@LocalServerPort
	private int port;

	public static HttpHeaders headers = new HttpHeaders();

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	TimeTravelService timeTravelService;

	@BeforeAll
	public static void beforeAll() {
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void testGetTimeTravelData() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		TimeTravelId timeTravelId = new TimeTravelId("ABC32", "DHANBAD", LocalDate.now());
		TimeTravel timeTravel = new TimeTravel(timeTravelId);
		timeTravelService.save(timeTravel);

		ResponseEntity<String> response = restTemplate.exchange(generateURLWithPort("/api/timetravel"), HttpMethod.GET,
				entity, String.class);

		String expected = "[{\"timeTravelId\":{\"personalGalacticIdentifier\":\"ABC32\",\"place\":\"DHANBAD\"}}]";
		JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testSaveTimeTravelDataValidInput() throws JSONException {

		JSONObject request = new JSONObject();

		JSONObject innerRequest = new JSONObject();
		innerRequest.put("personalGalacticIdentifier", "ABC12");
		innerRequest.put("place", "DHANB");
		innerRequest.put("date", "12-04-2020");
		request.put("timeTravelId", innerRequest);

		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(generateURLWithPort("/api/timetravel"), HttpMethod.POST,
				entity, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testSaveTimeTravelDataShoudFailBadPGI() throws JSONException {

		TimeTravelId timeTravelId = new TimeTravelId("12ABC", "LONDON", LocalDate.now());
		TimeTravel timeTravel = new TimeTravel(timeTravelId);

		Assertions.assertThrows(Exception.class, () -> {
			timeTravelService.save(timeTravel);
		});

	}

	@Test
	public void testSaveTimeTravelDataShoudFailGreaterThan10Character() throws JSONException {

		TimeTravelId timeTravelId = new TimeTravelId("ABC12FCVN1343", "LONDON", LocalDate.now());
		TimeTravel timeTravel = new TimeTravel(timeTravelId);

		Assertions.assertThrows(Exception.class, () -> {
			timeTravelService.save(timeTravel);
		});

	}

	@Test
	public void testSaveTimeTravelDataShoudFailLessThan5Character() throws JSONException {

		TimeTravelId timeTravelId = new TimeTravelId("ABC", "LONDON", LocalDate.now());
		TimeTravel timeTravel = new TimeTravel(timeTravelId);

		Assertions.assertThrows(Exception.class, () -> {
			timeTravelService.save(timeTravel);
		});

	}

	@Test
	public void testGetTimeTravelDataById() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		LocalDate today = LocalDate.now();

		TimeTravelId timeTravelId = new TimeTravelId("Z12Z33", "LONDON", today);
		TimeTravel timeTravel = new TimeTravel(timeTravelId);
		timeTravelService.save(timeTravel);

		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		ResponseEntity<String> response = restTemplate.exchange(
				generateURLWithPort("/api/timetravel/Z12Z33/LONDON/" + formattedDate), HttpMethod.GET, entity,
				String.class);

		String expected = "{\"timeTravelId\":{\"personalGalacticIdentifier\":\"Z12Z33\",\"place\":\"LONDON\"}}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetTimeTravelDataByDate() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		LocalDate today = LocalDate.now();

		TimeTravelId timeTravelId = new TimeTravelId("TARN12", "DUBAI", today);
		TimeTravel timeTravel = new TimeTravel(timeTravelId);
		timeTravelService.save(timeTravel);

		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		ResponseEntity<String> response = restTemplate.exchange(
				generateURLWithPort("/api/timetravel/allByDate/" + formattedDate), HttpMethod.GET, entity,
				String.class);

		String expected = "[{\"timeTravelId\":{\"personalGalacticIdentifier\":\"TARN12\",\"place\":\"DUBAI\"}}]";
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetTimeTravelDataByPGI() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		LocalDate today = LocalDate.now();

		TimeTravelId timeTravelId = new TimeTravelId("TKLO1344", "LONDON", today);
		TimeTravel timeTravel = new TimeTravel(timeTravelId);
		timeTravelService.save(timeTravel);

		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		ResponseEntity<String> response = restTemplate.exchange(
				generateURLWithPort("/api/timetravel/allByPGI/TKLO1344"), HttpMethod.GET, entity, String.class);

		String expected = "[{\"timeTravelId\":{\"personalGalacticIdentifier\":\"TKLO1344\",\"place\":\"LONDON\"}}]";
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testSaveTimeTravelDuplicateData() throws JSONException {

		JSONObject request = new JSONObject();

		JSONObject innerRequest = new JSONObject();
		innerRequest.put("personalGalacticIdentifier", "ABC12");
		innerRequest.put("place", "DHANB");
		innerRequest.put("date", "12-04-2020");
		request.put("timeTravelId", innerRequest);

		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		ResponseEntity<String> response1 = restTemplate.exchange(generateURLWithPort("/api/timetravel"),
				HttpMethod.POST, entity, String.class);

		ResponseEntity<String> response2 = restTemplate.exchange(generateURLWithPort("/api/timetravel"),
				HttpMethod.POST, entity, String.class);

		assertEquals(HttpStatus.CREATED, response1.getStatusCode());

		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());

	}

	private String generateURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}

	@AfterEach
	public void after() {
		timeTravelService.deleteAll();
	}

}
