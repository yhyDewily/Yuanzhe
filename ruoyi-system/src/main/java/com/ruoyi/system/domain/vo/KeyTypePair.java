package com.ruoyi.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lls
 * @Date 2022-08-23 16:27
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyTypePair {
    private String privateKey;
    private String publicKey;
    private String type;
}
