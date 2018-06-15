import java.util.ArrayList;
import java.util.Arrays;

public class Computer {
	static String[] possible = Player.possible;
	static char[] compBoardTop= new char[36];
	static char[] compBoardBottom = new char[36];
	static ArrayList<int[]> compBoats = new ArrayList<>();
	int[] boat1 = new int[2];
	int[] boat2 = new int[3];
	int[] boat3 = new int[4];
	int[] boat4 = new int[5];
	
	public Computer(){//constructor
		for(int i=0; i<36;i++){//this fills the boards with empty spaces
			compBoardTop[i] = ' ';
			compBoardBottom[i] = ' ';
		}
		//sets array[0] to corresponding boat size ex boat4 has 4 boats. boat[0] = 4 boat[1-5] = boat coordinates
		boat1[0] = 1;
		boat2[0] = 2;
		boat3[0] = 3;
		boat4[0] = 4;
		//adds boat arrays to array list of all boats
		compBoats.add(boat1);
		compBoats.add(boat2);
		compBoats.add(boat3);
		compBoats.add(boat4);
		//this places the computers boats on the board
		compBoats();
	}


	public static void CompTurn(Player player){//this method has the compter fire at a random location
		int rand1 = (int) (Math.random()*36);

		if(player.playerBoardBottom[rand1] == ' '){//if the space is empty the computer misses
			compBoardTop[rand1] = 'o';
			player.playerBoardBottom[rand1] = 'o';
			System.out.println("Computer missed! Computer fired @ "+possible[rand1]);
		}
		else if(player.playerBoardBottom[rand1] == 'B'){//if the space has a boat the computer hits
			compBoardTop[rand1] = 'X';
			player.playerBoardBottom[rand1] = 'X';
			System.out.println("Computer Hit! Computer hit your boat @ "+possible[rand1]);
			int numBoats =0;
			for(int n=0; n<player.playerBoardBottom.length;n++){//this calculates the number of boats the player has left
				if(player.playerBoardBottom[n] == 'B'){
					numBoats +=1;
				}
			}
			//this section checks if the hit boat was the last part of the boat
			for(int u=0;u<player.playerBoats.size();u++){
				boolean empty = true;
				for(int y=1;y<player.playerBoats.get(u).length;y++){//this removes boat from array
					if(rand1 == player.playerBoats.get(u)[y]){
						player.playerBoats.get(u)[y] = -1;
					}
				}
				for(int z=1;z<player.playerBoats.get(u).length;z++){//this checks if there are any more boats in the array
					if(player.playerBoats.get(u)[z] != -1){
						empty = false;
					}
				}
				if(empty){//if a battleship is sunk
					System.out.println("Computer sunk your " + player.playerBoats.get(u)[0] + " piece ship!");
					player.playerBoats.remove(u);
					
					if(player.playerBoats.isEmpty()){//if computer sunk all player battleships
						System.out.println("Computer sunk all your Battleships! You Lost!");
						System.exit(0); 
						return;
					}
				}				
				
			}
			System.out.println("You have "+numBoats+" boat(s) remaining.");
			
			
		}else{//if the random number was invalid
			CompTurn(player);
		}

	}

	public void compBoats(){//this method places the computer boats on the board randomly
		for(int i=3; i>-1; i--){//repeats process for the 4 piece boat to the 1 piece boat
			boolean okay = false;
			ArrayList<String> available = new ArrayList<>();	
			int place = -1;
			while(okay != true){
				available.add("U");available.add("D");available.add("L");available.add("R");//adds all options to the array
				int rand1 = (int) (Math.random()*36);
				if(compBoardBottom[rand1] == ' '){
					String boat = possible[rand1];
					place= rand1;
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
					//this section removes options from available if a boat cant go in that direction
					if((p1 + i)<7){//checks down
						for(int j=0;j<=i;j++){
							if(compBoardBottom[place+(j*6)] != ' '){
								down = false;
								available.remove("D");
							}
						}
					}else{	down = false; available.remove("D");}								
					if((p1- i)>0){//checks up
						for(int j=0;j<=i;j++){
							if(compBoardBottom[place -(j*6)] != ' '){
								up = false;
								available.remove("U");
							}
						}
					}else{	up = false;available.remove("U");}
					if((p2+i)<7){//checks right
						for(int j=0;j<=i;j++){
							if(compBoardBottom[place +j] != ' '){
								right = false;
								available.remove("R");
							}
						}
					}else{	right = false;available.remove("R");}
					if((p2-i)>0){//checks left
						for(int j=0;j<=i;j++){
							if(compBoardBottom[place -j] != ' '){
								left = false;
								available.remove("L");
							}
						}
					}else{	left = false;available.remove("L");}
					if(up == false && down ==false && right == false && left==false){//checks if there are any available options
						System.out.println("Unable to place boat here. Try a different starting block");
					}else{
						if(i>0){
							boolean okay2 =false;
							while(okay2 != true){//this section randomly chooses which direction to place the boat
								int rand2 = (int) (Math.random()*available.size());
								String ans = available.get(rand2);
								ans = ans.replaceAll("\\s+","");

								if(available.contains(ans)){
									if(ans.equalsIgnoreCase("U")){ 
										for(int j=0;j<=i;j++){
											compBoardBottom[place -(j*6)] = 'B';
											compBoats.get(i)[j+1] = place-(j*6);
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("D")){
										for(int j=0;j<=i;j++){
											compBoardBottom[place +(j*6)] = 'B';
											compBoats.get(i)[j+1] = place+(j*6);
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("L")){
										for(int j=0;j<=i;j++){
											compBoardBottom[place -j] = 'B';
											compBoats.get(i)[j+1] = place-j;
										}
										okay2 =true;
									}
									else if(ans.equalsIgnoreCase("R")){
										for(int j=0;j<=i;j++){
											compBoardBottom[place +j] = 'B';
											compBoats.get(i)[j+1] = place+j;
										}
										okay2 =true;

									}

								}else{
									System.out.println("Error! Invalid direction. Try again.");
								}
							}//end while okay2 aka direction placement
						}else{compBoardBottom[place] = 'B';compBoats.get(i)[1] = place;}
						
						okay = true;
					}				
				}
			}
		}
	}
}


