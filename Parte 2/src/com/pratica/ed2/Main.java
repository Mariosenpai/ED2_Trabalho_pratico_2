package com.pratica.ed2;


import com.pratica.ed2.plagio.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // **************
        // arquivos
        // **************

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JFrame jFrame = new JFrame();
        int returnVal = fileChooser.showOpenDialog(jFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File pasta = fileChooser.getSelectedFile();

            long tempoDocumentos1 = System.nanoTime();
            Documentos documentos = new Documentos(pasta.getAbsolutePath());

            long segundo1 = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tempoDocumentos1);
            long nano1 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - tempoDocumentos1)- segundo1;
            System.out.println(segundo1+","+nano1+" Segundos");

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal2 = fileChooser.showOpenDialog(jFrame);
            if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();

                Documento documento = new Documento(arquivo.getAbsolutePath());

                long tempoDocumentos = System.nanoTime();

                for (Documento doc : documentos.documentos) {
                    documento.checaPlagio(doc, 20, "Hash");
                }

                long segundo = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tempoDocumentos);
                long nano = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - tempoDocumentos)- segundo;
                System.out.println("Tabela Hash:"+segundo+","+nano+" Segundos");

            }

        }

        jFrame.dispose();
    }
}
