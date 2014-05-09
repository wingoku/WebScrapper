import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;

public class main {

	public static void main(String args[])
	{
		scrapLinks(getHtmlSource("http://www.rmi.edu.pk/healthcare/control/doctors"));
	
	}
	
	
	
	private static void scrapLinks(BufferedReader br)
	{
		if(br != null)
		{
			String line = null;
			
			try 
			{
				while((line = br.readLine()) != null)
				{
					if(line.contains("http://www.rmi.edu.pk/healthcare/control/doctors/profile"))
					{
						//System.out.println(line);
						int i = line.indexOf("href=");
						int j = line.indexOf("title");
						
						i+="href=".length()-1;
						
						
						if(i != -1 && j != -1)
						{
							System.out.println(line.substring(i+2, j-2));
							
							getTableData(getHtmlSource(line.substring(i+2, j-2)));
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		 //System.out.println("returning2");
	}
	
	
	
	private static void getTableData(BufferedReader br)
	{
		if(br == null)
			return;
		
		 
        String line = null;
        boolean started = false;
        
        try {
			while((line = br.readLine())!= null)
			{
				if(started)
					find(0,line);
			    if(line.contains("<tbody>")){
			        
			    	started = true;
			    	/*find(0,line);
			    	
			        int x = line.indexOf("<");
			        
			        if(line.charAt(x+1)== 't' && line.charAt(x+2) == 'b')
			        {
			            System.out.println("found");
			            
			            //find(x+6,line);
			        }
			        */
			    }
			    else
			    	
			    	if(line.contains("</tbody>"))
						started = false;
			}
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void find(int x, String line)
	{
		boolean started = false;
		String word = "";
		//System.out.println( line);
		line = line.replace("\n", "");
		line = line.replace(" ", "");
		
		//System.out.print(line);
		//x= 1000;
		
		for(int i=x; i<line.length();i++)
		{
			if(line.charAt(i) == '<')
				started = false;
			
			
			if(started )
			{
				word+=line.charAt(i);
			}
			else
			{
				if(!word.isEmpty())
					//System.out.println("empty");
				System.out.println(word);
				word = "";
			}
			
			if(line.charAt(i) == '>' && i+1 < line.length() && line.charAt(i+1) != '<')
			{
				//System.out.println("started");
				started = true;
			}
						
		}

	}
	
	
	private static BufferedReader getHtmlSource(String address)
	{
		// "http://www.rmi.edu.pk/healthcare/control/doctors/profile/?id=7983"
		BufferedReader br = null;
		 try {
			 	URL url = new URL(address);
		        InputStream is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));

		    } catch (Exception e) {
		         e.printStackTrace();
		    }
		 
		// System.out.println("returning1");
		 return br;
	}
	 
}
