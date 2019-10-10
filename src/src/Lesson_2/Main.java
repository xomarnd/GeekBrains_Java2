package Lesson_2;

import java.util.Arrays;

/*
1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4, при подаче массива другого размера
необходимо бросить исключение MyArraySizeException.

2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то
элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть
брошено исключение MyArrayDataException – с детализацией, в какой именно ячейке лежат неверные данные.

3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException
и вывести результат расчета.
*/
public class Main {
    public static void main(String[] args) {

        String[][] correctArray = {{"1", "2", "3", "4"}, {"4", "3", "2", "1"}, {"1", "2", "2", "1"}, {"4", "3", "3", "4"}};
        String[][] noCorrectArray1 = {{"1", "2", "3", "4", "5"}, {"4", "3", "2", "1"}, {"1", "2", "2", "1"}, {"4", "3", "3", "4"}};
        String[][] noCorrectArray2 = {{"1", "2", "#", "4"}, {"4", "3", "2", "1"}, {"1", "2", "2", "1"}, {"4", "3", "3", "4"}};

        System.out.println(Arrays.deepToString(correctArray));
        analyze(correctArray);

        System.out.println(Arrays.deepToString(noCorrectArray1));
        analyze(noCorrectArray1);

        System.out.println(Arrays.deepToString(noCorrectArray2));
        analyze(noCorrectArray2);

    }

    private static void analyze(String[][] array) throws MyArraySizeException, MyArrayDataException {
        int summ = 0;
        try {
            int value = 0;
            if (array.length != 4 || array[0].length != 4 || array[1].length != 4 || array[2].length != 4 || array[3].length != 4) {
                throw new MyArraySizeException();
            }

            for (int i=0; i < array.length; i++){
                for(int j=0; j < array.length; j++){
                    try {
                        value = Integer.parseInt(array[i][j]);
                        summ+= value;
                    } catch (IllegalArgumentException e) {
                        String message = "по адрессу"+ " " + String.valueOf(i) + " " + "ряд, " + String.valueOf(j)
                                + " " + "ячейка - \"" + array[i][j] + "\".";
                        throw new MyArrayDataException(message);
                    }
                }
            }
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сумма элементов массива равна:" + " " +String.valueOf(summ) + "\n");
        }
    }
}
