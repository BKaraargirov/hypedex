/*
*  Created by Bozhidar Karaargirov
*/

grammar Predicate;

@header {
package hypedex.antlr;
}

prog: formula;


formula
: '(' formula ')'         #Paranthesis
| formula AND formula   #AndConnection
| formula OR formula    #OrConnection
| NOT formula             #Negation
| condition             #ConditionNode
;


condition: id '=' number #EqualCondition
    | id '<' number #LessThanCondition
    | id '>' number #GreaterThanCondition
    | id '<=' number #LessThanEqualCondition
    | id '>=' number #GreaterThanEqualCondition
    | number '=' id #EqualCondition
    | number '<' id #GreaterThanCondition
    | number '>' id #LessThanCondition
    | number '<=' id #GreaterThanEqualCondition
    | number '>=' id #LessThanEqualCondition
    ;



id:ID;
number:NUMBER;

OR: 'OR';
AND: 'AND';
NOT:'!';
ID:([a-zA-Z]+)([a-zA-Z0-9]*);// match identifiers
NUMBER:'-'?('0' .. '9')+ ('.' ('0' .. '9') +)?; // match a number
NEWLINE:'\r'? '\n' -> skip ;     // return newlines to parser (end-statement signal)
WS:[\t]+ -> skip ; // toss out whitespace
