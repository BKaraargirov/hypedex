/*
*  Created by Bozhidar Karaargirov
*/

grammar predicate;

prog: formula;

formula
: condition              #fCondition
| formula 'AND' formula  #fAnd
| formula 'OR' formula   #fOr
| '(' formula ')'        #fParens
| NOT formula            #fNegation
;

condition: ID '=' NUMBER #Equals
    | ID '<' NUMBER      #LessThan
    | ID '>' NUMBER      #BiggerThan
    | ID '<=' NUMBER     #LessThanEquals
    | ID '>=' NUMBER     #BiggerThanEquals
    ;

NOT : '!';
ID : [a-zA-Z]+ ;      ​// match identifiers​
NUMBER : ('0' .. '9') + ('.' ('0' .. '9') +)?; // match a number
INT : [0-9]+ ;         ​// match integers​
NEWLINE:​'\r'​? ​'\n' -> skip​ ;     ​// return newlines to parser (end-statement signal)​
WS : [ \t]+ -> skip ; ​// toss out whitespace​