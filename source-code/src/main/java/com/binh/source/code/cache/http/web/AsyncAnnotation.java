/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/07	       binh              Initial
 */
package com.binh.source.code.cache.http.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @ClassName @{link AsyncAnnotation}
 * @Description TODO
 *
 * @author binh
 * @date 2018/08/07
 */
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AsyncAnnotation {

    String value() default "";
}
