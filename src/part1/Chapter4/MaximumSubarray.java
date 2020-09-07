package part1.Chapter4;

import java.util.Arrays;

/*
 * 给定了同一支股票在一段日期内每天的价格，求收益最大的买入和卖出日期
 * 卖出日期必须在买入日期之后，因为同一天买入卖出收益必为0，没有意义
 * 整段日期内只能买卖各一次，不能多次买入卖出
 */
public class MaximumSubarray {
    public static void main(String[] args) {
        int[] prices = new int[]{100, 113, 110, 85, 105, 102, 86, 63, 81, 101, 94, 106, 101, 79, 94, 90, 97};
        int[] priceDiff = new int[prices.length - 1];

        for (int i = 0; i < prices.length - 1; i++) {
            priceDiff[i] = prices[i + 1] - prices[i];
        }
        System.out.println(Arrays.toString(findMaximumSubarray(priceDiff, 0, priceDiff.length - 1)));
    }

    /*
     * 暴力求解法1：求所有日期组合中，价格差最大的
     * 卖出日期必须在买入日期后
     * 组合角度：n天中任选不同的两天，共C(n,2)种
     * 数列角度：第1天买入，共n-1对组合。第2天买入，共n-2对，第n-1天，共1对。第n天不能买入
     * 均可得出总的组合数为n(n-1)/2。
     * 每种组合计算所需时间为Θ(1)。算法总的运行时间T(n)=Θ(n^2)
     */
    public static int[] bruteForce1(int[] prices) {
        if (prices.length < 2)
            return null;

        int priceDiff;
        int[] result = new int[3];//r[0]存放买入日期,r[1]存放卖出日期,r[2]存放收益
        result[2] = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length - 1; i++) {// 只能到n-2，n-1即最后一天，不能买入
            for (int j = i + 1; j < prices.length; j++) {
                priceDiff = prices[j] - prices[i];
                if (result[2] < priceDiff) {
                    result[2] = priceDiff;
                    result[0] = i;
                    result[1] = j;// 实际上对应的卖出日期应该+1，即j+1
                }
            }
        }
        return result;
    }

    /*
     * 暴力求解法2：求元素和最大的非空连续子数组
     * 因为Ay-Ax = (Ay - Ay-1) + (Ay-1 - Ay-2) + ... + (Ax+1 - Ax)
     * 故原问题可转换为最大子数组问题
     * n-1个元素(价格差),非空连续子数组共n-1+n-2+...+1=n(n-1)/2个
     * 使用dp思想，用已求得的子数组的和加上一个新的元素得到新子数组的和，这样每个子数组求解时间由Θ(n)降为Θ(1)
     */
    public static int[] bruteForce2(int[] priceDiff) {
        if (priceDiff.length < 1)
            return null;

        int sumOfCurSubarray;// 保存当前所求的子数组的元素和
        int[] result = new int[3];
        result[2] = Integer.MIN_VALUE;

        for (int i = 0; i < priceDiff.length; i++) {
            sumOfCurSubarray = 0;
            for (int j = i; j < priceDiff.length; j++) {
                sumOfCurSubarray += priceDiff[j];// 求解并保存当前子数组A[i..j]的元素和
                if (result[2] < sumOfCurSubarray) {
                    result[2] = sumOfCurSubarray;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    /*
     * 分治法：
     * base case:只有一个元素
     * recursive case:元素个数>=2
     * 求跨中点mid的最大子数组，形式与原问题不同，归入合并步骤
     * T(n)=2T(n/2)+Θ(n)，解得Θ(nlgn)
     */
    public static int[] findMaximumSubarray(int[] a, int low, int high) {
        if (low == high)
            return new int[]{low, high, a[low]};

        int mid = (low + high) >>> 1;
        int[] leftMaxSubarray = findMaximumSubarray(a, low, mid);
        int[] rightMaxSubarray = findMaximumSubarray(a, mid + 1, high);
        int[] crossMaxSubarray = findMaxCrossingSubarray(a, low, mid, high);

        int[] temp = leftMaxSubarray[2] >= rightMaxSubarray[2] ? leftMaxSubarray : rightMaxSubarray;
        return temp[2] > crossMaxSubarray[2] ? temp : crossMaxSubarray;
    }

    // 递归情况才调用，跨中点数组元素个数必然>=2，必然包含a[mid]和a[mid+1]
    // 可用反证法证明：
    // A[i..j]为A[low..high]的最大子数组当且仅当A[i..mid]和A[mid+1..j],分别为A[low..mid]和A[mid+1..high]的以mid为边界的最大子数组
    // 两个for循环总共循环了high - low + 1 = n次，每次迭代的时间=Θ(1)，因此算法总的T(n)=Θ(n)
    private static int[] findMaxCrossingSubarray(int[] a, int low, int mid, int high) {
        int leftIndex = mid, leftSum = Integer.MIN_VALUE;
        int rightIndex = mid + 1, rightSum = Integer.MIN_VALUE;

        int sum = 0;// 当前子数组的元素和
        for (int i = mid; i >= low; i--) {
            sum += a[i];
            if (leftSum < sum) {
                leftSum = sum;
                leftIndex = i;
            }
        }

        sum = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += a[i];
            if (rightSum < sum) {
                rightSum = sum;
                rightIndex = i;
            }
        }
        return new int[]{leftIndex, rightIndex, leftSum + rightSum};
    }

    public static int[] dp(int[] priceDiff) {
        return null;//TODO
    }
}
