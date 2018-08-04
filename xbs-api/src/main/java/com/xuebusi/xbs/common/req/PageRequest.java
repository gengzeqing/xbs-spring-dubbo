package com.xuebusi.xbs.common.req;

import java.io.Serializable;
public class PageRequest<T> extends Request<T> {

    private static final long serialVersionUID = 1L;
    private int pageNum;
    private Integer pageSize=10;
    private PageInfo pageInfo;

    private PageRequest(String sid, int pageNum, Integer pageSize) {
        this.pageNum=pageNum;
        this.setSid(sid);
        if(pageSize!=null && pageSize>0) {
            this.pageSize=pageSize;
        }
        this.pageInfo=new PageInfo(this.pageNum,this.pageSize);
    }

    private PageRequest(int pageNum, Integer pageSize) {
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        if(pageSize!=null && pageSize>0) {
            this.pageSize=pageSize;
        }
        this.pageInfo=new PageInfo(this.pageNum,this.pageSize);
    }

    public static <T>PageRequest <T> create(String sid,int pageNum,Integer pageSize){
        return new PageRequest<T>(sid,pageNum,pageSize);
    }

    public static <T>PageRequest <T> create(int pageNum,Integer pageSize){
        return new PageRequest<T>(pageNum,pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }


    @Override
    public String toString() {
        return "PageRequest [pageNum=" + pageNum + ", pageSize=" + pageSize + ",superParent="+super.toString()+"]";
    }

    public static void main(String[] args) {
        PageRequest<String> pageReq = PageRequest.create(10,0);
        pageReq.setData("Hello");
        System.out.println(pageReq);
    }
    @Override
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public static class PageInfo implements Serializable{

        private static final long serialVersionUID = 1L;
        private int pageNum;
        private Integer pageSize;

        private PageInfo(int pageNum, Integer pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
        }
        public int getPageNum() {
            return pageNum;
        }
        public Integer getPageSize() {
            return pageSize;
        }

    }

}
