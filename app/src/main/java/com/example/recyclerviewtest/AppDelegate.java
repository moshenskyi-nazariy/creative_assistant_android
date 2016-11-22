package com.example.recyclerviewtest;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.recyclerviewtest.repository.RepositoryProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;


public class AppDelegate extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .rxFactory(new RealmObservableFactory())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    @NonNull
    public static Context getContext() {
        return sContext;
    }
}
