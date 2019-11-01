import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Reducer implements Runnable{
    private int index;
    private int cumulativeSize;
    public Reducer(int index, int cumulativeSize) {
        this.index = index;
        this.cumulativeSize = cumulativeSize;
    }
    public void run(){
        Shuffler shuffler=Shuffler.getInstance();
        ArrayList<Pair>current=shuffler.getArray(index);
        String allPairs="";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<current.size();i++){
            String key=current.get(i).getKey().toString();
            String value=current.get(i).getValue().toString();
            sb.append(key+" "+value+"\n");
        }
        allPairs=sb.toString();
        try {
            Process p = new ProcessBuilder("C:\\Users\\Essa\\IdeaProjects\\MapReduce\\inputFunctions\\reducer.exe",allPairs).start();
            try {
                p.waitFor();
            } catch (InterruptedException ie) {
                System.out.println("Exception "+ie);
            }
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            int i=cumulativeSize;
            while((line = br.readLine()) != null) {
                Main.setResult(i++,line);
            }
            Main.threadsAlive--;
        }
        catch(IOException e){
            System.out.println("Exception "+e);
        }

    }
}
