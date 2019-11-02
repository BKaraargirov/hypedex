import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import net.sf.jsqlparser.parser.CCJSqlParserUtil.parse
import net.sf.jsqlparser.statement.select.{PlainSelect, Select}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.apache.spark.sql.catalyst.parser.{CatalystSqlParser, SqlBaseLexer, SqlBaseParser}

val query = "SELECT x FROM a WHERE x >= 5 AND y < 10"

val stmt =  parse(query).asInstanceOf[PlainSelect]

stmt.getWhere
