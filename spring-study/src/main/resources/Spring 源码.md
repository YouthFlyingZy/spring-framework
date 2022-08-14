# Spring5.3 源码

## 1、IOC

### 概念

依赖注入，控制反转。将创建对象的功能转交给容器，摒弃 java 中繁复的 new，根据我们自己的定制可创建多样化的对象，在需要使用的地方注入即可。

### 历史

在 spring 早期，ssm，ssh 大行其道，基本都是 xml 文件集中管理 bean 定义，目前版本 Spring5.3  `@Configuration`,`@Bean`等注解方式逐渐占据主流，当然 Spring5.3 仍然支持 xml 方式的定义。使用注解式管理 bean 定义时，底层的容器创建类为 `AnnotationConfigApplicationContext`，而使用 xml 方式管理时，底层的容器创建类为 `ClassPathXmlApplicationContext`。本文档中，全部使用 `@Bean`方式进行讲解。

### 追溯

#### 编写测试类

```java
package com.zhongye.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author zhongye
 * @date 2022-08-06 11:15:29
 */

@Configuration
public class SpringConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://10.1.1.101/sc_whmc_prod?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
		dataSource.setUsername("cxdev");
		dataSource.setPassword("123456");
		return dataSource;
	}
}
```

```java
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
```

可以看到，我们初始化容器使用的是 `AnnotationConfigApplicationContext`，该类相关的继承结构如下

![image-20220814125145792](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220814125145792.png)

#### 解析

##### ① 构造函数链调用

```java
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
```

这里我们根据传入的自定义的 `SpringConfiguration` 配置类，初始化了一个容器，具体的初始化完全由 Spring 完成。

通过`AnnotationConfigApplicationContext` 的继承结构可以得知，具体的构造器调用过程如下：

![image-20220814131939229](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220814131939229.png)

所以，依次执行 `DefaultResourceLoader`，`AbstractApplicationContext`，`GenericApplicationContext`，`AnnotationConfigApplicationContext` 中的构造函数。

###### DefaultResourceLoader

```java
	/**
	 * 创建一个新的 DefaultResourceLoader。
	 * ClassLoader 访问将使用线程上下文类加载器发生在实际访问资源时(5.3起)。
	 * 为了更多的控制，通过指定一个 ClassLoader 到 {@link #DefaultResourceLoader(ClassLoader)}。
	 * @see java.lang.Thread # getContextClassLoader ()
	 */
	public DefaultResourceLoader() {
	}
```

###### AbstractApplicationContext

```java
	/**
	 * 创建一个新的没有父类的 AbstractApplicationContext。
	 */
	public AbstractApplicationContext() {
		this.resourcePatternResolver = getResourcePatternResolver();
	}

	/**
	 * 返回用于解析位置模式的 ResourcePatternResolver 转换为资源实例。默认是一个
	 * {@link org.springframework.core.io.support.PathMatchingResourcePatternResolver},
	 * 支持 Ant 风格的定位模式。可以在子类中重写，用于扩展解析策略，例如在 web 环境中。当需要解析位置模式时不要
	 * 调用该函数 ，调用上下文的 {@code getResources} 方法，将委托给 ResourcePatternResolver。
	 * @return 这个上下文的 ResourcePatternResolver
	 * @see # getresource
	 * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver
	 */
	protected ResourcePatternResolver getResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver(this);
	}

	/**
	 * 创建一个新的 PathMatchingResourcePatternResolver
	 * ClassLoader 访问将通过线程上下文类加载器发生。
	 * @param resourceLoader resourceLoader 加载根目录和实际资源
	 */
	public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
		Assert.notNull(resourceLoader, "ResourceLoader must not be null");
		this.resourceLoader = resourceLoader;
	}
```

###### GenericApplicationContext

```java
	/**
	 * 创建一个新的 GenericApplicationContext。
	 * @see # registerBeanDefinition
	 * @see #refresh
	 */
	public GenericApplicationContext() {
		this.beanFactory = new DefaultListableBeanFactory();
	}
```

GenericApplicationContext 中直接创建了一个 DefaultListableBeanFactory

###### DefaultListableBeanFactory

```java
	/**
	 * 创建一个新的DefaultListableBeanFactory。
	 */
	public DefaultListableBeanFactory() {
		super();
	}
```

其中 super() 调用的是 `DefaultListableBeanFactory` 父类 `AbstractAutowireCapableBeanFactory` 的构造函数

###### AbstractAutowireCapableBeanFactory

```java
	/**
	 * 创建一个新的 AbstractAutowireCapableBeanFactory。
	 */
	public AbstractAutowireCapableBeanFactory() {
		super();
		ignoreDependencyInterface(BeanNameAware.class);
		ignoreDependencyInterface(BeanFactoryAware.class);
		ignoreDependencyInterface(BeanClassLoaderAware.class);
		if (NativeDetector.inNativeImage()) {
			this.instantiationStrategy = new SimpleInstantiationStrategy();
		}
		else {
			this.instantiationStrategy = new CglibSubclassingInstantiationStrategy();
		}
	}
```



`AnnotationConfigApplicationContext`  构造函数如下：

