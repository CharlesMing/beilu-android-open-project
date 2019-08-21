package com.scj.beilu.app.mvp.common.pay.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/4 13:57
 */
public class AliPayInfoResultBean extends ResultMsgBean {

    /**
     * orderInfo : app_id=2019021863251143&biz_content=%7B%22body%22%3A%22%E5%95%86%E5%93%81%E7%AE%80%E4%BB%8B%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%22%2C%22out_trade_no%22%3A%22df66e7f6a40e40128f670aa77ed37ac9%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22seller_id%22%3A%22appsanchaji%40163.com%22%2C%22subject%22%3A%22%E5%93%91%E9%93%83%22%2C%22total_amount%22%3A%220.04%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=192.168.0.8%3A9000%2Fapigateway%2Fpay%2Fapi%2Falipay%2FproductPayBack&sign=LDYCKRXMcBZYxaZJ49RILDXHAnPebnNFf13DHr4Sfo0ofGnsctEJvv4882cTJDD9NJ%2Bpxl5FdTZj9oX6k2SNNzhI14ZbrKTy19o7t8b1Ue%2FXtfFES6ZsrQCdmo3czvBT%2FYwTOsKdmTbdDV7BMHLw1n29YdROxGF%2FS2KJ6OWnXruCnAb5mUKB05MyUqwJax4ZzxSsmeYCLDt9kVO10R9yrLPvnzZTqDgGa6LKPkhUL2oYmrLcWBqrYPz%2BLZeWHMBIPgEkA63hpkMmt6xd5zSaXYPvwPm%2FVvL5ybWtaqtY1teL48l3vAhVka23kK0OuYRZ2cid7wVMAyb2FW2bYpkcow%3D%3D&sign_type=RSA2&timestamp=2019-04-04+13%3A56%3A43&version=1.0
     */

    private String orderInfo;
    private String out_trade_no;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
