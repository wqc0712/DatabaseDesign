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

condExpr    :   col=ID op=OP value=(INTEGER|STRING|FLOAT) ('and' condExpr)?;

/*
This part is for select
*/

selectExpr  :   'select' '*' 'from' Tid=ID whereExpr?;

/*
This part is for insert
*/
insertExpr  :   'insert' 'into' Tid=ID 'values' '(' value=(INTEGER|STRING|FLOAT) insertAgm? ')';

insertAgm   :   ',' value=(INTEGER|STRING|FLOAT) insertAgm? ;

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

tableAgmt   :   Tid=ID '(' tableAgm tablePrim ')';

tableAgm    :   tableEle ',' (tableAgm)? ;

tableEle    :   Aid=ID type=('int'|CHAR|'float') (uni='unique')?;

tablePrim   :   'primary' 'key' '(' Pid=ID ')';
CHAR : 'char' WS? '(' len=INT ')';

/*
This is some globle define
*/
STRING : '\'' str=(ID|INTEGER) '\'';
ID : (ALPHA) (ALPHA|DIGIT)*;
OP  :   '='|'<>'|'>'|'<'|'<='|'>=';
INTEGER : '-'? DIGIT+;
INT :    [0-9]+;
FLOAT  :  '-'? [0-9]+ ('.' [0-9]+);
WS  :   [ \t\r\n]+ -> skip;

fragment
DIGIT : [0-9];
fragment
ALPHA : [a-zA-Z];
