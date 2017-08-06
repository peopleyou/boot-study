package com.boot.study.mvc;

import com.boot.study.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by 37300 on 2017/8/6.
 */
@Component
public class DateParamConverter implements Converter<String, Date> {

    private static final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_10 = "yyyy-MM-dd";

    @Override
    public Date convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }

        if (s.length() == DATE_FORMAT_19.length()) {
            return DateUtils.getDate(s, DATE_FORMAT_19);
        } else if (s.length() == DATE_FORMAT_10.length()) {
            return DateUtils.getDate(s, DATE_FORMAT_10);
        }

        return null;
    }
}
