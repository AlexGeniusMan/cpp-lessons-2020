#include <iostream>
#include <new>

using namespace std;

template<typename T>
class PriorityQueue {
private:
    T *mainStorage;
    int *priorityStorage;
    int count;

public:
    PriorityQueue()
            : count(0) {
    }

    PriorityQueue(const PriorityQueue &in_Queue) //copy constructor
    {
        //создание новых динамических массивов
        mainStorage = new T[in_Queue.count];
        priorityStorage = new int[in_Queue.count];

        count = in_Queue.count;

        for (int i = 0; i < count; i++)
            mainStorage[i] = in_Queue.mainStorage[i];

        for (int i = 0; i < count; i++)
            priorityStorage[i] = in_Queue.priorityStorage[i];
    }

    ~PriorityQueue() {
        //удаление дин массивов
        if (count > 0) {
            delete[] mainStorage;
            delete[] priorityStorage;
        }
    }

    PriorityQueue operator=(const PriorityQueue &in_Queue);

    void Push(T item, int priority);

    T Pop();

    int Count() {
        return count;
    }
};


//метод копирования
template<typename T>
PriorityQueue<T> PriorityQueue<T>::operator=(const PriorityQueue &in_Queue) {
    if (count > 0) {
        delete[] mainStorage;
        delete[] priorityStorage;
        count = 0;
    }

    mainStorage = new T[in_Queue.count];
    priorityStorage = new int[in_Queue.count];

    count = in_Queue.count;
    for (int i = 0; i < count; i++) {
        mainStorage[i] = in_Queue.mainStorage[i];
        priorityStorage[i] = in_Queue.priorityStorage[i];
    }
    return this;
}

//метод добавления
template<typename T>
void PriorityQueue<T>::Push(T item, int priority) {
    //создание новых дин массивов с большим размером
    T *mainStorage2;
    int *priorityStorage2;
    mainStorage2 = new T[count + 1];
    priorityStorage2 = new int[count + 1];
    int pos;

    if (count == 0)
        pos = 0;
    else {
        pos = 0;
        while (pos < count) {
            //сдвигать пока значение позиции не дойдет до нужной метки очереди
            if (priorityStorage[pos] < priority) break;
            pos++;
        }
    }
    //приравнение первой части до позиции
    for (int i = 0; i < pos; i++) {
        mainStorage2[i] = mainStorage[i];
        priorityStorage2[i] = priorityStorage[i];
    }
    //приравнение позиции
    mainStorage2[pos] = item;
    priorityStorage2[pos] = priority;
    //приравнение значений после позиции со сдвигом
    for (int i = pos + 1; i < count + 1; i++) {
        mainStorage2[i] = mainStorage[i - 1];
        priorityStorage2[i] = priorityStorage[i - 1];
    }
    //удаление "мусорных" старых массивов
    if (count > 0) {
        delete[] mainStorage;
        delete[] priorityStorage;
    }

    mainStorage = mainStorage2;
    priorityStorage = priorityStorage2;
    //увеличение обьема после добавления
    count++;
}

//метод удаления
template<typename T>
T PriorityQueue<T>::Pop() {
    if (count == 0)
        return T{};
    //создание новых, меньших массивов
    T *mainStorage2;
    int *priorityStorage2;
    mainStorage2 = new T[count - 1];
    priorityStorage2 = new int[count - 1];
    //возвращаемый обьект
    T item;
    item = mainStorage[0];
    //сдвиг значений массива очереди
    for (int i = 0; i < count - 1; i++) {
        mainStorage2[i] = mainStorage[i + 1];
        priorityStorage2[i] = priorityStorage[i + 1];
    }
    //удаление мосорных массивов
    if (count > 0) {
        delete[] mainStorage;
        delete[] priorityStorage;
    }
    //уменьшение размера после удаления одного элемента
    count--;

    mainStorage = mainStorage2;
    priorityStorage = priorityStorage2;
    //возврат элемента
    return item;
}