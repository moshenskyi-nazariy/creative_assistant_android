package com.example.recyclerviewtest.repository;


import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmResults;

import com.example.recyclerviewtest.content.RoomResponse;
import com.example.recyclerviewtest.utils.RxUtils;
import com.example.recyclerviewtest.api.ApiFactory;
import com.example.recyclerviewtest.content.Room;

import java.util.List;

import rx.Observable;


public class DefaultApiRepository implements ApiRepository {

    @NonNull
    @Override
    public Observable<List<Room>> rooms() {
        return ApiFactory.getApiService()
                .rooms()
                .map(RoomResponse::getRooms)
                .flatMap(rooms -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(Room.class);
                        realm.insert(rooms);
                    });
                    return Observable.just(rooms);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Room> rooms = realm.where(Room.class).findAll();

                    return Observable.just(realm.copyFromRealm(rooms));
                })
                .compose(RxUtils.async());
    }

}
