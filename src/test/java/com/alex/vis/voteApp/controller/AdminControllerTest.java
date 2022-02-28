package com.alex.vis.voteApp.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.alex.vis.voteApp.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.alex.vis.voteApp.TestUtil.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    private static final String ADMIN_URL = AdminController.ADMIN_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_URL + ADMIN_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(admin));
    }

    @Test
    void getNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_URL + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}