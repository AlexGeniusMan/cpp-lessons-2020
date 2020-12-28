package ru.alexgeniusman.java2020.lab_16;

public class Main {
    final static int n = 5;
    final static int m = 3;
    public static void main(String[] args) {
        double[] c = new double[n];
        double[][] a = new double[m][n];
        double[] cB = new double[m];
        int[] bV = new int[m];
        double[] bD = new double[m];

        c[0] = 1;
        c[1] = 0;
        c[2] = 0;
        c[3] = 0;
        c[4] = 0;

        a[0][0] = 1;
        a[0][1] = -2;
        a[0][2] = 1;
        a[0][3] = 0;
        a[0][4] = 0;
        a[1][0] = 1;
        a[1][1] = -1;
        a[1][2] = 0;
        a[1][3] = 1;
        a[0][4] = 0;
        a[2][0] = 1;
        a[2][1] = 1;
        a[1][2] = 0;
        a[1][3] = 0;
        a[0][4] = 1;


        cB[0] = 0;
        cB[1] = 0;
        cB[2] = 0;

        bV[0] = 2;
        bV[1] = 3;
        bV[2] = 4;

        bD[0] = 0;
        bD[1] = 1;
        bD[2] = 2;

        double[] min = simplexMin(c, a, cB, bV, bD);
        printArray(min);

        double[] max = simplexMax(c, a, cB, bV, bD);
        printArray(max);
    }

