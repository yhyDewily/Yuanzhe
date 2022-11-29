package com.ruoyi.common.exception.yuanzhe;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.base.BaseException;

/**
 * SQL文件被篡改异常
 * @author lls
 * @Date 2022-11-23 10:02
 **/

public class SqlTamperException extends BaseException {

    private static final long serialVersionUID = 1L;

    public SqlTamperException() {

        super("yuanzhe.secrekey.databackup", "SQL文件被篡改");
    }
}
