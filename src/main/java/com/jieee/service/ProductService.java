package com.jieee.service;

import com.jieee.pojo.*;

import java.util.Arrays;
import java.util.List;

public interface ProductService {

    void add(Product product);

    void updateProduct(Product product);

    PageBean list(Integer pageNum, Integer pageSize);

    void delete(Integer id);

    PageBean search(Integer pageNum, Integer pageSize, String name, Integer categoryId);

    PageBean categoryList(Integer pageNum, Integer pageSize);

    void addCategory(ProductCategory productCategory);

    void updateCategory(ProductCategory productCategory);

    void deleteCategory(Integer id);


    List<Product> list1();

    PageBean dltList(Integer pageNum, Integer pageSize);

    List<Product> selectedProductList(Integer pageNum, Integer pageSize, Integer[] categories);

    Product searchProductWithId(Integer id);

    PageBean productSales(Integer pageNum, Integer pageSize);

    PageBean getProductRateList(Integer pageNum, Integer pageSize);

    Rating getProductReview(Rating rating);

    List<Rating> getProductComment(Rating rating);

    List<Product> getFood();

    List<Product> getClothes();

    PageBean getNegativeStock(Integer pageNum, Integer pageSize);

    PageBean getExceedng(Integer pageNum, Integer pageSize);

    List<Product> getList();

    List<ProductCategory> category();

    List<Product> getListWithStock();
}
