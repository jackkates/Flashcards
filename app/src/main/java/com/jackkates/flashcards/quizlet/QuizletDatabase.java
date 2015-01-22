package com.jackkates.flashcards.quizlet;

import com.jackkates.flashcards.data.Set;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackkates on 1/21/15.
 */
public class QuizletDatabase {

    private static Map<Integer, Set> sets;

    private static Map<Integer, Set> getMap() {
        if (sets == null) {
            sets = new HashMap<>();
        }
        return sets;
    }

    public static void putAllSets(List<Set> sets) {
        for (Set set : sets) {
            getMap().put(set.getID(), set);
        }
    }

    public static void putSet(Set set) {
        getMap().put(set.getID(), set);
    }

    public static Set getSet(int id) {
        return getMap().get(id);
    }
}
