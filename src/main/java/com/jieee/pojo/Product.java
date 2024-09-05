package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private Integer categoryId;
    private String categoryName;
    private Integer[] categories;
    private String name;
    private String description;
    private String image;
    private float price;
    private float minPrice;
    private float maxPrice;
    private float cost;
    private Integer stock;
    private Integer sold;
    private float rating;
    private Integer status;
    private Integer sales;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
