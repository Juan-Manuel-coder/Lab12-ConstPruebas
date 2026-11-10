package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
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
public class VetSpecialtyControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindVetSpecialtyOK() throws Exception {
        int VET_ID = 2;

        this.mockMvc.perform(get("/vet_specialties/" + VET_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vetId", is(VET_ID)));
    }

    @Test
    public void testFindVetSpecialtyKO() throws Exception {
        int VET_ID = 999;

        this.mockMvc.perform(get("/vet_specialties/" + VET_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVetSpecialty() throws Exception {
        int VET_ID = 6;
        int SPECIALTY_ID = 1;

        VetSpecialtyDTO newDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .build();

        this.mockMvc.perform(post("/vet_specialties")
                        .content(om.writeValueAsString(newDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vetId", is(VET_ID)))
                .andExpect(jsonPath("$.specialtyId", is(SPECIALTY_ID)));
    }

    @Test
    public void testUpdateVetSpecialty() throws Exception {
        int VET_ID = 2;
        int UP_SPECIALTY_ID = 3;

        VetSpecialtyDTO upDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(UP_SPECIALTY_ID)
                .build();

        this.mockMvc.perform(put("/vet_specialties/2")
                        .content(om.writeValueAsString(upDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vetId", is(VET_ID)))
                .andExpect(jsonPath("$.specialtyId", is(UP_SPECIALTY_ID)));
    }

    @Test
    public void testDeleteVetSpecialty() throws Exception {
        int VET_ID = 5;
        int SPECIALTY_ID = 1;

        VetSpecialtyDTO tempDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .build();

        ResultActions resultActions = this.mockMvc.perform(post("/vet_specialties")
                        .content(om.writeValueAsString(tempDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(responseContent).read("$.vetId");

        this.mockMvc.perform(delete("/vet_specialties/" + id))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/vet_specialties/" + id))
                .andExpect(status().isNotFound());
    }
}