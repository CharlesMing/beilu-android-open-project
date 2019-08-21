package com.scj.beilu.app.mvp.home.model;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:SunGuiLan
 * date:2019/2/15
 * descriptin:
 */
public interface ICoachDetails {
    public Observable<List<String>> getImages();
}
