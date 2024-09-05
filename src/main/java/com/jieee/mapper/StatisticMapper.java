package com.jieee.mapper;


import com.jieee.pojo.Statistic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticMapper {

    Statistic getTotalUser();

    Statistic getTotalProduct();

    Integer getTotalTransaction();

    Integer getPurchaseToday();

    Integer getPurchaseYesterday();

    Integer getOrderToday();

    Integer getOrderYesterday();

    Integer getsalesToday();

    Integer getsalesYesterday();

    Statistic test();
}
