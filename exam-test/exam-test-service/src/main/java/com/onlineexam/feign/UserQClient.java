package com.onlineexam.feign;

import com.onlineexam.api.UserQApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface UserQClient extends UserQApi {
}
