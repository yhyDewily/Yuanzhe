package com.ruoyi.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lls
 * @Date 2022-11-01 09:54
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Result implements Serializable {

    String msg;
    String code;
    String token;

}
