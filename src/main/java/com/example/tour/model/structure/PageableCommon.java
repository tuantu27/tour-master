package com.example.tour.model.structure;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class PageableCommon {
    private Integer page; // index can truy van
    private Integer pageSize; // kich thuoc truy van
    private LinkedHashMap sort;
}