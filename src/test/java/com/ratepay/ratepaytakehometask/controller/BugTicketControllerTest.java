package com.ratepay.ratepaytakehometask.controller;

import com.ratepay.ratepaytakehometask.dao.BugTicketDao;
import com.ratepay.ratepaytakehometask.entity.BugTicket;
import com.ratepay.ratepaytakehometask.service.BugTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.hamcrest.CoreMatchers;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
@AutoConfigureMockMvc
class BugTicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    @Resource
    BugTicketService bugTicketService;

    @Mock
    BugTicketDao bugTicketDao;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(bugTicketService, "bugTicketDao", bugTicketDao);
    }

    @Test
    void getBugTickets() throws Exception {
        List<BugTicket> bugTickets = List.of(
                BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build(),
                BugTicket.builder().name("B").description("B").id(UUID.randomUUID()).build(),
                BugTicket.builder().name("C").description("C").id(UUID.randomUUID()).build()
        );
        Mockito.when(bugTicketDao.findAll()).thenReturn(bugTickets);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bugticket"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getBugTicketById() throws Exception {
        var bugTicket = BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build();
        Mockito.when(bugTicketDao.findById(any())).thenReturn(Optional.ofNullable(bugTicket));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bugticket/" + bugTicket.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(bugTicket.getId().toString())));
    }

    @Test
    void modifyBugTicket() throws Exception {
        var bugTicket = BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build();
        Mockito.when(bugTicketDao.existsById(bugTicket.getId())).thenReturn(true);
        Mockito.when(bugTicketDao.save(bugTicket)).thenReturn(bugTicket);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/bugticket")
                                .content(asJsonString(bugTicket))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(bugTicket.getId().toString())));
    }

    @Test
    void modifyBugNotExistingTicket() throws Exception {
        var bugTicket = BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build();
        Mockito.when(bugTicketDao.save(bugTicket)).thenReturn(bugTicket);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/bugticket")
                                .content(asJsonString(bugTicket))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void createBugTicket() throws Exception {
        var bugTicket = BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build();
        Mockito.when(bugTicketDao.save(bugTicket)).thenReturn(bugTicket);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/bugticket")
                                .content(asJsonString(bugTicket))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(bugTicket.getId().toString())));
    }

    @Test
    void deleteBugTicket() throws Exception {
        var bugTicket = BugTicket.builder().name("A").description("A").id(UUID.randomUUID()).build();
        Mockito.when(bugTicketDao.existsById(bugTicket.getId())).thenReturn(true);
        doNothing().when(bugTicketDao).deleteById(bugTicket.getId());
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/bugticket/" + bugTicket.getId().toString()))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            var objMapper = new ObjectMapper();
            objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}