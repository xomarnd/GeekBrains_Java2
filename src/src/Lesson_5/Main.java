package Lesson_5;

import java.util.Arrays;

public class Main {
    private static final int size = 10_000_000;
    private static final int h = size / 2;

    public static void main(String[] args){

        Main mainClass = new Main ();

        System.out.println (Arrays.equals(mainClass.methodOne(), mainClass.methodTwo()));
    }

    private void calculate(float[] arr, int shift) {
        final int u = arr.length + shift;
        for(int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + u / 5) * Math.cos(0.2f + u / 5) * Math.cos(0.4f + u / 2));
        }
    }

    private float[] methodOne(){
        System.out.println("Метод 1 - ПУСК");

        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        long start = System.currentTimeMillis();
        calculate(arr, 0);
        long stop = System.currentTimeMillis();
        System.out.println(String.format("Метод 1 - СТОП, время выполнения %s \n", String.valueOf(stop - start)));

        return arr;
    }

    private float[] methodTwo(){
        System.out.println("Метод 2 - ПУСК");

        float[] arr = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        Arrays.fill(arr, 1f);
        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        long split = System.currentTimeMillis();
        System.out.println(String.format("Создание массивов - %s \n", String.valueOf(split - start)));

        Thread thread1 = new Thread(() ->this.methodTwoCalculate (arr1, 1, 0));
        Thread thread2 = new Thread(() ->this.methodTwoCalculate (arr2, 2, h));

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }


        long connect = System.currentTimeMillis();

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        long end = System.currentTimeMillis();

        System.out.println(String.format("Склейка массивов - %s", String.valueOf(end - connect)));
        System.out.println(String.format("Метод 2 - СТОП, время выполнения %s \n", String.valueOf(end - start)));

        return arr;
    }

    private void methodTwoCalculate(float[] arr, int number, int shift){
        long start = System.currentTimeMillis();
        calculate(arr, shift);
        long end = System.currentTimeMillis();

        System.out.println(String.format("Поток %d работал %s", number, String.valueOf(end - start)));
    }
}
