package Encryption;

public class Encrypter {
	public Encrypter( ){
		
	}
	char encrypt_decryptchar(char data)
    {
		if (data >= 'a' && data <= 'z') {
            return (char) ('z' - (data - 'a'));
        }
        if (data >= 'A' && data <= 'Z') {
            return (char) ('Z' - (data - 'A'));
        }
        if (data >= '0' && data <= '9') {
            return (char) ('9' - (data - '0'));
        }
        return data;
    }
     String replaceCharAt(String str, int index, char ch) {
        if (str == null || index < 0 || index >= str.length()) {
            throw new IllegalArgumentException("Invalid index");
        }

        char[] chars = str.toCharArray();
        chars[index] = ch;
        return new String(chars);
    }
	public String EncryptString(String input){
		String output="";
		for(int i =0; i < input.length(); i++ )
   	 {
   		 output = output + encrypt_decryptchar(input.charAt(i));   ;
   	 }
		System.out.println("ENCRYPTION : "+input+" ---> " + output);
		return output;
	}
	public String DecryptString(String input) {return this.EncryptString(input);}
}
