grammar GyhLang;

@header{
    import java.util.ArrayList; // Você pode não precisar desta importação para apenas contadores, mas é um bom lugar para colocá-la.
}

@members {
    // Contadores para os diferentes tipos de elementos do programa
    private int qtdDecl = 0;
    private int qtdCmd = 0;
    private int qtdAtrib = 0;
    private int qtdEntrada = 0;
    private int qtdSaida = 0;
    private int qtdCond = 0;
    private int qtdRept = 0;
    private int qtdFunc = 0;
}

programa
    : DELIM 'DEC' listaDeclaracoes
      DELIM 'PROG' listaComandos
      {
          // Ações semânticas executadas após a análise completa do programa
          System.out.println("\n--- Resumo da Análise ---");
          System.out.println("  Declarações: " + qtdDecl);
          System.out.println("  Total de Comandos: " + qtdCmd);
          System.out.println("    - Atribuições: " + qtdAtrib);
          System.out.println("    - Entradas: " + qtdEntrada);
          System.out.println("    - Saídas: " + qtdSaida);
          System.out.println("    - Condições (SE): " + qtdCond);
          System.out.println("    - Repetições (ENQTO): " + qtdRept);
          System.out.println("    - Funcao (INI/FIM): " + qtdFunc);
          System.out.println("-------------------------\n");
      }
    ;

listaDeclaracoes : (declaracao { qtdDecl++; })+; // Incrementa qtdDecl para cada declaração
declaracao: VARIAVEL DELIM tipoVar;
tipoVar: 'INT' | 'REAL';

expressaoAritmetica: termoAritmetico (('+'|'-') termoAritmetico)*;
termoAritmetico: fatorAritmetico (('*'|'/') fatorAritmetico)*;
fatorAritmetico: NUMINT | NUMREAL | VARIAVEL | '(' expressaoAritmetica ')';

expressaoRelacional: termoRelacional ('E'|'OU' termoRelacional)*;
termoRelacional: expressaoAritmetica OpRel expressaoAritmetica | '(' expressaoRelacional ')';

listaComandos : (comando { qtdCmd++; })+ ; // Incrementa qtdCmd para cada comando
comando: comandoAtribuicao | comandoEntrada | comandoSaida | comandoCondicao | comandoRepeticao | subAlgoritmo;

comandoAtribuicao: VARIAVEL ATRIB expressaoAritmetica { qtdAtrib++; }; // Incrementa qtdAtrib
comandoEntrada: 'LER' VARIAVEL { qtdEntrada++; }; // Incrementa qtdEntrada
comandoSaida: 'IMPRIMIR' (VARIAVEL|CADEIA) { qtdSaida++; }; // Incrementa qtdSaida
comandoCondicao: 'SE' expressaoRelacional 'ENTAO' comando ('SENAO' comando)? { qtdCond++; }; // Incrementa qtdCond
comandoRepeticao: 'ENQTO' expressaoRelacional comando { qtdRept++; }; // Incrementa qtdRept
subAlgoritmo: 'INI' listaComandos 'FIM' { qtdFunc++; }; // Incrementa qtdSubAlg

// --- Tokens e Palavras-chave ---
PC: 'DEC' | 'PROG' | 'INT' | 'REAL' | 'LER' | 'IMPRIMIR' | 'SE' | 'ENTAO' | 'SENAO' | 'ENQTO' | 'INI' | 'FIM';
OpRel: '<' | '<=' | '>' | '>=' | '==' | '!=';
VARIAVEL: [a-zA-Z_][a-zA-Z0-9_]*;
NUMINT: [0-9]+;
NUMREAL: [0-9]+ '.' [0-9]+;
CADEIA: '"' .*? '"';

// --- Operadores e Símbolos ---
ATRIB: ':=';
DELIM: ':';
PONTO_VIRGULA: ';'; // Não usado nas regras de parsing, mas definido como token
ABRE_PAR: '(';
FECHA_PAR: ')';

// --- Ignorar ---
WS: [ \t\r\n]+ -> skip;
COMENTARIO: '#' ~[\r\n]* -> skip;