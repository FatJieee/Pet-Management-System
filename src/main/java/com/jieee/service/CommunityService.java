package com.jieee.service;


import com.jieee.pojo.Comment;
import com.jieee.pojo.PageBean;
import com.jieee.pojo.Post;

import java.util.List;

public interface CommunityService {
    List<Post> getPostList(Integer userId);

    void likePost(Post post);

    void addPost(Post post);

    void addComment(Comment comment);

    void addSubComment(Comment comment);

    List<Comment> getCommentList();

    void dltPost(Integer id);

    PageBean getPendingPost(Integer pageNum, Integer pageSize);

    void verifyPost(Post post);

    PageBean getPost(Integer pageNum, Integer pageSize);

    Post checkFirstPost(Integer userId);
}
