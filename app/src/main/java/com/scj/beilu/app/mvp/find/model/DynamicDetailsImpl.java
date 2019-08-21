package com.scj.beilu.app.mvp.find.model;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * author:SunGuiLan
 * date:2019/2/12
 * descriptin:
 */
public class DynamicDetailsImpl extends LoadDelegate implements IDynamicDetails {

    public DynamicDetailsImpl(Builder builder) {
        super(builder);
    }

    @Override
    public Observable<List<String>> getImages() {
        ObservableOnSubscribe<List<String>> imagsObser =
                new ObservableOnSubscribe<List<String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                        List<String> imgs = new ArrayList<>();
                        imgs.add("http://weiliicimg1.pstatp.com/weili/l/567204307573014667.webp");
                        imgs.add("http://icweiliimg6.pstatp.com/weili/sm/489292320964608119.webp");
                        imgs.add("http://icweiliimg9.pstatp.com/weili/sm/540018848208913417.webp");
                        emitter.onNext(imgs);
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(imagsObser);
    }
}
