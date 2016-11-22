package com.example.recyclerviewtest.repository;


import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class RepositoryProvider {

    private static ApiRepository sApiRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static ApiRepository provideApiRepository() {
        if(sApiRepository == null){
            sApiRepository = new DefaultApiRepository();
        }
        return sApiRepository;
    }
}
