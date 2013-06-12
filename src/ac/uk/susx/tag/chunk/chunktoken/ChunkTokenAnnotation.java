

/* First created by JCasGen Wed Jun 12 01:25:51 BST 2013 */
package ac.uk.susx.tag.chunk.chunktoken;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jun 12 01:25:51 BST 2013
 * XML source: /Users/jackpay/Documents/workspace/SemanticSearchAnalysisEngine/desc/ChunkTokenAnnotatorDescriptor.xml
 * @generated */
public class ChunkTokenAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ChunkTokenAnnotation.class);
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
  protected ChunkTokenAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ChunkTokenAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ChunkTokenAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ChunkTokenAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: chunk_token

  /** getter for chunk_token - gets 
   * @generated */
  public String getChunk_token() {
    if (ChunkTokenAnnotation_Type.featOkTst && ((ChunkTokenAnnotation_Type)jcasType).casFeat_chunk_token == null)
      jcasType.jcas.throwFeatMissing("chunk_token", "ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ChunkTokenAnnotation_Type)jcasType).casFeatCode_chunk_token);}
    
  /** setter for chunk_token - sets  
   * @generated */
  public void setChunk_token(String v) {
    if (ChunkTokenAnnotation_Type.featOkTst && ((ChunkTokenAnnotation_Type)jcasType).casFeat_chunk_token == null)
      jcasType.jcas.throwFeatMissing("chunk_token", "ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ChunkTokenAnnotation_Type)jcasType).casFeatCode_chunk_token, v);}    
  }

    