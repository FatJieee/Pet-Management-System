package com.jieee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jieee.mapper.TransactionMapper;
import com.jieee.pojo.PageBean;
import com.jieee.pojo.ProductPurchase;
import com.jieee.pojo.ProductTransaction;
import com.jieee.pojo.Transaction;
import com.jieee.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public PageBean list(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Transaction> transactions = transactionMapper.list();
        Page<Transaction> p = (Page<Transaction>) transactions;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(Transaction txn) {
        txn.setTransactionTime(LocalDateTime.now());
        txn.setUpdateTime(LocalDateTime.now());
        log.info("txn: {}",txn);
        transactionMapper.add(txn);
        transactionMapper.addProductTransaction(txn);
        transactionMapper.updateProductStock(txn);
        if(txn.getCost() != 0.0){
            transactionMapper.updateProductCost(txn);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Transaction txn) {
        txn.setUpdateTime(LocalDateTime.now());
        log.info("txn: {}",txn);

        transactionMapper.editProductTransaction(txn);
        transactionMapper.editProductStock(txn);
        transactionMapper.editTransaction(txn);
        if (txn.getCost() != 0.0) {
            transactionMapper.editProductCost(txn);
        }

    }

    @Override
    public PageBean productTxnList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductTransaction> productTransactions = transactionMapper.productTxnList();
        Page<ProductTransaction> p = (Page<ProductTransaction>) productTransactions;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void purchase(ProductPurchase order, List<ProductPurchase> productPurchase) {
        order.setPurchaseTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        if("1".equalsIgnoreCase(order.getMethod())){
            order.setMethod("Bank");
        }else{
            order.setMethod("Tng");
        }
        log.info("productlist {}",productPurchase);
        transactionMapper.insertUserPurchase(order);
        transactionMapper.insertProductSales(productPurchase);
        transactionMapper.updatePStock(productPurchase);
        transactionMapper.deleteCartProduct(productPurchase);
        if(order.getCode() != null){
            transactionMapper.updateVoucher(order);
        }
    }
}
