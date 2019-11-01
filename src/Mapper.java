import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;
public class Mapper implements Runnable{
    private String text;
    private int index;
    Process [] processes;
    public Mapper(int index,String text,Process [] processes){
        this.index=index;
        this.text=text;
        this.processes=processes;
    }
    public void run(){
        try {
            processes[index] = new ProcessBuilder("C:\\Users\\Essa\\IdeaProjects\\MapReduce\\inputFunctions\\mapper.exe",text).start();
            try {
                processes[index].waitFor();
            }
            catch(InterruptedException ie){
                System.out.println("Exception "+ie);
            }
            InputStream is = processes[index].getInputStream();
            Parser parser=Parser.getInstance();
            ListMaintaner listMaintaner=ListMaintaner.getInstance();
            listMaintaner.addArrayList(index,parser.parse(is));
            Main.threadsAlive--;
        }
        catch(IOException e){
            System.out.println("Exception "+e);
        }
    }
}
