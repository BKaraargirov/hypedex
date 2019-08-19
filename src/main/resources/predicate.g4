/*
*  Created by Bozhidar Karaargirov
*/

grammar predicate;

prog: formula;

formula
: '(' formula ')'
| condition
| formula 'AND' formula
| formula 'OR' formula
| NOT formula
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

NOT:'!';
ID:[a-zA-Z][a-zA-Z0-9]*?;// match identifiers
NUMBER:'-'?('0' .. '9')+ ('.' ('0' .. '9') +)?; // match a number
NEWLINE:'\r'? '\n' -> skip ;     // return newlines to parser (end-statement signal)
WS:[\t]+ -> skip ; // toss out whitespace
