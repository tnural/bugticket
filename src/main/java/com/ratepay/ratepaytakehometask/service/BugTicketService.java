package com.ratepay.ratepaytakehometask.service;

import com.ratepay.ratepaytakehometask.dao.BugTicketDao;
import com.ratepay.ratepaytakehometask.entity.BugTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BugTicketService {
    @Autowired
    BugTicketDao bugTicketDao;

    public Optional<List<BugTicket>> getBugTickets() {
        return Optional.of(this.bugTicketDao.findAll());
    }

    public Optional<BugTicket> getBugTicketById(UUID id) {
        return this.bugTicketDao.findById(id);
    }


    public BugTicket saveBugTicket(BugTicket bugTicket) {
        return this.bugTicketDao.save(bugTicket);
    }

    public Optional<BugTicket> modifyBugTicket(BugTicket bugTicket) {
        if (!bugTicketDao.existsById(bugTicket.getId()))
            return Optional.empty();

        return Optional
                .ofNullable(bugTicketDao.save(bugTicket));
    }

    public Optional<Object> deleteBugTicketById(UUID id) {
        if(!this.bugTicketDao.existsById(id)){
            return Optional.empty();
        }
        this.bugTicketDao.deleteById(id);
        return Optional.of(true);
    }


}
