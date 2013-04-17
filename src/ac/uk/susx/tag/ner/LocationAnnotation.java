

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
public class LocationAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(LocationAnnotation.class);
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
  protected LocationAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public LocationAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public LocationAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public LocationAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: percent

  /** getter for percent - gets 
   * @generated */
  public String getPercent() {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_percent == null)
      jcasType.jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.LocationAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_percent);}
    
  /** setter for percent - sets  
   * @generated */
  public void setPercent(String v) {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_percent == null)
      jcasType.jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.LocationAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_percent, v);}    
  }

    