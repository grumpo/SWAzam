
package at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reduceCoins complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reduceCoins">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/}user" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/}fingerprintResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reduceCoins", propOrder = {
    "arg0",
    "arg1"
})
public class ReduceCoins {

    protected User arg0;
    protected FingerprintResult arg1;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setArg0(User value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link FingerprintResult }
     *     
     */
    public FingerprintResult getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link FingerprintResult }
     *     
     */
    public void setArg1(FingerprintResult value) {
        this.arg1 = value;
    }

}
