package org.ddd.fundamental.Thread;

import org.ddd.fundamental.core.tenant.FactoryId;
import org.ddd.fundamental.core.tenant.TenantId;
import org.ddd.fundamental.core.tenant.TenantInfo;


/**
 * 线程变量在父子线程之间不共享的情况 ThreadLocal
 * 线程变量在父子线程之间共享的情况 InheritableThreadLocal initialValue set get
 * 线程变量不仅在父子线程之间共享,而且子线程装饰了父线程的变量 childValue set get
 */
public final class ThreadSharedUtils {
    private static InheritableThreadLocal<TenantInfo> shared = new InheritableThreadLocal<>(){
        public TenantInfo initialValue() {
            return TenantInfo.create(
                    new TenantId("bf0e30d6-1fc6-4874-91b3-af352404e50a"),
                    FactoryId.randomId(FactoryId.class));
        }
    };

    public static void setTenantInfo(TenantInfo tenantInfo) {
        shared.set(tenantInfo);
    }

    public static TenantInfo getTenantInfo(){
        return shared.get();
    }

}
