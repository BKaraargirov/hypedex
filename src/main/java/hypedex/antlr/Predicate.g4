/*
*  Created by Bozhidar Karaargirov
*/

grammar Predicate;

prog: formula;

formula
: '(' formula ')'         #Paranthesis
| condition               #SingleFormula
| formula AND formula   #AndConnection
| formula OR formula    #OrConnection
| NOT formula             #Negation
;

condition: id '=' NUMBER
    | id '<' NUMBER
    | id '>' NUMBER
    | id '<=' NUMBER
    | id '>=' NUMBER
    | NUMBER '=' id
    | NUMBER '<' id
    | NUMBER '>' id
    | NUMBER '<=' id
    | NUMBER '>=' id
    ;

id:ID;

OR: 'OR';
AND: 'AND';
NOT:'!';
ID:[a-zA-Z][a-zA-Z0-9]*?;// match identifiers
NUMBER:'-'?('0' .. '9')+ ('.' ('0' .. '9') +)?; // match a number
NEWLINE:'\r'? '\n' -> skip ;     // return newlines to parser (end-statement signal)
WS:[\t]+ -> skip ; // toss out whitespace
