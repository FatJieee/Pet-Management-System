package com.jieee.mapper;

import com.jieee.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface ProductMapper {


    @Insert("insert into furry.product(category_id, name, description, image, price, cost, stock, rating, create_time, update_time) " +
            "values (#{categoryId},#{name},#{description},#{image},#{price},#{cost},#{stock},#{rating},#{createTime},#{updateTime})")
    void add(Product product);

    void updateProduct(Product product);

    List<Product> getList();

    List<Product> productList();

    @Update("update furry.product set status = 0 where id = #{id}")
    void delete(Integer id);

    List<Product> search(String name, Integer categoryId);

    @Select("select * from furry.product_category")
    List<ProductCategory> categoryList();

    @Insert("insert into furry.product_category(name, description, create_time, update_time) VALUES (#{name},#{description},#{createTime},#{updateTime})")
    void addCategory(ProductCategory productCategory);


    void updateCategory(ProductCategory productCategory);

    //delete category
    @Delete("delete from furry.product_category where id = #{id}")
    void deleteCategory(Integer id);

    @Select("select * from furry.product")
    List<Product> list1();

    List<Product> dltProductList();

    List<Product> getSelectedProduct(Integer minPrice, Integer maxPrice, Integer[] categories);

    Product getProductWithId(Integer id);

    List<ProductPurchase> productSales();

    List<Rating> productRateList();

    List<Rating> getProductReview(Rating rating);

    List<Rating> getProductComment(Rating rating);

    @Select("select * from furry.product where category_id = 1 limit 4")
    List<Product> getFood();

    @Select("select * from furry.product where category_id = 3 limit 4")
    List<Product> getClothes();

    List<Product> getNegativeStock();

    List<Product> getExceeding();

    @Select("select * from furry.product_category")
    List<ProductCategory> getPCategory();

    @Select("select * from furry.product where status = 1 and stock >= 1")
    List<Product> getListWithStock();
}
