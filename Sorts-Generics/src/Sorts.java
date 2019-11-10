import java.util.Arrays;
import java.util.Random;

public  class Sorts<E extends Comparable<E>> {
	private Helper hp;
//---------------------------------------------------------------------------------------------------------------------	 
//Sort Methods
	public Sorts() {
		hp= new Helper("Shell Sort");
	}


	/* hybrid Sort derived from insertion sort and merge sort
	 * 
	 * */
	public void timSort(int[] nums) {
			
	}

	/* ~n*n compares and n swamps, O(n*n), in-place
	 * Every time select the smallest/largest item from the unordered array,
	 * Put the item at the end of the ordered array
	 */	
	public void selectionSort(int [] nums) {
		for(int i=0;i<nums.length;i++) {
			int min=i;
			for(int j=i+1;j<nums.length;j++) {
				if(less(nums[j],nums[i])) 
					min=j;
			}
			swap(nums,i,min);
		}
	}
	

//---------------------------------------------------------------------------------------------------------------------	 
//Swap and Check
	public int binarySearch(int[] nums, int target, int lo, int hi) {

		while (lo <= hi) {

			int mid = lo + (hi - lo) / 2;

			if (target > nums[mid]) {
				lo = mid + 1;
			} else if (target < nums[mid]) {
				hi = mid - 1;
			} else {
				return mid;
			}

		}

		return -1;

	}

	public int binarySearchRecursive(int[] nums, int target, int lo, int hi) {
		if (lo > hi)
			return -1;

		int mid = lo + (hi - lo) / 2;

		if (target > nums[mid]) {
			binarySearchRecursive(nums, target, mid + 1, hi);
		} else if (target < nums[mid]) {
			binarySearchRecursive(nums, target, lo, mid - 1);
		}

		return mid;
	}

	public static void swap(int[] nums, int index1, int index2) {
		int temp = nums[index1];
		nums[index1] = nums[index2];
		nums[index2] = temp;
	}

	public boolean isSorted(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i + 1] < nums[i]) {
				return false;
			}
		}
		return true;
	}

	public static <T extends Comparable<T>> boolean less(T a, T b) {
		return a.compareTo(b) < 0 ? true : false;
	}

	public <T extends Comparable<T>> boolean isSortedT(T[] nums) {
		int i = 0;
		while (less(i, nums.length - 1)) {
			if (less(nums[i + 1], nums[i]))
				return false;
			i++;
		}
		return true;
	}
}
