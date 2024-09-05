package com.jieee.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jieee.mapper.CommunityMapper;
import com.jieee.mapper.UserMapper;
import com.jieee.pojo.*;
import com.jieee.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jieee.utils.RandomVoucher.generateVoucherCode;

@Slf4j
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Post> getPostList(Integer userId) {
        log.info("userid check: {}",userId);
        return communityMapper.getPostList(userId);
    }

    @Override
    public List<Comment> getCommentList() {
        return communityMapper.getCommentList();
    }

    @Override
    public void likePost(Post post) {
        LocalDateTime likedTime = post.getLikedTime();
        if(likedTime != null) {
            communityMapper.likePost(post);
        } else {
            communityMapper.dltLikePost(post);
        }
    }

    @Override
    public void addPost(Post post) {
        Integer userId = post.getUserId();
        boolean firstPost = communityMapper.checkFirstPost(userId);
        if (firstPost) {
            Voucher voucher = new Voucher();
            voucher.setCode(generateVoucherCode());
            voucher.setUserId(userId);
            voucher.setCreateTime(LocalDateTime.now());
            voucher.setDiscount(15);
            LocalDateTime now = LocalDateTime.now();
            Integer days = 180;
            LocalDateTime expireTime = now.plus(days, ChronoUnit.DAYS);
            voucher.setExpiredTime(expireTime);

            userMapper.addVoucher(voucher);
            voucher.setCode(generateVoucherCode());
            voucher.setDiscount(25);
            userMapper.addVoucher(voucher);
        }

        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        communityMapper.addPost(post);
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        communityMapper.addComment(comment);
    }

    @Override
    public void addSubComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        communityMapper.addSubComment(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dltPost(Integer id) {
        communityMapper.dltPost(id);
        communityMapper.dltAllComment(id);
        communityMapper.dltAllPostLike(id);
    }

    @Override
    public PageBean getPendingPost(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = communityMapper.getPendingPost();
        Page<Post> p = (Page<Post>) posts;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void verifyPost(Post post) {
        communityMapper.verifyPost(post);
    }

    @Override
    public PageBean getPost(Integer pageNum, Integer pageSize) {
        PageBean pageBean = new PageBean();
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = communityMapper.getPost();
        Page<Post> p = (Page<Post>) posts;

        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public Post checkFirstPost(Integer userId) {
        return communityMapper.checkUserFirstPost(userId);
    }


}
