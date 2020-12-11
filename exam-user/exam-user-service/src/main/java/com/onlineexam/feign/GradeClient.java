package com.onlineexam.feign;

import com.onlineexam.api.GradeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-school-service")
public interface GradeClient extends GradeApi {
}
