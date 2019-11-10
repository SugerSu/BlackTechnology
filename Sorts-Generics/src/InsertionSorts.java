
public class InsertionSorts<E extends Comparable<E>> {

	private Helper<E> hp;
	private int N;

	public InsertionSorts() {
		hp = new Helper<E>("Insertion Sort");
	}

	/*
	 * Best(in order): N-1 compares,0 swaps worst(in reverse order): ~N*N compares,
	 * ~N*N/2 swaps Average: ~N*N/4 compares and swaps Inplace
	 * 
	 * Insertion sort is very fast in partionally ordered array and short array,
	 * that why we have shell sort
	 *
	 * From the index of 1; If the nums[i]<nums[i-1] Find the correct position and
	 * move to it.
	 * 
	 * Use binary search to find the position is binaryInsertSort. Use copy value in
	 * stead of swap is called half-exchange.
	 */
	public void insertionSort(E[] nums) {
		N = nums.length;
		hp.setN(N - 1);
		if (N <= 1)
			return;
		for (int i = 1; i < N; i++) {
			int j = i;
			while (j >= 1 && hp.less(nums[j], nums[j - 1])) {
				hp.swap(nums, 0, N - 1, j, j - 1);
				j--;
			}
		}
		System.out.print(hp.toString());
	}

	public void directInsertSortWithHalfExchange(E[] nums) {
		N = nums.length;
		hp = new Helper<E>("Insertion Sort with Half Exchange");
		hp.setN(N - 1);
		for (int i = 1; i < N; i++) {

			if (hp.less(nums[i], nums[i - 1])) {

				int j = i - 1;

				E temp = nums[i];

				while (j >= 0 && hp.less(temp, nums[j])) {
					nums[j + 1] = nums[j];
					j--;
				}

				nums[j + 1] = temp;

			}
		}

		System.out.print(hp.toString());

	}

	public void binaryInsertSort(E[] nums) {
		N = nums.length;
		hp = new Helper<E>("Insertion Sort with Binary search and Half Exchange");
		hp.setN(N - 1);
		for (int i = 1; i < nums.length; i++) {
			if (hp.less(nums[i], nums[i - 1])) {

				int lo = 0;
				int hi = i - 1;
				E target = nums[i];
				while (lo <= hi) {
					int mid = lo + (hi - lo) / 2;
					if (hp.less(nums[mid], target)) {
						lo = mid + 1;
					} else if (hp.less(target, nums[mid])) {
						hi = mid - 1;
					} else {
						lo = mid + 1;
					}
				}

				int j = i - 1;
				while (j >= lo && hp.less(target, nums[j])) {
					nums[j + 1] = nums[j];
					j--;
				}
				nums[j + 1] = target;

			}

		}
		System.out.print(hp.toString());
	}

	/*
	 * Improve the insertion sort And use the advantage of the insertion
	 * sort(Partitionally ordered is really fast to sort)
	 */
	public void shellSort(E[] nums) {
		N = nums.length;
		hp = new Helper<E>("Shell Sort");
		hp.setN(N - 1);
		int gap = 0;
		while (gap < N / 3)
			gap = 3 * gap + 1;
		while (gap > 0) {
			for (int i = gap; i < N; i += gap) {
				if (hp.less(nums[i], nums[i - gap])) {
					int j = i - gap;
					E temp = nums[i];
					// Half Exchange
					while (j >= 0 && hp.less(temp, nums[j])) {
						nums[j + gap] = nums[j];
						j -= gap;
					}
					nums[j + gap] = temp;
				}
			}
			gap = gap / 3;
		}

	}

}
