

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
public class PercentAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PercentAnnotation.class);
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
  protected PercentAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PercentAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PercentAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PercentAnnotation(JCas jcas, int begin, int end) {
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
    if (PercentAnnotation_Type.featOkTst && ((PercentAnnotation_Type)jcasType).casFeat_percent == null)
      jcasType.jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.PercentAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PercentAnnotation_Type)jcasType).casFeatCode_percent);}
    
  /** setter for percent - sets  
   * @generated */
  public void setPercent(String v) {
    if (PercentAnnotation_Type.featOkTst && ((PercentAnnotation_Type)jcasType).casFeat_percent == null)
      jcasType.jcas.throwFeatMissing("percent", "ac.uk.susx.tag.ner.PercentAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((PercentAnnotation_Type)jcasType).casFeatCode_percent, v);}    
  }

    