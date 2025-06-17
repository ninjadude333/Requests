
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class PrepareArrayForOutput{

    int[] pageBreaks; 
    String[] textLines;
    String[][] forTable;
    
    public PrepareArrayForOutput(TreeMap<String, Request> foundReqByDate, int type) {
    			
          forTable = new String[foundReqByDate.size()][7];
   		  SortedSet<String> keys = new TreeSet<String>(foundReqByDate.keySet());
   		  int i = 0;
   		  
   		  for (String key : keys) 
   			  
   		  { 
   			int j = 0;
   				 forTable[i][j++] = foundReqByDate.get(key).getID();
   				 forTable[i][j++] = foundReqByDate.get(key).getFullDate(); 
   				 forTable[i][j++] = foundReqByDate.get(key).getHourTimeFormat();
   	   			 forTable[i][j++] = foundReqByDate.get(key)._computerName;
   	   			 forTable[i][j++] = foundReqByDate.get(key)._requestName;
   	   			 forTable[i][j++] = foundReqByDate.get(key)._comment;
   	   			 forTable[i][j] = foundReqByDate.get(key)._techComment;
   	   		i++;
			}
   		  
   		  switch (type) {
		case 1:
			new CreateTableForPrint(forTable);
			break;
		case 2:
			new CreateTableForExcel(forTable);
			break;
		default:
			break;
		}
   		  
   		  
   		  }
   		  
}