import java.util.concurrent.TimeUnit;
public class Game {

	public static void main(String[] args) {
		//intro
		System.out.println("________________________________________________________________\n");
		System.out.println("         >>>  >> >   WELCOME TO BATTLESHIP!   < <<  <<<");
		System.out.println("________________________________________________________________\n");
		//initializers
		Computer x = new Computer();
		Player y= new Player();
		
		//game starts!
		for(int i=0;i<36;i++){
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Computers Turn:");
			x.CompTurn(y);//calls computers turn
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			//this section inserts a pause for the player to read
			try{
			TimeUnit.SECONDS.sleep(2);
			}catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			//Display gameboard to player
			System.out.println("  Your Monitor");
			PrintBoard(y.playerBoardTop);
			System.out.println("~~~~~~~~~~~~~~~~");
			System.out.println("  Your Boats");
			PrintBoard(y.playerBoardBottom);System.out.println("");
			//Player takes a turn
			y.playerTurn(x.compBoardBottom);
			//this section inserts a pause for the player to read
			try{//i got this code from adapting the advice at http://stackoverflow.com/questions/24104313/how-to-delay-in-java
				TimeUnit.SECONDS.sleep(2);
				}catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
		}

	}

	public static void PrintBoard(char[] board){//this function prints whatever player/computer board[] is placed into it

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


