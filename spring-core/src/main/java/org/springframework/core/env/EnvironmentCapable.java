/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.env;

/**
 * Interface indicating a component that contains and exposes an {@link Environment} reference.
 *
 * <p>All Spring application contexts are EnvironmentCapable, and the interface is used primarily
 * for performing {@code instanceof} checks in framework methods that accept BeanFactory
 * instances that may or may not actually be ApplicationContext instances in order to interact
 * with the environment if indeed it is available.
 *
 * <p>As mentioned, {@link org.springframework.context.ApplicationContext ApplicationContext}
 * extends EnvironmentCapable, and thus exposes a {@link #getEnvironment()} method; however,
 * {@link org.springframework.context.ConfigurableApplicationContext ConfigurableApplicationContext}
 * redefines {@link org.springframework.context.ConfigurableApplicationContext#getEnvironment
 * getEnvironment()} and narrows the signature to return a {@link ConfigurableEnvironment}.
 * The effect is that an Environment object is 'read-only' until it is being accessed from
 * a ConfigurableApplicationContext, at which point it too may be configured.
 *
 * @author Chris Beams
 * @see Environment
 * @see ConfigurableEnvironment
 * @see org.springframework.context.ConfigurableApplicationContext#getEnvironment()
 * @since 3.1
 */

/**
 * 指示包含并暴露 {@link Environment} 引用的组件
 *
 * 所有 Spring 应用程序上下文都是 EnvironmentCapable 的，接口主要用于在接受 BeanFactory 的框架方法中执行 {@code instanceof} 检查
 * 实例是否是 ApplicationContext 实例，以便进行交互，如果环境确实可用的话。
 *
 * 如前所述，{@link org.springframework.context.ApplicationContext ApplicationContext}
 * 扩展 EnvironmentCapable，从而暴露 {@link #getEnvironment()} 方法；然而,
 * {@link org.springframework.context.ConfigurableApplicationContext ConfigurableApplicationContext}
 * 重新定义 {@link org.springframework.context.ConfigurableApplicationContext#getEnvironment
 * getEnvironment()} 并缩小签名以返回 {@link ConfigurableEnvironment}。
 * 结果是一个环境对象是“只读的”，直到它被一个 ConfigurableApplicationContext 访问，在这一点上它也可以被配置。
 */
public interface EnvironmentCapable {

	/**
	 * Return the {@link Environment} associated with this component.
	 */
	/**
	 * 返回与该组件相关的 {@link Environment}。
	 */
	Environment getEnvironment();

}
