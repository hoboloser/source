/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/24	       binh              Initial
 */
package com.binh.source.code;

/**
 * @ClassName @{link Test}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public class Test {

    public static void main(String[] args) {
        try {
            try {
                int i = 1 / 0;
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println(e);
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
}
