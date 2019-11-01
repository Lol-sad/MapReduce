import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    public static int threadsAlive=0;
    public static int mapperNodes;
    public static int reducerNodes;
    public static String [] result;
    public static void setResult(int index,String res){
        if(index>=ListMaintaner.getInstance().getArray(0).size()||index<0)throw new IllegalArgumentException();
        if(result==null){
            result=new String[ListMaintaner.getInstance().getArray(0).size()];
        }
        result[index]=res;
    }
    public static void main(String[] args) {
        try (
                BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Essa\\IdeaProjects\\MapReduce\\inputFunctions\\input.txt"));
        ) {
            String nodeInputs=in.readLine();
            String [] splitNodeInputs=nodeInputs.split(" ");
            mapperNodes =Integer.parseInt(splitNodeInputs[0]);
            reducerNodes = Integer.parseInt(splitNodeInputs[1]);
            String allInput="";
            String str = in.readLine();
            while(str!=null){
                if(!str.equals("")&&!str.equals(" ")){
                    allInput+=str+" ";
                }
                str=in.readLine();
            }
            Splitter splitter=Splitter.getInstance();
            String [] splits=new String[mapperNodes];
            splits=splitter.split(allInput,mapperNodes);
            Process [] processes=new Process[mapperNodes];
            ExecutorService mapperExecutor = Executors.newFixedThreadPool(6);
            threadsAlive=mapperNodes;
            for(int i=0;i<mapperNodes;i++){
                Runnable runnable=new Mapper(i,splits[i],processes);
                mapperExecutor.execute(runnable);
            }
            while(threadsAlive!=0){
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ie){
                    System.out.println("Exception "+ie);
                }
            }
            mapperExecutor.shutdown();
            ListMaintaner listMaintaner= ListMaintaner.getInstance();
            ExecutorService sortingExecutor = Executors.newFixedThreadPool(6);
            while(listMaintaner.getSize()>1) {
                int size=listMaintaner.getSize();
                threadsAlive=size;
                for (int i = 0; i < size-1; i+=2) {
                    Runnable runnable = new Sorter(i, i + 1);
                    sortingExecutor.execute(runnable);
                }
                int newSize=size/2+(size%2);
                while(threadsAlive!=newSize){
                    try {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ie){
                        System.out.println("Exception "+ie);
                    }
                }
                listMaintaner.removeArrays();
            }
            sortingExecutor.shutdown();
            Shuffler shuffler=Shuffler.getInstance();
            shuffler.shuffle();
            int [] cumulativeSize=new int[reducerNodes];
            cumulativeSize[0]=0;
            for(int i=1;i<reducerNodes;i++){
                cumulativeSize[i]=cumulativeSize[i-1]+shuffler.getArraySize(i-1);
            }
            ExecutorService reducerExecutor = Executors.newFixedThreadPool(6);
            threadsAlive=reducerNodes;
            for(int i=0;i<reducerNodes;i++){ ;
                Runnable runnable=new Reducer(i,cumulativeSize[i]);
                reducerExecutor.execute(runnable);
            }
            while(threadsAlive!=0){
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ie){
                    System.out.println("Exception "+ie);
                }
            }
            reducerExecutor.shutdown();
            Runnable runnable=new Reducer(0,cumulativeSize[0]);
            runnable.run();
            for(String s:result){
                if(s!=null)
                    System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
