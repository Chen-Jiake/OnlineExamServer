package com.onlineexam.feign;

import com.onlineexam.api.SubjectApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-school-service")
public interface SubjectClient extends SubjectApi {
}
