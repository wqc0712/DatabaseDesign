grammar create;

exprs   :   (createExpr
        |   dropExpr
        |   insertExpr
        |   deleteExpr
        |   selectExpr
        ) ';'
        ;
/*
This part is for delete
*/

deleteExpr  :   'delete' 'from' Tid=ID whereExpr?;

/*
This part is for where
*/

whereExpr   :   'where' condExpr;

condExpr    :   col=ID OP value=INPUT ('and' condExpr)?;

/*
This part is for select
*/

selectExpr  :   'select' '*' 'from' Tid=ID whereExpr?;

/*
This part is for insert
*/
insertExpr  :   'insert' 'into' Tid=ID 'values' '(' value=INPUT insertAgm? ')';

insertAgm   :   ',' value=INPUT insertAgm? ;

/*
This part is for drop.
*/

dropExpr    :   'drop' (dropIndex | dropTable);

dropIndex   :   'index' indexid=ID;

dropTable   :   'table' tableid=ID;

/*
This part is for create.
*/
createExpr  :   'create' agmts;

agmts : tableExpr | indexExpr;

indexExpr   :   'index' indexAgmt;

indexAgmt   :   indexid=ID 'on' tableid=ID '(' listid=ID ')';

tableExpr   :   'table' tableAgmt;

tableAgmt   :   Tid=ID '(' tableAgm tablePrim ')' ';';

tableAgm    :   Aid=ID type=('int'|CHAR|'float') uni=UNI? (',' tableAgm)? ','? ;

tablePrim   :   'primary' 'key' '(' Pid=ID ')';
CHAR : 'char' '(' len=INT ')';

/*
This is some globle define
*/
INPUT       : (INT|STRING|FLOAT);
STRING : '\'' str=ID '\'';
ID : [a-zA-Z0-9]+ ;
UNI :   'unique';
OP  :   '='|'<>'|'>'|'<'|'<='|'>=';
INT :   '-'? [0-9]+;
FLOAT  :  '-'? [0-9]+ ('.' [0-9]+)?;
WS  :   [ \t\r\n]+ -> skip;



