package com.ruoyi.system.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dewily
 * @date 2022-07-15 15:28
 */
public class AuthUtil {

    public static List<Integer> getAuthList(String authString) {
        String[] authList = authString.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<authList.length;i++) {
            list.add(Integer.parseInt(authList[i]));
        }
        return list;
    }
}
