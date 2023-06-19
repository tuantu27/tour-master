package com.example.tour.utils;

public class Executes {

    public static void routingNoArgsConstructor(Class classAction, String method, Object... params) throws Exception {
        Object agreementObject = classAction.getConstructor(null).newInstance(null);
        Class[] paramsType = getParamsType(params);
        classAction.getMethod(method, paramsType).invoke(agreementObject, params);
    }

    public static <T> T routingReturnsValueNoArgsConstructor(Class classAction, String method, Object... params) throws Exception {
        Object agreementObject = classAction.getConstructor(null).newInstance(null);
        Class[] paramsType = getParamsType(params);
        return (T) classAction.getMethod(method, paramsType).invoke(agreementObject, params);
    }

    private static Class[] getParamsType(Object[] args) {
        if (args == null || args.length == 0)
            return null;
        Class[] paramsType = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramsType[i] = args[i].getClass();
        }
        return paramsType;
    }
}
