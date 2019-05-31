
/**
 * This Deterministic Finite State Machine determines whether a sentence is correctly formed or not.
 * The rules for what is a correctly formed sentence can be found in our README.
 * @author Alice Marbach and Greer Hoffmann
 * @version 27th April 2019
 */

import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
public class FSMReader
{
    /** Keeps track of the current state*/
    public static Enums curState;
    /** Checks to see if there is an unexpected character*/
    public static boolean unexpCharHuh = false; 
    /** Records the unexpected character*/
    public static char unexpChar;
    
    /**
     * An enum of the states in the finite state machine.
     * This explains the states.
     */
    private enum Enums{
        /** Is the start state. Needs to see a capital letter or rejects. */
        START(false), 
        
        /** Seen an upper case letter.*/
        SUPPER(false),

        /** Seen : ; , punctuation, and is waiting on a space to be legal*/
        WSPACE(false), 

        /** Seen terminal punctuation !. ?, waiting on space but allowed a second space
           and will still be legal.
           */
        WSPACET (false),

        /** This will only be reached if WSPACET has, i.e. a terminal punctuation
           has been seen. Seen a terminal and 1 space. Can see a second space.
           */    
        WSPACED(false),

        /** Seen a terminal and 2 spaces. A third space is not allowed (following
         * rules of the README.*/
        WSPACE2(false),

        /** Waiting on finishing a word i.e. still inside a word and can 
         * follow with letters, punctuation or space.*/
        WWORD(false), 

        /** Waiting on new word. Not same as START because does not have to be
           uppercase.*/
        WNEW(false), 
        
        /** Seen a hyphen, Following character must be uppercase or lowercase.
           No numbers.*/
        SHYPHEN(false),
        
        /** Seen an aspostrophe, so need to follow with lowercase letter,
           space, or punctuation.*/
        SAPOS(true),
        
        /** Seen a number, can have either a space or another number.*/
        WNUMBER(false),
        
        /** Rejecting state*/
        REJ(false),

        /** Accepting state.*/
        ACCEPT(true);

        //enum constructor
        Enums(boolean isAccept){this.isAccept = isAccept;}
        //enum instance variable
        private final boolean isAccept;
        //enum get method
        /**
         * Checks if the FSM will accept this state as a finishing state. (only END returns true).
         * @return boolean true if accepting state, false otherwise.
         */
        public boolean isAccept(){return isAccept;}
    }

