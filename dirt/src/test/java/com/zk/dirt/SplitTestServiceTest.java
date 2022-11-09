package com.zk.dirt;

 import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;



@SpringBootTest(classes = DirtApplication.class)
class SplitTestServiceTest {

    @Autowired
    private SplitTestService splitTestService;

    @Test
    @Timeout(3)
    public void splitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void testSplitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void splitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void splitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void splitByListAsCount2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int count = this.splitTestService.splitByListAsCount2(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsCount2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int count = this.splitTestService.splitByListAsCount2(params, 10L);
        Assertions.assertEquals(8, count);
    }


    @Test
    @Timeout(3)
    public void splitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params, 10L);
        Assertions.assertEquals(8, count);
    }


    @Test
    @Timeout(3)
    public void splitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params, 10L);
        Assertions.assertEquals(8, count);
    }

}