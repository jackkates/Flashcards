package com.jackkates.flashcards.quizlet;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonParseException;
import com.jackkates.flashcards.data.GsonManager;
import com.jackkates.flashcards.data.Set;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by jackkates on 1/17/15.
 */
public class SetListRequest extends Request<List<Set>> {

    private Response.Listener listener;
    private final String accessToken = "c4bAbERDtxtrkwNtUvyAuj2kHtvKRyB6gzx9eXR2";

    public SetListRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<List<Set>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("SetListRequest", json);
            List<Set> listOfSets = GsonManager.decodeListOfSets(json);
            return Response.success(listOfSets, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Set> response) {
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        getErrorListener().onErrorResponse(error);
    }
}
