package org.ddd.fundamental.factory.config;

import cn.hutool.core.lang.Assert;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.common.tenant.aop.LineLevelTenantOperationAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@ConditionalOnProperty(prefix = TenantInterceptorConfig.Properties.CONFIG_PREFIX, name = "enabled", havingValue = "true")
@EnableConfigurationProperties(TenantInterceptorConfig.Properties.class)
@Configuration
@Slf4j
public class TenantInterceptorConfig {
    @Bean
    public LineLevelTenantOperationAdvisor lineLevelTenantOperationAdvisor(Properties properties) {
        log.info("启用租户拦截器，需要拦截的表：{}", properties.getTables());
        Map<String, String> tableWithColumns = new HashMap<>(16);
        properties.getTables().forEach(ts -> ts.getTableNames().forEach(t -> {
            Assert.isFalse(tableWithColumns.containsKey(t), "同一张表具备只允许具备一个租户字段：{}", t);
            tableWithColumns.put(t, ts.getColumn());
        }));
        return new LineLevelTenantOperationAdvisor(tableWithColumns);
    }

    /**
     * @author huangchengxing
     */
    @ConfigurationProperties(prefix = Properties.CONFIG_PREFIX)
    @Data
    public static class Properties {

        public static final String CONFIG_PREFIX = "tenant.interceptor";

        /**
         * 表配置
         */
        private List<Tables> tables = new ArrayList<>();

        @Data
        public static class Tables {

            /**
             * 租户字段名
             */
            private String column;

            /**
             * 需要拦截的表名
             */
            private Set<String> tableNames;
        }
    }
}
