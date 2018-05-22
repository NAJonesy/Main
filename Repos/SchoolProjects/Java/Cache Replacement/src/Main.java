
	import java.util.Random;
	import java.util.Scanner;

	public class Main {
		public static String[] letters = new String[] {"A","B","C","D","E","F","G","H","I","J"};//possible page values
		public static Random rand = new Random(17);
		public static Scanner k = new Scanner(System.in);
		public static String[] pattern;
		public static void main(String[] args) {
			int length,pages,slots;
			length = getPatternLength();//call method to determine pattern length
			pages = getNumPages();//call method to determine number of unique pages
			slots = getNumSlots();//call method to determine number of slots
			k.close();//close scanner
			createPattern(length,pages);
			CacheReplace replacer = new CacheReplace(pattern,slots, rand);//instantiate a new object and pass in new parameters
			replacer.FIFORepalce();//call FIFO method
			replacer.LRUReplace();//call LRU method
			replacer.MINReplace();//call MIN method
			replacer.RANDRepalce();//call RAND method
			printStats(replacer);//call method to print out the stats at the end
		}

		public static void createPattern(int length, int pages){
			pattern = new String[length];//new pattern array size of given length
			for(int i=0;i<length;i++){
				int number = rand.nextInt(pages);//random number based unique number of pages
				pattern[i] = letters[number];//fill index of pattern with corresponding index of letters array
			}
			printPattern(pattern);
		}
		
		public static void printPattern(String[] pattern){
			String output = "Ref Str:";//header
			for(int i =0;i<pattern.length;i++){//go through pattern array
				output += " "+pattern[i];//make each slot empty
			}
			System.out.println(output);
			if(pattern.length > 35){//dividers
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}else{
				System.out.println("------------------------------------------------------------------------------");
			}
		}
		
		public static int getPatternLength(){//method to return page reference pattern length
			System.out.println("Enter page reference pattern length:");
			int length = k.nextInt();//user input
			if(length>=10 && length<=100){
				return length;
			}else{
				System.out.println("Error. Enter a pattern length between 10 and 100");
				getPatternLength();//call again
			}
			return 0;
		}
		
		public static int getNumPages(){//get unique number of pages
			System.out.println("Enter number of unique pages:");
			int pages = k.nextInt();
			if(pages>=2 && pages<=10){
				return pages;
			}else{
				System.out.println("Error. Enter a number of pages between 2 and 10");
				getNumPages();//call again
			}
			return 0;
		}
		
		public static int getNumSlots(){//return number of slots chosen by user
			System.out.println("Enter number of slots:");
			int slots = k.nextInt();
			if(slots>=2 && slots<=10){
				return slots;
			}else{
				System.out.println("Error. Enter a number of slots between 2 and 10");
				getNumSlots();//call again
			}
			return 0;
		}
		
		public static void printStats(CacheReplace cache){
			System.out.println("Cache Hit Rates:\n");
			double length = (double)cache.pLength;
			double fifoRatio = (double)cache.fifoHit/length;//calculate by dividing number of hits by the pattern length
			double lruRatio = (double)cache.lruHit/length;//calculate by dividing number of hits by the pattern length
			double minRatio = (double)cache.minHit/length;//calculate by dividing number of hits by the pattern length
			double randRatio = (double)cache.randHit/length;//calculate by dividing number of hits by the pattern length
			System.out.println("FIFO:  "+cache.fifoHit+" of " + pattern.length + " = " +fifoRatio);
			System.out.println("LRU :  "+cache.lruHit+" of " + pattern.length + " = " +lruRatio);
			System.out.println("MIN :  "+cache.minHit+" of " + pattern.length + " = " +minRatio);
			System.out.println("RAND:  "+cache.randHit+" of " + pattern.length + " = " +randRatio + "\n");
			
			if((fifoRatio == lruRatio) && (lruRatio == minRatio) && (minRatio == randRatio)) {//If all ratios are the same
				System.out.println("All are best and worst");
			}
			else {//check which has the highest ratio and lowest
				String names[] = new String[] {"FIFO", "LRU", "MIN", "RAND"};//string array to keep track of different algorithms
				String best = "";
				String worst = "";
				
				double ratios[] = new double[] {fifoRatio, lruRatio, minRatio, randRatio};//array of ratio doubles calculated above
				double biggest = 0;//since they will be less than one but bigger than zero
				double lowest = 1;//all ratios will be less than one
				
				for(int i = 0; i < ratios.length; i++) {//go through ratio array for the biggest
					if(ratios[i] > biggest) {
						biggest = ratios[i];//set biggest to the biggest ratio found
						best = names[i];//corresponding name in the names string array
					}
				}
				for(int i = 0; i < ratios.length; i++) {//go through ratio array for smallest
					if(ratios[i] < lowest) {
						lowest = ratios[i];
						worst = names[i];
					}
				}
				System.out.println(best + " is the best in this case");
				System.out.println(worst + " is the worst in this case");
			}
		}
		
	}
