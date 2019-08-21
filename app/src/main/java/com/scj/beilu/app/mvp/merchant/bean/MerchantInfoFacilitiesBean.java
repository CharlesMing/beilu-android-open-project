package com.scj.beilu.app.mvp.merchant.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 16:56
 */
public class MerchantInfoFacilitiesBean {
    /**
     * installId : 0
     * installName : null
     * installRe : [{"merchantId":56,"installId":0}]
     */
    private int installId;
    private String installName;
    private List<InstallReBean> installRe;//

    public int getInstallId() {
        return installId;
    }

    public void setInstallId(int installId) {
        this.installId = installId;
    }

    public String getInstallName() {
        return installName;
    }

    public void setInstallName(String installName) {
        this.installName = installName;
    }

    public List<InstallReBean> getInstallRe() {
        return installRe;
    }

    public void setInstallRe(List<InstallReBean> installRe) {
        this.installRe = installRe;
    }

    public static class InstallReBean {
        /**
         * merchantId : 56
         * installId : 0
         */

        private int merchantId;
        private int installId;

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getInstallId() {
            return installId;
        }

        public void setInstallId(int installId) {
            this.installId = installId;
        }
    }
}
