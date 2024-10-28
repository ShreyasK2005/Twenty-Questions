import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A model for the game of 20 questions
 *
 * @author Rick Mercer
 */
public class GameTree
{
    private BSTNode root;
    private BSTNode current;
    private String fileName;
  private class BSTNode 
  {
    public String val;
    public BSTNode yes, no;
   
    public BSTNode(String val) 
    {
      this.val = val;
      yes = no = null;
    }
   
    @Override
    public String toString() 
    { 
      return "" + this.val;
    }
  }

	/**
	 * Constructor needed to create the game.
	 *
	 * @param fileName
	 *          this is the name of the file we need to import the game questions
	 *          and answers from.
	 */
	public GameTree(String fileName)
	{
      this.fileName = fileName;
	   Scanner i = new Scanner(fileName);
      try
      {
        i = new Scanner(new File(fileName));
        root = new BSTNode (i.nextLine());
        current = root;
        bT(current, i);
         
      }
      catch(FileNotFoundException E)
      {
        System.out.println("Error");
      }

	}
   
   public void bT(BSTNode s, Scanner a)
   {
     if(s.val.contains("?"))
     {
       s.yes = new BSTNode(a.nextLine());
       bT(s.yes, a);
       s.no = new BSTNode(a.nextLine());
       bT(s.no, a);
     }
   }

	/*
	 * Add a new question and answer to the currentNode. If the current node has
	 * the answer chicken, theGame.add("Does it swim?", "goose"); should change
	 * that node like this:
	 */
	// -----------Feathers?-----------------Feathers?------
	// -------------/----\------------------/-------\------
	// ------- chicken  horse-----Does it swim?-----horse--
	// -----------------------------/------\---------------
	// --------------------------goose--chicken-----------
	/**
	 * @param newQ
	 *          The question to add where the old answer was.
	 * @param newA
	 *          The new Yes answer for the new question.
	 */
	public void add(String newQ, String newA)
	{
      current.yes = new BSTNode(newA);
      current.no = new BSTNode(current.val);
      current.val = newQ;      
	}
   

	/**
	 * True if getCurrent() returns an answer rather than a question.
	 *
	 * @return False if the current node is an internal node rather than an answer
	 *         at a leaf.
	 */
	public boolean foundAnswer()
	{
		if(getCurrent().contains("?") == false)
      {
        return true;
      }
		return false;
	}

	/**
	 * Return the data for the current node, which could be a question or an
	 * answer.  Current will change based on the users progress through the game.
	 *
	 * @return The current question or answer.
	 */
	public String getCurrent()
	{

		return current.val; //replace
	}

	/**
	 * Ask the game to update the current node by going left for Choice.yes or
	 * right for Choice.no Example code: theGame.playerSelected(Choice.Yes);
	 *
	 * @param yesOrNo
	 */
	public void playerSelected(Choice yesOrNo)
	{
		if(yesOrNo == Choice.Yes)
      {
        current = current.yes;
      }
      if(yesOrNo == Choice.No)
      {
        current = current.no;
      }
	}

	/**
	 * Begin a game at the root of the tree. getCurrent should return the question
	 * at the root of this GameTree.
	 */
	public void reStart()
	{
		current = root;
	}

	@Override
	public String toString()
	{
      String answer = "";
      String d = "";
		return s(root, d);
	}
   
   public String s(BSTNode q, String depthDash)
   {
     if(q == null)
     {
       return "";
     }
       String answer = "";
       answer+= s(q.no, depthDash + "- ");
       answer += depthDash + " " + q.val;
       answer+="\n";
       answer+=s(q.yes, depthDash + "- ");
       return answer;  
   }

	/**
	 * Overwrite the old file for this gameTree with the current state that may
	 * have new questions added since the game started.
	 *
	 */
	public void saveGame()
	{
	   String outputFileName = fileName;
	   PrintWriter diskFile = null;
 	 try { 
       diskFile = new PrintWriter(new File(outputFileName)); 
     }
	  catch (IOException io) { 
        System.out.println("Could not create file: " + outputFileName); 
     }
     saveH(diskFile, root);
     diskFile.close();
	}
   public void saveH(PrintWriter a, BSTNode s)
   {
     if(s == null)
     {
       return;
     }
     a.println(s.val);
     saveH(a, s.yes);
     saveH(a, s.no);
     
   }
     
 }
