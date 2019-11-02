package hypedex.services

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import hypedex.antlr.{PredicateLexer, PredicateParser}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.antlr.v4.runtime.tree.ParseTree

class SyntaxTreeFactory {
  def createAST(queryCondition: String): ParseTree= {
    val stream = new ByteArrayInputStream(queryCondition.getBytes(StandardCharsets.UTF_8))
    val lexer = new PredicateLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8))
    val tokens = new CommonTokenStream(lexer)
    val parser = new PredicateParser(tokens)

    parser.prog()
  }
}
