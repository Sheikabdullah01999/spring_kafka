package com.grootan.kafka.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ConsumeMessage {
    @Id
    private String senderId;
    private String message;
}
