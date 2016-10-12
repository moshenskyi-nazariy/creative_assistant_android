package space.dotcat.assistant.Interface;


import space.dotcat.assistant.Messages.Message;
import space.dotcat.assistant.Messages.PostResult;
import space.dotcat.assistant.Objects.Object;
import space.dotcat.assistant.Objects.ObjectsResponse;
import space.dotcat.assistant.Rooms.RoomsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestInterface {

    @GET("objects/")
    Call<ObjectsResponse>getObjectWithObjectList();

    @GET("rooms/")
    Call<RoomsResponse>getObjectWithRoomList();

    @POST("messages/")
    Call<PostResult>postMessage(@Body Message message);

    @GET("objects/{id}")
    Call<Object>getObjectById(@Path("id") String id);

}
