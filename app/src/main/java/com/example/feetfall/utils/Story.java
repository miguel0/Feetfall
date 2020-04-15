package com.example.feetfall.utils;

import java.util.ArrayList;
import java.util.List;

public class Story {
    public static List<Decision> list;
    private String text1 = "You suddenly appear, in the darkness, feeling like waking up. But a confortable bed, a painful headache and sore throat makes you want to sleep some more. You:";
    private String text2 = "You stand up and look around you. \"What's with everybody's dressing?\", you think, as people in dirty rags come and go, walking in the dirt, carrying wasted bags and mounting grubby horses. A very unpleasant odor fill the air. As you ask yourself \"Where the fuck am I?\", you:";
    private String text31 = "You realize you were sleeping in an alley, surrounded by shops and sellers of dubious provenance. You walk down the alley, and feel unconfortable as people judge you with their looks. \"The fuck you're looking at? You're the filthy ones here, not me\". As you walk, you look up in awe. A humongous manmade structure sits in front of you. It's not a castle, it's not a fortress, but a huge citadel carved out of the mountains. \"What the fu-\", you say, but a sudden noice interrupts you, accompanied by the sigh of flames. A explosion perhaps? A chemical accident? But far worse. An elder dragon has arrived.";
    private String text32 = "You ask the lady \"Excuse me, where am I? I think I might be lost\" \"Shup up, cunt!\", she yells, as she spits at your feet.";
    private String result11 = "You rub your eyes as you yawn, not feeling completeley sure you made the right decision.\n" +
            "As you lift your head, you hear a crowd closing up, revolving around you.\n" +
            "The light of your window becomes the scorching sun of the middle of the day.\n" +
            "The confortable bed becomes a bunch of pointy hay.\n" +
            "And as you rise new smells strike your nose. \n" +
            "Oh... is that horse shit?";
    private String result12 = "You welcome the darkness and tighten your eyes, letting yourself become one with the void.\n" +
            "But suddenly, you have trouble breathing.\n" +
            "Your heart rate intensifies and feel a crushing weight on you throat.\n" +
            "You open your eyes to give one more look to the world around you, as it disappears...\n" +
            "You leave this world, with nothing but a pale look.";
    private String result21 = "As you enter the mud in which everybody's walking, you realize you're wearing the same rags everyone's wearing (but they're cleaner),\n" +
            "and that you're not wearing shoes. The mud sticks in between your toes. You got goosebumps.";
    private String result22 = "As you enter the mud in which everybody's walking, you realize you're wearing the same rags everyone's wearing (but they're cleaner),\n" +
            "and that you're not wearing shoes. The mud sticks in between your toes. You got goosebumps.";
    private String bt11 = "Open your eyes and welcome the morning";
    private String bt12 = "Sleep 5 minutes more";
    private String bt21 = "Walk aorund in search of clues";
    private String bt22 = "Talk to a lady with a crying baby";


    private Decision d3_2 = new Decision(text32, new DecisionButton("You", "", 0, 0, 0, 0, null, null),
            new DecisionButton("died", "", 0, 0, 0, 0, null, null));

    private Decision d3_1 = new Decision(text31, new DecisionButton("It's", "", 0, 0, 0, 0, null, null),
            new DecisionButton("angry", "", 0, 0, 0, 0, null, null));

    private Decision d2 = new Decision(text2, new DecisionButton(bt21, result21, 2, 0, 0, 9, d3_1, null),
            new DecisionButton(bt22, result22, 0, 0, 0, 0, d3_2, null));

    private Decision d1 = new Decision(text1, new DecisionButton(bt11, result11, 6, 49, 0, 0, d2, null),
            new DecisionButton(bt12, result12, 0, 1000, 0, 0, null, null));

    public Story() {
        list = new ArrayList<>();

        list.add(d1);
        list.add(d2);
        list.add(d3_1);
        list.add(d3_2);

    }
}
