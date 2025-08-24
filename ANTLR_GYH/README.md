# Compilador GYH - Versão ANTLR4

Implementação eficiente do compilador GYH utilizando **ANTLR4** para geração automática de analisadores.

## ✨ Diferenciais
- Gramática formal em `GYHLang.g4`
- Geração de lexer/parser em 3 passos:
  1. Definir regras léxicas/sintáticas
  2. ANTLR gera código Java
  3. Visitors para análise semântica

