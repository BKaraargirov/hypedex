package hypedex.queryAnalyzer

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import hypedex.antlr.{PredicateLexer, PredicateParser}
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

class QueryAnalyzer {

  def splitQueryCondition(tree: ParseTree, ids: Set[String]): QuerySplitter.Mapping = {
    val querySplitter = new QuerySplitter(ids)
    querySplitter.visit(tree)
  }

  def getIds(tree: ParseTree): Set[String] = {
    val idExtractor = new IdExtractor()
    val walker = new ParseTreeWalker()
    walker.walk(idExtractor, tree)
    idExtractor.ids.toSet
  }

  def createAST(queryCondition: String): ParseTree= {
    val stream = new ByteArrayInputStream(queryCondition.getBytes(StandardCharsets.UTF_8))
    val lexer = new PredicateLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    val parser = new PredicateParser(tokens)

    parser.prog()
  }
}
