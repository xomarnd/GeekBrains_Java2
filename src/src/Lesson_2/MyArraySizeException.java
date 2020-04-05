package Lesson_2;

class MyArraySizeException extends RuntimeException{
    MyArraySizeException() {
        super("Не верный размер массива.");
    }
}
