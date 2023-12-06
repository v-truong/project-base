package com.example.common.model;

import com.example.common.config.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ThreadContext {
    private static ThreadLocal<Tenant> currentTenant = new ThreadLocal<>();
        private static ThreadLocal<User> currentuser = new ThreadLocal<>();
    public static User getCustomUserDetails() {
      return currentuser.get();
    }
    public static Tenant getCurrentTenant() {
        return currentTenant.get();
      }
    
//      public static String getTenantId() {
//        Tenant ct = currentTenant.get();
//        if (ct!= null && ct.getTenantId() != null) {
//          return ct.getTenantId();
//        } else {
//          return Constants.ZERO_UUID;
//        }
//      }
    
      public static void setCurrentTenant(Tenant tenant) {
        currentTenant.set(tenant);
      }
        public static void setCurrentuser(User user) {
        currentuser.set(user);
      }

 }

