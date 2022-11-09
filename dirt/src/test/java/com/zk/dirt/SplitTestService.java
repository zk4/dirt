package com.zk.dirt;


import com.google.common.base.Preconditions;
import com.zk.dirt.split.annotation.Split;
import com.zk.dirt.split.annotation.SplitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SplitTestService {

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public List<Long> splitByList(List<Long> params){
        return convert(params);
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public List<Long> splitByList(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return convert(params);
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Set<Long> splitByListAsSet(List<Long> params){
        return new HashSet<>(convert(params));
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Set<Long> splitByListAsSet(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return new HashSet<>(convert(params));
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Integer splitByListAsCount(List<Long> params){
        return convert(params).size();
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Integer splitByListAsCount(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return convert(params).size();
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public int splitByListAsCount2(List<Long> params){
        return convert(params).size();
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public int splitByListAsCount2(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return convert(params).size();
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Long splitByListAsLong(List<Long> params){
        return Long.valueOf(convert(params).size());
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public Long splitByListAsLong(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return Long.valueOf(convert(params).size());
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public long splitByListAsLong2(List<Long> params){
        return Long.valueOf(convert(params).size());
    }

    @Split(sizePrePartition = 2, taskPreThread = 2)
    public long splitByListAsLong2(@SplitParam List<Long> params, Long other){
        Preconditions.checkArgument(other != null);
        return Long.valueOf(convert(params).size());
    }

    private List<Long> convert(List<Long> input){
        if (input.size() > 5){
            throw new RuntimeException("Many Param");
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("failed to sleep", e);
            Thread.currentThread().interrupt();
        }
        log.info("Thread {} run with {}", Thread.currentThread().getName(), input);
        return input.stream()
                .map( i -> i + 10000L)
                .collect(Collectors.toList());
    }
}
