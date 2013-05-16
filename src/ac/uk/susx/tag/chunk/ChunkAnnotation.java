

/* First created by JCasGen Wed May 08 15:35:42 BST 2013 */
package ac.uk.susx.tag.chunk;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed May 08 15:35:42 BST 2013
 * XML source: /Users/jp242/Documents/workspace/SemanticSearchAnalysisEngine/desc/ChunkAnnotatorDescriptor.xml
 * @generated */
public class ChunkAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ChunkAnnotation.class);
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
  protected ChunkAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ChunkAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ChunkAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ChunkAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: chunk

  /** getter for chunk - gets 
   * @generated */
  public String getChunk() {
    if (ChunkAnnotation_Type.featOkTst && ((ChunkAnnotation_Type)jcasType).casFeat_chunk == null)
      jcasType.jcas.throwFeatMissing("chunk", "ac.uk.susx.tag.chunk.ChunkAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ChunkAnnotation_Type)jcasType).casFeatCode_chunk);}
    
  /** setter for chunk - sets  
   * @generated */
  public void setChunk(String v) {
    if (ChunkAnnotation_Type.featOkTst && ((ChunkAnnotation_Type)jcasType).casFeat_chunk == null)
      jcasType.jcas.throwFeatMissing("chunk", "ac.uk.susx.tag.chunk.ChunkAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ChunkAnnotation_Type)jcasType).casFeatCode_chunk, v);}    
  }

    