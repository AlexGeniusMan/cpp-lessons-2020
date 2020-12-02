import java.util.*;
import java.lang.*;

class AOD_13
{
    // Класс для представления ребра графа
    class Edge implements Comparable<Edge>
    {
        int src, dest, weight;
        // Функция компаратора, используемая для сортировки ребер в зависимости от их веса
        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    };
    // Класс для представления подмножества для union-find
    class subset
    {
        int parent, rank;
    };
    int V, E;    // V-> нет. вершин и E-> количество ребер
    Edge edge[]; // коллекция всех ребер
    // Создаем граф с V вершинами и E ребрами
    AOD_13(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
    // Функция для поиска множества элементов i (использует технику сжатия пути)
    int find(subset subsets[], int i)
    {
        // найти root и сделать root родителем i (сжатие пути)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }
    // Функция, которая объединяет два набора x и y
    // (использует объединение по рангу)
    void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        // Прикрепить дерево меньшего ранга под корень дерева высокого ранга
        // (Объединение по рангу)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
            // Если ранги одинаковы, сделать из них корень и увеличить
            // его ранг на единицу
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
    // Основная функция для построения MST с использованием алгоритма Крускала
    void KruskalMST()
    {
        Edge result[] = new Edge[V];  // Tnis будет хранить результирующий MST
        int e = 0;  // Индексная переменная, используемая для результата []
        int i = 0;  // Индексная переменная, используемая для отсортированных ребер
        for (i=0; i<V; ++i)
            result[i] = new Edge();
        // Шаг 1: Сортировка всех ребер в неубывающем порядке их
        // вес. Если нам не разрешено изменять данный график, мы
        // можем создать копию массива ребер
        Arrays.sort(edge);
        // Выделить память для создания V ssubsets
        subset subsets[] = new subset[V];
        for(i=0; i<V; ++i)
            subsets[i]=new subset();
        // Создать V подмножеств с отдельными элементами
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0;  // Индекс, используемый для выбора следующего ребра
        // Количество ребер, которые нужно взять, равно V-1
        while (e < V - 1)
        {
            // Шаг 2: Выберите наименьшее ребро. И прирост
            // индекс для следующей итерации
            Edge next_edge = new Edge();
            next_edge = edge[i++];
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
            // Если включение этого ребра не вызывает цикл,
            // включить его в результат и увеличить индекс
            // результата для следующего ребра
            if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Остальное сбрасываем next_edge
        }
        // выводим содержимое результата [] для отображения
        // построенный MST
        System.out.println("Following are the edges in " + "the constructed MST");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- " +
                    result[i].dest+" == " + result[i].weight);
    }

    //14.1.1
    public static void main (String[] args)
    {
        /* создадим следующий взвешенный граф
            10
        0 -------- 1
        | / |
        6 | 5 / | 15
          | / |
        2 -------- 3
            4 */
        int V = 4;  // Количество вершин в графе
        int E = 5;  // Количество ребер в графе
        AOD_13 AOD13 = new AOD_13(V, E);
        // добавляем ребро 0-1
        AOD13.edge[0].src = 0;
        AOD13.edge[0].dest = 1;
        AOD13.edge[0].weight = 10;
        // добавляем ребро 0-2
        AOD13.edge[1].src = 0;
        AOD13.edge[1].dest = 2;
        AOD13.edge[1].weight = 6;
        // добавляем ребро 0-3
        AOD13.edge[2].src = 0;
        AOD13.edge[2].dest = 3;
        AOD13.edge[2].weight = 5;
        // добавить ребро 1-3
        AOD13.edge[3].src = 1;
        AOD13.edge[3].dest = 3;
        AOD13.edge[3].weight = 15;
        // добавить ребро 2-3
        AOD13.edge[4].src = 2;
        AOD13.edge[4].dest = 3;
        AOD13.edge[4].weight = 4;
        AOD13.KruskalMST();
    }
}

