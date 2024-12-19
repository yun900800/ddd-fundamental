package org.ddd.fundamental.common.tenant.aop;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TenantOperation {

    /**
     * 表配置
     *
     * @return 表配置
     */
    Tables[] value() default {};

    /**
     * 是否对当前方法与后续调用链不进行租户拦截
     *
     * @return boolean
     * @see Ignore
     */
    boolean ignore() default false;

    /**
     * 对当前方法与后续调用链不进行租户拦截
     */
    @TenantOperation(ignore = true)
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @interface Ignore {}

    /**
     * 表配置
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface Tables {

        /**
         * 租户字段名，不指定时默认遵循配置文件中的字段名
         *
         * @return String
         */
        String column() default "";

        /**
         * 需要添加过滤条件的表名，不指定时默认遵循配置文件中的表名
         *
         * @return String
         */
        String[] tables() default {};
    }
}
