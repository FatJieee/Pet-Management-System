package com.jieee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchase {

    private Integer id;
    private Integer productId;
    private String productName;
    private Integer userId;
    private String userName;
    private String address;
    private String image;
    private Integer quantity;
    private float amount;
    private float price;
    private float shippingFee;
    private String method;
    private String rating;
    private String code;
    private LocalDateTime purchaseTime;
    private LocalDateTime updateTime;
}
