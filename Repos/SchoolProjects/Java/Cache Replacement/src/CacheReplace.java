import java.util.Random;

public class CacheReplace {

	String[] pattern;
	String[] slots;
	String[][] output;
	int[] counter;
	Random rand;
	int lruHit, minHit, randHit, fifoHit, sLength, pLength = 0;
	
	public CacheReplace(String[] pattern, int slots, Random rand){//constructor
		this.pattern = pattern;
		this.slots = new String[slots];
		this.sLength = slots;
		this.pLength = pattern.length;
		this.rand = rand;
		this.output = new String[pattern.length][slots];//create output matrix for printer
	}
	
	public void LRUReplace(){
		int[] recent = new int[sLength];//create an array of size # of memory slots
		for(int k =0;k<sLength;k++){//initially fill it in with -1
			recent[k] = -1;//meaning the memory is empty
		}
		int slot = -1;//this keeps track of which slot is going to be replaced
		resetArrays();//clear the current memory and outputs
		
		for(int i =0; i<pLength;i++){//go thru the pattern
			int biggest = -1;//used to track the memory that was used least frequently
			if(checkSame(i)){//if its already in memory
				for(int j=0;j<sLength;j++){//find which slot was holding the memory
					if(output[i][j] == "+"){//if holding
						recent[j] = 0; //reset count of that slot to zero
					}else if(recent[j]!=-1){//otherwise the cache wasnt used that time so increase count
						recent[j]+=1;
					}
				}
				lruHit += 1;
			}else{//not already in memory
				for(int j=0;j<recent.length;j++){//go through the count of memory usage
					if(recent[j]==-1){//if the slot is currently unused
						slot = j;//set slot to change
						/*for(j=j+1;j<recent.length;j++){
							if(recent[j]!= -1){
								recent[j] += 1;
							}	
						}*/
						break;//no longer need to search for memory to replace
					}
					else if(recent[j]+1>biggest){//find memory that hasnt been used in the most time
						recent[j] += 1;//increase its memory usage count
						biggest = recent[j];//set biggest to the new count
						slot = j;//set the slot to change to the one that hasnt been used lately
					}else{//has been used more recently that other memroy
						recent[j] += 1;//add to memory count
					}
				}
				output[i][slot] = pattern[i];//fill output slot with the new memory
				slots[slot] = pattern[i];//change the info in the memory slot
				recent[slot]=0;//reset the slot changed count 
			}
		}
		printCache("LRU ");//print results
	}
	
	public void MINReplace(){
		counter = startFill();
		minHit += counter[1];
		int dist[] = new int[sLength];//create an array of size # of memory slots
		
		for(int k = counter[0]; k < pLength; k++) {
			if(checkSame(k)) {//if it's already in memory
				minHit += 1;
			}
			else {
				for(int i = 0; i < sLength; i++) {
					for(int j = k; j < pLength; j++) {
						if(pattern[j] == slots[i]) {
							dist[i] = j-k;//find how far the page is from current spot
							break;
						}
					}
				}
				int farthest = -1;//to keep track of which page is farthest
				int ans = 0;//to hold which page is the farthest
				for(int i =0; i < sLength; i++) {
					if(dist[i] > farthest) {//go through distance array to check for biggest value
						farthest = dist[i];//set the new farthest
						ans = i;//set the new answer of farthest page
					}
				}
				output[k][ans] = pattern[k];//fill output slot with new memory
				slots[ans] = pattern[k];//change the info in the memory slot
			}
		}
		printCache("MIN ");//print results
	}
	
	public void RANDRepalce(){//random memory cache allocation		
		counter = startFill();
		randHit += counter[1];//add hits from stating filler

		for(int i =counter[0]; i<pLength;i++){
			if(checkSame(i)){//check if its already in memory
				randHit += 1;
			}else{
				int rando = rand.nextInt(sLength);//select a random memory slots
				output[i][rando] = pattern[i];//insert into the randomly selected memory slot
				slots[rando] = pattern[i];//replace memory
			}
		}
		printCache("RAND");//print the results
	}
	
	public void FIFORepalce(){//fifo cache memory replacement
		int slot = 0;//keep track of the order the slots got filled
		counter = startFill();//reset memory and start filling the cache
		fifoHit += counter[1];//any hits that happened in the beginning fill
		for(int i =counter[0]; i<pLength;i++){//starting from where the starting filler ended
			if(checkSame(i)){//if it was already in memory
				fifoHit += 1;
			}else{
				//to keep replacements in order we want the remainder of the current cache replacements
				//ex. if we have replaced only the first 2 out of 4; 2%4 = remainder 2, so replace the second memory cache ([2])
				//ex. this continues as far as needed to complete pattern; 5%4 = remainder 1 so replace the first memory cache([1])
				output[i][slot%sLength] = pattern[i];
				slots[slot%sLength] = pattern[i];
				slot +=1;
			}
		}		
		printCache("FIFO");//print the results
	}
	
	private void resetArrays(){//empties the memory slots and output/recent cache replacements
		output = new String[pLength][sLength];//creates or resets output matrix
		slots = new String[sLength]; //represents and recreates the memory cache slots
		//fill in output matrix 
		for (int j=0;j<pLength;j++){
			for(int i =0;i<sLength;i++){
				output[j][i] = " ";
			}
		}
	}
	
	private int[] startFill(){//beginning cache fill
		int answer[] = new int[2];
		resetArrays();
		//fill in the cache slots
		int hit = 0;
		int col = 0;
		while(slots[sLength-1]== null){//while last cache slot unused		
			if(!checkSame(col)){
				output[col][col-hit] = pattern[col];
				slots[col-hit] = pattern[col];
			}else{
				hit += 1;
			}
			col += 1;
		}
		answer[0]=col;//the next location in the pattern
		answer[1] = hit;//number of cache hits in the initial fill in
		return answer;		
	}
	
	private void printCache(String type){//print out the output/cache replacement
		String list;
		for (int row=0;row<sLength;row++){//each row/memory slot
			list = type +"  "+ (row+1)+":";//what memory replacement is being used
			for(int column =0;column<pLength;column++){
				list += " " + output[column][row] ;//add the state of memory at that time
			}
			System.out.println(list);//print the line
		}
		//dividers
		if(pLength >35){
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
		}else{
			System.out.println("------------------------------------------------------------------------------");
		}
		
	}
	
	
	private boolean checkSame(int col){//check if the item is already in memory
		String current = pattern[col];
		boolean ans = false;
		for (int i=0;i<sLength;i++){
			if(slots[i] == current){//if next piece is currently in memory				
				output[col][i] = "+";
				return true;
			}
		}
		return ans;
	}
	
}
