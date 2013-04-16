
/* First created by JCasGen Wed Apr 10 14:07:53 BST 2013 */
package ac.uk.susx.tag.sentann;

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
 * Updated by JCasGen Wed Apr 10 14:07:53 BST 2013
 * @generated */
public class SentenceAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SentenceAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SentenceAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SentenceAnnotation(addr, SentenceAnnotation_Type.this);
  			   SentenceAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SentenceAnnotation(addr, SentenceAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SentenceAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.sentann.SentenceAnnotation");
 
  /** @generated */
  final Feature casFeat_SentenceAnnotation;
  /** @generated */
  final int     casFeatCode_SentenceAnnotation;
  /** @generated */ 
  public String getSentenceAnnotation(int addr) {
        if (featOkTst && casFeat_SentenceAnnotation == null)
      jcas.throwFeatMissing("SentenceAnnotation", "ac.uk.susx.tag.sentann.SentenceAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SentenceAnnotation);
  }
  /** @generated */    
  public void setSentenceAnnotation(int addr, String v) {
        if (featOkTst && casFeat_SentenceAnnotation == null)
      jcas.throwFeatMissing("SentenceAnnotation", "ac.uk.susx.tag.sentann.SentenceAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_SentenceAnnotation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SentenceAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_SentenceAnnotation = jcas.getRequiredFeatureDE(casType, "SentenceAnnotation", "uima.cas.String", featOkTst);
    casFeatCode_SentenceAnnotation  = (null == casFeat_SentenceAnnotation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SentenceAnnotation).getCode();

  }
}



    