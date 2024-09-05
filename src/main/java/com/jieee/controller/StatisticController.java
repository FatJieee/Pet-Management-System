package com.jieee.controller;

import com.jieee.pojo.Result;
import com.jieee.pojo.Statistic;
import com.jieee.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistic")
@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("getTotalUser")
    public Result getTotalUser(){
        Statistic statistic = statisticService.getTotalUser();
        return Result.success(statistic);
    }

    @GetMapping("getTotalProduct")
    public Result getTotalProduct(){
        Statistic statistic = statisticService.getTotalProduct();
        return Result.success(statistic);
    }

    @GetMapping("getTotalTransaction")
    public Result getTotalTransaction(){
        Statistic statistic = statisticService.getTotalTransaction();
        return Result.success(statistic);
    }

}
