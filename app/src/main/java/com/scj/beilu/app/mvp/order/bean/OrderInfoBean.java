package com.scj.beilu.app.mvp.order.bean;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:21
 */
public class OrderInfoBean {
    /**
     * orderNo : 1d01c5d1940b4fb4b166026f80e24b5e
     * userId : null
     * productId : 1
     * productCount : 0
     * productDiscountPrice : 0.01
     * totalPrice : 0.01
     * orderMessages : null
     * orderStatusId : 0
     * waybillNumber : null
     * orderDate : 2019-04-04
     * orderPayId : 2
     * productName : 哑铃
     * productPicOriginalAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/2055accb-7eda-4cb9-a1a6-f0fcc94e4df720190492192017.jpg
     * productPicCompressionAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/Zip2055accb-7eda-4cb9-a1a6-f0fcc94e4df720190492192017.jpg
     * orderTransactionId : null
     * userName : 厉害
     * userTel : 13883448332
     * userDetailAddr : 干净利落即将开始
     * userApplyReason : null
     * Stringion : null
     * processingTime : null
     * description : "黄色"
     * orderPaymentStatus : 0
     */

    private String orderNo;
    private long userId;
    private int productId;
    private int productCount;
    private double productDiscountPrice;
    private double totalPrice;
    private String orderMessages;
    private int orderStatusId;
    private String waybillNumber;
    private String orderDate;
    private int orderPayId;
    private String productName;
    private String productPicOriginalAddr;
    private String productPicCompressionAddr;
    private String orderTransactionId;
    private String userName;
    private String userTel;
    private String userDetailAddr;
    private String userApplyReason;
    private String Stringion;
    private String processingTime;
    private String description;
    private int orderPaymentStatus;
    private String objection;
    private String refundNo;//
    private int addrId;

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderMessages() {
        return orderMessages;
    }

    public void setOrderMessages(String orderMessages) {
        this.orderMessages = orderMessages;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderPayId() {
        return orderPayId;
    }

    public void setOrderPayId(int orderPayId) {
        this.orderPayId = orderPayId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPicOriginalAddr() {
        return productPicOriginalAddr;
    }

    public void setProductPicOriginalAddr(String productPicOriginalAddr) {
        this.productPicOriginalAddr = productPicOriginalAddr;
    }

    public String getProductPicCompressionAddr() {
        return productPicCompressionAddr;
    }

    public void setProductPicCompressionAddr(String productPicCompressionAddr) {
        this.productPicCompressionAddr = productPicCompressionAddr;
    }

    public String getOrderTransactionId() {
        return orderTransactionId;
    }

    public void setOrderTransactionId(String orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserDetailAddr() {
        return userDetailAddr;
    }

    public String getUserApplyReason() {
        return userApplyReason;
    }

    public void setUserApplyReason(String userApplyReason) {
        this.userApplyReason = userApplyReason;
    }

    public String getStringion() {
        return Stringion;
    }

    public void setStringion(String stringion) {
        Stringion = stringion;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderPaymentStatus() {
        return orderPaymentStatus;
    }

    public void setOrderPaymentStatus(int orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus;
    }

    public void setUserDetailAddr(String userDetailAddr) {
        this.userDetailAddr = userDetailAddr;
    }
}
