package ru.alexgeniusman.java2020.lab_13;

import java.util.*;
import java.lang.*;

class Main
{
    class Edge implements Comparable<Edge>
    {
        int src, dest, weight;

        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    };
    class subset
    {
        int parent, rank;
    };
    int V, E;
    Edge[] edge;
    Main(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
    int find(subset[] subsets, int i)
    {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    void Union(subset[] subsets, int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
    void Function()
    {
        Edge[] result = new Edge[V];
        int e = 0;
        int i = 0;
        for (i=0; i<V; ++i)
            result[i] = new Edge();
        Arrays.sort(edge);
        subset[] subsets = new subset[V];
        for(i=0; i<V; ++i)
            subsets[i] = new subset();
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0;
        while (e < V - 1)
        {
            Edge next_edge = new Edge();
            next_edge = edge[i++];
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
            if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- " +
                    result[i].dest+" == " + result[i].weight);
    }
    public static void main (String[] args){
        int V = 4;
        int E = 5;
        Main obj = new Main(V, E);
        obj.edge[0].src = 0;
        obj.edge[0].dest = 1;
        obj.edge[0].weight = 10;

        obj.edge[1].src = 0;
        obj.edge[1].dest = 2;
        obj.edge[1].weight = 6;

        obj.edge[2].src = 0;
        obj.edge[2].dest = 3;
        obj.edge[2].weight = 5;

        obj.edge[3].src = 1;
        obj.edge[3].dest = 3;
        obj.edge[3].weight = 15;

        obj.edge[4].src = 2;
        obj.edge[4].dest = 3;
        obj.edge[4].weight = 4;
        obj.Function();
    }
}