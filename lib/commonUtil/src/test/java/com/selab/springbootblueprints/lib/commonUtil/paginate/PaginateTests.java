package com.selab.springbootblueprints.lib.commonUtil.paginate;


import com.selab.springbootblueprints.lib.commonutil.paginate.Paginate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaginateTests {

    @Test
    public void startZeroCommonTest() {
        Paginate.setPageNumberStartToZero(true);
        Paginate paginate = new Paginate(0 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 0);
        Assertions.assertEquals(paginate.getEndNumber(), 9);
    }

    @Test
    public void startZeroSecondPaginateTest() {
        Paginate.setPageNumberStartToZero(true);
        Paginate paginate = new Paginate(11 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 10);
        Assertions.assertEquals(paginate.getEndNumber(), 19);
    }

    @Test
    public void startZeroLastPaginateTest() {
        Paginate.setPageNumberStartToZero(true);
        Paginate paginate = new Paginate(20 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 20);
        Assertions.assertEquals(paginate.getEndNumber(), 27);
    }

    @Test
    public void startOneCommonTest() {
        Paginate.setPageNumberStartToZero(false);
        Paginate paginate = new Paginate(1 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 1);
        Assertions.assertEquals(paginate.getEndNumber(), 10);
    }

    @Test
    public void startOneSecondPaginateTest() {
        Paginate.setPageNumberStartToZero(false);
        Paginate paginate = new Paginate(11 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 11);
        Assertions.assertEquals(paginate.getEndNumber(), 20);
    }

    @Test
    public void startOneLastPaginateTest() {
        Paginate.setPageNumberStartToZero(false);
        Paginate paginate = new Paginate(21 , 28, 10);
        Assertions.assertEquals(paginate.getStartNumber(), 21);
        Assertions.assertEquals(paginate.getEndNumber(), 28);
    }


}
