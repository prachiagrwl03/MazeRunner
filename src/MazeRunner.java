import java.io.*;

public class MazeRunner
{	
//	Object creation
	static Maze m = new Maze();
	BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
	
//	Greeting the user
	void intro()
	{
		System.out.println("WELCOME TO MAZE RUNNER!");
		System.out.println("HERE'S YOUR CURRENT POSITION");
		System.out.println();
	}
	
//	Asking user to enter a valid move
	public String userMove() throws IOException
	{
		String  dir;
		
		do
		{
			System.out.print("Where would you like to move? (R, L, U, D) ");
			dir = br.readLine();
		}while(!(dir.equals("R")) && !(dir.equals("L")) && !(dir.equals("U")) && !(dir.equals("D")));		
		
		return dir;
	}
	
//	Making a move
	public boolean movify(String dir)  throws IOException 
	{
		boolean check = false;
		
//		Condition to check whether the user can proceed in the expected direction or not
		if(dir.equals("R"))
			check = m.canIMoveRight(); // Returns true if the space to the right is free, false if there is a wall
		if(dir.equals("L"))
			check = m.canIMoveLeft();  // Returns true if the space to the left is free, false if there is a wall
		if(dir.equals("U"))
			check = m.canIMoveUp();    // Returns true if the space to the up is free, false if there is a wall
		if(dir.equals("D"))
			check = m.canIMoveDown();  // Returns true if the space to the down is free, false if there is a wall

//		If 'check' returns true then make a move
		if(check)
		{
			if(dir.equals("R"))
				m.moveRight();
			if(dir.equals("L"))
				m.moveLeft();
			if(dir.equals("U"))
				m.moveUp();
			if(dir.equals("D"))
				m.moveDown();
		}
		else 
		{
			return false;
		}
		
		return true;
	
	}
	
//	Warning user that they have exceeded some move limits
	public void moveMessage(int move)
	{
		if(move == 50)
			 System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
		if(move == 75)
			 System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape");
		 if(move == 90)
			 System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
		 if(move == 100)
		 {
			 System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
			 System.out.println("Sorry, but you didn't escape in time- you lose!");
				
		 }
	}
	
//	Sub-routine to handle if user wants to jump a pit
	public boolean navigate( String dir, boolean pit ) throws IOException
	{
		 if(pit)
		 {
			 System.out.println("Watch out! There's a pit ahead, jump it? press 'Y' to jump press 'N' to exit ");
			 String  jump = br.readLine();
			 if(jump.equals("Y"))
			 {
				 m.jumpOverPit(dir);
			 }
			 else
			 {
				 return true;
			 }
		 }
		 
		 return false;
	}
	
	public static void main( String args[] ) throws IOException
	{
		String dir;
		boolean check = true;
		boolean win = false;
		boolean chance = false;
		int move = 0;
		
		MazeRunner mr = new MazeRunner();
		
		mr.intro();
		m.printMap();
		do
		{
		    dir = mr.userMove();
		    move++ ;
		    
		    check = mr.movify(dir);
		    
		    if(!check)
		    {
		    	check = m.isThereAPit(dir);
		    	chance = mr.navigate(dir, check);
		    	
		    	if(chance)
		    		continue;
		    }
		    
		    m.printMap();
		    mr.moveMessage(move);
		    
		    win = m.didIWin();
		    
		    if(win)
		    	break;
		}while(check);
		
//		Telling user that you have hit a wall
		if(!check)
			System.out.println("Sorry, you’ve hit a wall.");
	   
//		Congratulating user on his win
		if(win)
		{
			System.out.println("Congratulations, you made it out alive!");
			System.out.println("You did it in " + move + " moves");
		}
	}
}
