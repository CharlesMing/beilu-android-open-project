package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 18:08
 */
public class CollectGoodsListResultBean extends ResultMsgBean {

    private CollectionPageBean collectionPage;

    public CollectionPageBean getCollectionPage() {
        return collectionPage;
    }

    public void setCollectionPage(CollectionPageBean collectionPage) {
        this.collectionPage = collectionPage;
    }

    public static class CollectionPageBean {
        /**
         * currentPage : 1
         * list : [{"productId":1,"productName":"哑铃","productPicOriginalAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/2055accb-7eda-4cb9-a1a6-f0fcc94e4df720190492192017.jpg","productPicCompressionAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/Zip2055accb-7eda-4cb9-a1a6-f0fcc94e4df720190492192017.jpg","productSynopsis":null,"productSalesVolume":0,"productRepertory":0,"productOriginalPrice":2.01,"productDiscountPrice":0.01,"productDec":null,"productStatus":0,"productCateId":0,"productFormat":null,"productPic":null,"isCollection":0},{"productId":37,"productName":"短袖衣","productPicOriginalAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/530fc683-8a69-47af-b5cd-51230679b0c320190491160431.jpg","productPicCompressionAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/Zip530fc683-8a69-47af-b5cd-51230679b0c320190491160431.jpg","productSynopsis":null,"productSalesVolume":0,"productRepertory":0,"productOriginalPrice":2.01,"productDiscountPrice":0.01,"productDec":null,"productStatus":0,"productCateId":0,"productFormat":null,"productPic":null,"isCollection":0}]
         * startCount : 0
         * nextPage : 1
         * totalCount : 0
         */

        private int currentPage;
        private int startCount;
        private int nextPage;
        private int totalCount;
        private List<GoodsInfoBean> list;

        public List<GoodsInfoBean> getList() {
            return list;
        }

        public void setList(List<GoodsInfoBean> list) {
            this.list = list;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getStartCount() {
            return startCount;
        }

        public void setStartCount(int startCount) {
            this.startCount = startCount;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

    }
}
