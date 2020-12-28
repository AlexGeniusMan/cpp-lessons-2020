package ru.alexgeniusman.java2020.lab_15;

public class Main {

    public static void main(String[] args) {
        int[] mass1 = { 5,10,3,12,5,50,6} ;//массив для примера

        System.out.println("Ответ: " + matrix(mass1));
        
    }


    public static int matrix(int [] arr){
        int [][] mass = new int[arr.length-1][arr.length-1];//двумерный массив для промежуточных вычислений


        int kolvo = arr.length-1; //кол-во матриц

        for (int i = 0; i < kolvo; i++) //нулевой двумерный массив
            mass[i][i] = 0;


        for (int m = 1; m < kolvo; m++) {//смещение вправо на один элемент
            for (int n = 0; n < kolvo-m; n++) {
                int j = n+m; //вторая коорд
                mass[n][j]=Integer.MAX_VALUE;
                for (int k = n; k < j; k++) {
                    mass[n][j] = Math.min(mass[n][j], mass[n][k]+mass[k+1][j] + arr[n]*arr[k+1]*arr[j+1]);//хранение
                    if(n == kolvo-m-1 && m==kolvo-1)//n=0 и m= kolvo-1
                        System.out.println( mass[n][k]+mass[k+1][j]+ arr[n]*arr[k+1]*arr[j+1]);//вывод
                }
            }
        }

        return mass[0][kolvo-1];//ответ правая верхняя ячейка
    }
}