    /**
     * CorrectSentence is the code for the finite state machine, determining if a line is a correctly formed sentence.
     * @param line a sentence to be checked
     * @return true if correct, false if not
     * @throws IllegalArgumentException (unless it is broken this shouldn't be thrown).
     */
    private static boolean CorrectSentence(String line){
        line +="$";
        int exprLen = line.length();
        int i =0, j=0;
        curState = Enums.START;

        while (curState != Enums.REJ && i < exprLen){
            i++;
            char c = line.charAt(j++);
            if // Just to be simpler, give each type a number
            (Character.isUpperCase(c)){
                c=0;
            } else if (Character.isLowerCase(c)){
                c=1;
            } else if ((c == ':')| (c == ',')| (c == ';')){
                c=2; // Non terminal punctuation
            } else if ((c == '.')| (c == '?')| (c == '!')){
                c=3; //Terminal punctuation
            } else if (c=='-'){
                c=4;
            } else if (c == '\''){
                c=5; // apostrophe
            } else if (c == ' '){
                c=6;
            } else if (Character.isDigit(c)){
                c=7; //ch
            } else if (c == '$'){
                c=8;
            }
            else {
                unexpChar = c;
                unexpCharHuh = true;
            }
            switch(curState){

                case START:
                if (c == 0)
                    curState = Enums.SUPPER;
                else    
                    curState = Enums.REJ;
                break;

                case SUPPER:
                if (c==1)
                    curState = Enums.WWORD;
                else if (c==2)  
                    curState = Enums.WSPACE;
                else if (c==3)
                    curState = Enums.WSPACET;
                else if (c==4)
                    curState = Enums.SHYPHEN;
                else if (c == 5)
                    curState = Enums.SAPOS;
                else if (c==6)
                    curState = Enums.WNEW;
                else 
                    curState = Enums.REJ;
                break;

                case WSPACE:
                if (c==6)
                    curState = Enums.WNEW;
                else 
                    curState = Enums.REJ;
                break;

                case WSPACET:
                if (c==6)
                    curState = Enums.WSPACED;
                else if (c==8)
                    curState = Enums.ACCEPT;
                else 
                    curState = Enums.REJ; 
                break;

                case WSPACED:
                if (c==0)
                    curState = Enums.WNEW;
                else if (c==6)
                    curState = Enums.WSPACE2;
                else if (c==8)
                    curState = Enums.ACCEPT;
                else 
                    curState = Enums.REJ; 
                break;

                case WSPACE2:
                if (c==0)
                    curState = Enums.WNEW;
                else if (c==8)
                    curState = Enums.ACCEPT;
                else 
                    curState = Enums.REJ; 
                break;

                case WWORD:
                if (c==1)
                    curState = Enums.WWORD;
                else if (c==2)  
                    curState = Enums.WSPACE;
                else if (c==3)
                    curState = Enums.WSPACET;
                else if (c==4)
                    curState = Enums.SHYPHEN;
                else if (c == 5)
                    curState = Enums.SAPOS;
                else if (c==6)
                    curState = Enums.WNEW;
                else 
                    curState = Enums.REJ;
                break;

                case WNEW:
                if (c == 0)
                    curState = Enums.SUPPER;
                else if (c==1)
                    curState = Enums.WWORD;
                else if (c==7)
                    curState = Enums.WNUMBER;
                else 
                    curState = Enums.REJ;
                break;

                case SHYPHEN:
                if (c == 0)
                    curState = Enums.SUPPER;
                else if (c==1)
                    curState = Enums.WWORD;
                else 
                    curState = Enums.REJ;
                break;

                case SAPOS:
                if (c==1)
                    curState = Enums.WWORD;
                else if (c==2)  
                    curState = Enums.WSPACE;
                else if (c==3)
                    curState = Enums.WSPACET; 
                else if (c==6)
                    curState = Enums.WNEW;
                else 
                    curState = Enums.REJ;
                break;

                case WNUMBER:
                if (c==2)  
                    curState = Enums.WSPACE;
                else if (c==3)
                    curState = Enums.WSPACET;
                else if (c==6)
                    curState = Enums.WNEW;
                else if (c==7)
                    curState = Enums.WNUMBER;
                else 
                    curState = Enums.REJ;
                break;

                case ACCEPT:
                curState = Enums.REJ;
                break;

                default:
                throw new IllegalArgumentException("There is an error in what you entered.");

            }
        }
        if (curState.isAccept()){          
            return true;
        } else{
            return false;
        }
    }

        
    /**
     * Main
     * @param a file name, in quotation marks e.g. "string.txt"
     * @return void, but goes line by line, prints out each sentence and determines if correct or incorrect.
     */
    public static void main(String[] args)
    {
        String fpath = args[0];
        try{
            BufferedReader r = new BufferedReader(new FileReader(fpath));  
            String line;
            while ((line = r.readLine()) != null){ //there is still data in the file
                unexpCharHuh = false;
                System.out.println("\n>" + line + "\n"); //print the sentence
               //then put if correct or incorrect
                if((CorrectSentence(line)) == true){
                    System.out.println("Correct"); 
                } else if (((CorrectSentence(line)) == false)&&(unexpCharHuh == true)){
                    System.out.printf("Sorry, your sentence contained an unrecognizable character %c.", unexpChar);
                    System.out.println("Incorrect"); // Just incorrect to have a + according to examples.
                } else { //Incorrect but no unexpecter char
                     System.out.println("Incorrect");
                }

            }
        }  catch (FileNotFoundException e){
            System.out.println("Sorry, file not found.");

        } catch (IOException e){
            System.out.println("There has been an error reading your file.");

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());

        }
    }
}