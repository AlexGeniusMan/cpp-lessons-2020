#include <iostream>
#include <string>
#include <initializer_list>
#include <set>
#include <fstream>
#include <algorithm>


//структура пациента
struct Patient
{
    int32_t numberCard; // ключ
    int32_t numberDisorder; //код заболевания
    char doctorsName[50]; //фамилия врача

    void fread(std::ifstream& out);
    void fwrite(std::ofstream& in);
    friend bool operator<(const Patient& lv, const Patient& rv);
    friend bool operator==(const Patient& lv, const Patient& rv);
    friend std::ostream& operator<<(std::ostream& out, const Patient& outOb);
    friend std::istream& operator>>(std::istream& in, const Patient& inOb);

};

//перегрузки операторов для работы контейнеров
bool operator<(const Patient& lv, const Patient& rv)
{
    return lv.numberCard < rv.numberCard;
}
bool operator==(const Patient& lv, const Patient& rv)
{
    return lv.numberCard == rv.numberCard;
}

//перегрузка выводов
std::ostream& operator<<(std::ostream& out, const Patient& outOb)
{
    out << "Number Card, Number Disorder, Doctor's name\n" <<
           outOb.numberCard << "\n" <<
           outOb.numberDisorder << "\n" <<
           outOb.doctorsName << "\n";
    return out;
}

std::istream& operator>>(std::istream& in, Patient& inOb)
{
    in >> inOb.numberCard >> inOb.numberDisorder >> inOb.doctorsName;
    return in;
}

//чтение одного элемента из файла
void Patient::fread(std::ifstream &in)
{
    in.read((char*) &numberCard, sizeof(int));
    in.read((char*) &numberDisorder, sizeof(int));
    in.read((char*) &doctorsName, sizeof(char) * 50);
}
//запись одного элемента в файл
void Patient::fwrite(std::ofstream &out)
{
    out.write((char*) &numberCard, sizeof(int));
    out.write((char*) &numberDisorder, sizeof(int));
    out.write((char*) &doctorsName, sizeof(char) * 50);
}

//запись вводимой в консоль информации до ввода двух нолей
void writeFile(std::string place)
{
    std::ofstream file;
    file.open(place, std::fstream::out | std::fstream::binary);
    //номер карты - счетчик
    for (int i = 1; true; ++i)
    {
        Patient tempPatient;
        tempPatient.numberCard = i;
        std::cin >> tempPatient.numberDisorder;
        std::cin.ignore(32000, '\n');
        if (tempPatient.numberDisorder == 0) //условие окончания
            break;
        std::cin.getline(tempPatient.doctorsName, 50);
        tempPatient.fwrite(file);
    }
    file.close();

}

//вывод в консоль значений файла
void readAndOutFile(std::string workFile)
{
    std::ifstream readFile;
    readFile.open(workFile, std::fstream::in | std::fstream::binary);
    std::cout << "\nBEGIN READ\n";
    while (readFile)
    {
        //-1 необходимо что бы точно закончить чтение
        Patient temp{-1};
        temp.fread(readFile);
        if (temp.numberCard == -1)
            break;
        std::cout << temp;
    }
    std::cout << "END READ\n";
    readFile.close();
}



//запись в контейнеры из файла
template<class T>
void readFile(T& cont, std::string workFile)
{
    std::ifstream readFile;
    readFile.open(workFile, std::fstream::in | std::fstream::binary);
    while (readFile)
    {
        //-1 необходимо что бы точно закончить чтение
        Patient temp{-1};
        temp.fread(readFile);
        if (temp.numberCard == -1)
            break;
        //добавление элемента в контейнер
        cont.insert(temp);
    }
    readFile.close();
}


//запись в файл из контейнера
template<class T>
void writeFile(T& cont, std::string placeFile)
{
    std::ofstream outFile;
    outFile.open(placeFile, std::fstream::out | std::fstream::binary);
    //проход по всему контейнеру итератором
    for (auto c : cont)
    {
        c.fwrite(outFile);
    }
    outFile.close();
}
//полное удаление элемента
void deleteEl(std::string file, Patient delEl)
{
    std::cout << "del";
    std::set<Patient> cont;
    readFile(cont, file);
    cont.erase(delEl);
    writeFile(cont, file);
    std::cout << "\nDEL SET:\n";
    for (auto c : cont)
        std::cout << c;
    std::cout << "\nDEL SET END\n";
}
//изменение списка пациентов для изменения фамилий докторов
void reNameEl(std::string file, std::initializer_list<Patient> list)
{
    std::set<Patient> cont;
    readFile(cont, file);
    for (auto c : list)
    {
        //поиск пациента по карте
        auto t = find_if(cont.begin(), cont.end(), [c] (const Patient& el)
               {return  el == c;});
        //если найден - ввод нового имени и передобавление в контейнер
        if (t != cont.end())
        {
            Patient temp;
            temp.numberCard = t->numberCard;
            temp.numberDisorder = t->numberDisorder;
            std::cout << "write new doctor for patient : " << t->numberCard <<"\n";
            std::cin.getline(temp.doctorsName, 50);
            std::cout << "finder :\n" << temp;
            cont.erase(t);
            cont.insert(temp);
        }
    }
    writeFile(cont, file);
}

//проверочная программа
void testProgram()
{
    writeFile("test.txt"); //запись элементов в консоль БЕЗ номеров карт
    readAndOutFile("test.txt"); //чтение файла
    //переименновывание двух отправленых элементов
    reNameEl("test.txt", {Patient{2, 3141, "Enlightened"},
                         {Patient{4, 5322, "Father"}}});
    //удаление элемента
    deleteEl("test.txt", Patient{3, 4241, "Abbott"});
    deleteEl("test.txt", Patient{5, 4441, "Abel"});
    readAndOutFile("test.txt");

}
int main()
{
    testProgram();
    return 0;
}

//пример ввода
/*
1234
Aaron
3141
Enlightened
4241
Abbott
5322
Father
4441
Abel
4252
Breath
5252
Abner
5252
Father of Light
4145
Abraham
4252
Exalted Father
8362
Adam
5282
Man of Earth
4151
Addison
8363
Son of Adam
0
*/
