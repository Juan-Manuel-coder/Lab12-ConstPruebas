package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @BeforeAll
    public static void setUp() {
        om.registerModule(new JavaTimeModule());
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindVisitOK() throws Exception {
        int ID = 1;
        String DESCRIPTION = "rabies shot";

        this.mockMvc.perform(get("/visits/" + ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    public void testFindVisitKO() throws Exception {
        int ID = 999;

        this.mockMvc.perform(get("/visits/" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVisit() throws Exception {
        String DESCRIPTION = "general checkup";
        LocalDate VISIT_DATE = LocalDate.of(2026, 11, 10);

        VisitDTO newVisitDTO = VisitDTO.builder()
                .petId(1)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .build();

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    public void testUpdateVisit() throws Exception {
        String UP_DESCRIPTION = "rabies shot booster";
        LocalDate VISIT_DATE = LocalDate.of(2026, 11, 10);

        VisitDTO upVisitDTO = VisitDTO.builder()
                .petId(1)
                .visitDate(VISIT_DATE)
                .description(UP_DESCRIPTION)
                .build();

        this.mockMvc.perform(put("/visits/1")
                        .content(om.writeValueAsString(upVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(UP_DESCRIPTION)));
    }

    @Test
    public void testDeleteVisit() throws Exception {
        String DESCRIPTION = "routine visit";
        LocalDate VISIT_DATE = LocalDate.of(2026, 11, 10);

        VisitDTO tempVisitDTO = VisitDTO.builder()
                .petId(1)
                .visitDate(VISIT_DATE)
                .description(DESCRIPTION)
                .build();

        ResultActions resultActions = this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(tempVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(responseContent).read("$.id");

        this.mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/visits/" + id))
                .andExpect(status().isNotFound());
    }
}