import java.util.Scanner;
public class UserInput{
  /**
   * Where the user will put in there input
   * about their word
   */
   public static int input(){
      int input;
      Scanner keyboard;
      keyboard = new Scanner(System.in);
      System.out.println("What is the length of your word?");
      input = keyboard.nextInt();
      while (input.length() < 4 || input.length() > 9) {
         System.out.println("Make sure the word's character length is between 4 and 9");
         input = keyboard.nextInt();
      }
      return input;
   }
}
