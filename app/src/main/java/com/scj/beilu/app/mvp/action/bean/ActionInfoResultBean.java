package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/18 22:22
 */
public class ActionInfoResultBean extends ResultMsgBean {
    /**
     * action : {"actionId":38,"actionName":"跪姿俯卧撑","actionPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/action/head/2e0a5301-0884-41fa-9189-d7772308bfa320190492220124.png","actionVideoAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/action/video/912155be-d182-428e-8e9e-f8c94d27615620190492220118.mp4","actionContent":"<!doctype html>\n<html lang=\"zh-CN\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\"\n          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n    <title>Document<\/title>\n    <style>\n        :root{\n            --background-color: #3b3b3b;\n            --text-color: #eee;\n            --text-color-secondary: #626262;\n        }\n        *{\n            box-sizing: border-box;\n        }\n        html,body{\n            height: 100%\n        }\n        body{\n            margin: 0;\n            padding: 32px 16px 0 16px;\n            font-family: PingFang SC,Helvetica Neue,Hiragino Sans GB,Helvetica,Microsoft YaHei,Arial;\n            color: var( --text-color, #eee );\n            font-size: 16px;\n            background-color: var( --background-color, #3b3b3b );\n        }\n        .al-item>h2{\n            margin: 0;\n            color: var( --text-color-secondary, #626262 );\n            font-size: 14px\n        }\n        .al-item>h2+img{\n            width: 100%\n        }\n        .al-item>ul{\n            margin: 0.8em 0;\n            padding-inline-start: 20px\n        }\n        .al-item>ul>li+li{\n            margin-top: 0.4em;\n        }\n    <\/style>\n<\/head>\n<body>\n<section>\n        <main>\n            <div class=\"al-item\">\n                <h2>动作要领：<\/h2>\n                <ul>\n                    <li>发生科技<\/li>\n                    <li>啥肯定积分<\/li>\n                <\/ul>\n            <\/div>\n\n            <div class=\"al-item\">\n                <h2>呼吸：<\/h2>\n                <ul>\n                    <li>阿克苏积分<\/li>\n                    <li>热经济记录接口<\/li>\n                <\/ul>\n            <\/div>\n\n            <div class=\"al-item\">\n                <h2>目标肌肉：<\/h2><p><br><\/p>\n                \n            <\/div>\n        <\/main>\n    <\/section><\/body>\n<\/html>","actionCateId":1,"actionDesId":1,"actionDesPartId":0}
     */

    private ActionInfoBean action;

    public ActionInfoBean getAction() {
        return action;
    }

    public void setAction(ActionInfoBean action) {
        this.action = action;
    }
}
