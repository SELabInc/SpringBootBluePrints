package com.selab.springbootblueprints.model.bean;

public class Paginate {

    private static final int defaultPaginateSize = 5;

    private static boolean pageNumberStartToZero = true;

    private final int currentPageNumber;
    private final int totalPageAmount;
    private final int paginateSize;

    public static void setPageNumberStartToZero(boolean bool) {
        Paginate.pageNumberStartToZero = bool;
    }

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
        int currentPaginateIndex = currentPageNumber / paginateSize;
        int tmpEndNum = (paginateSize * (1 + currentPaginateIndex));

        int endNumber = Math.min(tmpEndNum, totalPageAmount);

        return pageNumberStartToZero ?
                endNumber - 1 : endNumber;
    }
}
