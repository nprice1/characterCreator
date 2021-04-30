package com.nolanprice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoiceUtils {

    private static final Random RANDOM = new Random();

    public static <T> List<T> makeRandomChoices(Choice<T> choice) {
        List<T> choices = choice.getFrom();
        int numChoices = choices.size();
        List<Integer> selectedIndexes = new ArrayList<>();
        List<T> selectedChoices = new ArrayList<>();
        for (int i=0; i < choice.getChoose(); i++) {
            int choiceIndex = RANDOM.nextInt(numChoices);
            if (!selectedIndexes.isEmpty()) {
                while (selectedIndexes.contains(choiceIndex)) {
                    choiceIndex = RANDOM.nextInt(numChoices);
                }
            }
            selectedIndexes.add(choiceIndex);
            selectedChoices.add(choices.get(choiceIndex));
        }
        return selectedChoices;
    }

}
