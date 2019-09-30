package arina.maplab.value;

import arina.maplab.processors.IMapComponentProcessor;
import javax.xml.datatype.DatatypeConfigurationException;

public interface IMapValue
{
    IMapValue create(Object newValue);
    IMapComponentProcessor getProcessor();
    <T> T getValue(Class<T> clazz) throws Exception;
    Object getValue();
    String getContext();
    boolean isNotNull();
    boolean isNull();
    long   getSCN();
}
