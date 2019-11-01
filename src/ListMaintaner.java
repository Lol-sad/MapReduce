import java.io.ObjectStreamField;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ListMaintaner {
    private static ListMaintaner listMaintaner=new ListMaintaner();
    private ArrayList<Pair>[] allPairs;
    private int size;
    private ListMaintaner(){
        this.size=0;
        this.allPairs=new ArrayList[Main.mapperNodes];
    }
    public int getSize() {
        return size;
    }
    public static ListMaintaner getInstance(){
        return listMaintaner;
    }
    public void addArrayList(int index,ArrayList<Pair>aList){
        if(aList.isEmpty())return;
        allPairs[index]=new ArrayList<>();
        for(Pair p:aList){
            allPairs[index].add(p);
        }
        size++;
    }
    public ArrayList<Pair>getArray(int index){
        if(index<0||index>=size)throw new IllegalArgumentException();
        return allPairs[index];
    }
    public void setArray(int index,ArrayList<Pair>aList){
        if(index<0||index>=size)throw new IllegalArgumentException();
        allPairs[index]=aList;
    }
    public void removeArrays(){
        int newSize=size/2+(size%2);
        ArrayList<Pair>[] newPairs=new ArrayList[newSize];
        newSize=0;
        for(int i=0;i<size;i+=2){
            newPairs[newSize++]=allPairs[i];
        }
        size=newSize;
        allPairs=newPairs;
    }
}
