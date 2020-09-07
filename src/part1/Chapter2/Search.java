package part1.Chapter2;

public class Search {
    public static int linearSearch(int[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key)
                return i;
        }
        return -1;
    }

    public static int binarySearch(int[] a, int key) {
        int low = 0, high = a.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) >>> 1;
            if (key == a[mid])
                return mid;
            else if (key < a[mid])
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -(low + 1);// 不存在时low是插入点，插在a[low]之前
    }
}
