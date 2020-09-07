package part1.Chapter2;

public class Sort {
    /*
     * 将a[j]插入已排序数组a[0..j-1]中
     *
     * 稳定
     * 最好情况：已按序排列，Θ(n)
     * 最坏情况：逆序排列，Θ(n^2)
     * 平均：每次比较已排序数组中的一半元素，仍为Θ(n^2)
     */
    public static void insertionSort(int[] a) {
        int key, j;

        for (int i = 1; i < a.length; i++) {
            key = a[i];

            j = i - 1;
            while (j >= 0 && a[j] > key) {// A[0..i-1]中大于key的均后移，直到第一个<=key的元素a[j]
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;// 插在a[j]之后
        }
    }

    /*
     * 选择剩余元素中的最小元素后交换
     *
     * 不稳定：5 2 5 1
     * 最好：序列已有序，不需要进行交换，次数为0
     * 最坏：均Θ(n^2)，进行n-1次交换
     * 不论最好最坏，均需要进行n(n-1)/2次比较。最多进行n-1次交换。T(n) = Θ(n^2)
     */
    public static void selectionSort(int[] a) {
        int temp, indexOfMin;// 交换所需的临时变量，第i小的元素的下标

        for (int i = 0; i < a.length - 1; i++) {// 进行n-1次迭代。之后a[n]必为最大值
            indexOfMin = i;
            for (int j = i + 1; j < a.length; j++) {// 从a[i+1]开始比较，找出a[i..n-1]的最小元素
                if (a[indexOfMin] > a[j])
                    indexOfMin = j;
            }

            if (indexOfMin != i) {// 仅a[i]不是最小元素时才交换
                temp = a[i];
                a[i] = a[indexOfMin];
                a[indexOfMin] = temp;
            }
        }
    }

    /*
     * 稳定
     *
     * 不论最好最坏均进行n(n-1)/2比较、交换
     * 比较次数和选择排序一样，不论好坏均是Θ(n^2)
     * 最好情况交换次数为0
     * 最坏情况每次外层迭代都进行n-i次交换，总共进行Θ(n^2)次交换
     * 故总的运行时间不论好坏均和选择排序相同，为Θ(n^2)。但最坏时交换次数比选择多
     */
    public static void bubbleSort(int[] a) {
        int temp;
        // n-1次外层迭代
        // 每次外层迭代，内层循环将待排序数组A[0..n-i-1]的最大元素移到A[n-i-1]的位置
        for (int i = 0; i < a.length - 1; i++) {
            // 内层循环的循环不变式：每次迭代前，A[0..j-1]的所有元素均<=A[j]
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    /*
     * 原数组等分成左、右子数组，递归排序它们，最后合并
     *
     * base case: n==1
     * recursive case: n>=2
     * T(n)=2T(n/2)+Θ(n)，解得T(n)=Θ(nlgn)
     */
    public static void mergeSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = (low + high) >>> 1;
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            merge(a, low, mid, high);
        }
    }

    // 递归情况：n>=2时才调用，故左右子数组元素个数必然均>=1。
    // 可证明low<=mid<high。且当且仅当low==high-1时mid==low
    // T(n)=Θ(n)
    private static void merge(int[] a, int low, int mid, int high) {
        int len1 = mid - low + 1;// 左子数组L长度(不包括哨兵)
        int len2 = high - mid;// 右子数组R长度(不包括哨兵)
        // 均额外申请一个空间作为哨兵
        int[] L = new int[len1 + 1];
        int[] R = new int[len2 + 1];
        // 哨兵的值均置为∞，用int型最大值代替
        L[len1] = Integer.MAX_VALUE;
        R[len2] = Integer.MAX_VALUE;

        System.arraycopy(a, low, L, 0, len1);
        System.arraycopy(a, mid + 1, R, 0, len2);

        int i = 0, j = 0;
        for (int k = low; k <= high; k++)// 使用哨兵后不需要每次都进行L、R的边界判断，只需直接迭代h-l+1次
            a[k] = L[i] <= R[j] ? L[i++] : R[j++];
    }
}
