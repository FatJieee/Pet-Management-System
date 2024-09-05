package com.jieee.service.impl;

import com.jieee.mapper.StatisticMapper;
import com.jieee.pojo.Statistic;
import com.jieee.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public Statistic getTotalUser() {
        Statistic statistic = statisticMapper.getTotalUser();
        double maleCount = statistic.getRatioMale();
        double femaleCount = statistic.getRatioFemale();
        int total = (int) (maleCount + femaleCount);

        statistic.setTotalUser(total);
        statistic.setRatioMale(Math.round((maleCount / total) * 100));
        statistic.setRatioFemale(Math.round((femaleCount / total) * 100));
        log.info("total: {}",statistic);
        return statistic;
    }

    @Override
    public Statistic getTotalProduct() {
        Statistic statistic = statisticMapper.getTotalProduct();
        log.info("product: {}",statistic);
        return statistic;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Statistic getTotalTransaction() {
        Statistic statistic = new Statistic();

        statistic.setTotalTransaction(statisticMapper.getTotalTransaction());

        Integer purchase = statisticMapper.getPurchaseToday();
        Integer purchaseYesterday = statisticMapper.getPurchaseYesterday();
        if (purchase != null && purchaseYesterday != null) {
            statistic.setPurchase(purchase);
            float roundPY = Math.round((float) (purchase - purchaseYesterday.intValue()) / purchaseYesterday.intValue() * 100);
            statistic.setPurchaseYesterday(roundPY);
        } else if (purchase != null) {
            statistic.setPurchase(purchase);
            statistic.setPurchaseYesterday(0F);
        } else {
            statistic.setPurchase(0);
            statistic.setPurchaseYesterday(0F);
        }

        Integer order = statisticMapper.getOrderToday();
        Integer orderYesterday = statisticMapper.getOrderYesterday();
        log.info("orderyesterday: {}",orderYesterday);
        if (order != 0 && orderYesterday != 0) {
            statistic.setOrder(order);
            float roundOY = Math.round((float) (order - orderYesterday.intValue()) / orderYesterday.intValue() * 100);
            statistic.setOrderYesterday(roundOY);
        } else if (order != 0) {
            statistic.setOrder(order);
            statistic.setOrderYesterday(0F);
        } else {
            statistic.setOrder(0);
            statistic.setOrderYesterday(0F);
        }

        Integer productSales = statisticMapper.getsalesToday();
        Integer productSalesYesterday = statisticMapper.getsalesYesterday();
        if (productSales != null && productSalesYesterday != null) {
            statistic.setProductSales(productSales);
            if (productSalesYesterday != 0) {
                float roundPSY = Math.round((float) (productSales - productSalesYesterday.intValue()) / productSalesYesterday.intValue() * 100);
                statistic.setProductSalesYesterday(roundPSY);
            } else {
                statistic.setProductSalesYesterday(0F);
            }
            log.info("transaction: {}", statistic);
        } else if (productSales != null) {
            statistic.setProductSales(productSales);
            statistic.setProductSalesYesterday(0F);
        } else {
            statistic.setProductSales(0);
            statistic.setProductSalesYesterday(0F);
        }
        return statistic;
    }
}
