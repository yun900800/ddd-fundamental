package org.ddd.fundamental.Thread;

import org.ddd.fundamental.core.tenant.FactoryId;
import org.ddd.fundamental.core.tenant.TenantId;
import org.ddd.fundamental.core.tenant.TenantInfo;

public final class ThreadUtils {
    private static InheritableThreadLocal<TenantInfo> shared = new InheritableThreadLocal<>(){
        public TenantInfo initialValue() {
            return TenantInfo.create(
                    TenantId.randomId(TenantId.class),
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
