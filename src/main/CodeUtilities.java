package src.main;

public class CodeUtilities {
	
	public static String CapitalizeFirstLetters(String str){

		String[] words = str.split(" ");
		StringBuilder result = new StringBuilder();

		for (String word : words){
			if(!word.isEmpty()){
				result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
			}
		}

		str = result.toString().trim();


		return str;

	}

}
