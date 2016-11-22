package com.example.recyclerviewtest.screen.roomlist;


import android.support.annotation.NonNull;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.content.Room;
import com.example.recyclerviewtest.repository.RepositoryProvider;

import ru.arturvasilov.rxloader.LifecycleHandler;

public class RoomsPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final RoomsView mRoomsView;

    public RoomsPresenter(@NonNull LifecycleHandler lifecycleHandler,
                          @NonNull RoomsView roomsView) {
        mLifecycleHandler = lifecycleHandler;
        mRoomsView = roomsView;
    }

    public void init() {
        RepositoryProvider.provideApiRepository()
                .rooms()
                .doOnSubscribe(mRoomsView::showLoading)
                .doOnTerminate(mRoomsView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.room_request))
                .subscribe(mRoomsView::showRooms, throwable -> mRoomsView.showError());
    }

    public void onItemClick(@NonNull Room room) { mRoomsView.showRoomDetail(room); }

}
