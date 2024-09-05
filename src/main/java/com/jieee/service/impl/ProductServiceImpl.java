package com.jieee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jieee.mapper.ProductMapper;
import com.jieee.pojo.*;
import com.jieee.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    //product list
    @Override
    public PageBean list(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.productList();
        Page<Product> p = (Page<Product>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    //add
    @Override
    public void add(Product product) {
        product.setStock(1);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        log.info("product {}",product);
        productMapper.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        product.setUpdateTime(LocalDateTime.now());
        productMapper.updateProduct(product);
    }

    //delete product
    @Override
    public void delete(Integer id) {
        productMapper.delete(id);
    }

    //search product
    @Override
    public PageBean search(Integer pageNum, Integer pageSize, String name, Integer categoryId) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.search(name,categoryId);
        Page<Product> p = (Page<Product>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    //get product category list
    @Override
    public PageBean categoryList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductCategory> products = productMapper.categoryList();
        Page<ProductCategory> p = (Page<ProductCategory>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    //add product category
    @Override
    public void addCategory(ProductCategory productCategory) {
        productCategory.setCreateTime(LocalDateTime.now());
        productCategory.setUpdateTime(LocalDateTime.now());
        productMapper.addCategory(productCategory);
    }

    //update product category
    @Override
    public void updateCategory(ProductCategory productCategory) {
        productCategory.setUpdateTime(LocalDateTime.now());
        productMapper.updateCategory(productCategory);
    }

    //delete product category
    @Override
    public void deleteCategory(Integer id) {
        productMapper.deleteCategory(id);
    }

    @Override
    public List<Product> list1() {
        return productMapper.list1();
    }

    @Override
    public PageBean dltList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.dltProductList();
        Page<Product> p = (Page<Product>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public List<Product> selectedProductList(Integer minPrice, Integer maxPrice, Integer[] categories) {
        log.info("categories: {}",categories);
        return productMapper.getSelectedProduct(minPrice,maxPrice,categories);
    }

    @Override
    public Product searchProductWithId(Integer id) {
        return productMapper.getProductWithId(id);
    }

    @Override
    public PageBean productSales(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<ProductPurchase> products = productMapper.productSales();
        Page<ProductPurchase> p = (Page<ProductPurchase>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public PageBean getProductRateList(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Rating> ratings = productMapper.productRateList();
        Page<Rating> p = (Page<Rating>) ratings;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public Rating getProductReview(Rating rating) {
        List<Rating> r = productMapper.getProductReview(rating);
        log.info("rating before: {}",r);


        Rating newRate = new Rating();
        if (r.isEmpty()) {
            return newRate;
        }
        Float totalRate = r.get(0).getTotalRate();
        newRate.setTotalRate(totalRate);

        // 计算每个评分的人数
        Map<Float, Integer> userCountMap = new HashMap<>();
        for (Rating ratings : r) {
            float rate = ratings.getRate();
            userCountMap.put(rate, userCountMap.getOrDefault(rate, 0) + 1);
        }
        log.info("userCountMap: {}", userCountMap);
        int totalUsers = userCountMap.values().stream().mapToInt(Integer::intValue).sum();
        newRate.setTotalCount(totalUsers);

        for (Rating ratings : r) {
            float rate = ratings.getRate();
            switch ((int) rate) {
                case 1:
                    newRate.setUserCount1(userCountMap.getOrDefault(rate, 0));
                    break;
                case 2:
                    newRate.setUserCount2(userCountMap.getOrDefault(rate, 0));
                    break;
                case 3:
                    newRate.setUserCount3(userCountMap.getOrDefault(rate, 0));
                    break;
                case 4:
                    newRate.setUserCount4(userCountMap.getOrDefault(rate, 0));
                    break;
                case 5:
                    newRate.setUserCount5(userCountMap.getOrDefault(rate, 0));
                    break;
                default:
                    break;
            }
        }
        log.info("ratings after: {}",r);
        return newRate;
    }

    @Override
    public List<Rating> getProductComment(Rating rating) {
        List<Rating> ratings = productMapper.getProductComment(rating);
        return ratings;
    }

    @Override
    public List<Product> getFood() {
        return productMapper.getFood();
    }

    @Override
    public List<Product> getClothes() {
        return productMapper.getClothes();
    }

    @Override
    public PageBean getNegativeStock(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = (List<Product>) productMapper.getNegativeStock();
        Page<Product> p = (Page<Product>) products;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public PageBean getExceedng(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = (List<Product>) productMapper.getExceeding();
        Page<Product> p = (Page<Product>) products;
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());

        return pageBean;
    }

    @Override
    public List<Product> getList() {
        return productMapper.getList();
    }

    @Override
    public List<ProductCategory> category() {
        return productMapper.getPCategory();
    }

    @Override
    public List<Product> getListWithStock() {
        return productMapper.getListWithStock();
    }
}
