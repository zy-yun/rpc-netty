package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/10 21:57
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceInfo {

    private String serviceName;

    private String serviceAddress;

    private int servicePort;
}
