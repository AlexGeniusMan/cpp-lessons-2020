#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <initializer_list>
#include <vector>
#include <iostream>
#include <initializer_list>
#include <vector>
#include <QLabel>
#include <QPushButton>
#include <QDebug>

class binaryTree
{
public:

    int key; //значение узла
    int height1; //высота узла
    binaryTree* left; //левый узел
    binaryTree* right; //правый узел

    void inorder(binaryTree *root, int x, int y, int replace = 0); //функция вывода

    public:
    binaryTree(const std::initializer_list<int>& list);
    binaryTree(int k);
    int height(binaryTree* p);
    int bfactor(binaryTree* p);
    void fixheight(binaryTree* p);
    binaryTree* rotateright(binaryTree* p);
    binaryTree* rotateleft(binaryTree* q);
    binaryTree* balance(binaryTree* p);
    binaryTree* insert(binaryTree* p, int k);
    binaryTree* findmin(binaryTree* p);
    binaryTree* removemin(binaryTree* p);
    binaryTree* remove(binaryTree* p, int k);
};




//графика
class Widget : public QWidget
{
    Q_OBJECT

public:
    Widget(QWidget *parent = nullptr);
    ~Widget();
    void setLabel(int x, int y, QString str);
};

#endif // WIDGET_H
