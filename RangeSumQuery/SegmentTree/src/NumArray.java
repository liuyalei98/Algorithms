import java.awt.image.BufferedImage;

/**
 * 线段树
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */

public class NumArray {
    /**
     * 节点内部类
     * left 左孩子节点值
     * right 右孩子节点值
     * val 节点值,即为左右孩子节点的和
     */
    static class Node {
        int left;
        int right;
        int val;

        Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
    static int N = 30005;
    static Node[] tr = new Node[N * 4];
    int[] nums;

    /**
     * 构造函数
     * @param nums 用整数数组nums初始化对象
     */
    public NumArray(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        build(1, 1, n);
        for (int i = 0; i < n; ++i) {
            add(1, i + 1, nums[i]);
        }
    }

    /**
     * 将nums[index]的值更新为val
     * @param index 要更新的索引
     * @param val 更新的值
     */
    public void update(int index, int val) {
        add(1, index + 1, val - nums[index]);
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
        return query(1, left + 1, right + 1);
    }

    void build(int u, int left, int right) {
        if (tr[u] == null) {
            tr[u] = new Node(left, right);
        } else {
            tr[u].left = left;
            tr[u].right = right;
            tr[u].val = 0;
        }
        if (left == right) return;
        int mid = left + right >> 1;
        build(u << 1, left, mid);
        build(u << 1 | 1, mid + 1, right);
    }

    void add(int u, int x, int val) {
        if (tr[u].left == x && tr[u].right == x) {
            tr[u].val += val;
            return;
        }
        int mid = tr[u].left + tr[u].right >> 1;
        if (x <= mid) {
            add(u << 1, x, val);
        } else {
            add(u << 1 | 1, x, val);
        }
        pushUp(u);
    }

    void pushUp(int u) {
        tr[u].val = tr[u << 1].val + tr[u << 1 | 1].val;
    }

    int query(int u, int left, int right) {
        if (left <= tr[u].left && tr[u].right <= right) {
            return tr[u].val;
        }
        int mid = tr[u].left + tr[u].right >> 1;
        int ans = 0;
        if (left <= mid) {
            ans += query(u << 1, left, right);
        }
        if (right > mid){
            ans += query(u << 1 | 1, left, right);
        }
        return ans;
    }
}
