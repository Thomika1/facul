grammar GyhLang;

prog: ':' 'DEC' listaDeclaracoes ':' 'PROG' listaComandos;

listaDeclaracoes: declaracao (';' declaracao)*;
declaracao: VARIAVEL ':' tipoVar;
tipoVar: 'INT' | 'REAL';

expressaoAritmetica: termoAritmetico (('+'|'-') termoAritmetico)*;
termoAritmetico: fatorAritmetico (('*'|'/') fatorAritmetico)*;
fatorAritmetico: NUMINT | NUMREAL | VARIAVEL | '(' expressaoAritmetica ')';

expressaoRelacional: termoRelacional ('E'|'OU' termoRelacional)*;
termoRelacional: expressaoAritmetica OpRel expressaoAritmetica | '(' expressaoRelacional ')';

listaComandos: comando (';' comando)*;
comando: comandoAtribuicao | comandoEntrada | comandoSaida | comandoCondicao | comandoRepeticao | subAlgoritmo;

comandoAtribuicao: VARIAVEL ':=' expressaoAritmetica;
comandoEntrada: 'LER' VARIAVEL;
comandoSaida: 'IMPRIMIR' (VARIAVEL|CADEIA);
comandoCondicao: 'SE' expressaoRelacional 'ENTAO' comando ('SENAO' comando)?;
comandoRepeticao: 'ENQTO' expressaoRelacional comando;
subAlgoritmo: 'INI' listaComandos 'FIM';

// Tokens e palavras-chave
PC: 'DEC' | 'PROG' | 'INT' | 'REAL' | 'LER' | 'IMPRIMIR' | 'SE' | 'ENTAO' | 'SENAO' | 'ENQTO' | 'INI' | 'FIM';
OpRel: '<' | '<=' | '>' | '>=' | '==' | '!=';
VARIAVEL: [a-zA-Z_][a-zA-Z0-9_]*;
NUMINT: [0-9]+;
NUMREAL: [0-9]+ '.' [0-9]+;
CADEIA: '"' .*? '"';

// Operadores e símbolos
ATRIB: ':=';
DELIM: ':';
PONTO_VIRGULA: ';';
ABRE_PAR: '(';
FECHA_PAR: ')';

// Ignorar
WS: [ \t\r\n]+ -> skip;
COMENTARIO: '#' ~[\r\n]* -> skip;