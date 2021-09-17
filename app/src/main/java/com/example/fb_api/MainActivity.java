package com.example.fb_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton=(LoginButton)findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setPermissions(Arrays.asList("user_gender","user_friends"));

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                      i=new Intent(MainActivity.this,User.class);
                        String imgURL="https://graph.facebook.com/" +loginResult.getAccessToken().getUserId() + "/picture?return_ssl_resources=1";
                        i.putExtra("img",imgURL);
                    }
                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                        try {
                            String name= jsonObject.getString("name");
                        i.putExtra("name",name);
                        startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle b=new Bundle();
        b.putString("fields","name");
        graphRequest.setParameters(b);
        graphRequest.executeAsync();

    }

    AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null)
            {
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.startTracking();
    }
}