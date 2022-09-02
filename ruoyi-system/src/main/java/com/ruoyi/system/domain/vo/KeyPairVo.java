package com.ruoyi.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lls
 * @Date 2022-08-22 16:47
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyPairVo {
    private String privateKey;
    private String publicKey;
}
