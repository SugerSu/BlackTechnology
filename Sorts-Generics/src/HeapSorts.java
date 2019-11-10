import java.util.Arrays;
import java.util.Random;

public class HeapSorts<E extends Comparable<E>> {
	
	private  Helper<E> hp;
	
	public HeapSorts() {
		hp= new Helper<>("Heap Sort");
	}
	
	public void HeapSortWithSinkConstructer(E[] nums) {
		int i = nums.length - 1;
		hp.setN(i);
		@SuppressWarnings("unchecked")
		E[] data = (E[])new Comparable[nums.length+1];
		System.arraycopy(nums, 0, data, 1, nums.length);
		
		HeapSortWithSinkConstructer(data,1,nums.length);
		
		System.arraycopy(data, 1, nums, 0, nums.length);
	}
	
	public void HeapSortWithSinkConstructer(E[] nums,int start,int end) {

		//Integer[] xs= Arrays.stream(nums).boxed().toArray(Integer[]::new);

		//construct heap with sink mathod
		//Sink Time is 1/2lgi => time < 0.5lgN;
		//Construct a hepp use Time:(0.5N(0.5lgi))=> time < 0.5NlgN
		constructMaxHeapWithSink(nums);
		//HeapSort with sink uses Time: N*(0.5lgi)) => time< N(0.5lgN) => time < NlgN

		while (end > 1) {
			hp.swap(nums,1,nums.length, 1, end--);
			//Do not need to construt whole heap, just sink the top element
			//constructMaxHeapWithSink(nums, 1, i);
			sink(nums,1,end);
		}
		
		
		System.out.println("Array Length: "+nums.length+ " 2NlgN " +2*nums.length*Math.log(nums.length)/Math.log(2));
		System.out.println(hp.toString());

	}
	
	public void HeapSortWithSwimConstructer(E[] nums) {
		int i = nums.length - 1;
		hp.setN(i);
		@SuppressWarnings("unchecked")
		E[] data = (E[])new Comparable[nums.length+1];
		//Make sure the index start from 1
		System.arraycopy(nums, 0, data, 1, nums.length);
		
		HeapSortWithSwimConstructer(data,1,nums.length);
		
		System.arraycopy(data, 1, nums, 0, nums.length);
		
	}
	
	public void HeapSortWithSwimConstructer(E[] nums,int start,int end) {
		
		//swim time lgi=> time < lgN
		//construct heap with swim: Nlgi=> time < NlgN
		constructMaxHeapWithSwim(nums);
		
		//HeapSort with swim uses time: ~N*NlgN <  HeapSort with sink
		while(end>1) {
			hp.swap(nums,1,nums.length,1,end--);
			//Because we hope to use swim, but we can not swim from the top of the heap,
			//we need to a loop to swim from the bottum, this procedure is less effective than use sink
			//And then the time complexity is O(N*NlgN)<  HeapSort with sink
			constructMaxHeapWithSwim(nums,1,end);
			//swim(nums,i);
		}
		
	}
//---------------------------------------------------------------------------------------------------------------------	 
// Swim And Sink
	//Time: O(lgi)
	private void swim(E[] nums, int i) {
		while (i > 1 && hp.less(nums[i / 2], nums[i])) {
			hp.swap(nums,0,nums.length, i, i / 2);
			i /= 2;

		}
	}

	//Time: O(Nlgi)<O(NlgN)
	private void constructMaxHeapWithSwim(E[] nums) {
		for(int i=1;i<nums.length;i++) {
			swim(nums,i);
		}
	}
	private void constructMaxHeapWithSwim(E[] nums,int start,int end) {
		for(int i=start;i<=end;i++) {
			swim(nums,i);
		}
	}
	
	private void sinkA(E[] nums,int i) {
		int N = nums.length;
		while (i * 2 < N - 1) {
			if (hp.less(nums[i], nums[2 * i]) || hp.less(nums[i], nums[2 * i + 1])) {
				//one more compare than swim and this is why sink method uses 2N compares
				int j = hp.less(nums[2 * i], nums[2 * i + 1]) ? 2 * i + 1 : 2 * i;
				hp.swap(nums,0,nums.length, i, j);
				//swap(nums, i, j);
				i = j;
			}

		}
	}
	//Time: O(0.5lgi)
	private void sink(E[] nums, int i) {
		while (i * 2 < nums.length) {
			int j = 2 * i;
			//find the larger child
			if (j < nums.length - 1 && hp.less(nums[j], nums[j + 1]))
				j++;
			//compare with parent with child
			if (!hp.less(nums[i], nums[j]))
				break;
			
			hp.swap(nums,0,nums.length, i, j);
			//swap(nums, i, j);
			i = j;

		}
	}

	private void sink(E[] nums, int i, int end) {
		while (i * 2 <= end) {
			int j = 2 * i;
			if (j < end && hp.less(nums[j], nums[j + 1]))
				j++;
			if (!hp.less(nums[i], nums[j]))
				break;
			//swap(nums, i, j);
			hp.swap(nums,0,nums.length, i, j);
			i = j;
		}
	}
	//Time:(0.5N(0.5lgi))
	private  void constructMaxHeapWithSink(E[] nums) {
		int N = nums.length - 1;
		for (int i = N / 2; i >= 1; i--) {
			//O(0.5lgN)
			sink(nums, i);
		}
	}

	private  void constructMaxHeapWithSink(E[] nums, int start, int end) {
		for (int i = end / 2; i >= start; i--) {
			sink(nums, i, end);
		}
	}

	//check maxHeap
	 boolean isMaxHeap(E[] nums) {
		for (int i = 1; i * 2 + 1 <= nums.length - 1; i++) {
			if (hp.less(nums[i], nums[2 * i]) || hp.less(nums[i], nums[2 * i + 1])) {
				return false;
			}
		}
		return true;
	}

	//check minHeap
	public boolean isMinHeap(E[] nums) {
		for (int i = 1; i * 2 + 1 <= nums.length - 1; i++) {
			if (hp.less(nums[2 * i], nums[i]) || hp.less(nums[2 * i + 1], nums[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean sorted(E[] nums) {
		return hp.sorted(nums);
	}
// ---------------------------------------------------------------------------------------------------------------------

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
			//nums= new int[] {2,1,3,2,5,6,7,8,9,9,10,3,2,1};
			Integer[] xs= Arrays.stream(nums).boxed().toArray(Integer[]::new);
			HeapSorts<Integer> hs= new HeapSorts<>();
			hs.HeapSortWithSinkConstructer(xs);
//			for(var i:xs2) {
//				System.out.print(i+" ");
//			}
			//Check			
			res=hs.sorted(xs);
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
