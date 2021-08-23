package com.pratica.ed2.plagio;

import com.pratica.ed2.estrutura.AVLTree;
import com.pratica.ed2.estrutura.MultMap;
import com.pratica.ed2.estrutura.RubroNegra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Documento {
    public String nome;
    private String texto;
    private ArrayList<String> palavras;
    MultMap<String, Integer> mapa;
    AVLTree arvoreAVL;
    RubroNegra<String, Integer> arvoreRubroNegra;

    public Documento(String caminho) {
        this.nome = null;
        try {
            File arquivo = new File(caminho);
            this.nome = arquivo.getName();
            Scanner scanner = new Scanner(arquivo);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(" ");
            }
            this.texto = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            this.texto = null;
        }

        this.geraPalavras();
        this.geraMapa();
        this.geraArvoreAVL();
        this.geraArvoreRubroNegra();
    }

    public String getTexto() {
        return this.texto;
    }

    public ArrayList<String> getPalavras() {
        return this.palavras;
    }

    public void geraPalavras() {
        if (this.texto == null) {
            this.palavras = null;
            return;
        }

        String padrao = "[A-Za-zÀ-ÖØ-öø-ÿ\\-]+";
        ArrayList<String> todasPalavras = new ArrayList<>();
        Matcher matcher = Pattern.compile(padrao).matcher(texto);
        while (matcher.find()) {
            todasPalavras.add(matcher.group());
        }
        this.palavras = todasPalavras;
    }

    public void geraMapa() {
        this.mapa = new MultMap<>(this.palavras.size() * 10);
        for (int i = 0; i < this.palavras.size(); i++) {
            this.mapa.put(this.palavras.get(i), i);
        }
    }

    public MultMap<String, Integer> getMapa() {
        return this.mapa;
    }

    public void geraArvoreAVL() {
        this.arvoreAVL = new AVLTree();
        for (int i = 0; i < this.palavras.size(); i++) {
            this.arvoreAVL.root = this.arvoreAVL.insert(this.arvoreAVL.root, this.palavras.get(i), i);
        }
    }

    public AVLTree getArvoreAVL() {
        return this.arvoreAVL;
    }

    public void geraArvoreRubroNegra() {
        this.arvoreRubroNegra = new RubroNegra<>();
        for (int i = 0; i < this.palavras.size(); i++) {
            this.arvoreRubroNegra.put(this.palavras.get(i), i);
        }
    }

    public RubroNegra<String, Integer> getArvoreRubroNegra() {
        return this.arvoreRubroNegra;
    }

    public boolean checaPlagio(Documento doc, int m, String tipo) {
        switch (tipo) {
            case "Hash":
                for (int i = 0; i < this.palavras.size(); i++) {
                    Set<MultMap<String, Integer>> posicoes = doc.getMapa().findAll(this.palavras.get(i));
                    if (posicoes == null) continue;
                    for (MultMap<String, Integer> map : posicoes) {
                        Integer posicao = map.valor;
                        int contador = 0;
                        for (int k = 0; k < m; k++) {
                            if ((i + k < this.palavras.size() && posicao + k < doc.palavras.size())
                                    && (this.palavras.get(i + k).compareTo(doc.palavras.get(posicao + k)) == 0)) {
                                contador++;
                            } else {
                                break;
                            }
                        }
                        if (contador >= m) {
                            System.out.println(this.nome + " plagio de " + doc.nome);
                            return true;
                        }
                    }
                }
                return false;
            case "AVL":
                for (int i = 0; i < this.palavras.size(); i++) {
                    ArrayList<Integer> posicoes = doc.getArvoreAVL().get(this.palavras.get(i));
                    if (posicoes == null) continue;
                    for (Integer posicao : posicoes) {
                        int contador = 0;
                        for (int k = 0; k < m; k++) {
                            if ((i + k < this.palavras.size() && posicao + k < doc.palavras.size())
                                    && (this.palavras.get(i + k).compareTo(doc.palavras.get(posicao + k)) == 0)) {
                                contador++;
                            }
                        }
                        if (contador >= m) {
                            System.out.println(this.nome + " plagio de " + doc.nome);
                            return true;
                        }
                    }
                }
                return false;
            case "RubroNegra":
                for (int i = 0; i < this.palavras.size(); i++) {
                    ArrayList<Integer> posicoes = doc.getArvoreRubroNegra().get(this.palavras.get(i));
                    if (posicoes == null) continue;
                    for (Integer posicao : posicoes) {
                        int contador = 0;
                        for (int k = 0; k < m; k++) {
                            if ((i + k < this.palavras.size() && posicao + k < doc.palavras.size())
                                    && (this.palavras.get(i + k).compareTo(doc.palavras.get(posicao + k)) == 0)) {
                                contador++;
                            }
                        }
                        if (contador >= m) {
//                            for (int l = 0; l < m; l++) {
//                                System.out.print(this.palavras.get(i + l) + " ");
//                            }
                            System.out.println();
                            System.out.println(this.nome + " plagio de " + doc.nome);
                            return true;
                        }
                    }
                }
                return false;
            default:
                return false;
        }
    }
}
