package com.ratepay.ratepaytakehometask.service;

import com.ratepay.ratepaytakehometask.dao.BugTicketDao;
import com.ratepay.ratepaytakehometask.entity.BugTicket;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BugTicketServiceTest {

    @Mock
    BugTicketDao bugTicketDao;

    @InjectMocks
    BugTicketService bugTicketService;

    @Test
    void getBugTickets() {
        var bugTicketList = List.of(
                BugTicket.builder().name("A").description("A ticket").build(),
                BugTicket.builder().name("B").description("B ticket").build(),
                BugTicket.builder().name("C").description("C ticket").build()
        );

        Mockito.when(bugTicketDao.findAll()).thenReturn(bugTicketList);
        var expected = bugTicketService.getBugTickets();
        assertThat(expected.get().size()).isEqualTo(3);
    }

    @Test
    void getBugTicketById() {
        var bugTicket = BugTicket.builder().name("A").description("A ticket").build();
        Mockito.when(bugTicketDao.findById(any())).thenReturn(Optional.ofNullable(bugTicket));
        Mockito.when(bugTicketDao.existsById(any())).thenReturn(true);
        var expected = bugTicketService.getBugTicketById(bugTicket.getId()).get();
        assertThat(expected.getName().equals(bugTicket.getName()));
    }

    @Test
    void saveBugTicket() {
        var bugTicket = BugTicket.builder().name("A").description("A ticket").build();
        Mockito.when(bugTicketDao.save(bugTicket)).thenReturn(bugTicket);
        assertThat(bugTicket.getName()).isEqualTo(bugTicketService.saveBugTicket(bugTicket).getName());
    }

    @Test
    void modifyBugTicket(){
        var bugTicket = BugTicket.builder().name("A").description("A ticket").build();
        Mockito.when(bugTicketDao.existsById(bugTicket.getId())).thenReturn(true);
        Mockito.when(bugTicketDao.save(bugTicket)).thenReturn(bugTicket);
        assertThat(bugTicket.getName()).isEqualTo(bugTicketService.saveBugTicket(bugTicket).getName());
    }

    @Test
    void deleteBugTicketById() {
        Mockito.when((bugTicketDao.existsById(any()))).thenReturn(true);
        doNothing().when(bugTicketDao).deleteById(any());
        assertThat(this.bugTicketService.deleteBugTicketById(UUID.randomUUID())).isEqualTo(Optional.of(true));
    }
}