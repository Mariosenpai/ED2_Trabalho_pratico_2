package com.pratica.ed2.estrutura;
import java.util.HashSet;
import java.util.Set;

/*
essa funçao hash aceita T(tipos) que tenham o hashCode
se implementar uma classe nova, terar que implentar o metodo hashCode e o metodo Equals
 */

public class TabelaHash<T> {

    private final Set<T>[] tab;
    private final int TamMax;

    public TabelaHash(int tam ){
        tab =  new HashSet[tam];
        TamMax = tam;

    }

    private int funcaohash(T chave) {

        return (chave.hashCode() & 0x7fffffff) % TamMax;
    }

    public void insere(T item) {

        int posicao = funcaohash(item); // CALCULA POSIÇAO

        if(tab[posicao] == null){
            tab[posicao] = new HashSet<>();
        }

        tab[posicao].add(item);
    }


    //verifica se a chave existe
    public boolean busca(T chave) {

        int pos = funcaohash(chave);

        if(this.tab[pos] == null)
            return false;

        return tab[pos].contains(chave);

    }

    public Set<T> getLista(T chave){
        int pos = funcaohash(chave);
        return tab[pos];
    }


}