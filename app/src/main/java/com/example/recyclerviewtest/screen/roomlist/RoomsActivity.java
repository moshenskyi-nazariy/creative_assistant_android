package com.example.recyclerviewtest.screen.roomlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.recyclerviewtest.R;
import com.example.recyclerviewtest.content.Room;
import com.example.recyclerviewtest.screen.BaseActivity;
import com.example.recyclerviewtest.screen.general.LoadingDialog;
import com.example.recyclerviewtest.screen.general.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class RoomsActivity extends BaseActivity implements RoomsView, RoomsAdapter.OnItemClick {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerVIew;

    private LoadingView mLoadingView;

    private RoomsAdapter mAdapter;

    private RoomsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        View room = inflater.inflate(R.layout.activity_rooms, frameLayout, true);
        mDrawerLayout.addView(room);

        ButterKnife.bind(this);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        mRecyclerVIew.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RoomsAdapter(new ArrayList<>(), this);

        mRecyclerVIew.setAdapter(mAdapter);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this,getSupportLoaderManager());
        mPresenter = new RoomsPresenter(lifecycleHandler, this);
        mPresenter.init();
    }

    @Override
    public void onItemClick(@NonNull Room room) { mPresenter.onItemClick(room);}

    @Override
    public void showRooms(@NonNull List<Room> rooms) {
        mAdapter.ChangeDataSet(rooms); }

    @Override
    public void showRoomDetail(@NonNull Room room) {
        //TODO
    }

    @Override
    public void showError() {
        // HANDLE ERROR
    }

    @Override
    public void showLoading() { mLoadingView.showLoading();}

    @Override
    public void hideLoading() { mLoadingView.hideLoading();}

}
