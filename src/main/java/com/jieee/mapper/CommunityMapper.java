package com.jieee.mapper;


import com.jieee.pojo.Comment;
import com.jieee.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommunityMapper {

    List<Post> getPostList(Integer userId);

    List<Comment> getCommentList();

    @Select("select liked_time from furry.post_like where post_id = #{id} and user_id = #{userId}")
    LocalDateTime checkPostLike(Post post);


    @Insert("insert into furry.post_like(post_id,user_id,liked_time) " +
            "values (#{id},#{userId},#{likedTime})")
    void likePost(Post post);


    @Update("delete from furry.post_like where post_id = #{id} and user_id = #{userId}")
    void dltLikePost(Post post);

    @Insert("insert into furry.post(user_id,description,media,category,create_time) " +
            "values (#{userId},#{description},#{media},#{category},#{createTime})")
    void addPost(Post post);

    @Insert("insert into furry.comment(post_id,user_id,description,create_time) " +
            "values (#{postId},#{userId},#{description},#{createTime})")
    void addComment(Comment comment);

    @Insert("insert into furry.comment(post_id,user_id,parent_id,description,target,create_time) " +
            "values (#{postId},#{userId},#{parentId},#{replyText},#{target},#{createTime})")
    void addSubComment(Comment comment);

    @Delete("delete from furry.post where id = #{id}")
    void dltPost(Integer id);

    @Delete("delete from furry.comment where post_id = #{id}")
    void dltAllComment(Integer id);

    @Delete("delete from furry.post_like where post_id = #{id}")
    void dltAllPostLike(Integer id);

    @Select("select p.*,u.username from furry.post as p join furry.user as u on p.user_id = u.id where verify = '0'")
    List<Post> getPendingPost();

    @Update("update furry.post set verify = #{verify} where id = #{id}")
    void verifyPost(Post post);

    @Select("select p.*,u.username from furry.post as p join furry.user as u on p.user_id = u.id ")
    List<Post> getPost();

    boolean checkFirstPost(Integer userId);

    Post checkUserFirstPost(Integer userId);
}
