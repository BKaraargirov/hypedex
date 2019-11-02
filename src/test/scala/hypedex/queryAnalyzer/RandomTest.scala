package hypedex.queryAnalyzer

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.apache.spark.sql.catalyst.parser.{SqlBaseLexer, SqlBaseParser}
import org.scalatest.FlatSpec

import net.sf.jsqlparser.parser.CCJSqlParserUtil.parse
import net.sf.jsqlparser.statement.select.{PlainSelect, Select}

class RandomTest extends FlatSpec {
  "Test" should "be debugget" in {

    val query = "SELECT * FROM a WHERE x >= 5 AND y < 10"

    val stmt =  parse(query)

    val where = stmt.asInstanceOf[Select].getSelectBody.asInstanceOf[PlainSelect].getWhere
    where
  }

}
