package com.jieee.controller;

import com.jieee.pojo.PageBean;
import com.jieee.pojo.ProductPurchase;
import com.jieee.pojo.Result;
import com.jieee.pojo.Transaction;
import com.jieee.service.TransactionService;
import com.jieee.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.apache.tomcat.util.http.parser.HttpParser.isNumeric;

@Slf4j
@RequestMapping("/transaction")
@RestController
public class TransactionController {

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("\\d+");
    }

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize){
        PageBean pagebean = transactionService.list(pageNum, pageSize);
        return Result.success(pagebean);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Transaction txn){
        if(txn.getProductId() != 0 && txn.getType() != null && txn.getQuantity() != 0){
            if ("Purchase".equals(txn.getType()) && txn.getCost() == 0) {
                return Result.error("Cost cannot be 0 for a purchase transaction");
            }
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("id");
            txn.setUserId(userId);
            transactionService.add(txn);
            return Result.success();
        }
        return Result.error("Transaction details cant be null or 0");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Transaction txn){
        String quantity = String.valueOf(txn.getQuantity());

        if (isInteger(quantity) && !quantity.equals("0")) {
            if(txn.getProductId() != 0 && txn.getType() != null && txn.getQuantity() != 0){
                Map<String, Object> map = ThreadLocalUtil.get();
                Integer userId = (Integer) map.get("id");
                txn.setUserId(userId);
                transactionService.update(txn);
                return Result.success();
            }
            return Result.error("Transaction details cant be null or 0");
        } else {
            return Result.error("Quantity can't be negative or 0");
        }
    }


    @GetMapping("/productTxnList")
    public Result getProductTxnList(Integer pageNum, Integer pageSize){
        PageBean pagebean = transactionService.productTxnList(pageNum, pageSize);
        return Result.success(pagebean);
    }

    @PostMapping("/purchase")
    public Result purchase(@RequestBody List<ProductPurchase> productPurchase){
        ProductPurchase order = productPurchase.get(0);
        log.info("order: {}",order);
        transactionService.purchase(order,productPurchase);
        return Result.success();
    }
}
