package com.example.tour.utils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Utils {

    public static final String PARTNER_ID = "PartnerId";
    public static final String USER_NAME = "UserName";
    public static final String THREAD_ID = "ThreadId";
    public static final String PARTNER_TYPE = "PartnerType";

    public static <E, F> List<E> copy(Class classCast, List<F> objs) {
        if (!Formatter.isNotNull(objs))
            return null;
        List<E> dataResult = new ArrayList<>();
        for (F item : objs) {
            E e = copy(classCast, item);
            if (e != null)
                dataResult.add(e);
        }
        return dataResult;
    }

    /**
     * method thuc hien viec copy data tu Object -> Object
     * yeu cau 2 object co chung field
     *
     * @param classCast data receiving module
     * @param objF      module contains data
     * @param <E>
     * @param <F>
     * @return
     */
    public static <E, F extends Object> E copy(Class classCast, F objF) {
        try {
            if (objF == null)
                return null;
            Map dataObjF;
            if (objF instanceof LinkedHashMap)
                dataObjF = (Map) objF;
            else
                dataObjF = prepareDataToMap(objF);
            if (dataObjF.isEmpty())
                return null;
            return create(classCast, dataObjF);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * method su dung de map data tu 1 object theo field tuong ung
     * key: ten field
     * value: du lieu cua field do
     *
     * @param object: doi tuong chua gia tri
     * @return
     */
    public static Map<String, Object> prepareDataToMap(Object object) {
        try {
            Map<String, Object> map = new HashMap<>();
            Field[] fields = getFields(object.getClass());
            for (int i = 0, count = fields.length; i < count; i++) {
                String fieldName = fields[i].getName();
                Object value = getValueOfField(fieldName, object);
                map.put(fieldName, value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getValueOfField(String fieldName, Object objectGetData) {
        try {
            Class compType = objectGetData.getClass();
            Field field = getField(compType, fieldName);
            field.setAccessible(true);
            return field.get(objectGetData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getValueWithFormatJson(String key, Object data) {
        try {
            String[] keys = key.split(".");
            Object value = data;
            for (String item : keys) {
                if (value == null)
                    return null;
                value = Utils.getValueOfField(item, value);
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> prepareDataToMapInSearch(Object object) {
        try {
            if (object == null)
                return null;
            Map<String, Object> map = new HashMap<>();
            Field[] fields = getFields(object.getClass());
            for (int i = 0, count = fields.length; i < count; i++) {
                Class compType = object.getClass();
                String fieldName = fields[i].getName();
                Field field = getField(compType, fieldName);
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    String fieldNameLowerCase = fieldName.toLowerCase();
                    if (fieldNameLowerCase.contains("date")) {
                        if (!(value instanceof LinkedHashMap<?, ?>)) {
                            Date day = new Date((Long) value);
                            Date startOfDay = Formatter.atStartOfDay(day);
                            Date endOfDay = Formatter.atEndOfDay(day);
                            value = new LinkedHashMap<>();
                            ((LinkedHashMap<Object, Object>) value).put("from", startOfDay.getTime());
                            ((LinkedHashMap<Object, Object>) value).put("to", endOfDay.getTime());
                        }
                    }
                    map.put(fieldName, value);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Field[] getFields(Class classGet) {
        Field[] fields = classGet.getDeclaredFields();
        // check subclass
        Class superClass = classGet.getSuperclass();
        if (superClass != null) {
            Field[] fieldsSuper = getFields(superClass);
            if (fieldsSuper != null && fieldsSuper.length > 0) {
                for (Field item : fieldsSuper) {
                    fields = ObjectUtils.addObjectToArray(fields, item);
                }
            }
        }
        return fields;
    }

    private static Field getField(Class compType, String fieldName) {
        Field field = null;
        try {
            field = compType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException n) {
            if (compType.getSuperclass() != null)
                field = getField(compType.getSuperclass(), fieldName);
        }
        return field;
    }

    /**
     * method thuc hien viec copy data tu Map sang Object
     * cac filed trong object se duoc tim trong Map theo key: field name
     * neu co data thi se convert sang Object
     *
     * @param classCast
     * @param mapData
     * @param <E>
     * @return
     */
    public static <E> E create(Class classCast, Map mapData) throws Exception {
        if (classCast == null)
            return null;
        Field[] fields = getFields(classCast);
        int count = fields.length;
        E obj = (E) createObject(classCast);
        if (count == 0)
            return obj;
        for (int i = 0; i < count; i++) {
            Field field = fields[i];
            if (Modifier.isStatic(field.getModifiers())) // bien static thi thoi
                continue;
            field.setAccessible(true);
            Object value = mapData.get(field.getName());
            String type = field.getType().toString();
            if (value == null) {
                // gen data with data field
                if (type.equals("long"))
                    value = Long.parseLong("1");
                else if (type.equals("int"))
                    value = Integer.parseInt("1");
                else if (type.equals("double"))
                    value = Double.parseDouble("1");
                else if (type.equals("float"))
                    value = Float.parseFloat("1");
            } else {
                // comment lai do dang khong can valid theo dang check
                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    for (Annotation item : annotations) {
                        String annotationType = item.annotationType().toString();
                        if (value == null || "".equals(value)) {
                            if (annotationType.contains("validation.constraints.NotNull"))
                                throw new Exception("Field " + field.getName() + " in class " + classCast.getName() + " required not null.");
                            if (annotationType.contains("validation.constraints.NotEmpty"))
                                throw new Exception("Field " + field.getName() + " in class " + classCast.getName() + " required not empty.");
                        }
                    }
                }
            }
            field.set(obj, getData(type, value));
        }
        return obj;
    }

    private static Object createObject(Class classCast) {
        try {
            Constructor constructor = classCast.getConstructor();
            Object obj = constructor.newInstance();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getData(String type, Object value) throws Exception {
        Formatter formatter = new Formatter();
        if (value == null)
            return null;
        ValueType valueType = ValueType.getValueType(type);
        if (valueType == null) {
            if (value instanceof LinkedHashMap<?, ?>) {
                Class classValue = Class.forName(ValueType.replace(type));
                return convertDataLinkedHashMap(classValue, (LinkedHashMap) value);
            } else if (type.equals(ValueType.getValueTypeByPrimitiveDataType(type))) {
                return value;
            }
            // type dang la 1 object khac cac dang du lieu ban dau cua JDK ma chua xac dinh duoc
            return value;
        }
        if (ValueType.GREGORIAN_CALENDAR_TYPE.equals(valueType)) {
            if (value instanceof String)
                return formatter.str2gc((String) value);
            return Formatter.date2gc((Date) value);
        } else if (ValueType.FLOAT_TYPE.equals(valueType)) {
            if (value instanceof String)
                return Float.valueOf((String) value);
            return value;
        } else if (ValueType.DOUBLE_TYPE.equals(valueType)) {
            if (value instanceof String)
                return Double.valueOf((String) value);
            if (value instanceof Integer)
                return Double.valueOf(((Integer) value).intValue());
            return value;
        } else if (ValueType.BOOLEAN_TYPE.equals(valueType)) {
            if (value instanceof String)
                return Boolean.valueOf((String) value);
            return value;
        } else if (ValueType.LONG_TYPE.equals(valueType)) {
            if (value instanceof String)
                return Long.valueOf((String) value);
            if (value instanceof Integer)
                return Long.parseLong(String.valueOf(value));
            return value;
        } else if (ValueType.SHORT_TYPE.equals(valueType) || ValueType.INTEGER_TYPE.equals(valueType) || ValueType.BYTE_TYPE.equals(valueType)) {
            if (value instanceof String)
                return Integer.valueOf((String) value);
            if (value instanceof Double)
                return ((Double) value).intValue();
            return value;
        } else if (ValueType.TIMESTAMP.equals(valueType)) {
            if (value instanceof String) {
                if (!Formatter.isNotNull((String) value))
                    return null;
                return formatter.str2time(String.valueOf(value));
            }
        } else if (ValueType.DATE_TYPE.equals(valueType)) {
            if (value instanceof String) {
                if (!Formatter.isNotNull((String) value))
                    return null;
                return formatter.str2date(String.valueOf(value));
            } else if (value instanceof Long) {
                return new Date((Long) value);
            } else if (value instanceof GregorianCalendar) {
                return ((GregorianCalendar) value).getTime();
            }
            return value;
        } else if (ValueType.LIST_TYPE.equals(valueType)) {
            if (!(value instanceof List<?>))
                return null; // dinh dang cua value dang khac dinh dang cua field can truyen du lieu
            return value;
        } else if (ValueType.LINKED_HASHMAP.equals(valueType)) {
            // chua nghi ra cach xu ly
        }
        return value;
    }

    public static Map getDataCorrectFromHeaderRequest(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String value = request.getHeader("${request.header.partner-id}");
        if (Formatter.isNotNull(value))
            data.put(PARTNER_ID, value);
        value = request.getHeader("${request.header.username}");
        if (Formatter.isNotNull(value))
            data.put(USER_NAME, value);
        value = request.getHeader("${request.header.thread-id}");
        if (Formatter.isNotNull(value))
            data.put(THREAD_ID, value);
        value = request.getHeader("${request.header.partner-type}");
        if (Formatter.isNotNull(value))
            data.put(PARTNER_TYPE, value);
        return data;
    }

    /**
     * method su dung de map data sang object khac
     *
     * @param data
     * @param classCast
     * @return
     * @throws Exception
     */
    public static Object castObject(Object data, Class classCast) throws Exception {
        if (data instanceof LinkedHashMap)
            return create(classCast, convertLinkedHashMapToMap((LinkedHashMap) data));
        else
            return copy(classCast, data);
    }

    public static Map convertLinkedHashMapToMap(LinkedHashMap dataLinked) {
        if (dataLinked == null || dataLinked.isEmpty())
            return null;
        List<String> keys = new ArrayList<>(dataLinked.keySet());
        Map<String, Object> result = new HashMap<>();
        for (String key : keys) {
            Object value = dataLinked.get(key);
            result.put(key, value);
        }
        return result;
    }

    private static Object convertDataLinkedHashMap(Class classCast, LinkedHashMap data) {
        Field[] fields = getFields(classCast);
        int count = fields.length;
        Object obj = createObject(classCast);
        if (count == 0)
            return obj;
        try {
            for (int i = 0; i < count; i++) {
                Field field = fields[i];
                if (Modifier.isStatic(field.getModifiers())) // bien static thi thoi
                    continue;
                field.setAccessible(true);
                Object value = data.get(field.getName());
                field.set(obj, getData(field.getType().toString(), value));
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <E, F> List prepareDataSearchResult(List<E> dataRaw, Class classResult) {
        List dataResults = new ArrayList<>();
        if (dataRaw == null || dataRaw.isEmpty())
            return dataResults;
        for (E item : dataRaw) {
            F data = Utils.copy(classResult, item);
            if (data != null)
                dataResults.add(data);
        }
        return dataResults;
    }

    public static void prepareDataSearch(LinkedHashMap dataSearch) {
        int size = dataSearch.keySet().size();
        List keys = dataSearch.keySet().stream().toList();
        LinkedHashMap value = new LinkedHashMap();
        for (int i = 0; i < size; i++) {
            String key = (String) keys.get(i);
            if (key.toLowerCase().contains("date") && !(dataSearch.get(key) instanceof LinkedHashMap<?, ?>)) {
                Date day = new Date((Long) dataSearch.get(key));
                Date startOfDay = Formatter.atStartOfDay(day);
                Date endOfDay = Formatter.atEndOfDay(day);
                value.put("from", startOfDay.getTime());
                value.put("to", endOfDay.getTime());
                dataSearch.put(key, value);
            }
        }
    }
}
