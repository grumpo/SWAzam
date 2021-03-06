
package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identifyMP3FingerprintResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identifyMP3FingerprintResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/}fingerprintResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identifyMP3FingerprintResponse", propOrder = {
    "_return"
})
public class IdentifyMP3FingerprintResponse {

    @XmlElement(name = "return")
    protected FingerprintResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link FingerprintResult }
     *     
     */
    public FingerprintResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link FingerprintResult }
     *     
     */
    public void setReturn(FingerprintResult value) {
        this._return = value;
    }

}
