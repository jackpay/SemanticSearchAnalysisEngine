

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
  //* Feature: location

  /** getter for location - gets 
   * @generated */
  public String getLocation() {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "ac.uk.susx.tag.ner.LocationAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_location);}
    
  /** setter for location - sets  
   * @generated */
  public void setLocation(String v) {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "ac.uk.susx.tag.ner.LocationAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_location, v);}    
  }

    