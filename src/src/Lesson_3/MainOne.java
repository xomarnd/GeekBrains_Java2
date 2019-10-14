package Lesson_3;

import java.util.*;

public class MainOne {
    public static void main(String[] args){
        List<String> vegetables = Arrays.asList(
                "лук",
                "нут",
                "огурец",
                "пастернак",
                "патиссон",
                "перец",
                "редис",
                "редька",
                "репа",
                "свекла",
                "топинамбур",
                "чеснок",
                "хрен",
                "патиссон",
                "перец",
                "редис",
                "свекла",
                "свекла",
                "хрен",
                "патиссон"
        );

        Set<String> unique = new HashSet<String>(vegetables);

        System.out.println("Сам список:\n" + vegetables.toString());
        System.out.println("\nУникальные слова:\n" + unique.toString());
        System.out.println("\nУпоминания:");
        for (String key : unique) {
            System.out.println(key + ": " + Collections.frequency(vegetables, key));
        }
    }
}
