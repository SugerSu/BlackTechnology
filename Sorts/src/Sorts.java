import java.util.Arrays;
import java.util.Random;

public  class Sorts {
//---------------------------------------------------------------------------------------------------------------------	 
//Sort Methods

	public void shellSort(int[] nums) {
		int n = nums.length;

		int gap = 0;

		while (gap < n / 3)
			gap = 3 * gap + 1;

		while (gap > 0) {
			for (int i = gap; i < n; i += gap) {

				if (less(nums[i],nums[i-gap])) {
					int j = i - gap;
					int temp = nums[i];

					while (j >= 0 && less(temp,nums[j])) {
						nums[j + gap] = nums[j];
						j -= gap;
					}
					nums[j + gap] = temp;
				}
			}
			gap = gap / 3;
		}


	}

	public void directInsertSort(int[] nums) {

		for (int i = 1; i < nums.length; i++) {

			if (nums[i] < nums[i - 1]) {

				int j = i - 1;

				int temp = nums[i];

				while (j >= 0 && nums[j] > temp) {
					nums[j + 1] = nums[j];
					j--;
				}

				nums[j + 1] = temp;

			}
		}
		

	}

	public void binaryInsertSort(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < nums[i - 1]) {

				int lo = 0;
				int hi = i - 1;
				int target = nums[i];
				while (lo <= hi) {
					int mid = lo + (hi - lo) / 2;
					if (target > nums[mid]) {
						lo = mid + 1;
					} else if (target < nums[mid]) {
						hi = mid - 1;
					} else {
						lo = mid + 1;
					}
				}

				int j = i - 1;
				while (j >= lo && nums[j] > target) {
					nums[j + 1] = nums[j];
					j--;
				}
				nums[j + 1] = target;

			}

		}
	}

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

	public void twoWayQuickSort(int[] nums, int lo, int hi) {

		if (lo >= hi) {
			return;
		}
		int partIndex = twoWaypartitionWithRandomPivot(nums, lo, hi);

		twoWayQuickSort(nums, lo, partIndex - 1);
		twoWayQuickSort(nums, partIndex + 1, hi);

	}

	public void dijkstraThreeWayQuickSort(int[] num, int lo, int hi) {
		if (lo >= hi) {
			return;
		}

		int[] partition = threeWayDijkstraPartition(num, lo, hi);
		dijkstraThreeWayQuickSort(num, lo, partition[0] - 1);
		dijkstraThreeWayQuickSort(num, partition[1] + 1, hi);

	}

	public void bentlyMclloryQucikSort(int[] nums, int lo, int hi) {
		if (lo >= hi)
			return;

		int[] part = threeWayBentleyMclloryPartition(nums, lo, hi);

		bentlyMclloryQucikSort(nums, lo, part[0]);

		bentlyMclloryQucikSort(nums, part[1], hi);

	}

	public void dualPivotQucikSort(int[] nums, int lo, int hi) {
		if (hi <= lo)
			return;

		int[] partion = dualPivotPartition(nums, lo, hi);

		dualPivotQucikSort(nums, lo, partion[0] - 1);
		dualPivotQucikSort(nums, partion[0] + 1, partion[1] - 1);
		dualPivotQucikSort(nums, partion[1] + 1, hi);

	}

	public void dualPivotQucikSortWithRandomPivots(int[] nums, int lo, int hi) {
		if (hi <= lo)
			return;

		int[] partion = dualPivotPartitionWithRandomPivot(nums, lo, hi);
		dualPivotQucikSortWithRandomPivots(nums, lo, partion[0] - 1);
		dualPivotQucikSortWithRandomPivots(nums, partion[0] + 1, partion[1] - 1);
		dualPivotQucikSortWithRandomPivots(nums, partion[1] + 1, hi);

	}

	/* Need extra places
	 * 6NlgN array accecss(2n copy,2n compare,2N write into the result array)
	 * 
	 * time: O(NlgN). T(n)=2T(n/2)+O(n)=> T(n)=nT(1)lgn+nlgn=>n(T(1)+lgn)=>nlgn
	 * becasue not matter what kinds of initial inputs, this sort will involke divide and merge methods
	 * thus, all the codition is same for merge sort, so there does not exist best condition or worst condition
	 * 
	 * compares between 1/2NlgN and NlgN
	 * 
	 * Divide the array in two half array,Unitl the sub array's length is one
	 * Merger and sort two nearby array until the whole array is ordered
	 * */
	public int[] mergeSort(int[] nums) {
		if(nums.length<=1) return nums;
		
		int mid=nums.length/2;
		int[] numsA=Arrays.copyOfRange(nums,0,mid);
		int[] numsB=Arrays.copyOfRange(nums,mid,nums.length);
		
		return merge(mergeSort(numsA),mergeSort(numsB));
		
	}

	public int[] merge(int[] numsA,int[] numsB) {
		int[] nums=new int[numsA.length+numsB.length];
		int a=0;
		int b=0;
		
		for(int i=0;i<nums.length;i++) {
			if(a>=numsA.length) nums[i]=numsB[b++];
			else if(b>=numsB.length) nums[i]=numsA[a++];
			else if(less(numsB[b],numsA[a])) nums[i]=numsB[b++];
			else nums[i]=numsA[a++];
		}
		return nums;
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
	
	/* 
	 * Best(in order): N-1 compares,0 swaps
	 * worst(in reverse order): ~N*N compares, ~N*N/2 swaps
	 * Average: ~N*N/4 compares and swaps
	 * Inplace
	 * 
	 * Insertion sort is very fast in partionally ordered array and short array, 
	 * that why we have shell sort
	 *
	 * From the index of 1; 
	 * If the nums[i]<nums[i-1]
	 * Find the correct position and move to it.
	 * 
	 * Use binary search to find the position is binaryInsertSort.
	 * Use copy value in stead of swap is called half-exchange.
	 * */	
	public void insertionSort(int[] nums) {
		if(nums.length<=1) return ;
		for(int i=1;i<nums.length;i++) {
			int j=i;
			while( j>=1 && less(nums[j],nums[j-1])) {
				swap(nums,j,j-1);
				j--;
			}
		}
	}

//---------------------------------------------------------------------------------------------------------------------	 
//Quick Sort Partition Methods
	
	/*
	 * while{ Scan from left to right as long as nums[++left]<pivot. if(left==hi)
	 * break; Scan from right to left as long as nums[--right]>pivot. if(right==lo)
	 * break; if left>=right corss break; else swap (nums,left,right) }
	 * swap(nums,pivotIndex,right); return right;
	 */
	public static int twoWayPartition(int[] nums, int lo, int hi) {
		// select the first element as the pivot
		int pivot = nums[lo];
		int i = lo;
		int j = hi + 1;
		while (true) {

			while (nums[--j] > pivot)
				if (j == lo)
					break;
			while (nums[++i] < pivot)
				if (i == hi)
					break;

			if (i >= j)
				break;

			// swap i j
			swap(nums, i, j);

		}
		swap(nums, lo, j);
		return j;
	}

	/*
	 * https://algs4.cs.princeton.edu/23quicksort/QuickBentleyMcIlroy.java.html
	 * phase 1: base on the 2wayPartition, and use p,q pointers, between [lo,p) and
	 * (q,hi) are pivots while(true){ while(nums[++i]<pivot) if(i==hi)break;
	 * while(nums[--J]>pivot) if(j==lo)break; if(i==j && nums[i]==pivot)
	 * swap(nums,p,i++); if(i>=j) break; swap(nums,i,j) if(nums[i]== pivot)
	 * swap(nums,i,p++); if(nums[j]==pivot) swap(nums,j,q--); } phase 2: move [lo,p)
	 * and (q,hi] to the center while(p>=lo) swap(nums,p--,j--); while(q<=hi)
	 * swap(nums,q++,i++);
	 *
	 * return [j,i]
	 **/
	public static int[] threeWayBentleyMclloryPartition(int[] nums, int lo, int hi) {

		int i = lo;
		int p = lo;
		int j = hi + 1;
		int q = hi + 1;
		int pivotIndex = randomPivot(lo, hi);
		int pivot = nums[pivotIndex];
		swap(nums, lo, pivotIndex);

		// phase 1
		while (true) {
			while (nums[++i] < pivot)
				if (i == hi)
					break;
			while (nums[--j] > pivot)
				if (j == lo)
					break;

			if (i == j && nums[i] == pivot)
				swap(nums, i, ++p);

			if (i >= j)
				break;

			swap(nums, i, j);

			if (nums[i] == pivot)
				swap(nums, i, ++p);
			if (nums[j] == pivot)
				swap(nums, j, --q);
		}
		// phase 2
		// swap p left to the center
		while (p >= lo)
			swap(nums, p--, j--);
		while (q <= hi)
			swap(nums, q++, i++);

		return new int[] { j, i };
	}

	/*
	 * Target: divide the array to three part[lo,pivot][pivot,pivot][pivot,hi]
	 * while(i<=gt){ if(nums[i]<pivot) swap(nums,lt++,i++); else if(nums[i]>pivot)
	 * while(gt-->pivot) if(gt>i){swap(nums,gt--,i)}; else i++; }
	 * 
	 */
	public static int[] threeWayDijkstraPartition(int[] nums, int lo, int hi) {
		int lt = lo;
		int i = lo;
		int gt = hi;
		int pivotIndex = randomPivot(lo, hi);
		int pivot = nums[pivotIndex];

		// put the pivot at first index
		swap(nums, lo, pivotIndex);

		while (i <= gt) {
			// larger than pivot exchange nums[i] with nums[gt]; gt--;
			if (nums[i] > pivot) {
				// skip all the larger element
				while (nums[gt] > pivot) {
					gt--;
				}
				// if gt==i means already ordered!
				if (gt > i) {
					swap(nums, i, gt);
				}

			}
			// smaller than pivot exchange nums[i] with nums[lt];lt++;i++;
			else if (nums[i] < pivot) {
				swap(nums, i, lt);
				lt++;
				i++;
			}
			// equals with pivot increment i;
			else if (nums[i] == pivot) {
				i++;
			}
		}

		return new int[] { lt, gt };
	}

	/*
	 * select two pivot put the samller one on the left put the larger one on the
	 * right; the element smaller than lp on the left of lp the element larger than
	 * rp on the right of rp scanner i start from (lo+1=lt) - (hi-1=gt), because lo
	 * is lp, hi is rp phase 1: find the patition of [lo,lp),[lp,rp],(rp,hi]
	 * while(i<=gt){ if(nums[i]<lt) swap(nums,lt++,i++); else if(nums[i]>gt)
	 * while(i<gt && nums[gt]>rp) gt--; swap(nums,gt--,i); else i++; } phase 2: move
	 * lp,rp to correct position if(lt!=lo) swap(nums,--lt,lo); if(gt!=hi)
	 * swap(nums,++gt,hi);
	 */
	public static int[] dualPivotPartition(int[] nums, int lo, int hi) {
		int lt = lo + 1;
		int i = lo + 1;
		int gt = hi - 1;

		int largePivot = nums[hi];
		int smallPivot = nums[lo];
		// exchange the pivot ensure left pivot smaller than or equals than right pivot
		if (smallPivot > largePivot) {
			swap(nums, hi, lo);
			int temp = largePivot;
			largePivot = smallPivot;
			smallPivot = temp;
		}

		// scan from left to right
		while (i <= gt) {
			if (nums[i] < smallPivot)
				swap(nums, lt++, i++);
			else if (nums[i] > largePivot) {
				while (i < gt && nums[gt] > largePivot)
					gt--;
				swap(nums, gt--, i);
			} else
				i++;
		}

		if (lt > lo)
			swap(nums, --lt, lo);
		if (gt < hi)
			swap(nums, ++gt, hi);

		return new int[] { lt, gt };
	}

	public static int[] dualPivotPartitionWithRandomPivot(int[] nums, int lo, int hi) {
		int lt = lo + 1;
		int i = lo + 1;
		int gt = hi - 1;

		// select two pivot randomly also need to make sure they are not same
		// left pivot index
		int lpIndex = randomPivot(lo, hi);
		// move to the left position
		swap(nums, lo, lpIndex);
		// ensure there are two different pivot
		// right pivot index
		int rpIndex = randomPivot(lo, hi, lo);
		// move to the right position
		swap(nums, hi, rpIndex);
		int lp = nums[lo];
		int rp = nums[hi];
		// exchange the pivot ensure left pivot smaller than or equals than right pivot
		if (lp > rp) {
			swap(nums, lo, hi);
			int temp = rp;
			rp = lp;
			lp = temp;

		}
		// scan from left to right
		while (i <= gt) {
			// smaller than left pivot
			if (nums[i] < lp)
				swap(nums, lt++, i++);
			// larger than right pivot
			else if (nums[i] > rp) {
				// jump the values have already larger than right pivot
				while (i < gt && nums[gt] > rp)
					gt--;
				swap(nums, gt--, i);
			} else
				i++;
		}

		if (lt > lo)
			swap(nums, --lt, lo);
		if (gt < hi)
			swap(nums, ++gt, hi);

		return new int[] { lt, gt };
	}

	public static int twoWaypartitionWithRandomPivot(int[] nums, int lo, int hi) {
		// select a random element as pivot
		int pivotIndex = randomPivot(lo, hi);
		int pivot = nums[pivotIndex];
		int i = lo;
		int j = hi + 1;
		// swap the pivot at the head of the array
		if (pivotIndex != lo) {
			swap(nums, pivotIndex, lo);
		}

		while (true) {

			while (nums[--j] > pivot)
				if (j == lo)
					break;
			while (nums[++i] < pivot)
				if (i == hi)
					break;

			if (i >= j)
				break;

			// swap i j
			swap(nums, i, j);

		}
		swap(nums, lo, j);

		return j;
	}

//---------------------------------------------------------------------------------------------------------------------	 
//Random Pivot 
	public static int randomPivot(int lo, int hi) {

		// int res=(int)Math.random()*(hi-lo+1)+lo;

		Random ran = new Random();

		int res = ran.nextInt(hi - lo + 1) + lo;
		// System.out.println("From: "+lo+" To:"+hi+" "+res);
		return res;
	}

	public static int randomPivot(int lo, int hi, int target) {

		// int res=(int)Math.random()*(hi-lo+1)+lo;

		Random ran = new Random();
		int res = ran.nextInt(hi - lo + 1) + lo;
		while (res == target) {
			res = ran.nextInt(hi - lo + 1) + lo;
		}

		// System.out.println("From: "+lo+" To:"+hi+" "+res);
		return res;
	}

//---------------------------------------------------------------------------------------------------------------------	 
//Swap and Check	 	 
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
