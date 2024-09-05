package com.jieee.service;

import com.jieee.pojo.PageBean;
import com.jieee.pojo.ProductPurchase;
import com.jieee.pojo.Transaction;

import java.util.List;

public interface TransactionService {
    PageBean list(Integer pageNum, Integer pageSize);


    void add(Transaction txn);

    void update(Transaction txn);

    PageBean productTxnList(Integer pageNum, Integer pageSize);

    void purchase(ProductPurchase order, List<ProductPurchase> productPurchase);
}
