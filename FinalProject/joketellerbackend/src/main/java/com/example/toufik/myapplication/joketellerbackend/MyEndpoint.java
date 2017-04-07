/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.toufik.myapplication.joketellerbackend;

import com.example.Joke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "joketellerbackend.myapplication.toufik.example.com",
                ownerName = "joketellerbackend.myapplication.toufik.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A method that grabs a joke from the {@link Joke} class
     *
     * @return {@link com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder} object.
     */
    @ApiMethod(name = "sayJoke")
    public MyBean sayJoke() {
        Joke jokeTeller = new Joke();
        MyBean response = new MyBean();
        response.setData(jokeTeller.getJoke());
        return response;
    }
}
