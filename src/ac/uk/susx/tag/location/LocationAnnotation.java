

/* First created by JCasGen Thu Oct 23 16:15:42 BST 2014 */
package ac.uk.susx.tag.location;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Specifies a location/country discovered in the input
 * Updated by JCasGen Thu Oct 23 16:15:42 BST 2014
 * XML source: /Volumes/LocalDataHD/jp242/Documents/workspace/SemanticSearchAnalysisEngine/desc/LocationAnnotationDescriptor.xml
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
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected LocationAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public LocationAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public LocationAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public LocationAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: location

  /** getter for location - gets The string representation of the location. e.g. "Africa"
   * @generated
   * @return value of the feature 
   */
  public String getLocation() {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "ac.uk.susx.tag.location.LocationAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_location);}
    
  /** setter for location - sets The string representation of the location. e.g. "Africa" 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLocation(String v) {
    if (LocationAnnotation_Type.featOkTst && ((LocationAnnotation_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "ac.uk.susx.tag.location.LocationAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((LocationAnnotation_Type)jcasType).casFeatCode_location, v);}    
  }

    