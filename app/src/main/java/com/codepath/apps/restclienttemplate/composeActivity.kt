package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class composeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button

    lateinit var client: TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        client = TwitterApplication.getRestClient(this)

        btnTweet.setOnClickListener(){
            //Grab the content of edittetxt(etCompose)
            val tweetContent = etCompose.text.toString()
                // make sure the tweet is not empty
            if (tweetContent.isEmpty()){
                Toast.makeText(this,"Empty tweets not allowed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else
                // make sure the tweet is under count
                if (tweetContent.length > 140)
                {
                    Toast.makeText(this, "tweet is Too Long ! Limit is 140 characters",
                        Toast.LENGTH_SHORT).show()
                }else {
                    client.publishTweet(tweetContent, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                            Log.e(TAG,"Successfully Published" )

                            val tweet = Tweet.fromJson(json.jsonObject)

                            val intent = Intent()
                            intent.putExtra("tweet",tweet)
                            setResult(RESULT_OK, intent)
                            finish()
                    }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Headers?,
                            response: String?,
                            throwable: Throwable?
                        ) {
                            Log.e(TAG,"Failed to publish tweet", throwable)
                        }



                    })
                }
                // make an api call to twitter to publish tweet

        }

    }
    companion object {
        val TAG = "ComposeActivity"
    }

}