package com.lifehouse.raceth.usbreader;

import java.util.Map;

public class UsbReader {

    private final static Map<Character, Character> letters = Map.ofEntries(
            Map.entry('й', 'q'),
            Map.entry('ц', 'w'),
            Map.entry('у', 'e'),
            Map.entry('к', 'r'),
            Map.entry('е', 't'),
            Map.entry('н', 'y'),
            Map.entry('г', 'u'),
            Map.entry('ш', 'i'),
            Map.entry('щ', 'o'),
            Map.entry('з', 'p'),
            Map.entry('ф', 'a'),
            Map.entry('ы', 's'),
            Map.entry('в', 'd'),
            Map.entry('а', 'f'),
            Map.entry('п', 'g'),
            Map.entry('р', 'h'),
            Map.entry('о', 'j'),
            Map.entry('л', 'k'),
            Map.entry('д', 'l'),
            Map.entry('я', 'z'),
            Map.entry('ч', 'x'),
            Map.entry('с', 'c'),
            Map.entry('м', 'v'),
            Map.entry('и', 'b'),
            Map.entry('т', 'n'),
            Map.entry('ь', 'm')
    );

    public String parseChip(String id) {
        char[] idArray = id.toCharArray();

        for (int i = 0; i < idArray.length; i++) {
            char letter = Character.toLowerCase(idArray[i]);

            if (Character.isDigit(letter)) continue;
            if (97 <= (int) letter && (int) letter <= 122) {
                idArray[i] = letter;
                continue;
            }

            try {
                idArray[i] = letters.get(letter);
            }
            catch (Exception exp) {
                String errorMessage = String.format("Символ '%c' отсутствует в библиотеке. Строка: '%s'", letter, id);
                System.out.println(errorMessage);
            }
        }

        return String.valueOf(idArray).toUpperCase().replaceAll(" ", "");
    }
}
