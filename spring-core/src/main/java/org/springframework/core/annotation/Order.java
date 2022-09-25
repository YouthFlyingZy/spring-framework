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

package org.springframework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;

/**
 * {@code @Order} defines the sort order for an annotated component.
 *
 * <p>The {@link #value} is optional and represents an order value as defined in the
 * {@link Ordered} interface. Lower values have higher priority. The default value is
 * {@code Ordered.LOWEST_PRECEDENCE}, indicating the lowest priority (losing to any
 * other specified order value).
 *
 * <p><b>NOTE:</b> Since Spring 4.0, annotation-based ordering is supported for many
 * kinds of components in Spring, even for collection injection where the order values
 * of the target components are taken into account (either from their target class or
 * from their {@code @Bean} method). While such order values may influence priorities
 * at injection points, please be aware that they do not influence singleton startup
 * order which is an orthogonal concern determined by dependency relationships and
 * {@code @DependsOn} declarations (influencing a runtime-determined dependency graph).
 *
 * <p>Since Spring 4.1, the standard {@link javax.annotation.Priority} annotation
 * can be used as a drop-in replacement for this annotation in ordering scenarios.
 * Note that {@code @Priority} may have additional semantics when a single element
 * has to be picked (see {@link AnnotationAwareOrderComparator#getPriority}).
 *
 * <p>Alternatively, order values may also be determined on a per-instance basis
 * through the {@link Ordered} interface, allowing for configuration-determined
 * instance values instead of hard-coded values attached to a particular class.
 *
 * <p>Consult the javadoc for {@link org.springframework.core.OrderComparator
 * OrderComparator} for details on the sort semantics for non-ordered objects.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.core.Ordered
 * @see AnnotationAwareOrderComparator
 * @see OrderUtils
 * @see javax.annotation.Priority
 */
/**
 * {@code @Order} 定义了一个带注释组件的排序顺序。
 * {@link Ordered} 接口中 {@link #value}是可选的，表示顺序值。数值越小优先级越高。缺省值为 {@code Ordered.LOWEST_PRECEDENCE}，
 * 表示最低优先级(低于其他指定的值)。
 *
 * 注意：自 Spring 4.0 以来，在 Spring 中基于注释的排序支被应用于各种类型的组件，甚至集合注入的顺序值的目标组件被考虑(从他们的目标类或
 * 从他们的 {@code @Bean} 方法)。在注入时，虽然这样的顺序值可能会影响优先级，请注意它们不会影响单例的启动顺序，单例启动顺序是由依赖关系
 * 和确定的正交关系 {@code @DependsOn} 声明（影响运行时确定的依赖关系图）决定的。
 *
 * 从 Spring 4.1开始，标准 {@link javax.annotation.Priority} 注解可以在排序场景中作为该注释的替代。注意当单个元素不得不被被
 * （参见{@link AnnotationAwareOrderComparator#getPriority}）选中时，{@code @Priority} 可能有额外的语义。
 *
 * 另外，顺序值也可以在每个实例的基础上通过 {@link Ordered} 接口确定，允许配置确定实例值，而不是附加到特定类的硬编码值。
 *
 * 查阅 javadoc {@link org.springframework.core.OrderComparator OrderComparator} 详细说明非有序对象的排序语义。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Order {

	/**
	 * The order value.
	 * <p>Default is {@link Ordered#LOWEST_PRECEDENCE}.
	 * @see Ordered#getOrder()
	 */
	int value() default Ordered.LOWEST_PRECEDENCE;

}
