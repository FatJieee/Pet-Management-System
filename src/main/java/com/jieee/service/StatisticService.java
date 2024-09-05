package com.jieee.service;


import com.jieee.pojo.Statistic;

public interface StatisticService {
    Statistic getTotalUser();

    Statistic getTotalProduct();

    Statistic getTotalTransaction();
}
