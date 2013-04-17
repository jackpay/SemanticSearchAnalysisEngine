

/* First created by JCasGen Tue Apr 16 16:21:04 BST 2013 */
package ac.uk.susx.tag.ner;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Apr 16 16:21:04 BST 2013
 * XML source: /Users/jp242/Documents/workspace/SemanticSearchAnalysisEngine/desc/NERAnnotatorDescriptor.xml
 * @generated */
public class TimeAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TimeAnnotation.class);
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
  protected TimeAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TimeAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TimeAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TimeAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: time

  /** getter for time - gets 
   * @generated */
  public String getTime() {
    if (TimeAnnotation_Type.featOkTst && ((TimeAnnotation_Type)jcasType).casFeat_time == null)
      jcasType.jcas.throwFeatMissing("time", "ac.uk.susx.tag.ner.TimeAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TimeAnnotation_Type)jcasType).casFeatCode_time);}
    
  /** setter for time - sets  
   * @generated */
  public void setTime(String v) {
    if (TimeAnnotation_Type.featOkTst && ((TimeAnnotation_Type)jcasType).casFeat_time == null)
      jcasType.jcas.throwFeatMissing("time", "ac.uk.susx.tag.ner.TimeAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TimeAnnotation_Type)jcasType).casFeatCode_time, v);}    
  }

    