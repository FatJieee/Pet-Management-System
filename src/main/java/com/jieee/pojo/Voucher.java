package com.jieee.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    private Integer id;
    private Integer userId;
    private String userName;
    private String code;
    private float discount;
    private Integer type;
    private Integer expired;
    private LocalDateTime createTime;
    private LocalDateTime expiredTime;
}
