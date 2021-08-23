package com.pratica.ed2.plagio;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.Scanner;

public class Documentos {

    public ArrayList<Documento> documentos;

    public Documentos(String caminho) {
        File pasta = new File(caminho);
        File[] listaDeArquivos = pasta.listFiles();
        if (listaDeArquivos.length > 0) {
            this.documentos = new ArrayList<>();

            for (File arquivo : listaDeArquivos) {
                Documento temp = new Documento(Paths.get(caminho, arquivo.getName()).normalize().toString());
                documentos.add(temp);
            }
        }
    }
}
