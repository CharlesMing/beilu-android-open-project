package com.scj.beilu.app.mvp.common.user;

import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/17 18:56
 */
public interface IUserInfo {
    Observable<UserInfoEntity> getUserInfoByToken();
}
