package com.jieee.controller;

import com.jieee.pojo.*;
import com.jieee.service.CommunityService;
import com.jieee.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/community")
@RestController
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/getPostList")
    public Result getPostList(@RequestParam(value = "userId", required = false) String id){
        Integer userId;
        if(id == null){
            userId = 0;
        }else{
            userId = Integer.parseInt(id);
        }
        log.info("community userid:{}",userId);
        List<Post> posts = communityService.getPostList(userId);
        List<Comment> comments = communityService.getCommentList();
        Map<String,Object> map = new HashMap<>();
        map.put("post",posts);
        log.info("comment list:{}",comments);
        map.put("comment",comments);
        return Result.success(map);
    }

    @GetMapping("/checkFirstPost")
    public Result checkFirstPost(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Post post = communityService.checkFirstPost(userId);
        return Result.success(post);
    }

    @PostMapping("/likedPost")
    public Result likePost(@RequestBody Post post){
        log.info("post: {}",post);
        communityService.likePost(post);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile media) throws IOException {
        log.info("image: {}",media);
        //get file name
        String oriName = media.getOriginalFilename();
        int index = oriName.lastIndexOf(".");
        String extname = oriName.substring(index);

        //generate new file name
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("new file name: {}",newFileName);

        //save file to assets/image in front end
        media.transferTo(new File("D:\\limzh\\VsCode\\Furry\\public\\image\\post" + newFileName));
        return Result.success("/image/post" + newFileName);
    }

    @PostMapping("/addPost")
    public Result addPost(@RequestBody Post post){
        log.info("post {}",post);

        if(post.getCategory() != ""){
            if(post.getDescription() != ""){
                communityService.addPost(post);
                return Result.success();
            }
            else{
                return Result.error("Description cannot be null.");
            }
        }
        else{
            return Result.error("Category must be select.");
        }
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        log.info("addComment {}",comment);
        if(comment.getDescription() == null){
            return Result.error("Comment description cant be null.");
        }else{
            communityService.addComment(comment);
            return Result.success();
        }
    }

    @PostMapping("/addSubComment")
    public Result addSubComment(@RequestBody Comment comment){
        log.info("addSubComment {}",comment);

        if(comment.getReplyText() == null && comment.getParentId() == null && comment.getPostId() == null && comment.getUsername() == null){
            return Result.error("Comment description cant be null.");
        }else{
            communityService.addSubComment(comment);
            return Result.success();
        }
    }

    @PostMapping("/dltPost")
    public Result dltPost(@RequestBody Map<String, Integer> post){
        Integer id = post.get("id");
        log.info("postid: {}",id);
        communityService.dltPost(id);
        return Result.success();
    }

    @GetMapping("/getPendingPost")
    public Result getPendingPost(Integer pageNum, Integer pageSize){
        PageBean pageBean = communityService.getPendingPost(pageNum, pageSize);
        return Result.success(pageBean);
    }

    @PostMapping("/verifyPost")
    public Result verifyPost(@RequestBody Post post){
        log.info("post model: {}",post);
        communityService.verifyPost(post);
        return Result.success();
    }

    @GetMapping("/getPost")
    public Result getPost(Integer pageNum, Integer pageSize){
        PageBean pageBean = communityService.getPost(pageNum, pageSize);
        return Result.success(pageBean);
    }
}
