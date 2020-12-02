#include<iostream>
#include <list>
#include <string>

struct typeDirection_5 //структура 5 типа (направление, универ)
{
    int typeNumber;
    std::string nameUniversity;
    friend bool operator==(const typeDirection_5& l_eq, const typeDirection_5& r_eq);
    friend std::ostream& operator<<(std::ostream& out, const typeDirection_5& outOb);
};

bool operator==(const typeDirection_5& l_eq, const typeDirection_5& r_eq)
{
    return (l_eq.typeNumber == r_eq.typeNumber) && (l_eq.nameUniversity == r_eq.nameUniversity);
}

std::ostream& operator<<(std::ostream& out, const typeDirection_5& outOb)
{
    out << "[" << outOb.typeNumber << ", " << outOb.nameUniversity << "]";
    return out;
}

class Hash
{
    // Количество ковшей
    int bucket;
    // Указатель на массив хранения
    std::list<typeDirection_5> *table;
public:
    Hash(int V);  // Конструктор
    // вставляем ключ в хеш-таблицу
    void insertItem(typeDirection_5 x);
    // удаляет ключ из хеш-таблицы
    void deleteItem(typeDirection_5 key);
    // хеш-функция для сопоставления значений с ключом
    int hashFunction(typeDirection_5 x);
    //функция вывода
    void displayHash();
};

Hash::Hash(int b)
{
    bucket = b;
    table = new std::list<typeDirection_5>[bucket];
}

void Hash::insertItem(typeDirection_5 key)
{
    //добавление элемента по ковшу
    int index = hashFunction(key);
    table[index].push_back(key);
}

void Hash::deleteItem(typeDirection_5 key)
{
    // получаем хеш-индекс ключа
    int index = hashFunction(key);
    // поиск ключа
    std::list<typeDirection_5>::iterator i;
    for (i = table[index].begin(); i != table[index].end(); ++i)
    {
        if (*i == key)
        break;
    }
  // если ключ найден в хеш-таблице, удалить его
    if (i != table[index].end())
        table[index].erase(i);
}

int Hash::hashFunction(typeDirection_5 x)
{
    return (x.typeNumber % bucket);
}


// функция для отображения значений хеш-таблицы

void Hash::displayHash() {

    for (int i = 0; i < bucket; i++)
    {
        std::cout << i;
        for (auto x : table[i])
            std::cout << " -> " << x;
        std::cout << "\n";
    }

}

int main()
{
    // массив, содержащий ключи для отображения
    auto allOb = {
        typeDirection_5{234523, "mirea"},
        typeDirection_5{242525, "mgu"},
        typeDirection_5{295226, "third"},
        typeDirection_5{582571, "__4__"},
        typeDirection_5{646521, "__5__"},
        typeDirection_5{788571, "__6__"},
        typeDirection_5{5, "__7__"}
    };
    // 7 - количество сегментов в хеш-таблице, произвольно
    Hash h(7);
    // вставляем ключи в хеш-таблицу
    for (auto c : allOb)
        h.insertItem(c);
    // удалить 4 элемент
    h.deleteItem(typeDirection_5{582571, "__4__"});
    // вывод значений
    h.displayHash();
    return 0;

}
