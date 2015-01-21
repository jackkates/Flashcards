package com.jackkates.flashcards.quizlet;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonParseException;
import com.jackkates.flashcards.data.GsonManager;
import com.jackkates.flashcards.data.Set;

import java.io.UnsupportedEncodingException;

/**
 * Created by jackkates on 1/17/15.
 */
public class SetRequest extends Request<Set> {

    private Response.Listener listener;
    private String accessToken = "c4bAbERDtxtrkwNtUvyAuj2kHtvKRyB6gzx9eXR2";

    public SetRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<Set> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Set quizletSet = GsonManager.decodeSet(json);
            return Response.success(quizletSet, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Set response) {
        listener.onResponse(response);
    }

}
