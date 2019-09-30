
package arina.maplab.definitions.mapforce.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datatype.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="datatype">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="anySimpleType"/>
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="boolean"/>
 *     &lt;enumeration value="float"/>
 *     &lt;enumeration value="decimal"/>
 *     &lt;enumeration value="integer"/>
 *     &lt;enumeration value="long"/>
 *     &lt;enumeration value="unsignedInt"/>
 *     &lt;enumeration value="int"/>
 *     &lt;enumeration value="unsignedShort"/>
 *     &lt;enumeration value="short"/>
 *     &lt;enumeration value="unsignedByte"/>
 *     &lt;enumeration value="byte"/>
 *     &lt;enumeration value="double"/>
 *     &lt;enumeration value="dateTime"/>
 *     &lt;enumeration value="date"/>
 *     &lt;enumeration value="time"/>
 *     &lt;enumeration value="duration"/>
 *     &lt;enumeration value="base64Binary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "datatype")
@XmlEnum
public enum Datatype {

    @XmlEnumValue("anySimpleType")
    ANY_SIMPLE_TYPE("anySimpleType"),
    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean"),
    @XmlEnumValue("float")
    FLOAT("float"),
    @XmlEnumValue("decimal")
    DECIMAL("decimal"),
    @XmlEnumValue("integer")
    INTEGER("integer"),
    @XmlEnumValue("long")
    LONG("long"),
    @XmlEnumValue("unsignedInt")
    UNSIGNED_INT("unsignedInt"),
    @XmlEnumValue("int")
    INT("int"),
    @XmlEnumValue("unsignedShort")
    UNSIGNED_SHORT("unsignedShort"),
    @XmlEnumValue("short")
    SHORT("short"),
    @XmlEnumValue("unsignedByte")
    UNSIGNED_BYTE("unsignedByte"),
    @XmlEnumValue("byte")
    BYTE("byte"),
    @XmlEnumValue("double")
    DOUBLE("double"),
    @XmlEnumValue("dateTime")
    DATE_TIME("dateTime"),
    @XmlEnumValue("date")
    DATE("date"),
    @XmlEnumValue("time")
    TIME("time"),
    @XmlEnumValue("duration")
    DURATION("duration"),
    @XmlEnumValue("base64Binary")
    BASE_64_BINARY("base64Binary");
    private final String value;

    Datatype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Datatype fromValue(String v) {
        for (Datatype c: Datatype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
