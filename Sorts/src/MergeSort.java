import java.util.Arrays;
import java.util.Random;

public class MergeSort<E extends Comparable<E>> {

	private Helper<E> hp;
	//private int N;
	public MergeSort() {
		hp= new Helper<E>("Merge Sort");
	}
	public boolean Sorted(Comparable[] nums) {
		return hp.sorted((E[])nums);
	}
	/*
	 * Need extra places 6NlgN array accecss(2n copy,2n compare,2N write into the
	 * result array)
	 * 
	 * time: O(NlgN). T(n)=2T(n/2)+O(n)=> T(n)=nT(1)lgn+nlgn=>n(T(1)+lgn)=>nlgn
	 * becasue not matter what kinds of initial inputs, this sort will involke
	 * divide and merge methods thus, all the codition is same for merge sort, so
	 * there does not exist best condition or worst condition
	 * 
	 * compares between 1/2NlgN and NlgN
	 * 
	 * Divide the array in two half array,Unitl the sub array's length is one Merger
	 * and sort two nearby array until the whole array is ordered
	 */
	public E[] mergeSort(E[] nums) {
		
		int N=nums.length;
		
		if (N <= 1) return nums;

		int mid = nums.length / 2;
		E[] numsA =	Arrays.copyOfRange(nums, 0, mid);
		E[] numsB = Arrays.copyOfRange(nums, mid, nums.length);
		
		@SuppressWarnings("unchecked")
		E[] res = (E[])new Comparable[nums.length];
			res=	merge(mergeSort(numsA), mergeSort(numsB));
		return res;

	}

	public E[] merge(E[] numsA, E[] numsB) {
		int lenA=numsA.length;
		int lenB=numsB.length;
		//System.arraycopy(numsA, srcPos, dest, destPos, length);
		@SuppressWarnings("unchecked")
		E[] nums = (E[])new Comparable[lenA+lenB];
		int leftPoint = 0;
		int rightPoint = 0;

		for (int i = 0; i < nums.length; i++) {
			if (leftPoint >= lenA)
				nums[i] = numsB[rightPoint++];
			else if (rightPoint >= lenB)
				nums[i] = numsA[leftPoint++];
			else if (hp.less(numsB[rightPoint], numsA[leftPoint]))
				nums[i] = numsB[rightPoint++];
			else
				nums[i] = numsA[leftPoint++];
		}
		return nums;
	}

	public static void main(String[] args) {
		
		boolean res=false;
		Random ran = new Random();	
		int count=0;
		for(int time=100000;time<200000;time+=1000){
			int n=time;
			int [] nums= new int[n];
			//initial the unsorted array
			for(int i=1;i<n;i++) {				
				int random=ran.nextInt(1000000000);
				nums[i]=random;
			}
			//nums= new int[] {-1,1,3,2,5,6,7,8,9,9,10,3,2,1};
			Integer[] xs= Arrays.stream(nums).boxed().toArray(Integer[]::new);
			MergeSort<Integer> ms= new MergeSort<>();
			Comparable [] xs2= new Comparable[xs.length];
			xs2=ms.mergeSort(xs);
//			for(var i:xs2) {
//				System.out.print(i+" ");
//			}
			//Check			
			res=ms.Sorted(xs2);
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
