import java.util.Random;



public class TestSorts {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Sorts sort= new Sorts();
		HeapSots hpSort= new HeapSots();
//		for(int i=1;i<8;i++) {
//			int x=sort.binarySearch(new int[] {1,2,3,4,5,6,7},i,0,6);
//			System.out.print(x);
//		}
		boolean res=false;
		Random ran = new Random();	
		int count=0;
		for(int time=10000;time<5000000;time+=1000){
			int n=time;
			int [] nums= new int[n];
			nums[0]=-1;
			//initial the unsorted array
			for(int i=1;i<n;i++) {				
				int random=ran.nextInt(1000000000);
				nums[i]=random;
			}
			//nums= new int[] {-1,1,3,2,5,6,7,8,9,9,10,3,2,1};
			//sort.binaryInsertSort(nums);
			//sort.dualPivotQucikSortWithRandomPivots(nums, 0, nums.length-1);
			//Heap Sort
			//hpSort.HeapSortWithSwimConstructer(nums);
			hpSort.HeapSortWithSinkConstructer(nums);
//			for(int i:nums) {
//				System.out.print(i+" ");
//			}
			//Check
			res=hpSort.isSorted(nums);
//			boolean isMaxHeap=hpSort.isMaxHeap(nums);
//			System.out.print("Max heap: "+isMaxHeap); 
//			for(Integer item:nums) {
//				System.out.print(item+" ");
//			}
			if(!res) { 
				
				System.out.print("The "+count+"th Time:"+" Sort Failed!!!!!!!!!!!!!!!!!!!"); 
				break;
				}
			
			else {
				count++;
				System.out.println("The "+count+"th Time"+" Sort Success!"); 
			}
			

		}
		//System.out.println(count+" "+res);
		

		
		

	}
	

}
