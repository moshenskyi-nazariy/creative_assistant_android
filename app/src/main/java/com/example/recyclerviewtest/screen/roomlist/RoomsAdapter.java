package com.example.recyclerviewtest.screen.roomlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.content.Room;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomHolder>{

    private final List<Room> mRooms;

    private final OnItemClick mOnItemClick;

    private final View.OnClickListener mRoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Room room = (Room) view.getTag();
            mOnItemClick.onItemClick(room);
        }
    };

    public RoomsAdapter(@NonNull List<Room> rooms, @NonNull OnItemClick onItemClick) {
        mRooms = rooms;
        mOnItemClick = onItemClick;
    }

    public void ChangeDataSet(@NonNull List<Room> rooms) {
        mRooms.clear();
        mRooms.addAll(rooms);
        notifyDataSetChanged();
    }

    @Override
    public RoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_room, parent, false);
        return new RoomHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoomHolder holder, int position) {
        Room room = mRooms.get(position);
        holder.bind(room);
        holder.itemView.setTag(room);
        holder.itemView.setOnClickListener(mRoomListener);
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public interface OnItemClick {
        void onItemClick(@NonNull Room room);
    }
}
