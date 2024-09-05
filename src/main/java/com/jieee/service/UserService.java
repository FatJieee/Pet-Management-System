package com.jieee.service;

import com.jieee.pojo.*;

import java.util.List;

public interface UserService {
    //login
    User login(User user);

    //register
    void register(User user);

    User userInfo(Integer userId);

    void updateSelfInfo(User user);

    PageBean list(Integer pageNum, Integer pageSize);

    PageBean search(Integer pageNum, Integer pageSize, String username, String role);

    void updateInfo(User user);

    void delete(Integer id);

    void addCart(Cart cart);

    List<Cart> getCart();

    void removeProduct(Cart cart);

    PageBean getProductHistory(Integer pageNum, Integer pageSize, Integer userId);

    PageBean getAllProductHistory(Integer pageNum, Integer pageSize);

    Voucher checkVoucher(Integer userId, String code);

    List<Voucher> getVoucher(Integer userId);

    PageBean getVoucherList(Integer pageNum, Integer pageSize);

    void addVoucher(Voucher voucher);

    void updateVoucher(Voucher voucher);

    void deleteVoucher(Voucher voucher);

    void updateAvatar(User user);

    List<User> getUserListWithoutPagi();

    void addRating(Rating rating);

    String getPassword(Integer userId);

    void updatePass(User user);
}
