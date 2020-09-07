package part1.Chapter2;

public class SortExtend {
    public static void mergeSort(int[] a) {
        int[] b = new int[a.length + 2];// 多用两个位置存放左右子数组的哨兵
        mergeSortAux(a, b, 0, a.length - 1);
    }

    // b为辅助数组，左右子数组L、R连同哨兵一起存放在b中。不用每次merge都重新创建左右子数组。
    public static void mergeSortAux(int[] a, int[] b, int low, int high) {
        if (low < high) {
            int mid = (low + high) >>> 1;
            mergeSortAux(a, b, low, mid);
            mergeSortAux(a, b, mid + 1, high);
            merge(a, b, low, mid, high);
        }
    }

    // 每次合并都使用b[0..high-low+2]，后面的不使用。通过len1、2来限制边界，不会读取到b后面的脏数据
    // 用a的元素覆盖b的前high-low+2个元素，覆盖完成后：
    // b[0..mid-low+1]为左子数组，其中b[mid-low+1]为哨兵
    // b[mid-low+2..high-low+2]为右子数组,其中b[high-low+2]为哨兵
    public static void merge(int[] a, int[] b, int low, int mid, int high) {
        int len1 = mid - low + 1;// 左子数组长度
        int len2 = high - mid;// 右子数组长度
        int i = 0, j = mid - low + 2;// 左右子数组在b中的起始下标

        System.arraycopy(a, low, b, i, len1);
        b[len1] = Integer.MAX_VALUE;
        System.arraycopy(a, mid + 1, b, j, len2);
        b[high - low + 2] = Integer.MAX_VALUE;

        for (int k = low; k <= high; k++)
            a[k] = b[i] <= b[j] ? b[i++] : b[j++];
    }
}
