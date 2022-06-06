/**
 * 树状数组
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */


public class NumArray {
    int[] tree;
    int[] nums;
    int n;

    /**
     * 构造函数
     * @param nums 用整数数组nums初始化对象
     */
    public NumArray(int[] nums) {
        n = nums.length;
        this.nums = nums;
        tree = new int[n + 1];
        for (int i = 0; i < n; i++) {
            add(i + 1, nums[i]);
        }
    }

    /**
     * 将nums[index]的值更新为val
     * @param index 要更新的索引
     * @param val 更新的值
     */
    public void update(int index, int val) {
        add(index + 1, val - nums[index]);
        nums[index] = val;
    }

    /**
     * 返回数组nums中索引left和索引right之间(包含)的nums元素的和
     * (即，nums[left] + nums[left + 1], ..., nums[right])
     * @param left 最小索引
     * @param right 最大索引
     * @return 索引left和索引right之间(包含)的nums元素的和
     */
    public int sumRange(int left, int right) {
        return query(right + 1) - query(left);
    }

    int lowBit(int x) {
        return x & (-x);
    }

    void add(int x, int u) {
        for (int i = x; i <= n; i += lowBit(i)) {
            tree[i] += u;
        }
    }

    int query(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowBit(i)) {
            res += tree[i];
        }
        return res;
    }
}