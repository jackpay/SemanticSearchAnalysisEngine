
/* First created by JCasGen Wed May 08 15:35:42 BST 2013 */
package ac.uk.susx.tag.chunk;

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
 * Updated by JCasGen Wed May 08 15:35:42 BST 2013
 * @generated */
public class ChunkAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ChunkAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ChunkAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ChunkAnnotation(addr, ChunkAnnotation_Type.this);
  			   ChunkAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ChunkAnnotation(addr, ChunkAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ChunkAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ac.uk.susx.tag.chunk.ChunkAnnotation");
 
  /** @generated */
  final Feature casFeat_chunk;
  /** @generated */
  final int     casFeatCode_chunk;
  /** @generated */ 
  public String getChunk(int addr) {
        if (featOkTst && casFeat_chunk == null)
      jcas.throwFeatMissing("chunk", "ac.uk.susx.tag.chunk.ChunkAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_chunk);
  }
  /** @generated */    
  public void setChunk(int addr, String v) {
        if (featOkTst && casFeat_chunk == null)
      jcas.throwFeatMissing("chunk", "ac.uk.susx.tag.chunk.ChunkAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_chunk, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ChunkAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_chunk = jcas.getRequiredFeatureDE(casType, "chunk", "uima.cas.String", featOkTst);
    casFeatCode_chunk  = (null == casFeat_chunk) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_chunk).getCode();

  }
}



    