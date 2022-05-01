package com.ratepay.ratepaytakehometask.entity;

import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bug_ticket")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class BugTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String name;
    private String description;

}
