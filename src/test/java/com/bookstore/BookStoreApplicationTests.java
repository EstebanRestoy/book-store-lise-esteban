package com.bookstore;

import com.bookstore.controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookStoreApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookController bookController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(bookController).isNotNull();
	}

	@Test
	public void testingAllBooksGET() throws Exception {
		this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"isbn\":\"154871564789\",\"name\":\"Livre 1\",\"quantity\":10},{\"isbn\":\"545157454574\",\"name\":\"Livre 2\",\"quantity\":2},{\"isbn\":\"141574574788\",\"name\":\"Livre 3\",\"quantity\":0}]"));
	}

	@Test
	public void testingOneBookGet() throws Exception {

		this.mockMvc.perform(get("/book/154871564789")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"isbn\":\"154871564789\",\"name\":\"Livre 1\",\"quantity\":10}"));

		this.mockMvc.perform(get("/book/999")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testingA404() throws Exception {
		this.mockMvc.perform(get("/aFakeRoute")).andDo(print()).andExpect(status().isNotFound());

	}



}
