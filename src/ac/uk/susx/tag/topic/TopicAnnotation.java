

/* First created by JCasGen Mon Apr 20 15:25:38 BST 2015 */
package ac.uk.susx.tag.topic;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Apr 21 14:28:08 BST 2015
 * XML source: /Volumes/LocalDataHD/jp242/Documents/workspace/SemanticSearchAnalysisEngine/desc/TopicDistributionDescriptor.xml
 * @generated */
public class TopicAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TopicAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TopicAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TopicAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TopicAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TopicAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: TopicAnnotation

  /** getter for TopicAnnotation - gets 
   * @generated
   * @return value of the feature 
   */
  public double getTopicAnnotation() {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_TopicAnnotation == null)
      jcasType.jcas.throwFeatMissing("TopicAnnotation", "ac.uk.susx.tag.topic.TopicAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_TopicAnnotation);}
    
  /** setter for TopicAnnotation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTopicAnnotation(double v) {
    if (TopicAnnotation_Type.featOkTst && ((TopicAnnotation_Type)jcasType).casFeat_TopicAnnotation == null)
      jcasType.jcas.throwFeatMissing("TopicAnnotation", "ac.uk.susx.tag.topic.TopicAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((TopicAnnotation_Type)jcasType).casFeatCode_TopicAnnotation, v);}    
  }

    