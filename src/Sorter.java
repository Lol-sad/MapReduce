import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Sorter implements Runnable{
    private int index1;
    private int index2;
    public Sorter(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }
    public void run(){
        ArrayList<Pair>array1,array2;
        ListMaintaner listMaintaner=ListMaintaner.getInstance();
        array1=listMaintaner.getArray(index1);
        array2=listMaintaner.getArray(index2);
        ArrayList<Pair>finalArray=new ArrayList<>();
        int pointer1=0,pointer2=0;
        int n=array1.size(),m=array2.size();
        while(pointer1<n&&pointer2<m){
            if((array1.get(pointer1).compareTo(array2.get(pointer2))<0)){
                finalArray.add(array1.get(pointer1++));
            }
            else finalArray.add(array2.get(pointer2++));
        }
        while(pointer1<n)
            finalArray.add(array1.get(pointer1++));

        while(pointer2<m)
            finalArray.add(array2.get(pointer2++));
        listMaintaner.setArray(index1,finalArray);
        Main.threadsAlive--;

    }
}
