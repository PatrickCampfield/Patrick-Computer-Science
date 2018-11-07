public class Hangman{
   /**
    * The main class that will run the output
    * of the guesser
    *
    * @param args
    */
   public static void main(String[] args){
     char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l',
     'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
     char[] vowels = {'a', 'e', 'i', 'o', 'u'};
     char[] constants = {'b','c','d','f','g','h', 'j','k','l',
     'm','n','p','q','r','s','t', 'v','w','x','y','z'};
      int wordLength, iteration;
      wordLength = UserInput.input();
      iteration = 0;
      // Output how long the word is and
      // the position of each letter
      while (iteration < wordLength){
        for (int i = 0; i < wordLength; i++{
          System.out.print(*);
          System.out.println();
          System.out.print(i + 1);
        }
        System.out.println("Does your word have the letter "); // this is where the guess will be output
      }
   }
}
