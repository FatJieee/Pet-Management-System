package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Integer id;
    private Integer productId;
    private String categoryName;
    private String productName;
    private String description;
    private String image;
    private Integer userId;
    private Integer quantity;
    private Float price;
    private LocalDateTime addedTime;
    private LocalDateTime modifyTime;
}
