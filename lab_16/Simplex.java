import java.util.ArrayList;

public class Simplex {
//    Вариант 2
//    Преобразуйте следующую задачу линейного программирования в
//    каноническую форму.
//    Максимизировать 2x1-6x3 при условиях:
//    1) x1 + x2 - x3 <= 7
//    2) 3x1 - x2 >= 8
//    3) -x1 + 2x2 + 2x3 >=0
//    4) x1, x2, x2 >=0
        private double[][] table; //симплекс таблица, которая задаётся в конструкторе
        private int m, n;
        private ArrayList<Integer> basis; //список базисных переменных

        public Simplex(double[][] source) {
            m = source.length;
            n = source[0].length;
            table = new double[m][n + m - 1];
            basis = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < table[0].length; j++) {
                    if (j < n)
                        table[i][j] =source[i][j];
                    else
                    table[i][j] = 0;
                }
                //выставляем коэффициент 1 перед базисной переменной в строке
                if ((n + i) < table[0].length) {
                    table[i][n + i] = 1;
                    basis.add(n + i);
                }
            }
            n = table[0].length;
        }
        //в массив result будут записаны полученные значения X
        double[][] calculate(double[] result) {
            int mainCol, mainRow; //ведущие столбец и строка

            while (!IsItEnd()) {
                mainCol = findMainCol();
                mainRow = findMainRow(mainCol);
                basis.set(mainRow, mainCol);

                double[][]new_table = new double[m][n];

                for (int j = 0; j < n; j++)
                    new_table[mainRow][j] =table[mainRow][j] /table[mainRow][mainCol];

                for (int i = 0; i < m; i++) {
                    if (i == mainRow)
                        continue;

                    for (int j = 0; j < n; j++)
                        new_table[i][j] = table[i][j] - table[i][mainCol] * new_table[mainRow][j];
                }
                table = new_table;
            }

            //заносим в result найденные значения X
            for (int i = 0; i < result.length; i++) {
                int k = basis.indexOf(i + 1);
                if (k != -1) result[i] = table[k][0];
                else result[i] = 0;
            }
            System.out.println("Базисные переменные X =  " + basis);
            return table;
        }

        private boolean IsItEnd() {
            boolean flag = true;
            for (int j = 1; j < n; j++) {
                if (table[m - 1][j] < 0)
                {
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        private int findMainCol() {
            int mainCol = 1;
            for (int j = 2; j < n; j++)
                if (table[m - 1][j] <table[m - 1][mainCol])
            mainCol = j;
            return mainCol;
        }

        private int findMainRow(int mainCol) {
            int mainRow = 0;
            for (int i = 0; i < m - 1; i++) {
                if (table[i][mainCol] > 0) {
                    mainRow = i;
                    break;
                }
            }

            for (int i = mainRow + 1; i < m - 1; i++){
                if ((table[i][mainCol] > 0) &&((table[i][0] / table[i][mainCol]) < (table[mainRow][0] /
                        table[mainRow][mainCol])))
                    mainRow = i;
            }
            return mainRow;
        }

    public static void main(String[] args) {
        /* задача преобразуется к таблице с коэффициентами, она должно быть в канонической форме
        последняя строка задаёт функцию, максимум которой нужно найти
        максимум данной функции при заданных условиях: x1 = 4*(2/3) x2 = 2*(1/3) x3 = 0*/
        double[][] table = { {7, 1,  1, -1},
                {8, -3,  1, 0},
                {0,  1,  -2, -2},
                {0,  -1, 0, 0},
                {0, 0, -1, 0},
                {0, 0, 0, -1},
                {0, -2, 0, 6}};


        double[] result = new double[3];
        double[][] table_result;
        Simplex S = new Simplex(table);
        table_result = S.calculate(result);

        System.out.println("Решенная симплекс-таблица:");
        for (double[] doubles : table_result) {
            for (int j = 0; j < table_result[0].length; j++)
                System.out.print(doubles[j] + " ");
            System.out.println();
        }
        System.out.println("x0 = " + result[0]);
        System.out.println("x1 = " + result[1]);
        System.out.println("x2 = " + result[2]);
    }
}