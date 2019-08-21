package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/22 20:58
 */
public class FindDetailsBean extends ResultMsgBean {

    /**
     * likeNumbers : []
     * dynamic : {"dynamicId":46,"userId":41,"userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132","userNickName":"笑笑","dynamicDec":"录取了","dynamicDate":"2019-02-22 16:28:14","dynamicVideoAddr":null,"dynamicVideoPic":null,"dynamicLikeCount":0,"dynamicBrowseCount":0,"dynamicOrganId":0,"commentCount":0,"dynamicPic":[{"dynamicPicId":71,"dynamicId":0,"dynamicPicAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/pic/286c4b77-dc49-40ae-ae60-7b25f53c91d620190253162815.png","dynamicPicZip":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/pic/zip286c4b77-dc49-40ae-ae60-7b25f53c91d620190253162815.png"}],"isLike":0,"isFocus":1,"dynamicOrganName":null,"isOwn":0}
     */
    private FindInfoBean dynamic;
    private List<FindInfoLikeMemberBean> likeNumbers;

    public FindInfoBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(FindInfoBean dynamic) {
        this.dynamic = dynamic;
    }

    public List<FindInfoLikeMemberBean> getLikeNumbers() {
        return likeNumbers;
    }

    public void setLikeNumbers(List<FindInfoLikeMemberBean> likeNumbers) {
        this.likeNumbers = likeNumbers;
    }

    @Override
    public String toString() {
        return "FindDetailsBean{" +
                "dynamic=" + dynamic +
                ", likeNumbers=" + likeNumbers +
                '}';
    }
}
