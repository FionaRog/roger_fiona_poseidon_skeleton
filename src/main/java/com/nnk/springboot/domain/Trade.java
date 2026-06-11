package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entity representing a trade stored in the trade table.
 */
@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TradeId")
    Integer tradeId;
    @Column(nullable = false)
    String account;
    @Column(nullable = false)
    String type;
    Double buyQuantity;
    Double sellQuantity;
    Double buyPrice;
    Double sellPrice;
    String benchmark;
    Timestamp tradeDate;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;

}
