package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.model.Vote;
import com.alex.vis.voteApp.service.vote.VoteService;
import com.alex.vis.voteApp.validation.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.alex.vis.voteApp.TestUtil.userHttpBasic;
import static com.alex.vis.voteApp.test_data.RestaurantTestData.*;
import static com.alex.vis.voteApp.test_data.UserTestData.*;
import static com.alex.vis.voteApp.test_data.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VoteControllerTest {
    private static final String VOTE_URL = VoteController.VOTE_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void vote() throws Exception {
        Vote vote = new Vote(null, USER_ID, FIRST_RESTAURANT_ID, LocalDateTime.of(99, 12, 31, 0, 0, 0));

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user)))
                .andExpect(status().isCreated());

        Vote created = VOTE_IGNORE_DATE_TIME_MATCHER.readFromJson(action);
        int newId = created.id();
        vote.setId(newId);

        VOTE_IGNORE_DATE_TIME_MATCHER.assertMatch(created, vote);
    }

    @Test
    void voteForUnExistingRestaurant() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void secondVoteToday() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + SECOND_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void devote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user)))
                .andExpect(status().isCreated());

        if (LocalTime.now().isAfter(ValidationUtil.endChangingVote)) {
            mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + "unvote/" + FIRST_RESTAURANT_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(userHttpBasic(user)))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(MockMvcRequestBuilders.post(VOTE_URL + "unvote/" + FIRST_RESTAURANT_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(userHttpBasic(user)))
                    .andExpect(status().isNoContent());
        }
    }//TODO Fix this datetime

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VOTE_URL)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_EQUALS_MATCHER.contentJson(firstVote, secondVote));
    }

    @Test
    void getAllWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VOTE_URL)
                        .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VOTE_URL + FIRST_VOTE_ID)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_EQUALS_MATCHER.contentJson(firstVote));
    }

    @Test
    void getWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VOTE_URL + FIRST_VOTE_ID)
                        .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }
}