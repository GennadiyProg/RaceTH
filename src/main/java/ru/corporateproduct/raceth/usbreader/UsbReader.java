package ru.corporateproduct.raceth.usbreader;

import java.util.HashMap;
import java.util.Map;

public class UsbReader {

    public String IdParser(String id) {
        char[] idArray = id.toCharArray();

        for (int i = 0; i < idArray.length; i++) {
            char letter = Character.toLowerCase(idArray[i]);

            if (Character.isDigit(letter)) continue;
            if (97 <= (int) letter && (int) letter <= 122) {
                idArray[i] = letter;
                continue;
            }

                 if (letter == 'й') idArray[i] = 'q';
            else if (letter == 'ц') idArray[i] = 'w';
            else if (letter == 'у') idArray[i] = 'e';
            else if (letter == 'к') idArray[i] = 'r';
            else if (letter == 'е') idArray[i] = 't';
            else if (letter == 'н') idArray[i] = 'y';
            else if (letter == 'г') idArray[i] = 'u';
            else if (letter == 'ш') idArray[i] = 'i';
            else if (letter == 'щ') idArray[i] = 'o';
            else if (letter == 'з') idArray[i] = 'p';
            else if (letter == 'ф') idArray[i] = 'a';
            else if (letter == 'ы') idArray[i] = 's';
            else if (letter == 'в') idArray[i] = 'd';
            else if (letter == 'а') idArray[i] = 'f';
            else if (letter == 'п') idArray[i] = 'g';
            else if (letter == 'р') idArray[i] = 'h';
            else if (letter == 'о') idArray[i] = 'j';
            else if (letter == 'л') idArray[i] = 'k';
            else if (letter == 'д') idArray[i] = 'l';
            else if (letter == 'я') idArray[i] = 'z';
            else if (letter == 'ч') idArray[i] = 'x';
            else if (letter == 'с') idArray[i] = 'c';
            else if (letter == 'м') idArray[i] = 'v';
            else if (letter == 'и') idArray[i] = 'b';
            else if (letter == 'т') idArray[i] = 'n';
            else if (letter == 'ь') idArray[i] = 'm';
        }

        return String.valueOf(idArray);
    }
}
