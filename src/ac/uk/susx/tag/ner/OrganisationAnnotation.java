

/* First created by JCasGen Tue Apr 16 16:21:04 BST 2013 */
package ac.uk.susx.tag.ner;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Apr 17 21:01:16 BST 2013
 * XML source: /Users/jackpay/Documents/workspace/SemanticSearchAnalysisEngine/desc/NERAnnotatorDescriptor.xml
 * @generated */
public class OrganisationAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(OrganisationAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected OrganisationAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public OrganisationAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public OrganisationAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public OrganisationAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: organisation

  /** getter for organisation - gets 
   * @generated */
  public String getOrganisation() {
    if (OrganisationAnnotation_Type.featOkTst && ((OrganisationAnnotation_Type)jcasType).casFeat_organisation == null)
      jcasType.jcas.throwFeatMissing("organisation", "ac.uk.susx.tag.ner.OrganisationAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OrganisationAnnotation_Type)jcasType).casFeatCode_organisation);}
    
  /** setter for organisation - sets  
   * @generated */
  public void setOrganisation(String v) {
    if (OrganisationAnnotation_Type.featOkTst && ((OrganisationAnnotation_Type)jcasType).casFeat_organisation == null)
      jcasType.jcas.throwFeatMissing("organisation", "ac.uk.susx.tag.ner.OrganisationAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((OrganisationAnnotation_Type)jcasType).casFeatCode_organisation, v);}    
  }

    