package Lesson_2;

class MyArrayDataException extends RuntimeException {
    MyArrayDataException(String message) {
        super("Некорректные данные" + " " + message);
    }
}
