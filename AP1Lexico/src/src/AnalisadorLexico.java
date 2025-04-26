package src;

import java.io.IOException;

public class AnalisadorLexico {
    public LeitorArquivo ldat;
    public Integer counter;
    public Integer pos;
    public String line;

    public AnalisadorLexico(String nome) throws IOException {
        ldat = new LeitorArquivo(nome);
        counter = 1;
        pos = 0;
        line = ldat.lerProxLinha();
    }

    public Token proximoToken() throws IOException {
        while (pos <= line.length()) {
            if (pos == line.length()) {
                line = ldat.lerProxLinha();
                counter++;
                pos = 0;

                if (line == null) {
                    System.out.println("Fim arq");
                    break;
                }
            }

            for (; pos < line.length(); ) {
                char c = line.charAt(pos);
                pos++;

                switch (c) {
                    case '\n': case '\t': case ' ':
                        break; // ignora espaços e quebras de linha

                    // Operadores aritméticos
                    case '+': return new Token("+", TipoToken.OpAritSoma, counter);
                    case '-': return new Token("-", TipoToken.OpAritSub, counter);
                    case '*': return new Token("*", TipoToken.OpAritMult, counter);
                    case '/': return new Token("/", TipoToken.OpAritDiv, counter);

                    // Operadores relacionais
                    case '>':
                        if (pos < line.length() && line.charAt(pos) == '=') {
                            pos++;
                            return new Token(">=", TipoToken.OpRelMaiorIgual, counter);
                        } else {
                            return new Token(">", TipoToken.OpRelMaior, counter);
                        }
                    case '<':
                        if (pos < line.length() && line.charAt(pos) == '=') {
                            pos++;
                            return new Token("<=", TipoToken.OpRelMenorIgual, counter);
                        } else {
                            return new Token("<", TipoToken.OpRelMenor, counter);
                        }
                    case '=':
                        if (pos < line.length() && line.charAt(pos) == '=') {
                            pos++;
                            return new Token("==", TipoToken.OpRelIgual, counter);
                        } else {
                            return new Token("=", TipoToken.ERROR, counter);
                        }
                    case '!':
                        if (pos < line.length() && line.charAt(pos) == '=') {
                            pos++;
                            return new Token("!=", TipoToken.OpRelDif, counter);
                        } else {
                            return new Token("!", TipoToken.ERROR, counter);
                        }

                    // Atribuição e delimitador
                    case ':':
                        if (pos < line.length() && line.charAt(pos) == '=') {
                            pos++;
                            return new Token(":=", TipoToken.Atrib, counter);
                        } else {
                            return new Token(":", TipoToken.Delim, counter);
                        }

                    // Parênteses
                    case '(':
                        return new Token("(", TipoToken.AbrePar, counter);
                    case ')':
                        return new Token(")", TipoToken.FechaPar, counter);

                    // Cadeia de caracteres
                    case '"': {
                        StringBuilder cadeia = new StringBuilder();
                        cadeia.append('"');
                        while (pos < line.length() && line.charAt(pos) != '"') {
                            cadeia.append(line.charAt(pos));
                            pos++;
                        }
                        if (pos < line.length() && line.charAt(pos) == '"') {
                            cadeia.append('"');
                            pos++;
                            return new Token(cadeia.toString(), TipoToken.Cadeia, counter);
                        } else {
                            return new Token(cadeia.toString(), TipoToken.ERROR, counter);
                        }
                    }

                    // Identificadores, variáveis e números
                    default:
                        if (Character.isLetter(c)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(c);
                            while (pos < line.length() && Character.isLetterOrDigit(line.charAt(pos))) {
                                sb.append(line.charAt(pos));
                                pos++;
                            }
                            String lexema = sb.toString();

                            // Palavras-chave
                            switch (lexema) {
                                case "LER": return new Token("LER", TipoToken.PCLer, counter);
                                case "PROG": return new Token("PROG", TipoToken.PCProg, counter);
                                case "IMPRIMIR": return new Token("IMPRIMIR", TipoToken.PCImprimir, counter);
                                case "SE": return new Token("SE", TipoToken.PCSe, counter);
                                case "ENTAO": return new Token("ENTAO", TipoToken.PCEntao, counter);
                                case "ENQTO": return new Token("ENQTO", TipoToken.PCEnqto, counter);
                                case "INI": return new Token("INI", TipoToken.PCIni, counter);
                                case "FIM": return new Token("FIM", TipoToken.PCFim, counter);
                                case "E": return new Token("E", TipoToken.OpBoolE, counter);
                                case "OU": return new Token("OU", TipoToken.OpBoolOu, counter);
                                case "INT": return new Token("INT", TipoToken.PCInt, counter);
                                case "DEC": return new Token("DEC", TipoToken.PCDec, counter);
                                default:
                                    if (Character.isLowerCase(lexema.charAt(0))) {
                                        return new Token(lexema, TipoToken.Var, counter); // é variável
                                    } else {
                                        return new Token(lexema, TipoToken.ERROR, counter); // erro
                                    }
                            }
                        } else if (Character.isDigit(c)) {
                            StringBuilder num = new StringBuilder();
                            num.append(c);
                            boolean isReal = false;

                            while (pos < line.length()) {
                                char next = line.charAt(pos);
                                if (Character.isDigit(next)) {
                                    num.append(next);
                                    pos++;
                                } else if (next == '.' && !isReal) {
                                    isReal = true;
                                    num.append(next);
                                    pos++;
                                } else {
                                    break;
                                }
                            }

                            if (isReal) {
                                return new Token(num.toString(), TipoToken.NumReal, counter);
                            } else {
                                return new Token(num.toString(), TipoToken.NumInt, counter);
                            }
                        } else {
                            return new Token(Character.toString(c), TipoToken.ERROR, counter);
                        }
                } // fim switch
            } // fim for
        } // fim while
        return null;
    }
}
