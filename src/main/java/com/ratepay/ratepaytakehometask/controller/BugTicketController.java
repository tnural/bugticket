package com.ratepay.ratepaytakehometask.controller;

import com.ratepay.ratepaytakehometask.entity.BugTicket;
import com.ratepay.ratepaytakehometask.service.BugTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/bugticket")
public class BugTicketController {

    @Autowired
    BugTicketService bugTicketService;

    @GetMapping
    public ResponseEntity<List<BugTicket>> getBugTickets() {
        return new ResponseEntity<>(bugTicketService.getBugTickets().get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugTicket> getBugTicketById(@PathVariable UUID id) {
        var bugTicketPersisted = bugTicketService.getBugTicketById(id);
        if (bugTicketPersisted.isEmpty())
            return ResponseEntity.unprocessableEntity().build();
        return Optional
                .ofNullable(bugTicketPersisted)
                .map(checkout -> ResponseEntity.ok().body(bugTicketPersisted.get())).get();
    }

    @PutMapping
    public ResponseEntity<BugTicket> modifyBugTicket(@RequestBody BugTicket bugTicket) {
        var bugTicketPersisted = bugTicketService.modifyBugTicket(bugTicket);
        if (bugTicketPersisted.isEmpty())
            return ResponseEntity.unprocessableEntity().build();
        return bugTicketPersisted.map(c -> ResponseEntity.ok().body(c)).get();

    }

    @PostMapping
    public ResponseEntity<BugTicket> saveBugTicket(@RequestBody BugTicket bugTicket) {
        return Optional
                .ofNullable(bugTicketService.saveBugTicket(bugTicket))
                .map(ck -> ResponseEntity.ok().body(ck)).get();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteBugTicket(@PathVariable UUID id) {
        var bugTicket = bugTicketService.deleteBugTicketById(id);
        if (bugTicket.isEmpty())
            return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.ok().body("Bug ticket has been deleted.");
    }
}
