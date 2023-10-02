package hangman;

import java.util.Random;

final class Dictionary {
    // Словарь слов, доступных для разгадывания
    static final String[] DICT = new String[] {"hello", "world", "java", "human", "people"};
    // Генератор для случайного выбора слова
    static final Random GENERATOR = new Random();

    static String chooseRandomWord() {
        int randIndex = GENERATOR.nextInt(0, DICT.length);
        return DICT[randIndex];
    }

    private Dictionary() {
    }
}
