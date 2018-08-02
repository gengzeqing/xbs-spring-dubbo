package com.xuebusi.xbs.utils.RequestUtils;

public class PageResponse<T> extends Response<T> {

    private static final long serialVersionUID = 1L;
    private int pageNum;
    private Integer pageSize;
    private long totalRecordCount;

    private PageResponse(String sid) {
        this.setSid(sid);
    }

    private PageResponse(){}

    public static <T>PageResponse <T> create(){
        return new PageResponse<>();
    }

    public static <T>PageResponse <T> create(String sid){
        return new PageResponse<>(sid);
    }

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 记录总条数
     * @return
     */
    public long getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(long totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public static void main(String[] args) {
        PageResponse<String> response = PageResponse.create();
        response.setData("Hello");
        System.out.println(response);
    }

    /**
     * 记录总页数
     * @return
     */
    public int getTotalPageNum() {
        if(null==pageSize) {
            pageSize=1;
        }
        double count=totalRecordCount/(double)pageSize;
        return (int)Math.ceil(count);
    }

}
