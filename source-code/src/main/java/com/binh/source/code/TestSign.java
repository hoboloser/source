/*
 * Project: eurekaclient
 * 
 * File Created at 2018年8月3日
 * 
 * Copyright 2016 CMCC Corporation Limited. All rights reserved.
 *
 * This software is the confidential and proprietary information of ZYHY Company. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license.
 */
package com.binh.source.code;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Type TestSign.java
 * @Desc
 * @author Administrator
 * @date 2018年8月3日 上午9:08:09
 * @version
 */
public class TestSign {

    public static void main(String[] args) {
        Map<String, Object> map = new TreeMap<String, Object>();
        // H5 单点
        // ver+appId+msgId + timestamp+ appkey+phoneNum+targetAppId
        // sign":"A7C9A80F0C8638764057A32D5E11EABD""

        map.put("version", "1.0");
        map.put("id", "050017");
        map.put("idtype", "0");
        map.put("msgid", "Ol97AxUWLB0kSqZWmfaMqaLFwR5zlfoJ");
        map.put("token", "YZsid17aead8fccdcdc43aa1c859098a9b");
        map.put("systemtime", "20180806172450763");
        map.put("key", "PgXwrErN4lO0KLQ3");
        map.put("apptype", "2");
        String enStr = mapToString(map);
        String sign = md5(enStr);
        System.out.println("sign=" + sign);
    }

    /**
     * 
     * 将Map转换为String
     * 
     * @Title: map2String
     * @param map
     * @return
     * @author: yanhuajian 2013-7-21下午7:25:08
     */
    private static String mapToString(Map<String, Object> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getValue());
        }

        return sb.toString();
    }

    public static String md5(String text) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("UTF-8"));
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
