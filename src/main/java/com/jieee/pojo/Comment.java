package com.jieee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer id;
    private Integer postId;
    private Integer userId;
    private String username;
    private String image;
    private Integer parentId;
    private String userImage;
    private String target;
    private String description;
    private String replyText;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
