package com.selab.springbootblueprints.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Paginate {

    private final int DEFAULT_PAGINATE_AMOUNT = 10;

    public int getPaginateStartNumber(int page, int pageAmount) {
        return page - (page % pageAmount);
    }

    public int getPaginateEndNumber(int page, int totalPage, int pageAmount) {
        int tmpEndNum = page + (page % pageAmount);
        int lastPageIndex = totalPage - 1;

        return tmpEndNum <= lastPageIndex ? tmpEndNum : lastPageIndex;
    }

    public int getPaginateStartNumber(int page) {
        return getPaginateStartNumber(page, DEFAULT_PAGINATE_AMOUNT);
    }

    public int getPaginateEndNumber(int page, int totalPage) {
        return getPaginateEndNumber(page, totalPage, DEFAULT_PAGINATE_AMOUNT);
    }
}
