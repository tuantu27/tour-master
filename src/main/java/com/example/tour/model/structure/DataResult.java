package com.example.tour.model.structure;

import lombok.Data;

import java.util.List;

@Data
public class DataResult {
    private PageableCommon pageable;
    private List dataResult;

}
