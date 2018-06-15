import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {
	Scanner k = new Scanner(System.in);
	ArrayList<String> Moves = new ArrayList<>();
	ArrayList<int[]> playerBoats = new ArrayList<>();//this contains all players boats
	int[] boat1 = new int[2];
	int[] boat2 = new int[3];
	int[] boat3 = new int[4];
	int[] boat4 = new int[5];
	public String[] Boats;
	public static final String[] possible = {"A1","A2","A3","A4","A5","A6","B1","B2","B3","B4","B5","B6","C1","C2","C3","C4","C5","C6","D1","D2","D3","D4","D5","D6","E1","E2","E3","E4","E5","E6","F1","F2","F3","F4","F5","F6"};//this array stores the coordinates of all places ex
													  //possible[0] = A1
	char[] playerBoardTop= new char[36];
	char[] playerBoardBottom = new char[36];

	//constructor
	public Player(){
		for(int i=0; i<36;i++){//fills boards with empty spaces
			playerBoardTop[i] = ' ';
			playerBoardBottom[i] = ' ';
		}
		//sets array[0] to corresponding boat size ex boat4 has 4 boats. boat[0] = 4 boat[1-5] = boat coordinates
		boat1[0] = 1;
		boat2[0] = 2;
		boat3[0] = 3;
		boat4[0] = 4;
		//adds boat arrays to array list of all boats
		playerBoats.add(boat1);
		playerBoats.add(boat2);
		playerBoats.add(boat3);
		playerBoats.add(boat4);
		//this calls the user to place their boats
		placeBoats();
		System.out.println("\n   ___LET THE GAMES BEGIN___   \n");
	}


	public void playerTurn(char[] board){//this is the method for a player turn
		String target = " ";
		int place =-1;
		boolean okay = false;
		while(okay!=true){//this loop makes sure coordinate they want to fire at is acceptable
			System.out.println("Where do you want to fire?");
			target = k.nextLine().toUpperCase();//gets coordinates as "a1" or "E6" etc
			if (target.length()==2 && Arrays.asList(possible).contains(target)){
				if (Moves.contains(target) == false){//checks that coordinate hasnt been fired at yet.
					okay = true;
				}else{
					System.out.println("Error! You have already fired at this location. Try again.");
				}
			}else{
				System.out.println("Error! Invalid coordinates. Try again.");
			}
		}
		Moves.add(target);
		for(int i=0;i<36;i++){
			if(possible[i].equalsIgnoreCase(target)){
				place = i;	//gets numerical coordinate ex. A1 = 0, f6 = 35 etc.
			}
		}

		if(board[place] == ' '){//if spot is empty
			board[place] = 'o';
			playerBoardTop[place] = 'o';
			Computer.compBoardBottom[place] = 'o';
			System.out.println("Miss! Shot missed at " +possible[place]+".");
		}else if(board[place] == 'B'){//if spot has a boat in it
			board[place] = 'X';
			playerBoardTop[place] = 'X';
			Computer.compBoardBottom[place] = 'X';
			System.out.println("Hit! You hit a boat at " +possible[place]+"!");
			int numBoats =0;
			for(int n=0; n<Computer.compBoardBottom.length;n++){//calculates remaining number of enemy boats
				if(Computer.compBoardBottom[n] == 'B'){
					numBoats +=1;
				}
			}			

			for(int u=0;u<Computer.compBoats.size();u++){// this section checks if player destroyed a battleship
				boolean empty = true;
				for(int y=1;y<Computer.compBoats.get(u).length;y++){//this removes the hit boat from the compBoat[]
					if(place == Computer.compBoats.get(u)[y]){
						Computer.compBoats.get(u)[y] = -1;
					}
				}
				for(int z=1;z<Computer.compBoats.get(u).length;z++){//checks for remaining boats in compBoatX[]
					if(Computer.compBoats.get(u)[z] != -1){
						empty = false;
					}
				}
				if(empty){//if battleship sinks
					System.out.println("You sunk their " + Computer.compBoats.get(u)[0] + " piece ship!");
					Computer.compBoats.remove(u);
					
					if(Computer.compBoats.isEmpty()){//if all boats are gone
						System.out.println("You sunk all their Battleships! You WIN!");
						System.exit(0); 
						return;
					}
				}			
			}
			System.out.println("Computer has "+numBoats+" boat(s) remaining.");			
		}
	}




	public void placeBoats(){//this method allows user to place their boats
		PrintBoard(playerBoardBottom);//shows "monitor"/the bottom board on a battleship board
		for(int i=3; i>-1; i--){//repeats process for the 4 piece boat to the 1 piece boat
			boolean okay = false;
			ArrayList<String> available = new ArrayList<>();	
			int place = -1;
			while(okay != true){
				available.add("U");available.add("D");available.add("L");available.add("R");//refills available options
				System.out.println("Where would you like to place your ("+(i+1)+")piece boat? (ex. a1, D4 etc.)");
				String boat = k.nextLine().toUpperCase();//get user coordinates for starting point
				if(Arrays.asList(possible).contains(boat)){
					for(int j=0;j<36;j++){
						if(possible[j].equalsIgnoreCase(boat)){
							place = j;	
						}
					}
					//this section breaks coordinate into each piece ex. "A6" into "A" which is equal to a 1 and  
					char char1 = boat.charAt(0);
					char char2 = boat.charAt(1);
					int p1 = -1;
					switch(char1){
					case 'A': p1 = 1;break;
					case 'B': p1 = 2;break;
					case 'C': p1 = 3;break;
					case 'D': p1 = 4;break;
					case 'E': p1 = 5;break;
					case 'F': p1 = 6;break;

					}
					int p2 = Character.getNumericValue(char2);
					boolean up = true; 
					boolean down = true; 		
					boolean right = true;
					boolean left = true;
					//this section removes directions from available ex. boat in the way or edge of the board
					if((p1 + i)<7){//checks down
						for(int j=0;j<=i;j++){
							if(playerBoardBottom[place+(j*6)] != ' '){
								down = false;
								available.remove("D");
							}
						}
					}else{	down = false; available.remove("D");}							
					if((p1- i)>0){//checks up
						for(int j=0;j<=i;j++){
							if(playerBoardBottom[place -(j*6)] != ' '){
								up = false;
								available.remove("U");
							}
						}
					}else{	up = false;available.remove("U");}
					if((p2+i)<7){//checks right
						for(int j=0;j<=i;j++){
							if(playerBoardBottom[place +j] != ' '){
								right = false;
								available.remove("R");
							}
						}
					}else{	right = false;available.remove("R");}
					if((p2-i)>0){//checks left
						for(int j=0;j<=i;j++){
							if(playerBoardBottom[place -j] != ' '){
								left = false;
								available.remove("L");
							}
						}
					}else{	left = false;available.remove("L");}
					if(up == false && down ==false && right == false && left==false){//if no available options
						System.out.println("Unable to place boat here. Try a different starting block");
					}else{
						if(i>0){
							boolean okay2 =false;
							while(okay2 != true){//this section has the user choose which direction to place the boat from available
								System.out.println("What direction would you like the boat? (U)p, (D)own, (L)eft or (R)ight?");
								System.out.print("Available options: ");
								for(int k=0;k<available.size();k++){//displays available options to the user
									System.out.print(available.get(k));
								}
								System.out.print("\n");

								String ans = k.nextLine().toUpperCase();
								ans = ans.replaceAll("\\s+","");//removes whitespace
								if(available.contains(ans)){
									if(ans.equalsIgnoreCase("U")){ 
										for(int j=0;j<=i;j++){
											playerBoardBottom[place -(j*6)] = 'B';//places boat on board
											playerBoats.get(i)[j+1] = place-(j*6);//adds boat to appropriate boat array[]
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("D")){
										for(int j=0;j<=i;j++){
											playerBoardBottom[place +(j*6)] = 'B';
											playerBoats.get(i)[j+1] = place+(j*6);
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("L")){
										for(int j=0;j<=i;j++){
											playerBoardBottom[place -j] = 'B';
											playerBoats.get(i)[j+1] = place-j;
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("R")){
										for(int j=0;j<=i;j++){
											playerBoardBottom[place +j] = 'B';
											playerBoats.get(i)[j+1] = place+j;
										}
										okay2 =true;

									}

								}else{
									System.out.println("Error! Invalid direction. Try again.");
								}
							}//end while okay2
						}else{playerBoardBottom[place] = 'B';playerBoats.get(i)[1] = place;}

						
						okay = true;
						PrintBoard(playerBoardBottom);//after choice displays board
					}				
				}else{
					System.out.println("Error! Invalid placement. Try again.");
					available.clear(); //resets availability
				}
			}
		}
	}

	public static void PrintBoard(char[] board){//this prints the board

		System.out.println("   1 2 3 4 5 6");
		System.out.println("  +-+-+-+-+-+-+");
		System.out.println("A |"+board[0]+"|"+board[1]+"|"+board[2]+"|"+board[3]+"|"+board[4]+"|"+board[5]+"|");
		System.out.println("--+-+-+-+-+-+-+");
		System.out.println("B |"+board[6]+"|"+board[7]+"|"+board[8]+"|"+board[9]+"|"+board[10]+"|"+board[11]+"|");
		System.out.println("--+-+-+-+-+-+-+");
		System.out.println("C |"+board[12]+"|"+board[13]+"|"+board[14]+"|"+board[15]+"|"+board[16]+"|"+board[17]+"|");
		System.out.println("--+-+-+-+-+-+-+");
		System.out.println("D |"+board[18]+"|"+board[19]+"|"+board[20]+"|"+board[21]+"|"+board[22]+"|"+board[23]+"|");
		System.out.println("--+-+-+-+-+-+-+");
		System.out.println("E |"+board[24]+"|"+board[25]+"|"+board[26]+"|"+board[27]+"|"+board[28]+"|"+board[29]+"|");
		System.out.println("--+-+-+-+-+-+-+");
		System.out.println("F |"+board[30]+"|"+board[31]+"|"+board[32]+"|"+board[33]+"|"+board[34]+"|"+board[35]+"|");
		System.out.println("  +-+-+-+-+-+-+");
	}
}
