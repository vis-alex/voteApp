package com.alex.vis.voteApp.controller;

import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.json.JsonUtil;
import com.alex.vis.voteApp.model.Dish;
import com.alex.vis.voteApp.service.dish.DishService;
import com.alex.vis.voteApp.test_data.DishTestData;
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
import static com.alex.vis.voteApp.test_data.DishTestData.*;
import static com.alex.vis.voteApp.test_data.RestaurantTestData.*;
import static com.alex.vis.voteApp.test_data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DishControllerTest {
    private static final String DISH_URL = DishController.DISH_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DishService dishService;

    @Test
    void getAll() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(DISH_URL)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISHES));
    }


    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DISH_URL + FIRST_DISH_ID)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(firstDish));
    }

    @Test
    void getNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(DISH_URL + NOT_FOUND)
                        .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllDishesForRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DISH_URL + "restaurant/" + FIRST_RESTAURANT_ID)
                        .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(firstDish, secondDish));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DISH_URL + FIRST_DISH_ID)
                        .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(FIRST_DISH_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DISH_URL + NOT_FOUND)
                        .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DISH_URL + FIRST_DISH_ID)
                        .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DISH_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void create() throws Exception {
        Dish newDish = DishTestData.getNew();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(DISH_URL)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(newId), newDish);
    }

    @Test
    void createWithRoleUser() throws Exception {
        Dish newDish = DishTestData.getNew();

        mockMvc.perform(MockMvcRequestBuilders.post(DISH_URL)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithId() throws Exception {
        Dish newDish = DishTestData.getNew();
        newDish.setId(1000);

        mockMvc.perform(MockMvcRequestBuilders.post(DISH_URL)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithNotFoundRestaurant() throws Exception {
        Dish newDish = DishTestData.getNew();

        mockMvc.perform(MockMvcRequestBuilders.post(DISH_URL)
                        .param("restaurant_id", String.valueOf(NOT_FOUND))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity());
    }
//TODO CHECK HERE status not found against unprocessable entity

    @Test
    void createInvalidDish() throws Exception {
        Dish newDish = new Dish(11, "", 0);

        mockMvc.perform(MockMvcRequestBuilders.post(DISH_URL)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishService.get(FIRST_DISH_ID), updated);
    }

    @Test
    void updateWithoutRestaurantId() throws Exception {
        Dish updated = DishTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishService.get(FIRST_DISH_ID), updated);
    }

    @Test
    void updateDishWithUnConsistentId() throws Exception {
        Dish updated = DishTestData.getUpdated();
        updated.setId(SECOND_DISH_ID);
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithRoleUser() throws Exception {
        Dish updated = DishTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithNotFoundRestaurant() throws Exception {
        Dish updated = DishTestData.getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .param("restaurant_id", String.valueOf(NOT_FOUND))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(user))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(firstDish.getId(), "", -5);
        mockMvc.perform(MockMvcRequestBuilders.put(DISH_URL + FIRST_DISH_ID)
                        .param("restaurant_id", String.valueOf(FIRST_RESTAURANT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity());
    }

}