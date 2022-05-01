package com.ratepay.ratepaytakehometask.dao;

import com.ratepay.ratepaytakehometask.entity.BugTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface BugTicketDao extends JpaRepository<BugTicket, UUID> {
}
