package com.jieee.mapper;

import com.jieee.pojo.ProductPurchase;
import com.jieee.pojo.ProductTransaction;
import com.jieee.pojo.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TransactionMapper {

    //get list
    List<Transaction> list();
    
    @Insert("INSERT INTO furry.transaction(user_id, type, quantity, cost, transaction_time, update_time, notes)" +
            "VALUES (#{userId},#{type},#{quantity},#{cost},#{transactionTime},#{updateTime},#{notes});")
    //add transaction
    void add(Transaction txn);

    void updateProductCost(Transaction txn);
    
    
    void addProductTransaction(Transaction txn);

    void updateProductStock(Transaction txn);

    @Update("update furry.product_transaction set transaction_user_id = #{userId} where transaction_id = #{id}")
    void editProductTransaction(Transaction txn);

    void editProductCost(Transaction txn);

    void editProductStock(Transaction txn);

    void editTransaction(Transaction txn);

    List<ProductTransaction> productTxnList();

    @Insert("insert into furry.user_purchase_history(user_id, amount, shipping_fee, method, address,purchase_time,update_time) " +
            "VALUES (#{userId},#{amount},#{shippingFee},#{method},#{address},#{purchaseTime},#{updateTime})")
    void insertUserPurchase(ProductPurchase order);


    void insertProductSales(@Param("productPurchase") List<ProductPurchase> productPurchase);

    void updatePStock(@Param("productPurchase") List<ProductPurchase> productPurchase);

    void deleteCartProduct(@Param("productPurchase") List<ProductPurchase> productPurchase);

    @Update("UPDATE FURRY.voucher SET type = 0 WHERE code = #{code}")
    void updateVoucher(ProductPurchase order);
}
