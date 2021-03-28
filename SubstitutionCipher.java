//part c
public class SubstitutionCipher extends Cipher implements MessageEncoder, MessageDecoder{
    private int shift;//private data field to store the shift

    SubstitutionCipher(){}//no-arg constructor
    SubstitutionCipher(int shift){this.shift = shift;}//constructor that takes shift value

    @Override
    public String cipherType(){return "SubstitutionCipher";    }

    @Override
    public String encode(String plainText){
        StringBuilder codedText = new StringBuilder(); //create string builder, codedText

        for(int i=0;i<plainText.length();i++){//loop through plainText
            codedText.append((char)(plainText.charAt(i)+shift));//append each character to codedText once it has been shifted
        }
        return codedText.toString();//return string representation of codedText string builder
    }

    //part f - implement MessageDecoder
    @Override
    public String decode(String cipherText){//method to decode substitution cipher text
        StringBuilder plainText = new StringBuilder();//create string builder, plain text

        for(int i=0;i<cipherText.length();i++){//loop through cipherText
            plainText.append((char)(cipherText.charAt(i)-shift));//append each character once it has reversed the shift
        }
        return plainText.toString();//return string representation of plainText string builder
    }
}