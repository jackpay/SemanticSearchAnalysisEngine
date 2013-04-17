

/* First created by JCasGen Tue Apr 16 16:21:04 BST 2013 */
package ac.uk.susx.tag.ner;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** An annotator which perfoms person entity recognition
 * Updated by JCasGen Tue Apr 16 16:21:04 BST 2013
 * XML source: /Users/jp242/Documents/workspace/SemanticSearchAnalysisEngine/desc/NERAnnotatorDescriptor.xml
 * @generated */
public class PersonAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PersonAnnotation.class);
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
  protected PersonAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PersonAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PersonAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PersonAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: person

  /** getter for person - gets The named entity
   * @generated */
  public String getPerson() {
    if (PersonAnnotation_Type.featOkTst && ((PersonAnnotation_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "ac.uk.susx.tag.ner.PersonAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PersonAnnotation_Type)jcasType).casFeatCode_person);}
    
  /** setter for person - sets The named entity 
   * @generated */
  public void setPerson(String v) {
    if (PersonAnnotation_Type.featOkTst && ((PersonAnnotation_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "ac.uk.susx.tag.ner.PersonAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((PersonAnnotation_Type)jcasType).casFeatCode_person, v);}    
  }

    