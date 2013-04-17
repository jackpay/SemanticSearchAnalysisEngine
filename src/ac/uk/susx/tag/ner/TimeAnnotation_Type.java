
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
 * Updated by JCasGen Tue Apr 16 16:21:04 BST 2013
 * @generated */
public class TimeAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TimeAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TimeAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TimeAnnotation(addr, TimeAnnotation_Type.this);
  			   TimeAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TimeAnnotation(addr, TimeAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TimeAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.ner.TimeAnnotation");
 
  /** @generated */
  final Feature casFeat_time;
  /** @generated */
  final int     casFeatCode_time;
  /** @generated */ 
  public String getTime(int addr) {
        if (featOkTst && casFeat_time == null)
      jcas.throwFeatMissing("time", "ac.uk.susx.tag.ner.TimeAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_time);
  }
  /** @generated */    
  public void setTime(int addr, String v) {
        if (featOkTst && casFeat_time == null)
      jcas.throwFeatMissing("time", "ac.uk.susx.tag.ner.TimeAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_time, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TimeAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_time = jcas.getRequiredFeatureDE(casType, "time", "uima.cas.String", featOkTst);
    casFeatCode_time  = (null == casFeat_time) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_time).getCode();

  }
}



    