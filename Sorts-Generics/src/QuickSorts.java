import java.util.Arrays;
import java.util.Random;

public class QuickSorts<E extends Comparable<E>> {

	private Helper<E> hp;

	public QuickSorts() {
		hp = new Helper<E>("Quick Sort");
	}

	public boolean sorted(E[] nums) {
		return hp.sorted(nums);
	}
	public void twoWayQuickSort(E[] nums, int lo, int hi) {

		if (lo >= hi) {
			return;
		}
		int partIndex = twoWaypartitionWithRandomPivot(nums, lo, hi);

		twoWayQuickSort(nums, lo, partIndex - 1);
		twoWayQuickSort(nums, partIndex + 1, hi);

	}

	public int twoWaypartitionWithRandomPivot(E[] nums, int lo, int hi) {
		// select a random element as pivot
		int pivotIndex = randomPivot(lo, hi);
		E pivot = nums[pivotIndex];
		int i = lo;
		int j = hi + 1;
		// swap the pivot at the head of the array
		if (pivotIndex != lo) {
			hp.swap(nums, 0, nums.length, pivotIndex, lo);
		}

		while (true) {

			while (hp.less(pivot,nums[--j]))
				if (j == lo)
					break;
			while (hp.less(nums[++i],pivot))
				if (i == hi)
					break;

			if (i >= j)
				break;

			// swap i j
			hp.swap(nums, 0, nums.length, i, j);

		}
		hp.swap(nums, 0, nums.length, lo, j);

		return j;
	}

	/*
	 * while{ Scan from left to right as long as nums[++left]<pivot. if(left==hi)
	 * break; Scan from right to left as long as nums[--right]>pivot. if(right==lo)
	 * break; if left>=right corss break; else swap (nums,left,right) }
	 * swap(nums,pivotIndex,right); return right;
	 */
	public int twoWayPartition(E[] nums, int lo, int hi) {
		// select the first element as the pivot
		E pivot = nums[lo];
		int i = lo;
		int j = hi + 1;
		while (true) {

			while (hp.less(pivot, nums[--j]))
				if (j == lo)
					break;
			while (hp.less(nums[++i], pivot))
				if (i == hi)
					break;

			if (i >= j)
				break;

			// swap i j
			hp.swap(nums, 0, nums.length, i, j);

		}

		hp.swap(nums, 0, nums.length, lo, j);
		return j;
	}

//---------------------------------------------------------------------------------------------------------------------	 
	public void dijkstraThreeWayQuickSort(E[] num, int lo, int hi) {
		if (lo >= hi) {
			return;
		}

		int[] partition = threeWayDijkstraPartition(num, lo, hi);
		dijkstraThreeWayQuickSort(num, lo, partition[0] - 1);
		dijkstraThreeWayQuickSort(num, partition[1] + 1, hi);

	}

	/*
	 * Target: divide the array to three part[lo,pivot)[pivot,pivot](pivot,hi]
	 * while(i<=gt){ if(nums[i]<pivot) swap(nums,lt++,i++); else if(nums[i]>pivot)
	 * while(gt-->pivot) if(gt>i){swap(nums,gt--,i)}; else i++; }
	 * 
	 */
	public int[] threeWayDijkstraPartition(E[] nums, int lo, int hi) {
		int lt = lo;
		int i = lo;
		int gt = hi;
		int pivotIndex = randomPivot(lo, hi);
		E pivot = nums[pivotIndex];

		// put the pivot at first index
		hp.swap(nums, 0, nums.length, lo, pivotIndex);

		while (i <= gt) {
			// larger than pivot exchange nums[i] with nums[gt]; gt--;
			if (hp.less(pivot, nums[i])) {
				// skip all the larger element
				while (hp.less(pivot, nums[gt])) {
					gt--;
				}
				// if gt==i means already ordered!
				if (gt > i) {
					hp.swap(nums, 0, nums.length, i, gt);
				}

			}
			// smaller than pivot exchange nums[i] with nums[lt];lt++;i++;
			else if (hp.less(nums[i], pivot)) {
				hp.swap(nums, 0, nums.length, i, lt);
				lt++;
				i++;
			}
			// equals with pivot increment i;
			else{
				i++;
			}
		}

		return new int[] { lt, gt };
	}
//---------------------------------------------------------------------------------------------------------------------	 

