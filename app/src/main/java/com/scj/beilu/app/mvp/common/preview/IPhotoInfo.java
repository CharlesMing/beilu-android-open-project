package com.scj.beilu.app.mvp.common.preview;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/16 20:18
 */
public interface IPhotoInfo {
    Observable<String> downloadPhoto(String path);
}
