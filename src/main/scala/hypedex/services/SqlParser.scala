package hypedex.services

import net.sf.jsqlparser.parser.CCJSqlParserUtil.parse
import net.sf.jsqlparser.statement.select.{PlainSelect, Select}

class SqlParser {
  def extractWhereClause(query: String): String = {
    val stmt =  parse(query)

    val parsedQuery = stmt.asInstanceOf[Select].getSelectBody

    val whereClause = parsedQuery.asInstanceOf[PlainSelect].getWhere

    whereClause.toString
  }
}
