package edu.stanford.nlp.trees.tregex.tsurgeon;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.util.Generics;

import java.util.Map;

/**
 * @author Roger Levy (rog@nlp.stanford.edu)
 */
class TsurgeonPatternRoot extends TsurgeonPattern {

  final CoindexationGenerator coindexer = new CoindexationGenerator();
  Map<String, Tree> newNodeNames;


  public TsurgeonPatternRoot(TsurgeonPattern[] children) {
    super("operations: ", children);
    setRoot(this);
  }


  /**
   * returns null if one of the surgeries eliminates the tree entirely.  The
   * operated-on tree is not to be trusted in this instance.
   */
  @Override
  public Tree evaluate(Tree t, TregexMatcher m) {
    newNodeNames = Generics.newHashMap();
    coindexer.setLastIndex(t);
    for (TsurgeonPattern child : children) {
      t = child.evaluate(t, m);
      if (t == null) {
        return null;
      }
    }
    return t;
  }
}
