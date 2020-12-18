import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    //    Вариант 3
//    Разработать процедуру решения задачи (на основе парадигмы жадного
//    программирования) о выборе подмножества взаимно совместимых
//    процессов, образующих множество максимального размера.
//     используется итерационный жадный алгоритм
    private ArrayList<Process> listOfProcesses = new ArrayList<>();

    public void addToList(int timeStart, int timeEnd) {
        listOfProcesses.add(new Process(timeStart, timeEnd));
    }

    public ArrayList<Process> getMaxProcesses() {
        //сортировка процессов по возрастанию по времени завершения
        Collections.sort(listOfProcesses);
        int n = listOfProcesses.size();
        ArrayList<Process> result = new ArrayList<>();
        result.add(listOfProcesses.get(0));
        int i = 0;
        for (int m = 1; m < n; m++) {
            if (listOfProcesses.get(m).getTimeOfBegin() >= listOfProcesses.get(i).getTimeOfEnd()) {
                result.add(listOfProcesses.get(m));
                i = m;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        /*Пример: заданы процессы [5,12], [0,1], [6,7], [8,9], [10,11], [13,17]
        [5,12],[0,1],[13,17] - было бы неверным решением задачи
        программа должна найти подмножество максимального размера, то есть:
        [0,1], [6,7], [8,9], [10,11], [13,17]*/
        Solution s = new Solution();
        s.addToList(5, 12);
        s.addToList(0, 1);
        s.addToList(6, 7);
        s.addToList(8, 9);
        s.addToList(10, 11);
        s.addToList(13, 17);
        System.out.println(s.getMaxProcesses());
    }
}