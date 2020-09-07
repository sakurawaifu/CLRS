package part4.Chapter15;

/**
 * 切成左右两段，左段不再切割，递归地求右段(规模更小的子问题)的最有切割方案
 * r[n]=max(p[i]+r[n-i]) 1<=i<=n
 */
public class CutRod {
    public static void main(String[] args) {
        int[] p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        // 调用一次bottomUp其实可求解出1~n的所有问题，但方法只返回n的最优解，因此多次调用。
        // 若确实要打印所有问题的最优解，可以进行优化
        for (int i = 1; i < p.length; i++) {
            int[] s = bottomUp(p, i);
            printSolution(s, i);
            System.out.println();
        }
    }

    /*
     * 朴素递归法
     * p为价格数组，保存了1~n英寸钢条的价格。n为要求的钢条长度。n不会大于价格表p的最大钢条长度
     * 返回n钢条的最大收益
     * 会反复求解相同的子问题。T(n)=1+∑T(i),i=0,1,...,n-1。解得T(n)=Θ(2^n)
     */
    public static int naive(int[] p, int n) {
        int max = 0;
        for (int i = 1; i <= n; i++)
            max = Math.max(max, p[i] + naive(p, n - i));
        return max;
    }

    // 带备忘的自顶向下法的外部接口，不是具体实现
    public static int topDown(int[] p, int n) {
        int[] r = new int[n + 1];
        return topDownAux(p, r, n);
    }

    /*
     * 具体实现
     * 用德摩根定律化简出未求解子问题的逻辑表达式
     */
    private static int topDownAux(int[] p, int[] r, int n) {
        if (n > 0 && r[n] == 0) {
            for (int i = 1; i <= n; i++) {
                r[n] = Math.max(r[n], p[i] + topDownAux(p, r, n - i));
            }
        }
        return r[n];
    }

    // 自底向上法
    // 不仅求出最优解的值，还在求解过程中保存了重构最优解本身所需的信息
    public static int[] bottomUp(int[] p, int n) {
        int[] r = new int[n + 1];
        int[] s = new int[n + 1];// s[i]保存i英寸钢条最优切割方案的第一段的长度(i>=1)

        int temp;
        for (int i = 1; i <= n; i++) {// 按规模的自然顺序，依次求子问题r[i]，直至r[n]
            for (int j = 1; j <= i; j++) {// 具体的求解某个子问题r[i]的方法
                temp = p[j] + r[i - j];
                if (r[i] < temp) {
                    r[i] = temp;
                    s[i] = j;// 第一段的长度为j
                }
            }
        }
        s[0] = r[n];// 之后打印最优解本身时不会用到s[0]，因此直接保存n英寸钢条最优收益。
        return s;
    }

    public static void printSolution(int[] s, int n) {
        System.out.printf("%d英寸钢条最大收益：%d\n", n, s[0]);
        System.out.print("切割方案：");
        // 因为求解时1<=i<=n，故钢条长度大于0时第一段长度一定大于0。
        // 打印第一段的长度，之后打印第二段的第一段长度(递归已转换成循环)。长度为0时打印完毕
        while (n > 0) {
            System.out.print(s[n] + " ");// 只在n>0时访问s[n]，因此不会用到s[0]
            n -= s[n];// n变为第二段的长度
        }
    }
}
