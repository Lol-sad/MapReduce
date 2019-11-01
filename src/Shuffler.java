import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;

public class Shuffler {
    private static Shuffler shuffler=new Shuffler();
    private ArrayList<Pair>[]allArrays;
    public static Shuffler getInstance(){
        return shuffler;
    }
    private Shuffler(){}
    public void shuffle(){
        ListMaintaner listMaintaner=ListMaintaner.getInstance();
        int size=listMaintaner.getArray(0).size();
        int pairsPerNode=size/Main.reducerNodes;
        int lastSplit=0;
        allArrays=new ArrayList[Main.reducerNodes];
        ArrayList<Pair>original=listMaintaner.getArray(0);
        int n=original.size();
        int [] howMuch= new int[Main.reducerNodes];
        int remainder=size%Main.reducerNodes;
        for(int i=1;i<=Main.reducerNodes-remainder;i++){
            howMuch[i-1]=pairsPerNode;
        }
        for(int i=Main.reducerNodes-remainder+1;i<=Main.reducerNodes;i++){
            howMuch[i-1]=pairsPerNode+1;
        }
        for(int i=0;i<Main.reducerNodes;i++){
            ArrayList<Pair>aList=new ArrayList<>();
            for(int j=lastSplit;j<lastSplit+howMuch[i]&&j<n;){
                aList.add(original.get(j++));
                while(j<n&&original.get(j).getKey().toString().equals(original.get(j-1).getKey().toString())){
                    aList.add(original.get(j++));
                }
            }
            allArrays[i]=aList;
            lastSplit+=allArrays[i].size();
        }
    }
    public ArrayList<Pair>getArray(int index){
        if(index>=Main.reducerNodes||index<0)throw new IllegalArgumentException();
        return allArrays[index];
    }
    public int getArraySize(int index){
        if(index>=Main.reducerNodes||index<0)throw new IllegalArgumentException();
        return allArrays[index].size();
    }
}
