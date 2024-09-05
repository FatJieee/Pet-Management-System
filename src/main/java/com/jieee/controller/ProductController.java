package com.jieee.controller;

import com.jieee.pojo.*;
import com.jieee.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Result productList(Integer pageNum, Integer pageSize) {
        PageBean pagebean = productService.list(pageNum, pageSize);
        return Result.success(pagebean);
    }

    @GetMapping("getListWithStock")
    public Result getListWithStock() {
        List<Product> products = productService.getListWithStock();
        return Result.success(products);
    }

    @GetMapping("getList")
    public Result getList() {
        List<Product> products = productService.getList();
        return Result.success(products);
    }

    @GetMapping("/dltList")
    public Result dltList(Integer pageNum, Integer pageSize){
        PageBean pagebean = productService.dltList(pageNum, pageSize);
        return Result.success(pagebean);
    }

    @GetMapping("list1")
    public Result getlist1(){
        List<Product> p = productService.list1();
        return Result.success(p);
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("image: {}",image);
        //get file name
        String oriName = image.getOriginalFilename();
        int index = oriName.lastIndexOf(".");
        String extname = oriName.substring(index);

        //generate new file name
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("new file name: {}",newFileName);

        //save file to assets/image in front end
        image.transferTo(new File("D:\\limzh\\VsCode\\Furry\\public\\image\\product" + newFileName));
        return Result.success("/image/product" + newFileName);
    }

    @PostMapping("/add")
    public Result addProduct(@RequestBody Product product){
        log.info("product {}",product);

        if(product.getName() != null && product.getCategoryName() != null && product.getCost() != 0.0
                && product.getPrice() != 0.0){
            productService.add(product);
            return Result.success();
        }
        return Result.error("Product details cant be null or 0");
    }

    @PostMapping("updateProduct")
    public Result updateProduct(@RequestBody Product product){
        productService.updateProduct(product);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result deleteProduct(@RequestBody Map<String, Integer> product){
        Integer id = product.get("id");
        log.info("productId : {}", id);
        productService.delete(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result search(
            Integer pageNum, Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId
    ){
        PageBean pageBean = productService.search(pageNum,pageSize,name, categoryId);
        return Result.success(pageBean);
    }

    @GetMapping("/categoryList")
    public Result categoryList(Integer pageNum, Integer pageSize){
        PageBean pageBean = productService.categoryList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/category")
    public Result category(){
        List<ProductCategory> productCategories = productService.category();
        return Result.success(productCategories);
    }

    @PostMapping("/addCategory")
    public Result addPCategory(@RequestBody ProductCategory productCategory){
        if(productCategory.getName() != null && productCategory.getDescription() != null){
            productService.addCategory(productCategory);
            return Result.success();
        }
        return Result.error("Product name or description cant be null");
    }

    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody ProductCategory productCategory){
        productService.updateCategory(productCategory);
        return Result.success();
    }

    @PostMapping("/deleteCategory")
    public Result deleteCategory(@RequestBody Map<String, Integer> category){
        Integer id = category.get("id");
        productService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/getProductWithPrice")
    public Result getProductSelected(Integer minPrice, Integer maxPrice, Integer[] categories) {
        List<Product> products = productService.selectedProductList(minPrice,maxPrice,categories);
        return Result.success(products);
    }

    @GetMapping("/getProductWithId")
    public Result getProductWithId(Integer id){
        log.info("id:{}",id);
        Product product =  productService.searchProductWithId(id);
        return Result.success(product);
    }

    @GetMapping("/getProductSales")
    public Result productSales(Integer pageNum, Integer pageSize){
        PageBean pageBean = productService.productSales(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/getProductRateList")
    public Result getProductRateList(Integer pageNum, Integer pageSize){
        PageBean pageBean = productService.getProductRateList(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/getProductReview")
    public Result getProductReview(Integer id){
        Rating rating = new Rating();
        rating.setProductId(id);
        Rating ratings = productService.getProductReview(rating);
        return Result.success(ratings);
    }

    @GetMapping("/getProductComment")
    public Result getProductComment(Integer id){
        Rating rating = new Rating();
        rating.setProductId(id);
        log.info("rating:{}",rating);
        List<Rating> ratings = productService.getProductComment(rating);
        return Result.success(ratings);
    }

    @GetMapping("/getFood")
    public Result getFood(){
        List<Product> products = productService.getFood();
        return Result.success(products);
    }

    @GetMapping("/getClothes")
    public Result getClothes(){
        List<Product> products = productService.getClothes();
        return Result.success(products);
    }

    @GetMapping("/getNegativeStock")
    public Result getNegativeStock(Integer pageNum, Integer pageSize){
        PageBean pageBean = productService.getNegativeStock(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/getExceedng")
    public Result getExceedng(Integer pageNum, Integer pageSize){
        PageBean pageBean = productService.getExceedng(pageNum,pageSize);
        return Result.success(pageBean);
    }


}
