package com.example.tour.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

public class ValueType {
    private String name = null;

    private static String[] arrReplace = {"class", "interface"};

    public static ValueType getValueType(String str) {
        str = replace(str);
        if (str.equals("String") || str.equals("java.lang.String"))
            return ValueType.STRING_TYPE;
        if (str.equals("Long") || str.equals("java.lang.Long"))
            return ValueType.LONG_TYPE;
        if (str.equals("Integer") || str.equals("java.lang.Integer"))
            return ValueType.INTEGER_TYPE;
        if (str.equals("Boolean") || str.equals("java.lang.Boolean"))
            return ValueType.BOOLEAN_TYPE;
        if (str.equals("Byte") || str.equals("java.lang.Byte"))
            return ValueType.BYTE_TYPE;
        if (str.equals("Float") || str.equals("java.lang.Float"))
            return ValueType.FLOAT_TYPE;
        if (str.equals("Double") || str.equals("java.lang.Double"))
            return ValueType.DOUBLE_TYPE;
        if (str.equals("Short") || str.equals("java.lang.Short"))
            return ValueType.SHORT_TYPE;
        if (str.equals("GregorianCalendar") || str.equals("java.util.GregorianCalendar"))
            return ValueType.GREGORIAN_CALENDAR_TYPE;
        if (str.equals("Timestamp") || str.equals("java.sql.Timestamp"))
            return ValueType.TIMESTAMP;
        if (str.equals("Date") || str.equals("java.util.Date"))
            return ValueType.DATE_TYPE;
        if (str.equals("List") || str.equals("java.util.List"))
            return ValueType.LIST_TYPE;
        if (str.equals("LinkedHashMap") || str.equals("java.util.LinkedHashMap"))
            return ValueType.LINKED_HASHMAP;
        return null;
    }

    public static String getValueTypeByPrimitiveDataType(String str) {
        if (str.equals("long"))
            return "long";
        if (str.equals("char"))
            return "char";
        if (str.equals("int"))
            return "int";
        if (str.equals("float"))
            return "float";
        return null;
    }

    public static String replace(String str) {
        for (String item : arrReplace) {
            str = str.replace(item, "").trim();
        }
        return str;
    }

    private ValueType(Object obj) {
        name = obj.getClass().getName();
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ValueType))
            return false;
        return obj.toString().equals(this.toString());
    }

    public static final ValueType STRING_TYPE = new ValueType("");
    public static final ValueType LONG_TYPE = new ValueType(Long.valueOf(1));
    public static final ValueType INTEGER_TYPE = new ValueType(Integer.valueOf(1)); // GregorianCalendar
    public static final ValueType BOOLEAN_TYPE = new ValueType(Boolean.valueOf(true));
    public static final ValueType BYTE_TYPE = new ValueType(Byte.valueOf((byte) 1));
    public static final ValueType FLOAT_TYPE = new ValueType(Float.valueOf(1));
    public static final ValueType DOUBLE_TYPE = new ValueType(Double.valueOf(1));
    public static final ValueType SHORT_TYPE = new ValueType(Short.valueOf((short) 1));
    public static final ValueType GREGORIAN_CALENDAR_TYPE = new ValueType(new GregorianCalendar());
    public static final ValueType DATE_TYPE = new ValueType(new Date());
    public static final ValueType TIMESTAMP = new ValueType(new Timestamp(new GregorianCalendar().getTimeInMillis()));
    public static final ValueType LIST_TYPE = new ValueType(new ArrayList<>());
    public static final ValueType LINKED_HASHMAP = new ValueType(new LinkedHashMap());

    public Object getObjectClass() {
        if (name.equals("String") || name.equals("java.lang.String"))
            return new String();
        if (name.equals("Long") || name.equals("java.lang.Long"))
            return Long.valueOf(1);
        if (name.equals("Integer") || name.equals("java.lang.Integer"))
            return Integer.valueOf(0);
        if (name.equals("Boolean") || name.equals("java.lang.Boolean"))
            return Boolean.valueOf(true);
        if (name.equals("Byte") || name.equals("java.lang.Byte"))
            return Byte.valueOf("1");
        if (name.equals("Character") || (name.equals("java.lang.Character")))
            return Character.valueOf('a');
        if (name.equals("Float") || name.equals("java.lang.Float"))
            return Float.valueOf(0);
        if (name.equals("Double") || name.equals("java.lang.Double"))
            return Double.valueOf(0);
        if (name.equals("Short") || name.equals("java.lang.Short"))
            return Short.valueOf("0");
        if (name.equals("StringBuffer") || name.equals("java.lang.StringBuffer"))
            return new StringBuffer("");
        if (name.equals("GregorianCalendar") || name.equals("java.util.GregorianCalendar"))
            return new GregorianCalendar();
        return null;
    }
}
