
/* First created by JCasGen Mon Apr 20 15:25:38 BST 2015 */
package ac.uk.susx.tag.topic;

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
 * Updated by JCasGen Tue Apr 21 14:28:08 BST 2015
 * @generated */
public class TopicAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TopicAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TopicAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TopicAnnotation(addr, TopicAnnotation_Type.this);
  			   TopicAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TopicAnnotation(addr, TopicAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TopicAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.topic.TopicAnnotation");
 
  /** @generated */
  final Feature casFeat_TopicAnnotation;
  /** @generated */
  final int     casFeatCode_TopicAnnotation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getTopicAnnotation(int addr) {
        if (featOkTst && casFeat_TopicAnnotation == null)
      jcas.throwFeatMissing("TopicAnnotation", "ac.uk.susx.tag.topic.TopicAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_TopicAnnotation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTopicAnnotation(int addr, double v) {
        if (featOkTst && casFeat_TopicAnnotation == null)
      jcas.throwFeatMissing("TopicAnnotation", "ac.uk.susx.tag.topic.TopicAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_TopicAnnotation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TopicAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_TopicAnnotation = jcas.getRequiredFeatureDE(casType, "TopicAnnotation", "uima.cas.Double", featOkTst);
    casFeatCode_TopicAnnotation  = (null == casFeat_TopicAnnotation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_TopicAnnotation).getCode();

  }
}



    