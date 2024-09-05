package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private Integer totalUser;
    private double ratioMale;
    private double ratioFemale;
    private Integer totalProduct;
    private Integer negativeStock;
    private Integer costExceeding;
    private double ratioRate;
    private Integer totalTransaction;
    private Integer purchase;
    private Float purchaseYesterday;
    private Integer order;
    private Float orderYesterday;
    private Integer productSales;
    private Float productSalesYesterday;
    private Integer totalComment;
    private LocalDateTime currentDate;
    private LocalDateTime testDate;

}
