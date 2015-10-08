grammar create;

exprs   :   createExpr
        ;

createExpr  :   'create' agmts;

agmts : tableExpr ;//| indexExpr;

tableExpr   :   'table' tableAgmt;

tableAgmt   :   id=ID '(' tableAgm tablePrim ')' ';';

tableAgm    :   Agm (',' tableAgm)? ','? ;

Agm :  ID TYPE UNI?;

tablePrim   :   'primary' 'key' '(' ID ')';
ID : [a-zA-Z0-9]+ ;
TYPE : INT|CHAR|FLOAT;
UNI :   'unique';
INT : 'int';
CHAR : 'char' '(' NUM ')';
NUM :[0-9]+ ;
FLOAT :'float';
WS  :   [ \t\r\n]+ -> skip;


