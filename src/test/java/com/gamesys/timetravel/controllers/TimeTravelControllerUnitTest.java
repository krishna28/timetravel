package com.gamesys.timetravel.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gamesys.timetravel.domain.TimeTravel;
import com.gamesys.timetravel.domain.TimeTravelId;
import com.gamesys.timetravel.services.TimeTravelService;
import com.gamesys.timetravel.services.ValidationErrorService;

@RunWith(SpringRunner.class)
@WebMvcTest(TimeTravelController.class)
class TimeTravelControllerUnitTest {

	@MockBean
	private TimeTravelService timeTravelService;

	@MockBean
	private ValidationErrorService validationErrorService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSaveTimeTravelData() throws Exception {

		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		TimeTravelId timeTravelId = new TimeTravelId("ABC32", "DHANBAD", LocalDate.now());
		TimeTravel timeTravel = new TimeTravel(timeTravelId);

		String timeTravelJson = "{\r\n" + "    \"timeTravelId\":{\r\n"
				+ "         \"personalGalacticIdentifier\":\"ABC32\",\r\n" + "        \"place\":\"DHANBAD\",\r\n"
				+ "        \"date\":\"" + formattedDate + "\"\r\n" + "    }\r\n" + "}";

		Mockito.when(timeTravelService.save(Mockito.any(TimeTravel.class))).thenReturn(timeTravel);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/timetravel")
				.accept(MediaType.APPLICATION_JSON).content(timeTravelJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

}
