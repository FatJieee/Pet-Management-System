package com.jieee.mapper;

import com.jieee.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //login
    @Select("select * from furry.user where username = #{username} and password = #{password}")
    User login(User user);

    //register
    @Insert("insert into furry.user(username, password, image, contact, gender, age, email, role,create_time, update_time) " +
            "values (#{username},#{password},#{image},#{contact},#{gender},#{age},#{email},#{role},#{createTime},#{updateTime})")
    void register(User user);

    @Select("select * from furry.user where id = #{userId}")
    User userInfo(Integer userId);

    void updateSelfInfo(User user);

    //get user list
    @Select("select * from furry.user")
    List<User> userList();

    //search user
    List<User> search(String username, String role);

    //update info
    void updateInfo(User user);

    //delete user
    @Delete("delete from furry.user where id = #{id}")
    void delete(Integer id);

    @Insert("insert into furry.cart(product_id, user_id, quantity, added_time, modify_time) " +
            "VALUES (#{productId},#{userId},#{quantity},#{addedTime},#{modifyTime})")
    void addCart(Cart cart);

    List<Cart> getCart(Integer userId);

    @Delete("DELETE from furry.cart where id = #{id}")
    void removeProduct(Cart cart);

    List<ProductPurchase> getProductHistory(Integer userId);

    List<ProductPurchase> getAllProductHistory();

    Voucher checkVoucher(Integer userId, String code);

    @Select("select * from furry.voucher where user_id = #{userId} and type = 1")
    List<Voucher> getVoucher(Integer userId);

    @Select("select *, u.username from furry.voucher join furry.user as u on voucher.user_id = u.id")
    List<Voucher> getVoucherList();

    @Insert("insert into furry.voucher(user_id, code, discount, create_time, expired_time) " +
            "VALUES (#{userId},#{code},#{discount},#{createTime},#{expiredTime})")
    void addVoucher(Voucher voucher);

    void updateVoucher(Voucher voucher);

    @Delete("delete from furry.voucher where id = #{id}")
    void deleteVoucher(Voucher voucher);

    @Update("update furry.user set image = #{image} where id = #{id}")
    void updateAvatar(User user);

    @Select("SELECT id,username from furry.user")
    List<User> getListWithoutPagi();

    @Insert("insert into furry.rating(user_id, product_id, rate, comment, added_time, update_time) " +
            "values (#{userId},#{productId},#{rate},#{comment},#{addedTime},#{updateTime})")
    void addRating(Rating rating);

    Integer getProductId(Rating rating);

    void updateProductSalesRating(Rating rating);

    void updateProductRate(Rating rating);

    @Select("select id from furry.cart where user_id = #{userId} and product_id = #{productId}")
    Integer checkCart(Cart cart);

    @Update("update furry.cart set quantity = quantity + #{quantity} where user_id = #{userId} and product_id = #{productId}")
    void updateCart(Cart cart);

    @Select("select password from furry.user where id = #{userId}")
    String getPass(Integer userId);

    @Update("update furry.user set password = #{newPass} where id = #{id}")
    void updatePass(User user);
}

