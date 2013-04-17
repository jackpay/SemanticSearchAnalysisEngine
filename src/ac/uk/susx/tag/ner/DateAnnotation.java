

/* First created by JCasGen Tue Apr 16 16:21:04 BST 2013 */
package ac.uk.susx.tag.ner;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Apr 17 21:01:15 BST 2013
 * XML source: /Users/jackpay/Documents/workspace/SemanticSearchAnalysisEngine/desc/NERAnnotatorDescriptor.xml
 * @generated */
public class DateAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DateAnnotation.class);
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
  protected DateAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DateAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DateAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DateAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: date

  /** getter for date - gets 
   * @generated */
  public String getDate() {
    if (DateAnnotation_Type.featOkTst && ((DateAnnotation_Type)jcasType).casFeat_date == null)
      jcasType.jcas.throwFeatMissing("date", "ac.uk.susx.tag.ner.DateAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DateAnnotation_Type)jcasType).casFeatCode_date);}
    
  /** setter for date - sets  
   * @generated */
  public void setDate(String v) {
    if (DateAnnotation_Type.featOkTst && ((DateAnnotation_Type)jcasType).casFeat_date == null)
      jcasType.jcas.throwFeatMissing("date", "ac.uk.susx.tag.ner.DateAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DateAnnotation_Type)jcasType).casFeatCode_date, v);}    
  }

    