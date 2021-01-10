package com.onlineexam.client;

import com.onlineexam.api.RoleRightsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface RoleRightsClient extends RoleRightsApi {
}
