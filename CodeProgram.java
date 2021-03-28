//part g
import java.io.*;
import java.util.*;
public class CodeProgram{
    private Scanner input = new Scanner(System.in);//create scanner object to read inputs
    public static void main(String [] args) throws Exception{
        CodeProgram code = new CodeProgram();//create object of CodeProgram to run the other methods in the class
        code.displayMenu();//call displayMenu method
    }
    public void displayMenu() throws Exception{
        /*Opening menu for program*/
        boolean loop=true;
        while(loop) {
            //display opening message & choose cipher type
            System.out.println("Welcome to the Cipher program");

            boolean validCipher = false;//indicates if cipher type is valid or not
            int cipherType = 0;//intialize cipher type

            /*Do-while loop to continue to read inputs until valid input is given*/
            do {
                try {
                    System.out.println("Type 1 for Substitution Cipher.");
                    System.out.println("Type 2 for Shuffle Cipher.");
                    cipherType = input.nextInt();//set ciphertype equal to the integer input

                    if (cipherType != 1 && cipherType != 2)//if valid input, throw input mismatch
                        throw new InputMismatchException();
                    else//if cipherType is 1 or 2, change validCipher to true to exit loop
                        validCipher = true;

                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.");
                    input.nextLine();//discard input
                }
            }while(!validCipher);

            /*User input for cipher type and key*/
            if (cipherType == 1) //if substitution cipher chosen
                substitutionCipher();
            else if (cipherType == 2)//if shuffle cipher chosen
                shuffleCipher();

            /*Portion of code determining whether to continue decoding/encoding messages*/
            boolean validResponse = false;
            while(!validResponse){//loop until given valid input
                    //prompt for input on whether to continue decoding/encoding messages
                    System.out.println("Do you want to do another message(Y/N)");
                    char response = Character.toUpperCase(input.nextLine().charAt(0));

                    if (response == 'N') {//if response is no, change loop variable to false to exit program
                        loop = false;
                        validResponse=true;//make validResponse true to exit validation loop
                        System.out.println("Exiting Program...");//show message for exiting the program
                    }
                    else if (response != 'N' && response != 'Y')//if the response does not match one of the two responses, throw inputmismatch
                        System.out.println("Invalid input. Please try again.");
                    else//if input is Y, also make validResponse true
                     validResponse = true;//if error is not thrown, make valid response true to exit loop
                System.out.println();//skip line

                }

        }
    }
    public void takeAction(Cipher cipher) throws FileNotFoundException{
        /*Creating necessary objects/variables */
        Scanner fInput = makeScanner();//make Scanner object to read file
        PrintWriter fOutput = makeWriter();//create PrintWriter object to write to a file
        char action ='\0';//initialize action

        /* Get and validate action*/
        boolean validAction = false;//indicate if action input is valid
        while(!validAction) {
            System.out.println("Encode(E) or Decode(D)?");//get input for whether to encode or decode a message
            action = Character.toUpperCase(input.nextLine().charAt(0));//save the upper case version of the entered character

            if(action == 'E'|| action=='D')
                validAction=true;//exit loop if valid input given
            else//if action is invalid input, show error message and continue to loop
                System.out.println("Invalid action. Please try again.");

        }
        String line = fInput.nextLine();//read message from input file

        /* Do the action */
        if(cipher instanceof SubstitutionCipher){//if cipher is an instance of sub cipher
            //do encode/decode for the message and write it to the output file
            if(action =='E')
                fOutput.println(((SubstitutionCipher)cipher).encode(line));
            else if(action == 'D')
                fOutput.println(((SubstitutionCipher)cipher).decode(line));
        }
        else if(cipher instanceof ShuffleCipher){//if cipher is an instance of shuffle cipher
            //do encode/decode and write result to output file
            if(action =='E')
                fOutput.println(((ShuffleCipher)cipher).encode(line));
            else if(action =='D')
                fOutput.println(((ShuffleCipher)cipher).decode(line));
        }
        if(action =='E')
            System.out.println("Encoded text saved in output file.");
        else
            System.out.println("Decoded text saved in output file.");
        fOutput.close();//close output file
        fInput.close();//close input file
    }

    public void substitutionCipher()throws Exception{
        /*Get inputs and create substitution cipher according to inputs*/
        boolean validKey = false;
        SubstitutionCipher subCipher = new SubstitutionCipher();

        /*Do-while loop to containing try-catch block to validate the input of the key.*/
        do {
            try {
                System.out.println("What is the key (shift amount) for your code?");//get shift key
                int shiftKey = input.nextInt();
                System.out.println("SubstitutionCipher-shift amount = " + shiftKey);//display confirmation
                subCipher = new SubstitutionCipher(shiftKey);
                input.nextLine();//read enter character from nextInt input line

                validKey = true;
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please try again.");
                input.nextLine();//discard input
            }

        }while(!validKey);

        takeAction(subCipher);//invoke takeAction method, passing subCipher
    }
    public void shuffleCipher() throws Exception{
        boolean validKey = false;//indicate if input was valid
        ShuffleCipher shuffleCipher=new ShuffleCipher();
        /*Do-while loop to containing try-catch block to validate the input of the key.*/
        do {
            try {

                /*Get inputs and create shuffle cipher according to inputs*/
                System.out.println("What is the key (shuffle amount) for your code?");//get shuffle key
                int shuffleKey = input.nextInt();
                System.out.println("ShuffleCipher-shuffle amount = " + shuffleKey);//display confirmation
                shuffleCipher = new ShuffleCipher(shuffleKey);
                input.nextLine();//read enter character from nextInt input line

                validKey =true;//if exception not thrown, make validKey true to exit do-while loop
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please try again.");
                input.nextLine();
            }
        }while(!validKey);

        takeAction(shuffleCipher);
    }
    public Scanner makeScanner()throws FileNotFoundException{

        System.out.println("Enter input filename.");
        String iFileName = input.nextLine();

        /*Create file object according to filename input, validate existence*/
        File iFile = new File(iFileName);
        while(!iFile.exists()){// if the file does not exist, ask for new file name, loop until valid file name given
            System.out.println("Input file not found. Please try again.");
            System.out.println("Enter input filename.");
            iFileName = input.nextLine();
            iFile = new File(iFileName);
        }
        Scanner fInput = new Scanner(iFile);//create scanner for file

        return fInput;//return the fInput object to the takeAction method
    }
    public PrintWriter makeWriter()throws FileNotFoundException{
    /*Create File object based on filename input, validate file did not already exist
    and create PrintWriter object for the file object
     */
        System.out.println("Enter output filename.");
        String oFileName = input.nextLine();
        File oFile = new File(oFileName);
        while(oFile.exists()){
            System.out.println("Output file already exists. Please try again.");
            System.out.println("Enter output filename.");
            oFileName = input.nextLine();
            oFile = new File(oFileName);
        }

        PrintWriter fOutput = new PrintWriter(oFileName);

        return fOutput;
    }


}
