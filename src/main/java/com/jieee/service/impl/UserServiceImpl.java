package com.jieee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jieee.mapper.UserMapper;
import com.jieee.pojo.*;
import com.jieee.service.UserService;
import com.jieee.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static com.jieee.utils.RandomVoucher.generateVoucherCode;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //login
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    //register
    @Override
    public void register(User user) {
        user.setImage("/src/assets/default.jpg");
        user.setRole("User");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.register(user);
    }

    @Override
    public User userInfo(Integer userId) {
        return userMapper.userInfo(userId);
    }

    //update user details
    @Override
    public void updateSelfInfo(User user) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        user.setId(userid);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateSelfInfo(user);
    }

    //get user list
    @Override
    public PageBean list(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<User> user = userMapper.userList();
        Page<User> p = (Page<User>) user;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public PageBean search(Integer pageNum, Integer pageSize, String username, String role) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<User> user = userMapper.search(username,role);
        Page<User> p = (Page<User>) user;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void updateInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateInfo(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    //add cart
    @Override
    public void addCart(Cart cart) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        cart.setUserId(userId);
        cart.setAddedTime(LocalDateTime.now());
        cart.setModifyTime(LocalDateTime.now());
        Integer cartId = userMapper.checkCart(cart);
        if (cartId != null){
            userMapper.updateCart(cart);
        }else{
            userMapper.addCart(cart);
        }

    }

    @Override
    public List<Cart> getCart() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return userMapper.getCart(userId);
    }

    @Override
    public void removeProduct(Cart cart) {
        userMapper.removeProduct(cart);
    }

    @Override
    public PageBean getProductHistory(Integer pageNum, Integer pageSize, Integer userId) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductPurchase> productPurchases = userMapper.getProductHistory(userId);
        Page<ProductPurchase> p = (Page<ProductPurchase>) productPurchases;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public PageBean getAllProductHistory(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductPurchase> productPurchases = userMapper.getAllProductHistory();
        Page<ProductPurchase> p = (Page<ProductPurchase>) productPurchases;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public Voucher checkVoucher(Integer userId, String code) {
        return userMapper.checkVoucher(userId,code);
    }

    @Override
    public List<Voucher> getVoucher(Integer userId) {
        return userMapper.getVoucher(userId);
    }

    @Override
    public PageBean getVoucherList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Voucher> vouchers = userMapper.getVoucherList();
        Page<Voucher> p = (Page<Voucher>) vouchers;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void addVoucher(Voucher voucher) {
        voucher.setCode(generateVoucherCode());
        voucher.setCreateTime(LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        Integer days = voucher.getExpired();
        LocalDateTime expireTime = now.plus(days, ChronoUnit.DAYS);

        log.info("voucher: {]",voucher);
        voucher.setExpiredTime(expireTime);
        userMapper.addVoucher(voucher);
    }

    @Override
    public void updateVoucher(Voucher voucher) {
        if(voucher.getExpired() != null){
            LocalDateTime now = LocalDateTime.now();
            Integer days = voucher.getExpired();

            LocalDateTime expireTime = now.plus(days, ChronoUnit.DAYS);
            voucher.setExpiredTime(expireTime);
            log.info("voucher expired time: {}",voucher);
        }

        userMapper.updateVoucher(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        userMapper.deleteVoucher(voucher);
    }

    @Override
    public void updateAvatar(User user) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        user.setId(userId);
        userMapper.updateAvatar(user);
    }

    @Override
    public List<User> getUserListWithoutPagi() {
        return userMapper.getListWithoutPagi();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRating(Rating rating) {
        Integer productId = userMapper.getProductId(rating);
        rating.setProductId(productId);
        rating.setAddedTime(LocalDateTime.now());
        rating.setUpdateTime(LocalDateTime.now());
        userMapper.addRating(rating);
        userMapper.updateProductSalesRating(rating);
        userMapper.updateProductRate(rating);
    }

    @Override
    public String getPassword(Integer userId) {
        return userMapper.getPass(userId);
    }

    @Override
    public void updatePass(User user) {
        userMapper.updatePass(user);
    }
}
