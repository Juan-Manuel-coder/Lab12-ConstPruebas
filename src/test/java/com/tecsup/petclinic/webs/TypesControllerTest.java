package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.TypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class TypesControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindTypeOK() throws Exception {
        int ID = 1;
        String NAME = "cat";

        this.mockMvc.perform(get("/types/" + ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.name", is(NAME)));
    }

    @Test
    public void testFindTypeKO() throws Exception {
        int ID = 999;

        this.mockMvc.perform(get("/types/" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateType() throws Exception {
        String NAME = "hamster";

        TypeDTO newTypeDTO = TypeDTO.builder()
                .name(NAME)
                .build();

        this.mockMvc.perform(post("/types")
                        .content(om.writeValueAsString(newTypeDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(NAME)));
    }

    @Test
    public void testUpdateType() throws Exception {
        String UP_NAME = "cat_updated";

        TypeDTO upTypeDTO = TypeDTO.builder()
                .name(UP_NAME)
                .build();

        this.mockMvc.perform(put("/types/1")
                        .content(om.writeValueAsString(upTypeDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(UP_NAME)));
    }

    @Test
    public void testDeleteType() throws Exception {
        String NAME = "rabbit";

        TypeDTO tempTypeDTO = TypeDTO.builder()
                .name(NAME)
                .build();

        ResultActions resultActions = this.mockMvc.perform(post("/types")
                        .content(om.writeValueAsString(tempTypeDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(responseContent).read("$.id");

        this.mockMvc.perform(delete("/types/" + id))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/types/" + id))
                .andExpect(status().isNotFound());
    }
}