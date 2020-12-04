package com.example.demo.moneySpreading.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.common.domain.KpayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MoneySrpeadingControllerTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	@Autowired
    private ObjectMapper objectMapper;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}
	
	@Test
	public void testSprayMoney() throws Exception {

		
		Map<String, String> params = new HashMap<>();
		params.put("moneyAmount", "50000");
		params.put("userCount", "5");
		String content = objectMapper.writeValueAsString(params);
		
		final ResultActions actions = mockMvc.perform(post("/kpay/moneyspreading")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", "11111")
				.header("X-ROOM-ID", "R01")
				.content(content));
		
		// then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.code", is("SUCCESS")))
			.andDo(print());
	}

	@Test
	public void testTakeMoney() throws Exception {
		
		// given
		String senderId = "11111";
		String receiverId = "45777";
		String roomId = "R01";
		
		Map<String, String> params = new HashMap<>();
		params.put("moneyAmount", "50000");
		params.put("userCount", "5");
		
		MvcResult result = mockMvc.perform(post("/kpay/moneyspreading")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", senderId)
				.header("X-ROOM-ID", roomId)
				.content(objectMapper.writeValueAsString(params)))
				.andReturn();

		// get token
		KpayResponse res = objectMapper.readValue(result.getResponse().getContentAsString(), KpayResponse.class);
		Map<String, String> resultMap = (Map<String, String>) res.getResult();
		
		String token = resultMap.get("token");
		
		// when
		final ResultActions actions = mockMvc.perform(post("/kpay/takemoney")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", receiverId)
				.header("X-ROOM-ID", roomId)
				.header("X-TOKEN", token));
		
		// then
		actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.code", is("SUCCESS")))
				.andDo(print());
	}

	@Test
	public void testDescribeMoneySpraying() throws Exception {
		
		String userId = "11111";
		String roomId = "R01";
		
		Map<String, String> params = new HashMap<>();
		params.put("moneyAmount", "50000");
		params.put("userCount", "5");
		
		MvcResult result = mockMvc.perform(post("/kpay/moneyspreading")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", userId)
				.header("X-ROOM-ID", roomId)
				.content(objectMapper.writeValueAsString(params)))
				.andReturn();

		KpayResponse res = objectMapper.readValue(result.getResponse().getContentAsString(), KpayResponse.class);
		Map<String, String> resultMap = (Map<String, String>) res.getResult();
		
		String token = resultMap.get("token");
		
		mockMvc.perform(get("/kpay/readinfo")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.header("X-USER-ID", userId)
					.header("X-ROOM-ID", roomId)
					.header("X-TOKEN", token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.code", is("SUCCESS")))
				.andDo(print());
	}
}
