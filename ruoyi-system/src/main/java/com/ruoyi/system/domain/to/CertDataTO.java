package com.ruoyi.system.domain.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenshijie
 * @date 2022/11/4 13:45
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CertDataTO implements Serializable {

    private String certSn;
    private String certSDN;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
