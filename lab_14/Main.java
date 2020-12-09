package com.company;

public class Main {
    static char t;
    public static void main(String[] args) {
	// write your code here
        Huffman huffman=new Huffman("Shevchenko Vladimir Vladimirovich");
        System.out.println(huffman.getCodeText());//
        //answer:110100001001101011000001001110111110110101100110110001001110101111100011101000011011000100111010111110001110100101110101111100000

        huffman.getSizeBits();//размер в битах
     ;//кол-во символов
        System.out.println((double) huffman.getSizeBits()/(huffman.getValueOfCharactersOfText()*8)); //коэффициент сжатия относительно aski кодов
        System.out.println((double) huffman.getSizeBits()/(huffman.getValueOfCharactersOfText()*
                (Math.pow(huffman.getValueOfCharactersOfText(),0.5)))); //коэффициент сжатия относительно равномерного кода. Не учитывается размер таблицы
        System.out.println(huffman.getMediumLenght());//средняя длина кодового слова
        System.out.println(huffman.getDisper());//дисперсия


    }


}
