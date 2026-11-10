package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VetDTO;
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
public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindVetOK() throws Exception {
        int ID = 1;
        String FIRST_NAME = "James";
        String LAST_NAME = "Carter";

        this.mockMvc.perform(get("/vets/" + ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)));
    }

    @Test
    public void testFindVetKO() throws Exception {
        int ID = 999;

        this.mockMvc.perform(get("/vets/" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Mendoza";

        VetDTO newVetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        this.mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)));
    }

    @Test
    public void testUpdateVet() throws Exception {
        String FIRST_NAME = "James";
        String UP_LAST_NAME = "CarterUpdated";

        VetDTO upVetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(UP_LAST_NAME)
                .build();

        this.mockMvc.perform(put("/vets/1")
                        .content(om.writeValueAsString(upVetDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(UP_LAST_NAME)));
    }

    @Test
    public void testDeleteVet() throws Exception {
        String FIRST_NAME = "Temporary";
        String LAST_NAME = "Vet";

        VetDTO tempVetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        ResultActions resultActions = this.mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(tempVetDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(responseContent).read("$.id");

        this.mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/vets/" + id))
                .andExpect(status().isNotFound());
    }
}