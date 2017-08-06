package com.boot.study.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通过@ConfigurationProperties将properties属性和一个Bean及其属性关联，从而实现类型安全的配置
 *
 * 通过@Value来注入配置的方式不是类型安全的配置
 *
 * Created by mickjoust on 2016/6/16.
 * com.hjf.boot.demo.boot_properties.domain
 */
@Component
@ConfigurationProperties(prefix = "usetest")
public class TestInfoSettings {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
