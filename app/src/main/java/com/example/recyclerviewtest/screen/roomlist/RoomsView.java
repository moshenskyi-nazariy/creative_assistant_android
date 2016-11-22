package com.example.recyclerviewtest.screen.roomlist;

import android.support.annotation.NonNull;

import com.example.recyclerviewtest.content.Room;
import com.example.recyclerviewtest.content.RoomResponse;
import com.example.recyclerviewtest.screen.general.LoadingView;

import java.util.List;

public interface RoomsView extends LoadingView{

     void showRooms(@NonNull List<Room> rooms);

     void showRoomDetail(@NonNull Room room);

     void showError();

}