    static double[] printDecision(int[] bV, double[] bD) {
        int i, j;
        System.out.println("Все оценки неположительны, подсчет завершен.");
        System.out.print("Решение x = (");
        double[] res = new double[m];
        boolean f;
        for (j = 0; j < n; ++j) {
            f = false;
            for (i = 0; i < m; ++i) {
                if (bV[i] == j) {
                    if (j < m) {
                        res[j] = bD[i];
                    }
                    System.out.print(round(bD[i], 2));
                    f = true;
                    break;
                }
            }
            if (!f) {
                System.out.print("0");
            }
            if (j < n - 1) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        return res;
    }

    static void printDelta(double[] delta) {
        System.out.print("\t\t\t\t");
        for (int j = 0; j < n; ++j) {
            System.out.print(round(delta[j], 2) + "\t");
        }
        System.out.println("delta[j]");
    }

    static void printTable(double[] c, double[][] a, double[] cB, int[] bV, double[] bD, double[] min) {
        int i, j;
        System.out.print("\t\t\t\t");
        for (j = 0; j < n; ++j) {
            System.out.print(round(c[j], 2) + "\t");
        }
        System.out.println("C[j]");

        System.out.print("\tcB\tbV\tbD\t");
        for (j = 0; j < n; ++j) {
            System.out.print("x[" + j + "]\t");
        }
        System.out.println("bD[i] / a[i][r]");

        for (i = 0; i < m; ++i) {
            System.out.print("\t" + round(cB[i], 2) + "\tx[" + (bV[i] + 1) + "]\t"
                    + round(bD[i], 2) + "\t");
            for (j = 0; j < n; ++j) {
                System.out.print(round(a[i][j], 2) + "\t");
            }
            System.out.println(round(min[i], 2));
        }
    }

    static void printArray(double[] ar) {
        System.out.println();
        for (int i = 0; i < ar.length; ++i) {
            System.out.println("\t[" + i + "] = " + ar[i]);
        }
        System.out.println();
    }

    static String round(double n, int p) {
        if (Double.isNaN(n)) {
            return "NaN";
        }
        if (Double.isInfinite(n)) {
            return "\u221E";
        }
        double d = Math.pow(10, p);
        return (Math.round(n * d) / d) + "";
    }

    static double[] simplexMin(double[] c, double[][] a, double[] cB, int[] bV, double[] bD) {
        double[] delta = new double[n];
        double[] min = new double[m];

        System.out.println("\n\nПоиск минимума\n\n");
        int i, j, r = -1, s = -1;
        double[][] tmpA = new double[m][];
        double[] tmpBD = new double[m];

        int k = 0;
        while (true) {
            ++k;
            double deltaMin = Double.POSITIVE_INFINITY;
            r = -1;
            for (j = 0; j < n; ++j) {
                double z = 0;
                for (i = 0; i < m; ++i) {
                    z += cB[i] * a[i][j];
                }
                delta[j] = c[j] - z;
                if (deltaMin > delta[j]) {
                    deltaMin = delta[j];
                    r = j;
                }
            }

            if (deltaMin >= 0 || k > 100) {
                break;
            }

            double minRow = Double.POSITIVE_INFINITY;
            s = -1;
            for (i = 0; i < m; ++i) {
                min[i] = bD[i] / a[i][r];
                if (min[i] < 0) {
                    min[i] = Double.NaN;
                }
                if (minRow > min[i]) {
                    minRow = min[i];
                    s = i;
                }
            }

            System.out.println("Разрешающий столбец: r = " + r);
            System.out.println("Разрешающая строка:  s = " + s);
            printTable(c, a, cB, bV, bD, min);

            double element = a[s][r];

            for (i = 0; i < m; ++i) {
                tmpA[i] = new double[n];
                for (j = 0; j < n; ++j) {
                    tmpA[i][j] = a[i][j];
                }
                tmpBD[i] = bD[i];
            }

            bV[s] = r;
            cB[s] = c[r];
            for (j = 0; j < n; ++j) {
                a[s][j] /= element;
            }
            bD[s] /= element;

            for (i = 0; i < m; ++i) {
                if (i == s) {
                    continue;
                }
                double air = tmpA[i][r];

                for (j = 0; j < n; ++j) {
                    a[i][j] -= (air * tmpA[s][j]) / element;
                }
                bD[i] -= (air * tmpBD[s]) / element;
            }

            printDelta(delta);
            System.out.println("----------------------------------------------------");
        }

        System.out.println("Разрешающий столбец: r = " + (r + 1));
        System.out.println("Разрешающая строка:  s = " + (s + 1));
        printTable(c, a, cB, bV, bD, min);
        printDelta(delta);
        return printDecision(bV, bD);
    }

    static double[] simplexMax(double[] c, double[][] a, double[] cB, int[] bV, double[] bD)
    {
        double[] delta = new double[n];
        double[] min = new double[m];

        System.out.println("\n\nПоиск максимума\n\n");
        int i, j, r = -1, s = -1;
        double[][] tmpA = new double[m][];
        double[] tmpBD = new double[m];

        int k = 0;
        while (true) {
            ++k;
            double deltaMax = Double.NEGATIVE_INFINITY;
            r = -1;
            for (j = 0; j < n; ++j) {
                double z = 0;
                for (i = 0; i < m; ++i) {
                    z += cB[i] * a[i][j];
                }
                delta[j] = c[j] - z;
                if (deltaMax < delta[j]) {
                    deltaMax = delta[j];
                    r = j;
                }

                if (deltaMax <= 0 || k > 100) {
                    break;
                }
                double minRow = Double.POSITIVE_INFINITY;
                s = -1;
                for (i = 0; i < m; ++i) {
                    min[i] = bD[i] / a[i][r];
                    if (min[i] < 0) {
                        min[i] = Double.NaN;
                    }
                    if (minRow > min[i]) {
                        minRow = min[i];
                        s = i;
                    }
                }

                System.out.println("Разрешающий столбец: r = " + r);
                System.out.println("Разрешающая строка:  s = " + s);
                printTable(c, a, cB, bV, bD, min);

                double element = a[s][r];
                for (i = 0; i < m; ++i) {
                    tmpA[i] = new double[n];
                    for (j = 0; j < n; ++j) {
                        tmpA[i][j] = a[i][j];
                    }
                    tmpBD[i] = bD[i];
                }

                bV[s] = r;
                cB[s] = c[r];
                for (j = 0; j < n; ++j) {
                    a[s][j] /= element;
                }
                bD[s] /= element;

                for (i = 0; i < m; ++i) {
                    if (i == s) {
                        continue;
                    }
                    double air = tmpA[i][r];
                    for (j = 0; j < n; ++j) {
                        a[i][j] -= (air * tmpA[s][j]) / element;
                    }

                    bD[i] -= (air * tmpBD[s]) / element;
                }

                printDelta(delta);
                System.out.println("----------------------------------------------------");
            }

            System.out.println("Разрешающий столбец: r = " + (r + 1));
            System.out.println("Разрешающая строка:  s = " + (s + 1));
            printTable(c, a, cB, bV, bD, min);
            printDelta(delta);
            return printDecision(bV, bD);
        }
    }
}