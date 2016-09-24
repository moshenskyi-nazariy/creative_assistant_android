package com.example.test.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("obj_id")
    @Expose
    private String objId;

    @SerializedName("action_params")
    @Expose
    private ActionParams actionParams;

    public Body(String action,
                String obj_id,
                ActionParams actionParams) {

        this.action = action;
        this.objId = obj_id;
        this.actionParams = actionParams;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public ActionParams getActionParams() {
        return actionParams;
    }

    public void setActionParams(ActionParams actionParams) {
        this.actionParams = actionParams;
    }
}
