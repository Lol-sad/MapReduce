public class Pair implements Comparable<Pair>{
    private Object key;
    private Object value;
    public Pair(Object a,Object b){
        key=a;
        value=b;
    }
    public Object getKey() {
        return key;
    }
    public void setKey(Object key) {
        this.key = key;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public int compareTo(Pair t){
        if(key.equals(t))
            return value.toString().compareTo(t.value.toString());
        return key.toString().compareTo(t.key.toString());
    }
}
