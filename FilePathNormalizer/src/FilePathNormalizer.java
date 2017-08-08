public class FilePathNormalizer 
{
	public static void main(String args[])
	{
		String path1 = "../foo/./amol/../nirav/anita/../amol/anay";
		String path2 = "../././amol/../nirav/anita/../amol/anay";
		String path3 = "../foo/./../nirav/anita/../amol/anay";
		String path4 = "../amol/../nirav/anita/../amol/anay";
		String path5 = "../foo/./amol/./.././nirav/anita/../amol/anay";
		String path6 = "../foo/./amol/../nirav/anita/../amol/anay";
		String path7 = "../foo/./amol/../nirav/./anita/../amol/anay";
		String path8 = "../foo/./amol/./nirav/../anita/../amol/anay";
		String path9 = "../foo/./amol/../nirav/anita/../amol/anay";
		String path10 = "../foo/./amol/./nirav/./anita/../amol/anay";
		
		System.out.println("1 Normalized file path is:\n"+normalize(path1));
		System.out.println("2 Normalized file path is:\n"+normalize(path2));
		System.out.println("3 Normalized file path is:\n"+normalize(path3));
		System.out.println("4 Normalized file path is:\n"+normalize(path4));
		System.out.println("5 Normalized file path is:\n"+normalize(path5));
		System.out.println("6 Normalized file path is:\n"+normalize(path6));
		System.out.println("7 Normalized file path is:\n"+normalize(path7));
		System.out.println("8 Normalized file path is:\n"+normalize(path8));
		System.out.println("9 Normalized file path is:\n"+normalize(path9));
		System.out.println("10 Normalized file path is:\n"+normalize(path10));
		
	}
	
	/*
	 * normalize() calls three functions one by one and passes the output to the next calling function
	 * 1. handleFirstRoot() removes the root from the start of the string
	 * 2. handleSingleDot() removes the dot a
	 * 3. handleDoubleDot() removes the double dots returns the final normalized result
	 */
	static String normalize(String path)
	{
		String one = handleFirstRoot(path);
		String two = handleSingleDot(one);
		String three = handleDoubleDot(two);
		
		return three;
	}
	
	/*
	 * handleFirstRoot() checks whether the given input starts with the root (../) 
	 * and removes the two dots 
	 * Hence, for input "../foo/amol" it will return "/foo/amol" as the normalized path
	 */
	static String handleFirstRoot(String input)
	{
		if(input.contains("../"))
		{
			int index = input.indexOf("../");
			
			if(index==0)
			{
				input = input.substring(2);
			}
				
		}
		
		return input;
	}
	
	/*
	 * handleSingleDot() goes through the input string and checks for the indices
	 * where the string contains the character "/./" and substitutes the 
	 * single slash "/" for it
	 */
	static String handleSingleDot(String input)
	{
		
		StringBuilder sb = new StringBuilder(input.length());
		
		// if the string does not contain any of the special characters,  simply return the string
		if(!(input.contains("/./"))&&!(input.contains("/../"))&&!(input.contains("../")))
				{
					return input;
				}
		
		//get the first index of ("/./")
		int i = input.indexOf("/./");
		
		if(i==0)
			input = input.substring(2);
		
		//initialize the start indices to zero
		int start = 0;
		
		//traverse till the last occurrence of the character set - "/./"
		while(i>=0)
		{
			//append the part of the string before the occurrence of the ("/./") to StringBuilder
			sb.append(input.substring(start, i)+"/");
			
			//update start position 
			start = i+3;
			
			//get the next indices where the character set ("/./") is observed
			i = input.indexOf("/./", i+1);	
		}
		
		//traverse till the end of the string and return the string representation of the StringBuilder
		sb.append(input.substring(start));
		
		return sb.toString();

	}
	
	/*
	 * handleDoubleDot() function searches for the each occurrence of the "/../"
	 * Whenever it founds the index or indices where the character-set "/../" appears,
	 * it loops back till it finds the character "/". It then skips the characters from
	 * "/" onwards till the end of character set "/../" and updates the new start
	 * pointer to point to the character next to "/../". The string entries are strored
	 * in the stringBuilder except the ones which are skipped 
	 * "nirav/anita/../amol" for this string, it will loop back till the nirav/ &
	 * the skipped character set includes - "anita/.." and will simply append amol to nirav/
	 * thus returning "nirav/amol"
	 */
	static String handleDoubleDot(String input)
	{
		StringBuilder sb = new StringBuilder(input.length());
		
		//get the first index of ("/../")
		int pos = input.indexOf("/../");
		
		//initializing the start position
		int start = 0;
		
		
		while(pos >= 0)
		{
			int temp=0;
			int count = 0;
			
			for(int i=pos-1; i>=0; i--)
			{
				count++;
				if(input.charAt(i) == '/')
				{
					temp=i;
					break;
				}
				
			}
			
			sb.append(input.substring(start, temp+1));
			//updating the start pointer to point to the character after "/../" 
			start=temp+count+4;
			
			//getting the next occurrence of "/../"
			pos = input.indexOf("/../", pos+1);	
		}
			
		//traverse till the end of the string and return the string representation of the StringBuilder	
		sb.append(input.substring(start));
		
		return sb.toString();
		
	}

}
