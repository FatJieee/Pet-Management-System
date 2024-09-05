package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private Integer id;
    private Integer userId;
    private String userName;
    private String image;
    private Integer productId;
    private String productName;
    private Float rate;
    private String comment;
    private Float totalRate;
    private Integer totalCount;
    private Integer userCount1;
    private Integer userCount2;
    private Integer userCount3;
    private Integer userCount4;
    private Integer userCount5;
    private LocalDateTime addedTime;
    private LocalDateTime updateTime;
}
