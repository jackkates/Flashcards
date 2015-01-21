package com.jackkates.flashcards.data;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jackkates on 1/17/15.
 */
public class GsonManager {

    private static Gson gson;

    private static Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .serializeNulls()
                    .create();
        }
        return gson;
    }

    public static Set decodeSet(String setJson) throws JsonParseException {
        return getGsonInstance().fromJson(setJson, Set.class);
    }

    public static List<Set> decodeListOfSets(String setJson) throws JsonParseException {
        Set[] setArray = getGsonInstance().fromJson(setJson, Set[].class);
        return Arrays.asList(setArray);
    }

}
