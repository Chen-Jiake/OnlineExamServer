package com.onlineexam.client;

import com.onlineexam.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface UserClient extends UserApi {
}
