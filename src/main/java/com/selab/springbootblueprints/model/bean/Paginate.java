package com.selab.springbootblueprints.model.bean;

public class Paginate {

    private static final int defaultPaginateSize = 5;

    private final int currentPageNumber;
    private final int totalPageAmount;
    private final int paginateSize;

    public Paginate(int currentPageNumber, int totalPageAmount, int paginateSize) {
        this.currentPageNumber = currentPageNumber;
        this.totalPageAmount = totalPageAmount;
        this.paginateSize = paginateSize;
    }

    public Paginate(int currentPageNumber, int totalPageAmount) {
        this(currentPageNumber, totalPageAmount, defaultPaginateSize);
    }

    public int getStartNumber() {
        return currentPageNumber - (currentPageNumber % paginateSize);
    }

    public int getEndNumber() {
        int tmpEndNum = (paginateSize * (1 + (currentPageNumber) / paginateSize)) - 1;
        int lastPageIndex = totalPageAmount - 1;

        return Math.min(tmpEndNum, lastPageIndex);
    }
}
