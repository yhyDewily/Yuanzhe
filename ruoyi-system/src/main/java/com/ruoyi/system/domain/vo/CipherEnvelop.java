package com.ruoyi.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author bestFang666
 * @date 2022/11/1 18:25
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CipherEnvelop {
    /** 对称密钥 */
    private String key;
    /** 和对称密钥合用 */
    private String iv;
    /** 密文（Base64 格式）或者明文 */
    private String text;
    /** 加解密算法 */
    private String algorithm;
}
