package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransaction {

    private Integer transactionId;
    private Integer productId;
    private String productName;
    private Integer productCategoryId;
    private String productCategoryName;
    private Integer userId;
    private String userName;
    private LocalDateTime transactionTime;
    private LocalDateTime updateTime;


}
