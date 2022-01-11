package com.ruoyi.common.enums;

/**
 * @author Dewily
 * @date 2022-01-07 10:31
 */
public enum CerStatus {
    /**
     * 正常
     */
    NORMAL("1", "正常"),
    /**
     * 撤销
     */
    REVOKE("2", "撤销"),
    /**
     * 挂起
     */
    SUSPEND("3", "挂起");

    private String code;
    private String info;

    CerStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static String getStatusByCode(String code) {
        for (CerStatus value : CerStatus.values()) {
            if (value.getCode().equals(code)) return value.getInfo();
        }
        return null;
    }

    public static String getCodeByStatus(String status) {
        for (CerStatus value : CerStatus.values()) {
            if (value.getInfo().equals(status)) return value.getCode();
        }
        return null;
    }
}
