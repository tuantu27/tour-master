package com.example.tour.utils;
import com.example.tour.config.app.CustomStatus;
import com.example.tour.exception.CustomException;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Formatter {
    private SimpleDateFormat sdf = null;
    private SimpleDateFormat sdfTime = null;
    private NumberFormat nf = null;
    private NumberFormat nff = null;
    private NumberFormat nf4 = null;
    private String language = null;
    private String date_format = null;
    private String date_symbol = null;
    private String decimal_symbol = ",";
    private DecimalFormat df4en = null;
    public static final int ROUND_ONE = 1;

    public Formatter() {
        Locale vietnamLocale = new Locale("vi", "VN");
        this.setDefaultWithLocale(vietnamLocale);
    }

    private void setDefaultWithLocale(Locale locale) {
        try {
            Locale lo = locale;
            language = locale.getLanguage().toLowerCase();
            if ("vi".equals(language)) {
                date_symbol = "ddMMyyyy";
                date_format = "dd/MM/yyyy";
                decimal_symbol = ",";
                lo = Locale.FRANCE;
            } else {
                date_symbol = "MMddyyyy";
                date_format = "MM/dd/yyyy";
                decimal_symbol = ".";
            }
            sdf = new SimpleDateFormat(date_format);
            sdfTime = new SimpleDateFormat(date_format + " HH:mm:ss.SSSZ");
            nf = NumberFormat.getNumberInstance(lo);
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(2);
            nf4 = NumberFormat.getNumberInstance(lo);
            nf4.setMinimumFractionDigits(4);
            nf4.setMaximumFractionDigits(4);

            nff = NumberFormat.getNumberInstance(lo);
            nff.setMinimumFractionDigits(2);
            nff.setMaximumFractionDigits(2);

            df4en = (DecimalFormat) DecimalFormat.getInstance(lo);
            df4en.setMinimumFractionDigits(0);
            df4en.setMaximumFractionDigits(4);
            df4en.getDecimalFormatSymbols().setDecimalSeparator('.');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNotNull(Object value) {
        if (value instanceof String)
            return !((value == null) || (((String) value).trim().length() == 0));
        else if (value instanceof Collection || value instanceof List)
            return !((value == null) || ((Collection<?>) value).size() == 0);
        else if (value instanceof LinkedHashMap || value instanceof Map || value instanceof HashMap)
            return !((value == null) || ((Map<?, ?>) value).size() == 0);
        return !(value == null);
    }

    public static boolean isNotNull(Collection collection) {
        return !((collection == null) || collection.size() == 0);
    }

    public static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

    public Timestamp stringToTime(String str) {
        if (!isNotNull(str))
            return null;
        Timestamp result = null;
        try {
            result = new Timestamp(sdf.parse(str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String dateToString(Date date) {
        if (date == null)
            date = new Date();
        return sdf.format(date);
    }

    public String dateTimeToString(Date date) {
        if (date == null)
            date = new Date();
        return sdfTime.format(date);
    }

    public Date stringToDate(String strDate) {
        Date result = null;
        try {
            result = sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public Date stringToDateTime(String date) {
        Date result = null;
        try {
            result = sdfTime.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public GregorianCalendar stringToGregorianCalendar(String str) {
        if (!isNotNull(str))
            return null;
        GregorianCalendar result = new GregorianCalendar();
        try {
            result.setTime(stringToDate(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static GregorianCalendar dateToGregorianCalendar(Date t) {
        if (t == null)
            return null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(t.getTime());
        return gc;
    }

    public static String getTimestamp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZ");
        return dateTimeFormatter.format(ZonedDateTime.now());
    }

    public String nextTime(String currentDate, int next, int type, boolean getTime) {
        try {
            Calendar today = Calendar.getInstance();
            Date date;
            if (getTime)
                date = stringToDateTime(currentDate);
            else
                date = stringToDate(currentDate);
            today.setTime(date);
            today.add(type, next);
            if (getTime)
                return dateTimeToString(today.getTime());
            else
                return dateToString(today.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public String previouslyDate(String currentDate, int previously, boolean getTime) {
        try {
            Calendar today = Calendar.getInstance();
            Date date;
            if (getTime)
                date = stringToDateTime(currentDate);
            else
                date = stringToDate(currentDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, -previously);
            if (getTime)
                return dateTimeToString(today.getTime());
            else
                return dateToString(today.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public String nextYearDate(String currentDate, int next) {
        String nextYearDate = "";
        try {
            Calendar today = Calendar.getInstance();
            Date date = stringToDate(currentDate);
            today.setTime(date);
            today.add(Calendar.YEAR, next);
            nextYearDate = dateToString(today.getTime());
        } catch (Exception e) {
            return nextYearDate;
        }
        return nextYearDate;
    }

    public String nextOneYearDateRound(String currentDate) {
        String nextYearDate = this.nextYearDate(currentDate, ROUND_ONE);
        int dayOfMonthCurrent = getDayOfMonth(stringToDate(currentDate));
        int dayOfMonthNextYear = getDayOfMonth(stringToDate(nextYearDate));
        if (dayOfMonthNextYear < dayOfMonthCurrent) {
            return nextYearDate;
        }
        nextYearDate = this.previouslyDate(nextYearDate, ROUND_ONE, false);
        return nextYearDate;
    }

    public String nextMonthDate(String currentDate, int next) {
        String nextMonthDate = "";
        try {
            Calendar today = Calendar.getInstance();
            Date date = stringToDate(currentDate);
            today.setTime(date);
            today.add(Calendar.MONTH, next);
            nextMonthDate = dateToString(today.getTime());
        } catch (Exception e) {
            return nextMonthDate;
        }
        return nextMonthDate;
    }

    public String nextOneMonthDateRound(String currentDate) {
        String nextMonthDate = this.nextMonthDate(currentDate, ROUND_ONE);
        int dayOfMonthCurrent = getDayOfMonth(stringToDate(currentDate));
        int dayOfMonthNextMonth = getDayOfMonth(stringToDate(nextMonthDate));
        if (dayOfMonthNextMonth < dayOfMonthCurrent) {
            return nextMonthDate;
        }
        nextMonthDate = this.previouslyDate(nextMonthDate, ROUND_ONE, false);
        return nextMonthDate;
    }

    public int getDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int compareDate(String dateStra, String dateStrb) {
        Date dateA = stringToDate(dateStra);
        Date dateB = stringToDate(dateStrb);
        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        }
        if (dateA.getTime() < dateB.getTime()) {
            return -1;
        }
        return 1;
    }

    public String formatCurrency(String money, String currency_code) throws CustomException {
        if (!isNotNull(money)) {
            return money;
        }
        String retval = "";
        boolean rownMoney = "VND".equals(currency_code) ||
                "JPY".equals(currency_code);
        try {
            double dMoney = this.str2num(money).doubleValue();

            if (rownMoney) {
                dMoney = this.round(dMoney, 0);
                retval = this.num2str(dMoney);

            } else { //check ,00
                dMoney = this.round(dMoney, 2);
                retval = nff.format(dMoney);

                if (!"vi".equals(language)) {
                    retval = retval.replaceAll(",", " ");
                }

            }
        } catch (Exception e) {
            throw new CustomException(CustomStatus.SOMETHING_WENT_WRONG, e.getLocalizedMessage());
        }

        return retval;
    }

    public String formatCurrency(double dMoney, String currency_code) throws CustomException {
        String retval = "";
        boolean rownMoney = "VND".equals(currency_code) ||
                "JPY".equals(currency_code);
        try {
            if (rownMoney) {
                dMoney = this.round(dMoney, 0);
                retval = this.num2str(dMoney);
            } else { //check ,00
                dMoney = this.round(dMoney, 2);
                retval = nff.format(dMoney);

                if (!"vi".equals(language)) {
                    retval = retval.replaceAll(",", " ");
                }
            }
        } catch (Exception e) {
            throw new CustomException(CustomStatus.SOMETHING_WENT_WRONG, e.getLocalizedMessage());
        }

        return retval;
    }

    public Number str2num(String str) throws CustomException {
        if (!isNotNull(str)) {
            str = "0";
        }

        str = str.replaceAll(" ", "");

        Number result = null;

        try {
            result = nf.parse(str);
        } catch (Exception e) {
            throw new CustomException(CustomStatus.SOMETHING_WENT_WRONG, e.getLocalizedMessage());
        }

        return result;
    }

    public String num2str(double number) {
        String result = nf.format(number);

        if (!"vi".equals(language)) {
            result = result.replaceAll(",", " ");
        }

        return result;
    }

    public static double round(double val, int precision) {
        double powprecision = Math.pow(10, precision);
        return Math.floor((val * powprecision) + 0.5) / powprecision;
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public GregorianCalendar str2gc(String str) {
        if (!isNotNull(str))
            return null;
        GregorianCalendar result = new GregorianCalendar();
        try {
            result.setTime(str2date(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static GregorianCalendar date2gc(Date t) {
        if (t == null)
            return null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(t.getTime());
        return gc;
    }

    public Timestamp str2time(String str) {
        if (!isNotNull(str))
            return null;
        Timestamp result = null;
        try {
            result = new Timestamp(sdf.parse(str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Date str2date(String strDate) {
        Date result = null;
        try {
            result = sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
