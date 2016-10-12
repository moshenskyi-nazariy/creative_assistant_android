package space.dotcat.assistant.Rooms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Room {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("objects")
    @Expose
    private ArrayList<String> objects = new ArrayList<String>();


    private Room(String description, String id, ArrayList<String> objects) {

        this.description = description;

        this.id = id;

        this.objects = objects;
    }

    public String GetId() {
        return id;
    }

    public String GetDescription() {
        return description;
    }

    public ArrayList<String> GetObjectList() {
        return objects;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObjects(ArrayList<String> objects) {
        this.objects = objects;
    }

}
