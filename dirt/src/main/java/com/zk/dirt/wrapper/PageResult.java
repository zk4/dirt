package com.zk.dirt.wrapper;

import lombok.Data;

@Data
public class PageResult {
    public long curPage;
    public long pageSize;
    public long totalPages;

}
