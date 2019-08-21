package com.scj.beilu.app.mvp.find.model;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:SunGuiLan
 * date:2019/2/12
 * descriptin:
 */
public interface IDynamicDetails {
    public Observable<List<String>> getImages();
}
