#include "widget.h"
#include <QPainter>

Widget* www;
//конструктор дерева
binaryTree::binaryTree(const std::initializer_list<int>& list)
    : key(0), left(nullptr), right(nullptr)
{
    binaryTree *root = nullptr;
    for (auto &c : list)
    {
        root = insert(root, c);
        //qDebug() << c;
    }
    qDebug() << left << right;
    inorder(root, 700, 30);

}
//стартовый конструктор
binaryTree::binaryTree(int k)
{
    key = k;
    left = 0;
    right = 0;
    height1 = 1;
}

int binaryTree::height(binaryTree* p)
{
    return p ? p->height1 : 0; // возврат высоты если указатель не нулевой
}

int binaryTree::bfactor(binaryTree* p)
{
    return height(p->right) - height(p->left); //параметр баланса основанный на различии высоты
}

void binaryTree::fixheight(binaryTree* p) //восстановление высоты
{
    unsigned char hl = height(p->left);
    unsigned char hr = height(p->right);
    p->height1 = ((hl > hr) ? hl : hr) + 1;
}

binaryTree* binaryTree::rotateright(binaryTree* p) // правый поворот
{
    binaryTree* q = p->left;
    p->left = q->right;
    q->right = p;
    fixheight(p);
    fixheight(q);
    return q;
}

binaryTree* binaryTree::rotateleft(binaryTree* q) // левый поворот
{
    binaryTree* p = q->right;
    q->right = p->left;
    p->left = q;
    fixheight(q);
    fixheight(p);
    return p;
}

binaryTree* binaryTree::balance(binaryTree* p) // балансировка узла
{
    fixheight(p);
    if( bfactor(p) == 2)
    {
        if( bfactor(p->right) < 0 )
            p->right = rotateright(p->right);
        return rotateleft(p);
    }
    if( bfactor(p) == -2)
    {
        if( bfactor(p->left) > 0  )
            p->left = rotateleft(p->left);
        return rotateright(p);
    }
    return p;
}

binaryTree* binaryTree::insert(binaryTree* p, int k) // вставка ключа k в дерево с корнем p
{
    if( !p ) return new binaryTree(k);
    if( k<p->key )
        p->left = insert(p->left,k);
    else
        p->right = insert(p->right,k);
    return balance(p);
}

binaryTree* binaryTree::findmin(binaryTree* p) // поиск узла с минимальным ключом в дереве p
{
    return p->left?findmin(p->left):p;
}

binaryTree* binaryTree::removemin(binaryTree* p) // удаление узла с минимальным ключом из дерева p
{
    if( p->left==0 )
        return p->right;
    p->left = removemin(p->left);
    return balance(p);
}

binaryTree* binaryTree::remove(binaryTree* p, int k) // удаление ключа k из дерева p
{
    if( !p ) return 0;
    if( k < p->key )
        p->left = remove(p->left,k);
    else if( k > p->key )
        p->right = remove(p->right,k);
    else
    {
        binaryTree* q = p->left;
        binaryTree* r = p->right;
        delete p;
        if(!r)
            return q;
        binaryTree* min = findmin(r);
        min->right = removemin(r);
        min->left = q;
        return balance(min);
    }
    return balance(p);
}

void binaryTree::inorder(binaryTree *root, int x, int y, int replace)
{
    static constexpr int startReplace = 350;
    //root = root->balance(root);
    if(!root)
    {
        return;
    }
    www->setLabel(x, y, QString::number(root->key)); //установка лейбла значения
    inorder(root->left, x - startReplace + replace, y + 30, (replace == 0 ? startReplace / 2 : replace * 1.5));
    inorder(root->right, x + startReplace - replace, y + 30, (replace == 0 ? startReplace / 2 : replace * 1.5));
}

//////графика
Widget::Widget(QWidget *parent)
    : QWidget(parent)
{
    resize(1400, 800);
    QPushButton *button = new QPushButton("2222" ,this);
    connect(button, SIGNAL(pressed()), this, SLOT(paint()));
    www = this;
    new binaryTree{6, 2, 7, 14,100, 66, 44, 57, 446};

}

Widget::~Widget()
{
}



void Widget::setLabel(int x, int y, QString str)
{
    QLabel* label = new QLabel(str, this);
    label->setGeometry(x, y, 50, 20);
}



