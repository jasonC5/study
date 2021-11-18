package com.jason.study.algorithmQuestions.class34;

public class LC384 {

    class Solution {
        int[] origin;
        int[] shuffle;
        int length;

        public Solution(int[] nums) {
            origin = nums;
            length = nums.length;
            shuffle = new int[length];
            for (int i = 0; i < length; i++) {
                shuffle[i] = nums[i];
            }
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            return origin;
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            for (int i = length - 1; i >= 0; i--) {
                int random = (int) (Math.random() * (i + 1));
                int tmp = shuffle[i];
                shuffle[i] = shuffle[random];
                shuffle[random] = tmp;
            }
            return shuffle;
        }
    }

}
