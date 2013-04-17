
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
public class PercentAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PercentAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PercentAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PercentAnnotation(addr, PercentAnnotation_Type.this);
  			   PercentAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PercentAnnotation(addr, PercentAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PercentAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.ner.PercentAnnotation");
 
  /** @generated */
  final Feature casFeat_percent;
  /** @generated */
  final int     casFeatCode_percent;
  /** @generated */ 
  public String getPercent(int addr) {
        if (featOkTst && casFeat_percent == null)
      jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.PercentAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_percent);
  }
  /** @generated */    
  public void setPercent(int addr, String v) {
        if (featOkTst && casFeat_percent == null)
      jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.PercentAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_percent, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PercentAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_percent = jcas.getRequiredFeatureDE(casType, "percent", "uima.cas.String", featOkTst);
    casFeatCode_percent  = (null == casFeat_percent) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_percent).getCode();

  }
}



    