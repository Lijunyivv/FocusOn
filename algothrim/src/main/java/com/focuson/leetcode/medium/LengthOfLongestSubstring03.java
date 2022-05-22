package com.focuson.leetcode.medium;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lijunyi
 * @date 2022/5/14 1:33 下午
 * @Description TODO
 */
public class LengthOfLongestSubstring03 {

    public static void main(String[] args) {

        System.out.println(-2 >> 1);
        System.out.println(-2 >>> 1);
        int i = new LengthOfLongestSubstring03().lengthOfLongestSubstring("abba");
        System.out.println(i);
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     *  
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *  
     * <p>
     * 提示：
     * <p>
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * <p>
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int lengthOfLongestSubstring = 0;
        int currentLength = 0;
        int left = 0;
        int right = 0;
        HashMap<Character, Integer> container = new HashMap<Character, Integer>();
        char[] chars = s.toCharArray();
        while (right < s.length()) {
            Integer preValueIndex = container.put(chars[right], right);
            if (preValueIndex != null && preValueIndex >= left) {
                currentLength = right - left;
                lengthOfLongestSubstring = lengthOfLongestSubstring > currentLength ? lengthOfLongestSubstring : currentLength;
                left = preValueIndex + 1;
            }
            right++;
        }
        currentLength = right - left;
        lengthOfLongestSubstring = lengthOfLongestSubstring > currentLength ? lengthOfLongestSubstring : currentLength;
        ArrayList<Object> objects = new ArrayList<>();
        return lengthOfLongestSubstring;
    }

}
