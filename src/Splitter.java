public class Splitter {
    private static Splitter splitter=new Splitter();
    private Splitter(){}
    public static Splitter getInstance(){
        return splitter;
    }
    public String [] split(String s,int nodes){
        int n=s.length();
        int noOfCharsPerSplit=n/nodes;
        int [] howMuch= new int[nodes];
        int lastSplit=0;
        String [] splits=new String[nodes];
        int remainder=n%nodes;
        for(int i=1;i<=nodes-remainder;i++){
            howMuch[i-1]=noOfCharsPerSplit;
        }
        for(int i=nodes-remainder+1;i<=nodes;i++){
            howMuch[i-1]=noOfCharsPerSplit+1;
        }
        for(int i=0;i<nodes;i++){
            int extra=0;
            StringBuilder st=new StringBuilder();
            for(int j=lastSplit;j<lastSplit+howMuch[i]&&j<n;){
                if(st.length()==0&&s.charAt(j)==' '){
                    extra++;
                    j++;
                    continue;
                }
                st.append(s.charAt(j++));
                while(j<n&&s.charAt(j)!=' '){
                    st.append(s.charAt(j++));
                }
            }
            splits[i]=st.toString();
            lastSplit+=splits[i].length()+extra;
        }
        return splits;
    }
}