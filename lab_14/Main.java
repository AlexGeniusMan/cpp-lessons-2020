package ru.alexgeniusman.java2020.lab_14;

public class Main {

    public static void main(String[] args) {

        QQ qq = new QQ("Ченцов Александр Борисович");
        
        System.out.println(qq.getCode());


        qq.getSize();//обьём биты


        //n символы
        System.out.println((double) qq.getSize()/(qq.getVal()*8)); //k сжатия относительно aski кодов
        System.out.println((double) qq.getSize()/(qq.getVal()* (Math.pow(qq.getVal(),0.5)))); //k сжатия отн равномерного кода



        System.out.println(qq.getMed());//длина усреднённого кодового слова
        System.out.println(qq.getDisp());//дисперсия
    }

}