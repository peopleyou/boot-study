package com.boot.study.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @version 1.0
 * @since 2014-05-12 22:43
 * 功能说明：
 */
public class DateUtils {

    /**
     * 完整时间格式串，如:yyyy年mm月dd
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 用中文字符分隔的时间格式串，如:yyyy年mm月dd
     */
    public static final String YYYY_YEAR_MM_MONTH_DD = "yyyy年MM月dd";

    /**
     * 日期时间格式串，如:yyyy-mm-dd
     */
    public static final String YYYY_MM__DD = "yyyy-MM-dd";
    /**
     * 时分秒时间格式串，如:HH:mm:ss
     */
    public static final String HH_MM_SS = "HH:mm:ss";

    /**
     * 将生日存储的时间格式转化为年龄（周岁，小数点后不计）
     *
     * @param birthdayStr 生日字段 "yyyymmdd"
     * @return 年龄
     */
    public static String birthdayToAge(String birthdayStr) {
        if (birthdayStr == null || birthdayStr.length() < 6) {
            return "";
        } else {
            int birthYear = Integer.parseInt(birthdayStr.substring(0, 4));
            int birthMonth = Integer.parseInt(birthdayStr.substring(4, 6));
            Calendar cal = new GregorianCalendar();
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH);
            int age = currYear - birthYear;
            age -= (currMonth < birthMonth) ? 1 : 0;
            return "" + age;
        }
    }


    /**
     * 日期是几天前的
     *
     * @param date
     * @return
     */
    public static int dayBefore(Date date) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Calendar today = Calendar.getInstance();
        long days = (today.getTimeInMillis() - calendar1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    /**
     * 功能描述: 增加一年
     *
     * @param birDate
     * @return
     */
    public static String nextAgeDate(String birDate) {

        int currDateInt = Integer.parseInt(birDate);
        int ageDateInt = currDateInt + 100;
        return String.valueOf(ageDateInt);
    }

    /**
     * 时间戳转化为时间
     *
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(timestamp);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期、时间格式化
     *
     * @param date   Date 将要被格式化的日期对象
     * @param outFmt String 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String 格式化后的日期、时间字符串，data为null时返回null，outFmt非法时返回yyyyMMdd格式
     */
    public static String getDateFormat(Date date, String outFmt) {
        if (null == date) {
            return null;
        }
        if (null == outFmt || "".equals(outFmt.trim())) { // outFmt非法
            outFmt = "yyyyMMdd";
        }

        String retu = null;
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(outFmt);
        } catch (IllegalArgumentException iaex) { // outFmt非法
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        }
        retu = dateFormat.format(date);

        dateFormat = null;

        return retu;
    }


    /**
     * 日期、时间格式化
     *
     * @param millis long the number of milliseconds（毫秒） since January 1, 1970,
     *               00:00:00 GMT.
     * @param outFmt String 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String 格式化后的日期、时间字符串
     */
    public static String getDateFormat(long millis, String outFmt) {
        Date d = new Date(millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String retu = getDateFormat(calendar.getTime(), outFmt);
        calendar = null;
        return retu;
    }

    /**
     * 获取UTC格式时间字符串
     * @param theDate
     * @return
     */
    public static String getDateUTCFormat(Date theDate) {
        String strDate = getDateFormat(theDate, "yyyy-MM-dd");
        String strTime = getDateFormat(theDate, "HH:mm:ss");

        StringBuilder strb = new StringBuilder();
        strb.append(strDate).append("T").append(strTime).append("Z");

        return strb.toString();
    }

    /**
     * 日期、时间格式化
     *
     * @param datestr String 存在一定格式的日期、时间字符串，如：20041001、200410011503
     * @param inFmt   String 对datestr参数格式说明，参照类说明，如：yyyyMMdd、yyyyMMddHHmm
     * @param outFmt  String 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String 格式化后的日期、时间字符串，如：2004年10月01日、2004年10月01日
     * <p>
     * <p>
     * <p>
     * <BR>
     * 输出样式outFmt非法时，使用yyyyMMdd格式输出
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     */
    public static String getDateFormat(String datestr, String inFmt, String outFmt) throws ParseException {
        if (null == datestr || "".equals(datestr.trim())) {
            return datestr;
        }

        if (null == inFmt || "".equals(inFmt.trim())) {
            return datestr;
        }

        if (null == outFmt || "".equals(outFmt.trim())) { // 输出样式非法
            outFmt = "yyyyMMdd";
        }

        Date inDate = getDate(datestr, inFmt);

        if (null == inDate) { // 根据inFmt分析datestr时抛出异常

            return datestr;
        }

        String retu = getDateFormat(inDate, outFmt);
        inDate = null;
        return retu;
    }

    /**
     * 根据inFmt的样式，日期时间字符串转化为日期时间对象
     *
     * @param datestr String 日期时间字符串，如：20041001、2004年10月01日 15:03
     * @param format  String 对datestr参数格式说明，参照类说明，如yyyyMMdd、yyyy年MM月dd日 HH:mm
     * @return Date 日期时间对象，格式inFmt非法时，使用yyyyMMdd格式
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     */
    public static Date getDate(String datestr, String format) {
        if (datestr == null || datestr.isEmpty()) {
            return null;
        }
        if (format == null || format.isEmpty()) { // inFmt非法
            format = "yyyyMMdd";
        }

        try {
            // 依据inFmt格式把日期字符串转化为日期对象

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(true);
            return dateFormat.parse(datestr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取指定日期的开始时间00:00:00
     *
     * @param theDate
     * @return
     */
    public static Date getDateBegin(Date theDate) {
        if (theDate == null) {
            return null;
        }

        String strDate = DateUtils.getDateFormat(theDate, "yyyy-MM-dd");
        strDate += " 00:00:00";

        return DateUtils.getDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取指定日期的结束时间23:59:59
     *
     * @param theDate
     * @return
     */
    public static Date getDateEnd(Date theDate) {
        if (theDate == null) {
            return null;
        }

        String strDate = DateUtils.getDateFormat(theDate, "yyyy-MM-dd");
        strDate += " 23:59:59";

        return DateUtils.getDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 对日期时间对象进行调整，实现如昨天是几号，去年的今天星期几等. <BR>
     * 例子：
     * <p>
     * <p>
     * <p>
     * <pre>
     * &lt;blockquote&gt;
     * 计算去年今天星期几
     *
     * Date date = DateUtils.addDate(new Date(),Calendar.YEAR,-1);
     * System.out.println(DateUtils.getDateFormat(date,&quot;E&quot;));
     * 打印60天后是什么日期，并显示为 yyyy-MM-dd 星期
     * Date date = DateUtils.addDate(new Date(),Calendar.DATE,60);
     * System.out.println(DateUtils.getDateFormat(date,&quot;yyyy-MM-dd E&quot;));
     * &lt;/blockquote&gt;
     * </pre>
     *
     * @param date          Date 需要调整的日期时间对象
     * @param CALENDARFIELD int 对日期时间对象以什么单位进行调整：
     *                      <p>
     *                      <pre>
     *                                                                                                                                                                                                                                        </pre>
     * @param amount        int 调整数量，>0表向后调整（明天，明年），<0表向前调整（昨天，去年）
     * @return Date 调整后的日期时间对象
     */
    public static Date addDate(Date date, int CALENDARFIELD, int amount) {
        if (null == date) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(CALENDARFIELD, amount);
        return calendar.getTime();
    }

    /**
     * 对日期时间对象进行调整.
     *
     * @param datestr       String 需要调整的日期时间字符串，它的格式为yyyyMMdd
     * @param CALENDARFIELD int 对日期时间对象以什么单位进行调整
     * @param amount        int 调整数量
     * @return Date 调整后的日期时间对象
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     * @see #addDate(Date, int, int)
     */
    public static Date addDate(String datestr, int CALENDARFIELD, int amount) throws ParseException {
        return addDate(getDate(datestr, "yyyyMMdd"), CALENDARFIELD, amount);
    }

    /**
     * 根据出生日期，计算出在某一个日期的年龄
     *
     * @param birthday Date 出生日期时间对象
     * @param date2    Date 计算日期对象
     * @return int 返回date2那一天出生日期为birthday的年龄，如果birthday大于date2则返回-1
     */
    public static int getAge(Date birthday, Date date2) {
        if (null == birthday || null == date2) {
            return -1;
        }

        if (birthday.after(date2)) { // birthday大于date2
            return -1;
        }

        int ibdYear = Integer.valueOf(getDateFormat(birthday, "yyyy"), -1);
        int idate2Year = Integer.valueOf(getDateFormat(date2, "yyyy"), -1);

        if (ibdYear < 0 || idate2Year < 0) {
            return -1;
        }
        if (ibdYear > idate2Year) {
            return -1;
        }

        return idate2Year - ibdYear + 1;
    }

    /**
     * 根据出生日期，计算出当前的年龄
     *
     * @param birthday Date 出生日期时间对象
     * @return int 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     */
    public static int getAge(Date birthday) {
        return getAge(birthday, new Date());
    }

    /**
     * 根据出生日期，计算出当前的年龄
     *
     * @param birthdaystr String 出生日期时间字符串，其格式一定为yyyyMMdd
     * @return int 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     */
    public static int getAge(String birthdaystr) throws ParseException {
        return getAge(getDate(birthdaystr, "yyyyMMdd"));
    }

    /**
     * 获得当前日期与本周日相差的天数
     *
     * @return
     */
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周第一天的日期
     *
     * @param format
     * @return
     */
    public static String getCurrentWeekBegin(String format) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat simpleDateFormat = null;
        if (!"".equals(format.trim()) && null != format.trim()) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return simpleDateFormat.format(cal.getTime());
    }

    /**
     * 获得本周星期日的日期
     *
     * @param format
     * @return
     */
    public static String getCurrentWeekEnd(String format) {
        SimpleDateFormat simpleDateFormat = null;
        if (!"".equals(format.trim()) && null != format.trim()) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        return simpleDateFormat.format(currentDate.getTime());
    }

    /**
     * 获取当前月份的第一天
     *
     * @param format
     * @return
     */
    public static String getCurrentMonthBegin(String format) {
        SimpleDateFormat simpleDateFormat = null;
        if (!"".equals(format.trim()) && null != format.trim()) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前月的最后一天
     *
     * @param format
     * @return
     */
    public static String getCurrentMonthEnd(String format) {
        SimpleDateFormat simpleDateFormat = null;
        if (!"".equals(format.trim()) && null != format.trim()) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 得到几天前的时间
     *
     * @param d   指定日期
     * @param day 几天前
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d   指定日期
     * @param day 几天前
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获得当前所在周的开始时间和结束时间
     *
     * @return
     */
    public static String[] getCurrentWeek() {
        GregorianCalendar cal = new GregorianCalendar();
        Date now = new Date();
        cal.setTime(now);
        cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("now=" + simpleDateFormat.format(cal.getTime())); // 今天

        cal.add(GregorianCalendar.DATE, -1);
        System.out.println("now=" + simpleDateFormat.format(cal.getTime())); // 昨天

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        System.out.println("星期一 = " + simpleDateFormat.format(cal.getTime())); // 本周1

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SUNDAY);
        System.out.println("星期天 = " + simpleDateFormat.format(cal.getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("本月第一天为 = " + simpleDateFormat.format(calendar.getTime())); // 本月1日

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("本月最后一天为 = " + simpleDateFormat.format(calendar.getTime()));
        return null;
    }

    private static String UPDATED_AT = "%1$s前";

    private static String UPDATED_JUST_NOW = "刚刚更新";

    /**
     * 一分钟的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;

    /**
     * 日期与当前日期的差值
     * 小于1分数的  返回 刚刚更新
     * 小于1小的    返回  *分钟前
     * 小于1天的    返回  *小时前
     * 小于1个月的   返回 *天前
     * 大于1个月的  返回 MM-DD
     *
     * @param lastUpdateTime
     */
    public static String refreshUpdatedAtValue(long lastUpdateTime) {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastUpdateTime;
        long timeIntoFormat;
        String updateAtValue;
        if (timePassed < ONE_MINUTE) {
            updateAtValue = UPDATED_JUST_NOW;
        } else if (timePassed < ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_MINUTE;
            String value = timeIntoFormat + "分钟";
            updateAtValue = String.format(UPDATED_AT, value);
        } else if (timePassed < ONE_DAY) {
            timeIntoFormat = timePassed / ONE_HOUR;
            String value = timeIntoFormat + "小时";
            updateAtValue = String.format(UPDATED_AT, value);
        } else if (timePassed < ONE_MONTH) {
            timeIntoFormat = timePassed / ONE_DAY;
            String value = timeIntoFormat + "天";
            updateAtValue = String.format(UPDATED_AT, value);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            updateAtValue = sdf.format(new Date(lastUpdateTime));
        }
        return updateAtValue;
    }

    /**
     * 日期与当前日期的差值
     * 大于1天的    返回  *天
     * 大于1小时    返回  *小时*分钟
     * 小于1小时    返回  *分钟
     *
     * @param lastUpdateTime
     */
    public static String refreshUpdatedAtValue2(long lastUpdateTime) {
        long currentTime = System.currentTimeMillis();
        long timePassed = Math.abs(currentTime - lastUpdateTime);
        long timeIntoFormat;
        String updateAtValue;
        if (timePassed > ONE_DAY) {
            timeIntoFormat = timePassed / ONE_DAY;
            updateAtValue = timeIntoFormat + "天";
        } else if (timePassed > ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_HOUR;
            long minute = (timePassed % ONE_HOUR) / ONE_MINUTE;
            updateAtValue = timeIntoFormat + "小时" + minute + "分钟";
        } else {
            updateAtValue = timePassed / ONE_MINUTE + "分钟";
//            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//            updateAtValue = sdf.format(new Date(lastUpdateTime));
        }
        return updateAtValue;
    }

    /**
     * 转时间戳
     *
     * @param strTime
     * @return
     * @throws ParseException
     */
    public static long formatToTimes(String strTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取指定日期所在周的周一
     */
    public static Date getAssignedWeekBegin(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定日期所在周的周一
     * 若是去年，则取今年的1月1日
     */
    public static Date getAssignedWeekBeginInYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日

        int assignedDateYear = cal.get(Calendar.YEAR);

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        int mondayDateYear = cal.get(Calendar.YEAR);

        return assignedDateYear == mondayDateYear ? cal.getTime() : getFirstDayOfMonth(date);
    }

    /**
     * 获取指定日期所在周的周日
     *
     * @param date
     * @return
     */
    public static Date getAssignedWeekEnd(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        return cal.getTime();
    }

    /**
     * 若是去年，则取今年的12月31日
     *
     * @param date
     * @return
     */
    public static Date getAssignedWeekEndInYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.setFirstDayOfWeek(GregorianCalendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日

        int assignedDateYear = cal.get(Calendar.YEAR);

        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        int sundayDateYear = cal.get(Calendar.YEAR);

        return assignedDateYear == sundayDateYear ? cal.getTime() : getLastDayOfMonth(date, 0);
    }

    /**
     * 获取指定日期是一年中的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekInYear(Date date) {
        if (date == null) {
            return 0;
        }

        Calendar current = Calendar.getInstance();
        current.setFirstDayOfWeek(Calendar.MONDAY);
        current.setTime(date);

        return current.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取日期所属的星期
     *
     * @param date
     * @return
     */
    public static int[] getYearAndWeekForHTML(Date date) {
        //年度第一天
        Calendar tmpCalendar = Calendar.getInstance();
        tmpCalendar.setTime(date);
        tmpCalendar.set(Calendar.DAY_OF_YEAR, 1);
        int firstDay = getChinaDayOfWeek(tmpCalendar.getTime());
        //参数日期
        Calendar current = Calendar.getInstance();
        current.setFirstDayOfWeek(Calendar.MONDAY);
        current.setTime(date);

        int week = current.get(Calendar.WEEK_OF_YEAR);
        int month = current.get(Calendar.MONTH) + 1;
        int year = current.get(Calendar.YEAR);

        if (week == 1) {//是否是跨年周
            if (month == 12) {//判断12-31号星期几(四就是53周,否则来年第一周)
                int day = getChinaDayOfWeek(getLastDayOfMonth(date, 0));
                if (day >= 4) {
                    Date lastWeek = addDate(current.getTime(), Calendar.WEEK_OF_YEAR, -1);
                    week = getYearAndWeekForHTML(lastWeek)[1] + 1;//上一星期是第几周
                } else {
                    year += 1;
                }
            } else if (month == 1) {//判断1-1号星期几(三就是第一周,否则去年53周)
                int day = getChinaDayOfWeek(getFirstDayOfMonth(date));
                if (day > 4) {
                    Date lastWeek = addDate(current.getTime(), Calendar.WEEK_OF_YEAR, -1);//上一星期是第几周
                    int[] yearAndWeek = getYearAndWeekForHTML(lastWeek);//上一星期是第几周
                    year = yearAndWeek[0];
                    week = yearAndWeek[1] + 1;
                }
            }
        } else {
            if (firstDay > 4) {
                week -= 1;
            }
        }
        return new int[]{year, week};
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static String getLastDayOfWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置周
        cal.set(Calendar.WEEK_OF_YEAR, week);
        //设置该周第一天为星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //设置最后一天是星期日
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfWeek = sdf.format(cal.getTime());
        return lastDayOfWeek;
    }

    /**
     * 获取星期几(周一为1)
     *
     * @param date
     * @return
     */
    public static int getChinaDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return (day > 0) ? day : 7;
    }

    /**
     * 获取所属的星期的开始时间和结束时间
     *
     * @param year
     * @param week
     * @return
     */
    public static Date[] getWeekStartAndEndFromHTML(int year, int week) {
        int[] yearAndWeek = getYearAndWeekForHTML(getDate(year + "0101", "yyyymmdd"));
        Calendar current = Calendar.getInstance();
        current.setFirstDayOfWeek(Calendar.MONDAY);
        current.set(Calendar.YEAR, year);
        if (yearAndWeek[0] == year) {//如果1-1号今年第一周
            current.set(Calendar.WEEK_OF_YEAR, week);
        } else {//如果1-1号是去年第53周
            current.set(Calendar.WEEK_OF_YEAR, week + 1);
        }
        Date currentTime = current.getTime();
        return new Date[]{
                getAssignedWeekBegin(currentTime),
                getAssignedWeekEnd(currentTime)
        };
    }


    /**
     * 获取指定日期是一年中的第几个月
     *
     * @param date
     * @return
     */
    public static int getMonthInYear(Date date) {
        if (date == null) {
            return 0;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(date);

        return current.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得指定月份的第一天
     *
     * @param strdate
     * @param inFrm
     * @param outFrm
     * @return
     * @throws ParseException
     */
    public static String getAssignedMonthBegin(String strdate, String inFrm, String outFrm) throws ParseException {
        Date date = getDate(strdate, inFrm);
        if ("".equals(outFrm.trim()) || null == outFrm.trim()) {
            outFrm = "yyy-MM-dd";
        }
        return getDateFormat(getDateFormat(date, "yyyy-MM") + "-01", "yyy-MM-dd", outFrm);
    }

    /**
     * 获得指定月份最后一天
     *
     * @param strdate
     * @param inFrm
     * @param outFrm
     * @return
     */
    public static String getAssignedMonthEnd(String strdate, String inFrm, String outFrm) {
        Date date = getDate(strdate, inFrm);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = null;
        if (!"".equals(outFrm.trim()) && null != outFrm.trim()) {
            simpleDateFormat = new SimpleDateFormat(outFrm);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定日期前几个月的最后一天
     *
     * @param date
     * @param intervalMonth
     * @return
     */
    public static Date getLastDayOfMonth(Date date, int intervalMonth) {
        Calendar current = Calendar.getInstance();
        current.setTime(date);

        int month = current.get(Calendar.MONTH);
        current.set(Calendar.MONTH, month - intervalMonth + 1);
        current.set(Calendar.DAY_OF_MONTH, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        Date firstDayOfNextMonth = current.getTime();
        return getDateBefore(firstDayOfNextMonth, 1);
    }

    /**
     * 获取指定日期所属月份的第1天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar current = Calendar.getInstance();
        current.setTime(date);

        current.set(Calendar.DAY_OF_MONTH, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        return current.getTime();
    }

    /**
     * 获取指定周之前或之后
     *
     * @param date
     * @param addWeekCount
     * @return
     */
    public static Date getAddWeek(Date date, int addWeekCount) {
        Calendar current = Calendar.getInstance();
        current.setFirstDayOfWeek(Calendar.MONDAY);
        current.setTime(date);

        current.add(Calendar.WEEK_OF_YEAR, addWeekCount);

        return current.getTime();
    }

    /**
     * 获取指定月之前或之后
     *
     * @param date
     * @param addMonthCount
     * @return
     */
    public static Date getAddMonth(Date date, int addMonthCount) {
        Calendar current = Calendar.getInstance();
        current.setTime(date);

        current.add(Calendar.MONTH, addMonthCount);

        return current.getTime();
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 获取指定月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        return a.getTime();
    }

    /**
     * 获取指定月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Date firstDayOfMonth = getFirstDayOfMonth(year, month);
        Date nextMonthFirstDay = getAddMonth(firstDayOfMonth, 1);
        return getDateBefore(nextMonthFirstDay, 1);
    }

    /**
     * 获取日期范围内月份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 获取指定日期所在月的第一天
     * @param theDate
     * @return
     */
    public static Date getMonthBegin(Date theDate) {
        Calendar current = Calendar.getInstance();
        current.setTime(theDate);

        current.set(Calendar.DAY_OF_MONTH, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        return current.getTime();
    }

    /**
     * 获取指定日期所在月的最后一天
     *
     * @param theDate
     * @return
     */
    public static Date getMonthEnd(Date theDate) {
        Date nextMonthDay = getAddMonth(theDate, 1);
        Date nextMonthBegin = getMonthBegin(nextMonthDay);
        return getDateBefore(nextMonthBegin, 1);
    }

    private static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    /**
     * 根据日期获取星座
     *
     * @return
     */
    public static String getStarSign(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }

    /**
     * 比较time大小
     * time1 大于 time2时返回 1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareTime(String time1, String time2) {
        int flag = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        try {
            Time date1 = new Time(sdf.parse(time1).getTime());
            Time date2 = new Time(sdf.parse(time2).getTime());
            int intTime1 = Integer.parseInt(getDateFormat(date1, "HHmmss"));
            int intTime2 = Integer.parseInt(getDateFormat(date2, "HHmmss"));
            if (intTime1 > intTime2) {
                flag = 1;
            } else if (intTime1 < intTime2) {
                flag = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return flag;
    }

    // 时间减(time的格式为"HH:mm:ss"，offTime为分钟,返回值格式为"HH:mm:ss")
    public static Date timeSub(String time, long offTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long resultTime;
        try {
            resultTime = sdf.parse(time).getTime() - offTime * 60 * 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Date(resultTime);
    }

    // 时间加(time的格式为"HH:mm:ss"，offTime为分钟,返回值格式为"HH:mm:ss")
    public static Date timeAdd(String time, long offTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long resultTime;
        try {
            resultTime = sdf.parse(time).getTime() + offTime * 60 * 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Date(resultTime);
    }

    // 时间加(time的格式为"HH:mm:ss"，offTime为分钟,返回值格式为"yyyy-MM-dd HH:mm:ss")
    public static Date dateAdd(String time, long offTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            long resultTime = sdf.parse(time).getTime() + offTime * 60 * 1000;
            String result = formatter.format(new Date()) + " " + sdf.format(new Date(resultTime));

            return format.parse(result);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 时间减(time的格式为"HH:mm:ss"，offTime为分钟,返回值格式为"yyyy-MM-dd HH:mm:ss")
    public static Date dateSub(String time, long offTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            long resultTime = sdf.parse(time).getTime() - offTime * 60 * 1000;
            String result = formatter.format(new Date()) + " " + sdf.format(new Date(resultTime));

            return format.parse(result);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证指定时间是否在某个时间段内(start和end只有时间，没有日期)
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static Boolean isValidTime(Date date, Date start, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            long currentTime = sdf.parse(sdf.format(new Date())).getTime();
            long startTime = sdf.parse(sdf.format(start)).getTime();
            long endTime = sdf.parse(sdf.format(end)).getTime();
            if (currentTime >= startTime && currentTime <= endTime) {
                return true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public static void main(String[] arg) throws Exception {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1 = simpleDateFormat.parse("2017-04-10 18:00:00");
//        Date date2 = simpleDateFormat.parse("2017-04-10 21:01:00");
//        boolean a = compareTimeSize(date2,date1, 3, Calendar.HOUR );

//        System.out.println(getDateFormat(getWeekStartAndEndFromHTML(2017, 18)[1], "yyyy-MM-dd"));
//        System.out.println(getDateFormat(getWeekStartAndEndFromHTML(2017, 19)[1], "yyyy-MM-dd"));
//        System.out.println(getDateFormat(getWeekStartAndEndFromHTML(2017, 20)[1], "yyyy-MM-dd"));
//        System.out.println(getDateFormat(getWeekStartAndEndFromHTML(2017, 21)[1], "yyyy-MM-dd"));
//        System.out.println(getDateFormat(getWeekStartAndEndFromHTML(2017, 22)[1], "yyyy-MM-dd"));
    }


}

