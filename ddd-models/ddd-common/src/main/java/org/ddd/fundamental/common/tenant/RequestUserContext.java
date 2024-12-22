package org.ddd.fundamental.common.tenant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUserContext {

    private static final ThreadLocal<User> USER_CONTEXT = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_CONTEXT.set(user);
    }

    @Nullable
    public static User getUser() {
        return USER_CONTEXT.get();
    }

    public static void clear() {
        USER_CONTEXT.remove();
    }

    @Data
    public static class User {
        private String userId;
        public User(String userId){
            this.userId = userId;
        }
    }
}
