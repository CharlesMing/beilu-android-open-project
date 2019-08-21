package com.scj.beilu.app.mvp.order.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:26
 */
public class OrderLogisticsInfoResultBean {


    /**
     * object : {"company":"自动匹配","com":"auto","no":"75137217173016","status":"1","list":[{"datetime":"2019-03-25 17:45:11","remark":"【杭州市】【杭州东新路】（0571-86013750）的科凌集团驻点业务员[18042481803]（18042481803）已揽收","zone":""},{"datetime":"2019-03-25 20:13:15","remark":"【杭州市】快件离开【杭州东新路】已发往【重庆】","zone":""},{"datetime":"2019-03-25 22:28:26","remark":"【嘉兴市】快件已经到达【杭州中转部】","zone":""},{"datetime":"2019-03-25 22:29:58","remark":"【嘉兴市】快件离开【杭州中转部】已发往【重庆】","zone":""},{"datetime":"2019-03-27 01:12:45","remark":"【重庆市】快件已经到达【重庆】","zone":""},{"datetime":"2019-03-27 01:32:33","remark":"【重庆市���快件离开【重庆】已发往【重庆加州】","zone":""},{"datetime":"2019-03-27 10:08:24","remark":"【重庆市】快件已经到达【重庆加州】","zone":""},{"datetime":"2019-03-27 10:08:24","remark":"【重庆市】【重庆加州】的蒋忠财[13896642785]（13896642785）正在第1次派件,请保持电话畅通,并耐心等待","zone":""},{"datetime":"2019-03-27 12:03:14","remark":"【重庆市】快件已送达【\"丰巢\"的丰巢重庆君悦世纪服务站】,如有问题请电联（13896642785/15223134780,13896642785）,感谢使用中通快递,期待再次为您服务!","zone":""},{"datetime":"2019-03-27 19:20:22","remark":"【重庆市】已签收,签收人凭取货码签收,如有疑问请电联:13896642785/15223134780,13896642785,您的快递已经妥投,如果您对我们的服务感到满意,请给个五星好评,鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】","zone":""}]}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * company : 自动匹配
         * com : auto
         * no : 75137217173016
         * status : 1
         * list : [{"datetime":"2019-03-25 17:45:11","remark":"【杭州市】【杭州东新路】（0571-86013750）的科凌集团驻点业务员[18042481803]（18042481803）已揽收","zone":""},{"datetime":"2019-03-25 20:13:15","remark":"【杭州市】快件离开【杭州东新路】已发往【重庆】","zone":""},{"datetime":"2019-03-25 22:28:26","remark":"【嘉兴市】快件已经到达【杭州中转部】","zone":""},{"datetime":"2019-03-25 22:29:58","remark":"【嘉兴市】快件离开【杭州中转部】已发往【重庆】","zone":""},{"datetime":"2019-03-27 01:12:45","remark":"【重庆市】快件已经到达【重庆】","zone":""},{"datetime":"2019-03-27 01:32:33","remark":"【重庆市���快件离开【重庆】已发往【重庆加州】","zone":""},{"datetime":"2019-03-27 10:08:24","remark":"【重庆市】快件已经到达【重庆加州】","zone":""},{"datetime":"2019-03-27 10:08:24","remark":"【重庆市】【重庆加州】的蒋忠财[13896642785]（13896642785）正在第1次派件,请保持电话畅通,并耐心等待","zone":""},{"datetime":"2019-03-27 12:03:14","remark":"【重庆市】快件已送达【\"丰巢\"的丰巢重庆君悦世纪服务站】,如有问题请电联（13896642785/15223134780,13896642785）,感谢使用中通快递,期待再次为您服务!","zone":""},{"datetime":"2019-03-27 19:20:22","remark":"【重庆市】已签收,签收人凭取货码签收,如有疑问请电联:13896642785/15223134780,13896642785,您的快递已经妥投,如果您对我们的服务感到满意,请给个五星好评,鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】","zone":""}]
         */

        private String company;
        private String com;
        private String no;
        private String status;
        private List<OrderLogisticsInfoBean> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<OrderLogisticsInfoBean> getList() {
            return list;
        }

        public void setList(List<OrderLogisticsInfoBean> list) {
            this.list = list;
        }

    }
}
