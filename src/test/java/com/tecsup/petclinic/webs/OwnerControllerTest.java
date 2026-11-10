package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
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
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindOwnerOK() throws Exception {
        int ID = 1;
        String FIRST_NAME = "George";
        String LAST_NAME = "Franklin";

        this.mockMvc.perform(get("/owners/" + ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)));
    }

    @Test
    public void testFindOwnerKO() throws Exception {
        int ID = 999;

        this.mockMvc.perform(get("/owners/" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwner() throws Exception {
        String FIRST_NAME = "Mariano";
        String LAST_NAME = "Santi";
        String ADDRESS = "123 Main St";
        String CITY = "Springfield";
        String TELEPHONE = "6085551023";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        this.mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)));
    }

    @Test
    public void testUpdateOwner() throws Exception {
        String FIRST_NAME = "George";
        String UP_LAST_NAME = "FranklinUpdated";

        OwnerDTO upOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(UP_LAST_NAME)
                .build();

        this.mockMvc.perform(put("/owners/1")
                        .content(om.writeValueAsString(upOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(UP_LAST_NAME)));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        String FIRST_NAME = "Temp";
        String LAST_NAME = "Owner";

        OwnerDTO tempOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        ResultActions resultActions = this.mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(tempOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(responseContent).read("$.id");

        this.mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/owners/" + id))
                .andExpect(status().isNotFound());
    }
}