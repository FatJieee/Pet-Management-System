package com.jieee.controller;

import com.jieee.pojo.*;
import com.jieee.service.UserService;
import com.jieee.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequestMapping("/user")
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("User Info: {}",user);
        if(!Objects.equals(user.getPassword(), user.getRePassword())){
            return Result.error("Password Not Match");
        }
        if(user.getUsername() != null && user.getContact() != null && user.getEmail() != null && user.getPassword() != null
                && user.getAge() != null && user.getGender() != null){
            if(user.getUsername().length() >= 5 && user.getUsername().length() <= 16 && user.getPassword().length() >= 5
                    && user.getContact().matches("\\d+") && user.getContact().length() >= 10 && user.getContact().length() <= 11
                    && user.getPassword().length() <= 16 && user.getEmail().contains("@") && user.getEmail().contains(".com")){
                userService.register(user);
                return Result.success();
            }
            return Result.error("Invalid format, Please fill in again.");
        }
        return Result.error("Please fill in all infomation.");
    }

    @GetMapping("/info")
    public Result userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        log.info("userid: {}",userId);
        User user = userService.userInfo(userId);
        return Result.success(user);
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody User user) throws IOException{
        log.info("image: {}",user);

        userService.updateAvatar(user);
        return Result.success();
    }

    @PostMapping("/updateSelfInfo")
    public Result updateSelfInfo(@RequestBody User user){
        log.info("user: {}",user);
        if(user.getUsername().length() >= 5 && user.getUsername().length() <= 16
                && user.getContact().matches("\\d+") && user.getContact().length() >= 10 && user.getContact().length() <= 11
                && user.getEmail().contains("@") && user.getEmail().contains(".com")){
            userService.updateSelfInfo(user);
            return Result.success();
        }
        else{
            return Result.error("Invalid format, Please fill in again.");
        }
    }

    @PostMapping("/updatePass")
    public Result updatePass(@RequestBody User user){

        if(user.getPassword() != ""){
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("id");
            log.info("userid: {}",userId);
            String pass = userService.getPassword(userId);

            if(!Objects.equals(pass, user.getPassword())){
                return Result.error("Password Not Match");
            }
            else if(!Objects.equals(user.getNewPass(), user.getReNewPass())){
                return Result.error("New Password Not Match");
            }
            if (user.getNewPass().length() >= 5 && user.getNewPass().length() <= 16 && user.getReNewPass().length() >= 5 && user.getReNewPass().length() <= 16) {
                user.setId(userId);
                userService.updatePass(user);
                return Result.success();
            }
            else{
                return Result.error("Password length must be 5-16");
            }
        }else{
            return Result.error("Password can't be empty");
        }
    }



    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody User user){
        log.info("user:{}",user);
        if(user.getUsername().length() >= 5 && user.getUsername().length() <= 16
                && user.getContact().matches("\\d+") && user.getContact().length() >= 10 && user.getContact().length() <= 11
                && user.getEmail().contains("@") && user.getEmail().contains(".com")){
            userService.updateInfo(user);
            return Result.success();
        }
        else{
            return Result.error("Invalid format, Please fill in again.");
        }
    }

    @GetMapping("/list")
    public Result userList(Integer pageNum, Integer pageSize){
        PageBean pageBean = userService.list(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/search")
    public Result search(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role
    ){
        PageBean pageBean = userService.search(pageNum,pageSize,username,role);
        return Result.success(pageBean);
    }

    @PostMapping("/delete")
    public Result deleteUser(@RequestBody Map<String, Integer> user){
        Integer id = user.get("id");
        log.info("Userid: {}",id);
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/addCart")
    public Result addCart(@RequestBody Cart cart){
        userService.addCart(cart);
        return Result.success();
    }

    @GetMapping("getCart")
    public Result getCart(){
        List<Cart> carts = userService.getCart();
        return Result.success(carts);
    }

    @PostMapping("/removeProduct")
    public Result removeProduct(@RequestBody Cart cart){
        log.info("cart :{]",cart);
        userService.removeProduct(cart);
        return Result.success();
    }

    @GetMapping("/purchaseHistory")
    public Result puchaseHistory(Integer pageNum, Integer pageSize, Integer userId){
        PageBean pageBean = userService.getProductHistory(pageNum,pageSize,userId);
        return Result.success(pageBean);

    }

    @GetMapping("/allPurchaseHistory")
    public Result allPuchaseHistory(Integer pageNum, Integer pageSize){
        PageBean pageBean = userService.getAllProductHistory(pageNum,pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/checkVoucher")
    public Result checkVoucher(@RequestParam(required = false) Integer userId, String code){
        log.info("cart :{]",code);
        Voucher voucher = userService.checkVoucher(userId,code);

        if (voucher == null) {
            return Result.error("Voucher not found.");
        }

        if(LocalDateTime.now().isAfter(voucher.getExpiredTime()) ){
            return Result.error("Voucher Expired.");
        }
        return Result.success(voucher);
    }

    @GetMapping("/getVoucher")
    public Result getVoucher(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Voucher> vouchers = userService.getVoucher(userId);
        return Result.success(vouchers);
    }

    @GetMapping("/getVoucherList")
    public Result getVoucherList(Integer pageNum, Integer pageSize){
        PageBean pageBean = userService.getVoucherList(pageNum,pageSize);
        log.info("pagebean: {}",pageBean);
        return Result.success(pageBean);
    }

    @PostMapping("/addVoucher")
    public Result addVoucher(@RequestBody Voucher voucher){
        userService.addVoucher(voucher);
        return Result.success();
    }

    @PostMapping("/updateVoucher")
    public Result updateVoucher(@RequestBody Voucher voucher){
        userService.updateVoucher(voucher);
        return Result.success();
    }

    @PostMapping("/deleteVoucher")
    public Result deleteVoucher(@RequestBody Voucher voucher){
        userService.deleteVoucher(voucher);
        return Result.success();
    }

    @GetMapping("/getListWithoutPagi")
    public Result userListWithoutPagi(){
        List<User> users = userService.getUserListWithoutPagi();
        return Result.success(users);
    }

    @PostMapping("/addRating")
    public Result addRating(@RequestBody Rating rating){
        userService.addRating(rating);
        return Result.success();
    }
}
