package com.zhongye.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author zhongye
 * @date 2022-08-06 11:15:29
 */
public class SpringIoc {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		DataSource dataSource = context.getBean(DataSource.class);
		System.out.println(dataSource);
	}
}
