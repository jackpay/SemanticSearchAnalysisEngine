
/* First created by JCasGen Wed Jun 12 01:25:51 BST 2013 */
package ac.uk.susx.tag.chunk.chunktoken;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Jun 12 01:25:51 BST 2013
 * @generated */
public class ChunkTokenAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ChunkTokenAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ChunkTokenAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ChunkTokenAnnotation(addr, ChunkTokenAnnotation_Type.this);
  			   ChunkTokenAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ChunkTokenAnnotation(addr, ChunkTokenAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ChunkTokenAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation");
 
  /** @generated */
  final Feature casFeat_chunk_token;
  /** @generated */
  final int     casFeatCode_chunk_token;
  /** @generated */ 
  public String getChunk_token(int addr) {
        if (featOkTst && casFeat_chunk_token == null)
      jcas.throwFeatMissing("chunk_token", "ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_chunk_token);
  }
  /** @generated */    
  public void setChunk_token(int addr, String v) {
        if (featOkTst && casFeat_chunk_token == null)
      jcas.throwFeatMissing("chunk_token", "ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_chunk_token, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ChunkTokenAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_chunk_token = jcas.getRequiredFeatureDE(casType, "chunk_token", "uima.cas.String", featOkTst);
    casFeatCode_chunk_token  = (null == casFeat_chunk_token) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_chunk_token).getCode();

  }
}



    