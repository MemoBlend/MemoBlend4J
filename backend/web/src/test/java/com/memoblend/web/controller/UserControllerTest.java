package com.memoblend.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.memoblend.web.WebApplication;

/**
 * {@link UserController} クラスのテストクラスです。
 */
@SpringJUnitConfig
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser
  void testGetUser_正常系_ユーザーを返す() throws Exception {
    long id = 1;
    this.mockMvc.perform(get("/api/user/" + id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser
  void testGetUser_異常系_ユーザーが存在しない() throws Exception {
    long id = 999;
    this.mockMvc.perform(get("/api/user/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testPostUser_正常系_ユーザーを登録する() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode userJsonNode = objectMapper.createObjectNode();
    userJsonNode.put("name", "Test name");
    userJsonNode.put("authId", "user_1");
    String userJson = objectMapper.writeValueAsString(userJsonNode);

    this.mockMvc.perform(post("/api/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  @WithMockUser
  void testDeleteUser_正常系_ユーザーを削除する() throws Exception {
    long id = 10;
    this.mockMvc.perform(delete("/api/user/" + id))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void testDeleteUser_異常系_ユーザーが存在しない() throws Exception {
    long id = 999;
    this.mockMvc.perform(delete("/api/user/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testPutUser_正常系_ユーザーを更新する() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode userJsonNode = objectMapper.createObjectNode();
    userJsonNode.put("id", 1);
    userJsonNode.put("name", "Test name");
    userJsonNode.put("authId", "user_1");
    String userJson = objectMapper.writeValueAsString(userJsonNode);

    this.mockMvc.perform(put("/api/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void testPutUser_異常系_ユーザーが存在しない() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode userJsonNode = objectMapper.createObjectNode();
    userJsonNode.put("id", 999);
    userJsonNode.put("name", "Test name");
    userJsonNode.put("authId", "user_999");
    String userJson = objectMapper.writeValueAsString(userJsonNode);

    this.mockMvc.perform(put("/api/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson))
        .andExpect(status().isNotFound());
  }
}
