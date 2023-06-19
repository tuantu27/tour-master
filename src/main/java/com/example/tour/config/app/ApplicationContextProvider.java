package com.example.tour.config.app;

import org.springframework.context.ApplicationContext;

/**
 * class su dung de nhung vung nho cua cac Bean vao day
 * co the su dung de goi cac Bean tu cac Class Java binh thuong ma khong can khai bao annotation class
 */
public enum ApplicationContextProvider {
    INSTANCE;

    public static ApplicationContextProvider getInstance() {
        return INSTANCE;
    }

    private ApplicationContext applicationContext;

    /**
     * Default constructor
     */
    private ApplicationContextProvider() {
    }

    /**
     *
     */
    public void setContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @return
     */
    public ApplicationContext getContext() {
        return applicationContext;
    }
}
