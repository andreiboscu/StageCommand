package stagecommand;
/*
 	Main este punctul de intrare al aplicației.
 	Ferestrele sunt implementate separat ca JFrame-uri.
*/
public class Main 
{
	public static void main(String[] args) 
	{
        MessageBoard board = new MessageBoard(); // centrul de mesaje
        // pornește prima fereastră
        HomeWindow.showUI(board);
    }
}

