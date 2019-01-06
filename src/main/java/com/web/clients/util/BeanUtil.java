package com.web.clients.util;

import org.springframework.beans.BeanUtils;

/**
 * 通过反射，把类A中的field映射到类B中
 * 
 * @author hqh
 *
 */
public class BeanUtil {

	public static void copyProperties(Object source, Object target) {

		BeanUtils.copyProperties(source, target);

	}

}
