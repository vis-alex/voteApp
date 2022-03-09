package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.json.JsonUtil;
import com.alex.vis.voteApp.model.Restaurant;
import com.alex.vis.voteApp.service.restaurant.RestaurantService;
import com.alex.vis.voteApp.test_data.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.alex.vis.voteApp.TestUtil.userHttpBasic;
import static com.alex.vis.voteApp.test_data.RestaurantTestData.*;
import static com.alex.vis.voteApp.test_data.UserTestData.*;
import static com.alex.vis.voteApp.test_data.UserTestData.admin;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestaurantControllerTest {
    private static final String RESTAURANT_URL = RestaurantController.RESTAURANT_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(firstRestaurant, secondRestaurant, thirdRestaurant));
    }

    @Test
    void get() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(firstRestaurant));
    }

    @Test
    void getNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + NOT_FOUND)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(FIRST_RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + NOT_FOUND)
                        .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getVoteCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + FIRST_RESTAURANT_ID + "/count")
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("1"));
    }

    @Test
    void getVoteCountZero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + THIRD_RESTAURANT_ID + "/count")
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("0"));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void createWithRoleUser() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();

        mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithId() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(1000);

        mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant newRestaurant = new Restaurant(11, "");

        mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantService.get(FIRST_RESTAURANT_ID), updated);
    }

    @Test
    void updateWithUnConsistentId() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        updated.setId(SECOND_RESTAURANT_ID);
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithRoleUser() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(firstRestaurant.getId(), "");
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + FIRST_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity());
    }
}