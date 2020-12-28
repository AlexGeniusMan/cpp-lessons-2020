#include <iostream> 
#include <string> 
#include "Windows.h" 
using namespace std; 
class HashClass { 
public: 
 int quantity = 10; 
 HashClass* Table; 
 HashClass* save; 
 HashClass* next = NULL; 
 int number = 0; 
 string fio; 
 string adress; 
 HashClass() { 
 } 
 HashClass(int number, string fio, string adress) { 
  this->number = number; 
  this->fio = fio; 
  this->adress = adress; 
 } 
 void addElem() { 
  cout << "Введите значение: "; 
  int a; 
  string b, c; 
  cin >> a >> b >> c; 
  HashClass* q = new HashClass(a, b, c); 
  int index = q->number%quantity; 
  if (Table[index].next == NULL) Table[index].next = q; 
  else saveHashes(q); 
 } 
 void saveHashes(HashClass* q) { 
  int j = quantity; 
  save = new HashClass[quantity]; 
  for (int i = 0; i < quantity; i++)  
   if (Table[i].next != NULL)  
    save[i].next = Table[i].next; 
  while (0);{ 
  next1: 
   quantity++; 
   Table = new HashClass[quantity]; 
   for (int i = 0; i < quantity; i++)  
    Table[i].next = NULL; 
   int k = 0; 
   int i = 0; 
   while (k < j) { 
    while (i < j) { 
     if (save[i].next == NULL) i++; 
     else break; 
    } 
    if (i >= j) break; 
    if (Table[save[i].next->number%quantity].next == NULL)  
     Table[save[i].next->number%quantity].next = save[i].next; 
    else { 
     i = -1; 
     goto next1; 
    } 
    i++; 
    k++; 
   } 
  qwer1: 
   if (Table[q->number%quantity].next == NULL) Table[q->number%quantity].next = q; 
   else if (Table[q->number%quantity].next != NULL) goto next1; 
   goto endThis; 
  } 
 endThis: 
  return; 
 } 
 void print() { 
  cout << "Хеш таблица: "<<endl; 
  for (int i = 0; i < quantity; i++)  
   if (Table[i].next != NULL)  
    cout << i << "  " << Table[i].next->number << " " << endl; 
  cout << endl; 
 } 
 void search() { 
  cout << "Введите информацию о читательском абонементе в формате Номер ФИО Адрес" << endl; 
  int number; 
  int checker = 0; 
  string fio; 
  string adress; 
  cin >> number; 
  cin >> fio; 
  cin >> adress; 
  for (int i = 0; i < quantity; i++) { 
   if (Table[i].next != NULL) { 
    if ((Table[i].next->number == number) && (Table[i].next->fio == fio) && (Table[i].next->adress == adress)) { 
     cout << i << " " << Table[i].next->number << " " << Table[i].next->fio << " " << Table[i].next->adress << endl; 
     checker = 1; 
    } 
   } 
  } 
  if (checker == 0) { 
   cout << "Элемент не найден" << endl; 
  } 
 } 
}; 
 
int main() { 
 setlocale(0, "Russian"); 
 SetConsoleCP(1251); 
 SetConsoleOutputCP(1251); 
 HashClass* m = new HashClass(); 
 m->Table = new HashClass[m->quantity]; 
 int ch, i = 0; 
 do { 
  cout << "\n Хеш таблица "; 
  cout << "\n 1. Вставить значение "; 
  cout << "\n 2. Вывести хеш таблицу "; 
  cout << "\n 3. Найти элемент "; 
  cout << "\n 4. Завершить выполнение программы "; 
  cout << "\n Выберите необходимый вариант: "; 
  cin >> ch; 
  switch (ch) 
  { 
  case 1: m->addElem(); 
   break; 
  case 2: m->print(); 
   break; 
  case 3: m->search(); 
   break; 
  case 4:  i = 1; 
   break; 
  } 
 } while (i != 1); 
 cout << endl; 
 return 1; 
} 
 
 