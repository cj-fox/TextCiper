//part d
public class ShuffleCipher extends Cipher implements MessageEncoder, MessageDecoder{
    private int shuffle;
    ShuffleCipher(){}
    ShuffleCipher(int n){shuffle = n;}


    @Override
    public String cipherType(){return "ShuffleCipher";    }

    @Override
    public String encode(String plainText){//method to perform n shuffles
        String codedText=""+plainText;//make codedText be the same string as plainText
        for(int i=0; i<this.shuffle;i++){//iterate as many times as data field shuffle specifies
            codedText = shuffleOnce(codedText);//invoke shuffleOnce on codedText and set codedText equal to new string
        }
        return codedText;

    }
    private String shuffleOnce(String str){//method to perform one shuffle
        StringBuilder shuffle = new StringBuilder();
        StringBuilder substr1 = new StringBuilder(str.substring(0, str.length()/2)); //create substring for first half of str
        StringBuilder substr2 = new StringBuilder(str.substring(str.length()/2));//create substring for second half of str


        //for the length of substr1, append substr1 and then substr2 char to shuffle
        for(int i=0;i<substr1.length();i++){
            shuffle.append(substr1.charAt(i));
            shuffle.append(substr2.charAt(i));
        }
        if(substr2.length()>substr1.length())//if substr2 length is more than substr1, append last char of substr2 to shuffle
            shuffle.append(substr2.charAt(substr2.length()-1));

        return shuffle.toString();//return string representation of shuffle string builder
    }

    //part f - implement MessageDecoder
    @Override
    public String decode(String cipherText){//method to decode shuffle cipher text
        String plainText = ""+cipherText; //make plainText be the same string as cipherText

        for(int i=0;i<this.shuffle;i++)//iterate as many times as it was shuffled
            plainText = reverseShuffleOnce(plainText); //invoke reverseShuffleOnce and set plainText equal to new string

        return plainText;//return decoded message
    }
    private String reverseShuffleOnce(String str){//method to reverse one shuffle iteration
        StringBuilder reverse = new StringBuilder();

        for(int i =0; i<str.length();i+=2)//start at first letter and append every other character
            reverse.append(str.charAt(i));//appends the characters in order that they would be in in substr1 from shuffle method
        for(int i=1; i<str.length();i+=2)//start at second letter and append every other character
            reverse.append(str.charAt(i));//appends the characters in order that they would be in in substr2 from shuffle method

        return reverse.toString();
    }
}