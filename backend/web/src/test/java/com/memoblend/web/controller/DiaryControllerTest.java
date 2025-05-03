package com.memoblend.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.memoblend.web.WebApplication;

/**
 * {@link DiaryController} クラスのテストクラスです。
 */
@SpringJUnitConfig
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
class DiaryControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser
  void testGetDiaries_正常系_日記のリストを返す() throws Exception {
    this.mockMvc.perform(get("/api/diary/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetDiaries_異常系_権限が足りない() throws Exception {
    this.mockMvc.perform(get("/api/diary/list"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testGetDiariesByUserId_正常系_日記のリストを返す() throws Exception {
    long userId = 1;
    this.mockMvc.perform(get("/api/diary/list/" + userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetDiariesByUserId_異常系_権限が足りない() throws Exception {
    long userId = 1;
    this.mockMvc.perform(get("/api/diary/list/" + userId))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testGetDiary_正常系_日記を返す() throws Exception {
    long id = 1;
    this.mockMvc.perform(get("/api/diary/" + id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser
  void testGetDiary_異常系_日記が存在しない() throws Exception {
    long id = 999;
    this.mockMvc.perform(get("/api/diary/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetDiary_異常系_権限が足りない() throws Exception {
    long id = 1;
    this.mockMvc.perform(get("/api/diary/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testPostDiary_正常系_日記を登録する() throws Exception {
    String diaryJson = "{"
        + "\"userId\": 1, "
        + "\"title\": \"Test title\", "
        + "\"content\": \"Test content\", "
        + "\"createdDate\": \"2025-01-01\""
        + "}";
    this.mockMvc.perform(post("/api/diary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(diaryJson))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  void testPostDiary_異常系_権限が足りない() throws Exception {
    String diaryJson = "{"
        + "\"userId\": 1, "
        + "\"title\": \"Test title\", "
        + "\"content\": \"Test content\", "
        + "\"createdDate\": \"2025-01-01\""
        + "}";
    this.mockMvc.perform(post("/api/diary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(diaryJson))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testDeleteDiary_正常系_日記を削除する() throws Exception {
    long id = 10;
    this.mockMvc.perform(delete("/api/diary/" + id))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void testDeleteDiary_異常系_日記が存在しない() throws Exception {
    long id = 999;
    this.mockMvc.perform(delete("/api/diary/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteDiary_異常系_権限が足りない() throws Exception {
    long id = 1;
    this.mockMvc.perform(delete("/api/diary/" + id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void testPutDiary_正常系_日記を更新する() throws Exception {
    String diaryJson = "{"
        + "\"id\": 1,"
        + "\"userId\": 1, "
        + "\"title\": \"Test title\", "
        + "\"content\": \"Test content\", "
        + "\"createdDate\": \"2025-01-01\""
        + "}";
    this.mockMvc.perform(put("/api/diary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(diaryJson))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void testPutDiary_異常系_日記が存在しない() throws Exception {
    String diaryJson = "{"
        + "\"id\": 999,"
        + "\"userId\": 1, "
        + "\"title\": \"Test title\", "
        + "\"content\": \"Test content\", "
        + "\"createdDate\": \"2025-01-01\""
        + "}";
    this.mockMvc.perform(put("/api/diary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(diaryJson))
        .andExpect(status().isNotFound());
  }

  @Test
  void testPutDiary_異常系_権限が足りない() throws Exception {
    String diaryJson = "{"
        + "\"id\": 1,"
        + "\"userId\": 1, "
        + "\"title\": \"Test title\", "
        + "\"content\": \"Test content\", "
        + "\"createdDate\": \"2025-01-01\""
        + "}";
    this.mockMvc.perform(put("/api/diary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(diaryJson))
        .andExpect(status().isNotFound());
  }
}
