import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileDictCreateur 
{
	static String PATH = "/home/matthias/Workbench/SUTD/ISTD_50.570/assignments/practice_data/data/train";
	
	static Map<File, ArrayList<String> > fileDict = new HashMap<>();
	
	public static void main(String[] args) throws IOException 
	{
		//each of the diferent categories
		String[] categories = { "/atheism", "/politics", "/science", "/sports"};
		
		//cycle through all categories once to populate the global dict
		for(int cycle = 0; cycle <= 3; cycle++)
		{
			String general_data_partition = PATH + categories[cycle];
			
			File directory = new File( general_data_partition );
			iterateDirectory( directory );	
		}
		
		for (Map.Entry entry : fileDict.entrySet()) 
		{
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
	
	private static void iterateDirectory(File directory) throws IOException 
	{
	    for (File file : directory.listFiles()) 
	    {
	        if (file.isDirectory()) 
	        {
	            iterateDirectory(directory);
	        } 
	        else 
	        {
	            System.out.println(file);
	            
	    		String line; 
	    		BufferedReader br = new BufferedReader(new FileReader( file ));

	    		while ((line = br.readLine()) != null) 
	    		{
	    			String[] words = line.split(" ");//those are your words

	    			//populate_globo_dict(words);
	    			
	    			create_file_dict( file, words );
	    				
	    		}
	        }
	    }
	}
	
	public static void create_file_dict( File file, String[] words ) throws IOException
	{	
		
		if (!fileDict.containsKey(file))
		{
			ArrayList document_words = new ArrayList<String>();
			
			String word;
			
			for (int i = 0; i < words.length; i++) 
			{
				word = words[i];
		    
				document_words.add(word);
			}
			fileDict.put(file, document_words);
		}
	}
}