	public void bentlyMclloryQucikSort(E[] nums, int lo, int hi) {
		if (lo >= hi)
			return;

		int[] part = threeWayBentleyMclloryPartition(nums, lo, hi);

		bentlyMclloryQucikSort(nums, lo, part[0]);

		bentlyMclloryQucikSort(nums, part[1], hi);

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
	public int[] threeWayBentleyMclloryPartition(E[] nums, int lo, int hi) {

		int i = lo;
		int p = lo;
		int j = hi + 1;
		int q = hi + 1;
		int pivotIndex = randomPivot(lo, hi);
		E pivot = nums[pivotIndex];
		hp.swap(nums, 0, nums.length, lo, pivotIndex);

		// phase 1
		while (true) {
			while (hp.less(nums[++i], pivot))
				if (i == hi)
					break;
			while (hp.less(pivot, nums[--j]))
				if (j == lo)
					break;

			if (i == j && nums[i] == pivot)
				hp.swap(nums, 0, nums.length, i, ++p);

			if (i >= j)
				break;

			hp.swap(nums, 0, nums.length, i, j);

			if (nums[i] == pivot)
				hp.swap(nums, 0, nums.length, i, ++p);
			if (nums[j] == pivot)
				hp.swap(nums, 0, nums.length, j, --q);
		}
		// phase 2
		// swap p left to the center
		while (p >= lo)
			hp.swap(nums, 0, nums.length, p--, j--);
		while (q <= hi)
			hp.swap(nums, 0, nums.length, q++, i++);

		return new int[] { j, i };
	}

//---------------------------------------------------------------------------------------------------------------------	 

	public void dualPivotQucikSort(E[] nums, int lo, int hi) {
		if (hi <= lo)
			return;

		int[] partion = dualPivotPartition(nums, lo, hi);

		dualPivotQucikSort(nums, lo, partion[0] - 1);
		dualPivotQucikSort(nums, partion[0] + 1, partion[1] - 1);
		dualPivotQucikSort(nums, partion[1] + 1, hi);

	}

	public void dualPivotQucikSortWithRandomPivots(E[] nums, int lo, int hi) {
		if (hi <= lo)
			return;

		int[] partion = dualPivotPartitionWithRandomPivot(nums, lo, hi);
		dualPivotQucikSortWithRandomPivots(nums, lo, partion[0] - 1);
		dualPivotQucikSortWithRandomPivots(nums, partion[0] + 1, partion[1] - 1);
		dualPivotQucikSortWithRandomPivots(nums, partion[1] + 1, hi);

	}

	/*
	 * select two pivot put the samller one on the left put the larger one on the
	 * right; the element smaller than lp on the left of lp, the element larger than
	 * rp on the right of rp, scanner i start from (lo+1=lt) - (hi-1=gt), because lo
	 * is lp, hi is rp phase 1: find the patition of [lo,lp),[lp,rp],(rp,hi]
	 * while(i<=gt){ 
	 * if(nums[i]<lt) swap(nums,lt++,i++); else if(nums[i]>gt)
	 * while(i<gt && nums[gt]>rp) gt--; swap(nums,gt--,i); else i++; } phase 2: move
	 * lp,rp to correct position if(lt!=lo) swap(nums,--lt,lo); if(gt!=hi)
	 * swap(nums,++gt,hi);
	 */
	public int[] dualPivotPartition(E[] nums, int lo, int hi) {
		int lt = lo + 1;
		int i = lo + 1;
		int gt = hi - 1;

		E largePivot = nums[hi];
		E smallPivot = nums[lo];
		// exchange the pivot ensure left pivot smaller than or equals than right pivot
		if (hp.less(largePivot, smallPivot)) {
			hp.swap(nums, 0, nums.length, hi, lo);
			E temp = largePivot;
			largePivot = smallPivot;
			smallPivot = temp;
		}

		// scan from left to right
		while (i <= gt) {
			if (hp.less(nums[i], smallPivot))
				hp.swap(nums, 0, nums.length, lt++, i++);
			else if (hp.less(largePivot,nums[i])) {
				while (i < gt && hp.less(largePivot,nums[gt]))
					gt--;
				hp.swap(nums, 0, nums.length, gt--, i);
			} else
				i++;
		}

		if (lt > lo)
			hp.swap(nums, 0, nums.length, --lt, lo);
		if (gt < hi)
			hp.swap(nums, 0, nums.length, ++gt, hi);

		return new int[] { lt, gt };
	}

	public int[] dualPivotPartitionWithRandomPivot(E[] nums, int lo, int hi) {
		int lt = lo + 1;
		int i = lo + 1;
		int gt = hi - 1;

		// select two pivot randomly also need to make sure they are not same
		// left pivot index
		int lpIndex = randomPivot(lo, hi);
		// move to the left position
		hp.swap(nums, 0, nums.length, lo, lpIndex);
		// ensure there are two different pivot
		// right pivot index
		int rpIndex = randomPivot(lo, hi, lo);
		// move to the right position
		hp.swap(nums, 0, nums.length, hi, rpIndex);
		E lp = nums[lo];
		E rp = nums[hi];
		// exchange the pivot ensure left pivot smaller than or equals than right pivot
		if (hp.less(rp, lp)) {
			hp.swap(nums, 0, nums.length, lo, hi);
			E temp = rp;
			rp = lp;
			lp = temp;

		}
		// scan from left to right
		while (i <= gt) {
			// smaller than left pivot
			if (hp.less(nums[i], lp))
				hp.swap(nums, 0, nums.length, lt++, i++);
			// larger than right pivot
			else if (hp.less(rp, nums[i])) {
				// jump the values have already larger than right pivot
				while (i < gt && hp.less(rp, nums[gt]))
					gt--;
				hp.swap(nums, 0, nums.length, gt--, i);
			} else
				i++;
		}

		if (lt > lo)
			hp.swap(nums, 0, nums.length, --lt, lo);
		if (gt < hi)
			hp.swap(nums, 0, nums.length, ++gt, hi);

		return new int[] { lt, gt };
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean res=false;
		Random ran = new Random();	
		int count=0;
		for(int time=10000;time<1000001;time+=1000){
			int n=time;
			int [] nums= new int[n];
			//initial the unsorted array
			for(int i=1;i<n;i++) {				
				int random=ran.nextInt(1000000000);
				nums[i]=random;
			}
			//nums= new int[] {-1,1,3,2,5,6,7,8,9,9,10,3,2,1};

			Integer[] xs= Arrays.stream(nums).boxed().toArray(Integer[]::new);
			QuickSorts <Integer> qs= new QuickSorts<>();
			qs.dijkstraThreeWayQuickSort(xs, 0, xs.length-1);		
//			for(int i:xs) {
//				System.out.print(i+" ");
//			}
			res=qs.sorted(xs);
			if(!res) { 
				
				System.out.print("The "+count+"th Time:"+" Sort Failed!!!!!!!!!!!!!!!!!!!"); 
				break;
				}
			
			else {
				count++;
				System.out.println("The "+count+"th Time"+" Sort Success!"); 
			}
			

		}
	}

}
