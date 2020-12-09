package com.company;


import java.util.*;

public class Huffman {
    private PriorityQueue<TreeNode> queue;
    //строка - анализ
    private String text;
    private Dirctire dirctire;

    private HashMap<Character,String> codeTable;//[символ]-[двоичный код]

    public Huffman(String text){
        this.text=text;
        this.dirctire=new Dirctire();
        dirctire.fillTable(text);
        codeTable=new HashMap<>();
        queue=new PriorityQueue<>(new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return o1.c-o2.c;
            }
        });


        for (Character t: dirctire.tableFrequency.keySet()
             ) {
            queue.add(new TreeNode(t,dirctire.tableFrequency.get(t)));//создаём деревья
        }

        while (queue.size()!=1){
            TreeNode treeNode1=queue.poll();
            TreeNode treeNode2=queue.poll();
            TreeNode treeNodeQueue=new TreeNode(treeNode1.c+treeNode2.c);
            treeNodeQueue.leftTreeNode=treeNode1;
            treeNodeQueue.rightTreeNode=treeNode2;
            queue.add(treeNodeQueue);
        }
        TreeNode head=queue.peek();//корень

        codeCharacter(head,"");
        //left =0
        //right = 1
        for (Character t:codeTable.keySet()
             ) {
            System.out.println(t +": " + codeTable.get(t));

        }

    }

    private void codeCharacter(TreeNode treeNode, String code){//правый - 1, левый - 0
        if(treeNode.chr!=0) {
            codeTable.put(treeNode.chr,code);
            return;
        }
        codeCharacter(treeNode.leftTreeNode,code +"0");
        codeCharacter(treeNode.rightTreeNode,code +"1");
    }

    public String getCodeText(){
        String code="";
        for (Character t:text.toCharArray()
        ) {
            code +=codeTable.get(t);
        }
        return code;
    }
    public int getSizeBits(){
        return getCodeText().length();
    }
    public int getValueOfCharactersOfText(){
        return text.toCharArray().length;
    }
    public double getMediumLenght(){
        int value=0;
        for (Character t: codeTable.keySet()
             ) {
            value+=codeTable.get(t).length();
        }
        return (double) value/codeTable.keySet().size();
    }
    public double getDisper(){
        double value=0;
        for (Character t: codeTable.keySet()
        ) {
            value+=Math.pow(getMediumLenght()-codeTable.get(t).length(),2);
        }


        return value/codeTable.keySet().size();
    }
    

    private class TreeNode {
        TreeNode leftTreeNode;
        TreeNode rightTreeNode;


        int c;
        char chr;
        TreeNode(int c){
            this.c=c;
        }

        TreeNode(char chr, int c){
            this.c=c;
            this.chr=chr;

        }

    }

    private class Dirctire{
        private HashMap<Character,Integer> tableFrequency;
        private Dirctire(){
            tableFrequency=new HashMap<>();
        }
        private void fillTable(String text){
            for (Character t:text.toCharArray()
                 ) {
                if(tableFrequency.get(t)==null) tableFrequency.put(t,1);
                else tableFrequency.put(t,tableFrequency.get(t)+1);
            }

        }

    }

}
