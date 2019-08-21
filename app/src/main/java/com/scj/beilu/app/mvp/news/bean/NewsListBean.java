package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/2/14 16:21
 */
public class NewsListBean extends ResultMsgBean {

    /**
     * page : {"currentPage":1,"list":[{"newsId":59,"userId":11,"newsTitle":"asdads","newsAuthor":"asdasd","newsSource":"asdads","newsBrief":"asdasd","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/cc54a377-529f-406a-8edf-638858ca2b6020190244210743.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 21:07:42","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/ff7fa073-c1df-4b50-8f38-3f8844a45d5f20190244210743.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":59,"userId":11,"newsTitle":"asdads","newsAuthor":"asdasd","newsSource":"asdads","newsBrief":"asdasd","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/cc54a377-529f-406a-8edf-638858ca2b6020190244210743.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 21:07:42","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/ff7fa073-c1df-4b50-8f38-3f8844a45d5f20190244210743.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":59,"userId":11,"newsTitle":"asdads","newsAuthor":"asdasd","newsSource":"asdads","newsBrief":"asdasd","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/cc54a377-529f-406a-8edf-638858ca2b6020190244210743.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 21:07:42","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/ff7fa073-c1df-4b50-8f38-3f8844a45d5f20190244210743.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":59,"userId":11,"newsTitle":"asdads","newsAuthor":"asdasd","newsSource":"asdads","newsBrief":"asdasd","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/cc54a377-529f-406a-8edf-638858ca2b6020190244210743.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 21:07:42","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/ff7fa073-c1df-4b50-8f38-3f8844a45d5f20190244210743.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":54,"userId":11,"newsTitle":"魅族 16th","newsAuthor":"MEIUZU","newsSource":"https://detail.meizu.com/item/meizu16th.html?click=store_index_banner_1&click=click_store_index_banner_1","newsBrief":"追求源于热爱","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/9b36d0bc-abda-479c-a9cb-e76d007b191720190244192641.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 19:26:40","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/3da05c5d-6708-4b7a-951a-d3cdfa52659120190244192640.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":54,"userId":11,"newsTitle":"魅族 16th","newsAuthor":"MEIUZU","newsSource":"https://detail.meizu.com/item/meizu16th.html?click=store_index_banner_1&click=click_store_index_banner_1","newsBrief":"追求源于热爱","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/9b36d0bc-abda-479c-a9cb-e76d007b191720190244192641.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 19:26:40","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/3da05c5d-6708-4b7a-951a-d3cdfa52659120190244192640.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":54,"userId":11,"newsTitle":"魅族 16th","newsAuthor":"MEIUZU","newsSource":"https://detail.meizu.com/item/meizu16th.html?click=store_index_banner_1&click=click_store_index_banner_1","newsBrief":"追求源于热爱","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/9b36d0bc-abda-479c-a9cb-e76d007b191720190244192641.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 19:26:40","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/3da05c5d-6708-4b7a-951a-d3cdfa52659120190244192640.html","newsCollectionCount":0,"newsShareCount":0},{"newsId":54,"userId":11,"newsTitle":"魅族 16th","newsAuthor":"MEIUZU","newsSource":"https://detail.meizu.com/item/meizu16th.html?click=store_index_banner_1&click=click_store_index_banner_1","newsBrief":"追求源于热爱","newsPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/9b36d0bc-abda-479c-a9cb-e76d007b191720190244192641.jpg","newsContent":null,"cateId":3,"newsDate":"2019-02-13 19:26:40","newsBrowseCount":0,"newsShareWebSite":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/3da05c5d-6708-4b7a-951a-d3cdfa52659120190244192640.html","newsCollectionCount":0,"newsShareCount":0}],"startCount":0,"nextPage":0}
     */

    private PageBean<NewsInfoBean> page;

    public PageBean<NewsInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<NewsInfoBean> page) {
        this.page = page;
    }
}
