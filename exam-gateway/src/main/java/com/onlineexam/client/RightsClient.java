package com.onlineexam.client;

import com.onlineexam.api.RightsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface RightsClient extends RightsApi {
}
