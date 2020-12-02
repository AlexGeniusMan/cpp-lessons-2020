#include <iostream>
#include "PriorityQueue.h"

struct order {
    int employeeNumber;
    int employeePriority;
    int time;

};

int main() {
    PriorityQueue<order> queue;

    queue.Push(order{13, 2, 9}, 2);
    queue.Push(order{10, 1, 30}, 1);
    queue.Push(order{9, 3, 28}, 3);
    queue.Push(order{11, 2, 12}, 2);
    queue.Push(order{14, 3, 19}, 3);
    queue.Push(order{18, 1, 2}, 1);
    queue.Push(order{13, 2, 81}, 2);
    queue.Push(order{16, 2, 95}, 2);
    
    int answer = 0;
    while (queue.Count() != 0) {
        order temp = queue.Pop();
        std::cout << temp.employeeNumber << " " << temp.employeePriority << " " << temp.time << "\n";
        answer += temp.time;
    }

    std::cout << "All time: " << answer << "\n";


    return 0;
}
