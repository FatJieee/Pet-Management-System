package com.jieee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseHistory {

    private Integer id;
    private Integer userId;
    private Integer quantity;
    private Float amount;
    private String method;
    private String address;
    private Float rating;
    private LocalDateTime purchaseTime;
}
