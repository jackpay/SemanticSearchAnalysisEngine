
/* First created by JCasGen Tue Apr 16 16:21:04 BST 2013 */
package ac.uk.susx.tag.ner;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Apr 17 21:01:16 BST 2013
 * @generated */
public class LocationAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (LocationAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = LocationAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new LocationAnnotation(addr, LocationAnnotation_Type.this);
  			   LocationAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new LocationAnnotation(addr, LocationAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = LocationAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.ner.LocationAnnotation");
 
  /** @generated */
  final Feature casFeat_location;
  /** @generated */
  final int     casFeatCode_location;
  /** @generated */ 
  public String getLocation(int addr) {
        if (featOkTst && casFeat_location == null)
      jcas.throwFeatMissing("location", "ac.uk.susx.tag.ner.LocationAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_location);
  }
  /** @generated */    
  public void setLocation(int addr, String v) {
        if (featOkTst && casFeat_location == null)
      jcas.throwFeatMissing("location", "ac.uk.susx.tag.ner.LocationAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_location, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public LocationAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_location = jcas.getRequiredFeatureDE(casType, "location", "uima.cas.String", featOkTst);
    casFeatCode_location  = (null == casFeat_location) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_location).getCode();

  }
}



    