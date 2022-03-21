package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.parcelize.Parcelize
import org.json.JSONObject


@Parcelize
class User(var name:String = "",
           var screenName:String = "",
           var publicImageUrl:String = ""):Parcelable{


    companion object{
            fun fromJson(JsonObject: JSONObject): User {
                val user = User()
                user.name = JsonObject.getString("name")
                user.screenName= JsonObject.getString("screen_name")
                user.publicImageUrl = JsonObject.getString("profile_image_url_https")
                return user

            }

    }

}