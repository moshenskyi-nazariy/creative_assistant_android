package com.example.recyclerviewtest.screen.roomlist;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.content.Room;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.roomName)
    TextView mRoomName;


    public RoomHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(@NonNull Room room) {
        mRoomName.setText(room.GetDescription());
    }
}
