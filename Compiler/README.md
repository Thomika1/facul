# 🛠️ Compilador para a Linguagem GYH

**Projeto educacional** desenvolvido para demonstrar os princípios de construção de compiladores, com implementações **manual** e **automática** dos analisadores léxico e sintático.

## 📋 Visão Geral
- **Linguagem Alvo**: GYH (linguagem didática com suporte a variáveis, condicionais, loops e operações aritméticas)
- **Output**: Código C funcional
- **Abordagens Implementadas**:
  - **Versão Manual**: Analisador léxico e sintático escritos do zero em Java
  - **Versão Automática**: Implementação paralela usando ANTLR4

## 🧩 Funcionalidades
### Análise Léxica
- Identificação de tokens (variáveis, números, operadores, palavras-chave)
- Detecção de erros léxicos (símbolos inválidos)

### Análise Sintática
- Validação da estrutura do código-fonte conforme gramática definida
- Dois modos de implementação:
  - **Manual**: Algoritmo recursivo com pilha de estados
  - **Automático**: Geração via ANTLR4

### Análise Semântica
- Tabela de símbolos para gerenciamento de escopos
- Verificação de tipos (INT/REAL)
- Detecção de:
  - Variáveis não declaradas
  - Atribuições inválidas
  - Divisão por zero

### Geração de Código
- Tradução para C99 com:
  - Preservação da lógica original
  - Mapeamento de tipos (GYH → C)

## 🛠️ Tecnologias
| Componente       | Tecnologias Utilizadas |
|------------------|-----------------------|
| Frontend         | Java 11               |
| Análise Automática | ANTLR4               |


