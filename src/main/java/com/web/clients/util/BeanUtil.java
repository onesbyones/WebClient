package com.web.clients.util;

import org.springframework.beans.BeanUtils;

/**
 * 
 * @author hqh
 *
 */
public class BeanUtil {

	public static void copyProperties(Object source, Object target) {

		BeanUtils.copyProperties(source, target);

	}

}
