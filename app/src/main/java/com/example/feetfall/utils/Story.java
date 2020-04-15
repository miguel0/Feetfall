package com.example.feetfall.utils;

import java.util.ArrayList;
import java.util.List;

public class Story {
    public static List<Decision> list;

    private Decision d3_3 = new Decision("third 3 decision", new DecisionButton("third 3 1", "third 3 1 res", 0, 0, 0, 0, null, null),
            new DecisionButton("third 3 2", "third 3 2 res", 0, 0, 0, 0, null, null));

    private Decision d3_2 = new Decision("third 2 decision", new DecisionButton("third 2 1", "third 2 res", 0, 0, 0, 0, null, null),
            new DecisionButton("third 2 2", "third 2 2 res", 0, 0, 0, 0, null, null));

    private Decision d3_1 = new Decision("third 1 decision", new DecisionButton("third 1 1", "third 1 1 res", 0, 47, 0, 0, null, null),
            new DecisionButton("third 1 2", "third 1 2 res", 0, 0, 0, 0, null, null));

    private Decision d2 = new Decision("second decision", new DecisionButton("second 1", "second 1 res", 2, 5, 5, 0, d3_1, null),
            new DecisionButton("second 2", "second 2 res", 0, 0, 15, 0, d3_2, d3_3));

    private Decision d1 = new Decision("first decision", new DecisionButton("first 1", "first 1 res", 0, 47, 0, 0, null, null),
            new DecisionButton("first 2", "first 2 res", 6, 0, 0, 0, d2, null));

    public Story() {
        list = new ArrayList<>();

        list.add(d1);
        list.add(d2);
        list.add(d3_1);
        list.add(d3_2);
        list.add(d3_3);
    }
}
