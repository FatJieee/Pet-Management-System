package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String userName;
    private String productName;
    private String type;
    private Integer quantity;
    private Float cost;
    private String notes;
    private LocalDateTime transactionTime;
    private LocalDateTime updateTime;
}
