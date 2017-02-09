package com.mh.systems.brokenhurst.web.api;

//import com.squareup.okhttp.OkHttpClient;

/**
 * Created by HP on 06-07-2016.
 */
public class RestClient {

    private static WebServiceMethods REST_CLIENT;
    public static String ROOT =
            "http://mhsserver3.com";

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static WebServiceMethods get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

       /* OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60 * 2000, TimeUnit.MILLISECONDS);


        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(okHttpClient));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        RestAdapter restAdapter = builder.build();

        REST_CLIENT = restAdapter.create(WebServiceMethods.class);*/
    }

}
