public class DataTypeFactory {
    public Object createDataType(String dataType,String val){
        if(dataType.equalsIgnoreCase("integer"))return new Integer(val);
        else if(dataType.equalsIgnoreCase("double"))return new Double(val);
        else if(dataType.equalsIgnoreCase("long"))return new Long(val);
        return new String(val);
    }
}
