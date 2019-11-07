
public class HeapSots {
	
	public void HeapSortWithSinkConstructer(int[] nums) {

		int i = nums.length - 1;
		//construct heap with sink mathod
		//Sink Time is 1/2lgi => time < 0.5lgN;
		//Construct a hepp use Time:(0.5N(0.5lgi))=> time < 0.5NlgN
		constructMaxHeapWithSink(nums);
		//HeapSort with sink uses Time: N*(0.5N(0.5lgi)) => time< 0.5N*N(0.5lgN) => time < N*NlgN
		// is it Fast????????
		while (i > 1) {
			swap(nums, 1, i--);
			constructMaxHeapWithSink(nums, 1, i);
		}

	}
	
	public void HeapSortWithSwimConstructer(int[] nums) {
		int i=nums.length-1;
		//swim time lgi=> time < lgN
		//construct heap with swim: Nlgi=> time < NlgN
		constructMaxHeapWithSwim(nums);
		
		//HeapSort with swim uses time: ~N*NlgN <  HeapSort with sink
		while(i>1) {
			swap(nums,1,i--);
			constructMaxHeapWithSwim(nums,1,i);
		}
		
	}
//---------------------------------------------------------------------------------------------------------------------	 
// Swim And Sink
	//Time: O(lgi)
	public static void swim(int[] nums, int i) {
		while (i > 1 && less(nums[i / 2], nums[i])) {
			swap(nums, i, i / 2);
			i /= 2;

		}
	}
	//Time: O(Nlgi)<O(NlgN)
	public static void constructMaxHeapWithSwim(int[] nums) {
		for(int i=1;i<nums.length;i++) {
			swim(nums,i);
		}
	}
	public static void constructMaxHeapWithSwim(int[] nums,int start,int end) {
		if(start<1|| end<1) return;
		if(less(end,start)) return;
		
		for(int i=start;i<=end;i++) {
			swim(nums,i);
		}
	}
	
	public static void sinkA(int[] nums,int i) {
		int N = nums.length;
		while (i * 2 < N - 1) {
			if (less(nums[i], nums[2 * i]) || less(nums[i], nums[2 * i + 1])) {
				//one more compare than swim and this is why sink method uses 2N compares
				int j = less(nums[2 * i], nums[2 * i + 1]) ? 2 * i + 1 : 2 * i;
				swap(nums, i, j);
				i = j;
			}

		}
	}
	//Time: O(0.5lgi)
	public static void sink(int[] nums, int i) {
		while (i * 2 < nums.length) {
			int j = 2 * i;
			if (j < nums.length - 1 && less(nums[j], nums[j + 1]))
				j++;
			if (!less(nums[i], nums[j]))
				break;
			swap(nums, i, j);
			i = j;

		}
	}

	public static void sink(int[] nums, int i, int end) {
		while (i * 2 <= end) {
			int j = 2 * i;
			if (j < end && less(nums[j], nums[j + 1]))
				j++;
			if (!less(nums[i], nums[j]))
				break;
			swap(nums, i, j);
			i = j;
		}
	}
	//Time:(0.5N(0.5lgi))
	public static void constructMaxHeapWithSink(int[] nums) {
		int N = nums.length - 1;
		for (int i = N / 2; i >= 1; i--) {
			sink(nums, i);
		}
	}

	public static void constructMaxHeapWithSink(int[] nums, int start, int end) {
		if (start < 1 || end < 1)
			return;
		if (less(end, start))
			return;

		for (int i = end / 2; i >= start; i--) {
			sink(nums, i, end);
		}
	}

	//is this method right to check maxHeap?
	public boolean isMaxHeap(int[] nums) {
		for (int i = 1; i * 2 + 1 <= nums.length - 1; i++) {
			if (nums[i] < nums[2 * i] || nums[i] < nums[2 * i + 1]) {
				return false;
			}
		}
		return true;
	}

	//is this method right to check minHeap?
	public boolean isMinHeap(int[] nums) {
		for (int i = 1; i * 2 + 1 <= nums.length - 1; i++) {
			if (less(nums[2 * i], nums[i]) || less(nums[2 * i + 1], nums[i])) {
				return false;
			}
		}
		return true;
	}

// ---------------------------------------------------------------------------------------------------------------------
// Swap and Check
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
