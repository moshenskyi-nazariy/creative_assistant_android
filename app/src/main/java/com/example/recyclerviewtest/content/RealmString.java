package com.example.recyclerviewtest.content;

import io.realm.RealmObject;

public class RealmString extends RealmObject {

    private String mVar;

    public RealmString() {
    }

    public RealmString(String var) {
        mVar = var;
    }

    public String getmVar() { return mVar; }

    public void setmVar(String var) { mVar = var;}

}
