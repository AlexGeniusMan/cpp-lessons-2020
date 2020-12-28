package ru.alexgeniusman.java2020.lab_14;

import java.util.*;

public class QQ {
    private PriorityQueue<TreeNode> queue;//наша очередь

    private String t;
    private Dict dict;

    private HashMap<Character,String> tabcode;//символ + bin код

    public QQ(String text){
        this.dict = new Dict();
        this.t = text;

        dict.tabFill(text);
        tabcode = new HashMap<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.a));


        for (Character t: dict.tableFreq.keySet())
            queue.add(new TreeNode(t, dict.tableFreq.get(t)));//создаём деревья


        while (queue.size() != 1){

            TreeNode tree1 = queue.poll();
            TreeNode tree2 = queue.poll();
            TreeNode QueueTree = new TreeNode(tree1.a +tree2.a);

            QueueTree.left = tree1;
            QueueTree.right = tree2;
            queue.add(QueueTree);
        }

        TreeNode head = queue.peek();//корешок

        codeChar(head,"");

        for (Character t: tabcode.keySet())
            System.out.println(t +": " + tabcode.get(t));


    }

    private class Dict {
        private HashMap<Character,Integer> tableFreq;

        private Dict(){
            tableFreq = new HashMap<>();
        }

        private void tabFill(String text){
            for (Character t:text.toCharArray()) {
                if(tableFreq.get(t) == null)
                    tableFreq.put(t,1);
                else
                    tableFreq.put(t, tableFreq.get(t)+1);
            }

        }

    }
    public int getSize(){
        return getCode().length();
    }

    public int getVal(){
        return t.toCharArray().length;
    }

    private void codeChar(TreeNode treeNode, String code){//левый - 0, правый - 1
        if(treeNode.c !=0) {
            tabcode.put(treeNode.c,code);
            return;
        }
        codeChar(treeNode.left,code +"0");
        codeChar(treeNode.right,code +"1");
    }

    public String getCode(){
        String code="";
        for (Character t: t.toCharArray()) {
            code += tabcode.get(t);
        }
        return code;
    }





    public double getMed(){

        int k = 0;
        for (Character t: tabcode.keySet())
            k += tabcode.get(t).length();

        return (double) k / tabcode.keySet().size();
    }


    public double getDisp(){
        double k = 0;
        for (Character t: tabcode.keySet())
            k += Math.pow(getMed() - tabcode.get(t).length(),2);

        return k / tabcode.keySet().size();
    }


    private class TreeNode {
        TreeNode left, right;
        int a;
        char c;

        TreeNode(int a){
            this.a = a;
        }

        TreeNode(char c, int a){
            this.a = a;
            this.c = c;
        }
    }



}