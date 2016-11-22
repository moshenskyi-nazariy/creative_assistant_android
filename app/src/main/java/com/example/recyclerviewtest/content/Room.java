package com.example.recyclerviewtest.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.arturvasilov.rxloader.LifecycleHandler;

public class Room extends RealmObject {

    @SerializedName("description")
    private String mDescription;

    @SerializedName("id")
    private String mId;

    @SerializedName("objects")
    private RealmList<RealmString> mObjects ;

    public Room() {
    }

    public Room(@NonNull String description, @NonNull String id, @NonNull RealmList<RealmString> objects) {
        mDescription = description;

        mId = id;

        mObjects = objects;
    }

    @NonNull
    public String GetId() {
        return mId;
    }

    @NonNull
    public String GetDescription() {
        return mDescription;
    }

    @NonNull public RealmList<RealmString> GetObjectList() { return mObjects; }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setId(String id) { mId = id; }

    public void setObjects(RealmList<RealmString> objects) { mObjects = objects; }
}
