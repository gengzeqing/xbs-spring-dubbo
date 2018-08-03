package com.xuebusi.xbs.util.pageutils;


import java.io.Serializable;

/**
 * 分页信息工具类
 * PageUtil page = new PageUtil(pageNum, pageSize);
 * page.getOffset(), page.getLimit()
 */
public class PageUtil implements Serializable{

	private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页条数
     */
    private int limit;
    
    private int offset;

    
    public PageUtil(int pageNum, Integer pageSize){
        //分页参数
        this.page = pageNum;
        this.limit = pageSize;
        this.offset = (page - 1) * limit;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}
}
