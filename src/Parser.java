import javax.imageio.spi.IIOServiceProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Parser {
    private static Parser parser=new Parser();
    private Parser(){}
    public static Parser getInstance(){
    return parser;
}
    public ArrayList<Pair> parse(InputStream is){
        ArrayList<Pair> pairs = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            String[] dataTypes = new String[2];
            if ((line = br.readLine()) != null) {
                dataTypes = line.split(" ");
            }
            while ((line = br.readLine()) != null) {
                Object key, value;
                String[] temp = line.split(" ");
                key = getObject(dataTypes[0], temp[0]);
                value = getObject(dataTypes[1], temp[1]);
                pairs.add(new Pair(key, value));
            }
            Collections.sort(pairs);
            return pairs;
        }
        catch(IOException e){
            System.out.println("Exception "+e);
        }
        return pairs;
    }
    private Object getObject(String dataType, String val){
        DataTypeFactory dataTypeFactory=new DataTypeFactory();
        return dataTypeFactory.createDataType(dataType,val);
    }
}
