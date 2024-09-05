package com.jieee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Integer id;
    private Integer userId;
    private String userImage;
    private String username;
    private String description;
    private String media;
    private String category;
    private boolean userLiked;
    private Integer likeCount;
    private String verify;
    private boolean firstPost;
    private LocalDateTime createTime;
    private LocalDateTime likedTime;
    private LocalDateTime updateTime;

}
