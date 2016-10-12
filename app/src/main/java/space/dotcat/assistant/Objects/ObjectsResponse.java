package space.dotcat.assistant.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ObjectsResponse {

    @SerializedName("objects")
    @Expose
    private List<Object> objects = new ArrayList<>();

    public List<Object> GetObjects() { return objects; }

    public void SetObjects(List<Object> objects) { this.objects = objects; }
}
