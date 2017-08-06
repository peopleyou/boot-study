package com.boot.study.api;

import com.boot.study.domain.JsonResp;
import com.boot.study.domain.TestInfoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by mickjoust on 2016/6/14.
 * com.hjf.boot.demo.boot_properties.api
 */
@RestController
public class TestController {

    @Autowired
    private TestInfoSettings testInfoSettings;

    @RequestMapping("/success")
    public Object success() {
        String testStr = "hello world!";
        return JsonResp.success(testStr);
    }

    @RequestMapping("/fail")
    public Object fail() {
        return JsonResp.fail();
    }


    @RequestMapping("/showSetting")
    public Object showSetting() {
        StringBuffer sb = new StringBuffer();
        sb.append("setting name is : ").append(testInfoSettings.getName());
        sb.append("setting age is : ").append(testInfoSettings.getAge());
        sb.append("all is : ").append(testInfoSettings);
        return JsonResp.success(sb);
    }

    /**
     * 自动配置的静态资源：跳转到指定的静态资源路径
     * 把类路径下的/static、/public、/resources和/META-INF/resources文件夹下的静态文件直接映射为 /** 来提供访问。
     * @return
     */
    @RequestMapping("/to-helloworld")
    public ModelAndView toHelloWorld() {
        return new ModelAndView("/hello/helloworld2.html");
    }

    /**
     * 配置 Formatter 和 Converter:
     * 只需要定义 Converter, GenericConverter 和 Formatter 接口实现的实现类Bean，就会注册Bean到Spring MVC中了
     * e.g. 将请求参数中的日志类型转换为日期对象
     * @param theDate
     * @return
     */
    @RequestMapping("/test-date-param")
    public Object testDateFormatParam(@RequestParam("theDate") Date theDate) {
        return JsonResp.success(theDate);
    }
}
