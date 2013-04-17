
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

/** An annotator which perfoms person entity recognition
 * Updated by JCasGen Tue Apr 16 16:21:04 BST 2013
 * @generated */
public class PersonAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PersonAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PersonAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PersonAnnotation(addr, PersonAnnotation_Type.this);
  			   PersonAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PersonAnnotation(addr, PersonAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PersonAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.ner.PersonAnnotation");
 
  /** @generated */
  final Feature casFeat_person;
  /** @generated */
  final int     casFeatCode_person;
  /** @generated */ 
  public String getPerson(int addr) {
        if (featOkTst && casFeat_person == null)
      jcas.throwFeatMissing("person", "ac.uk.susx.tag.ner.PersonAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_person);
  }
  /** @generated */    
  public void setPerson(int addr, String v) {
        if (featOkTst && casFeat_person == null)
      jcas.throwFeatMissing("person", "ac.uk.susx.tag.ner.PersonAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_person, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PersonAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_person = jcas.getRequiredFeatureDE(casType, "person", "uima.cas.String", featOkTst);
    casFeatCode_person  = (null == casFeat_person) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_person).getCode();

  }
}



    