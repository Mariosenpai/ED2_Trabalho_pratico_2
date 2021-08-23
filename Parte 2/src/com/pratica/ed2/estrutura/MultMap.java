package com.pratica.ed2.estrutura;


import java.util.Set;


public class MultMap<T,K> extends Object {


    private TabelaHash<T> tab;
    private T chave;
    public K valor;
    private Integer code = 1;

    private MultMap(T chave, K valor){
        this.chave = chave;
        this.valor = valor;
        code++;
    }

    private MultMap(T chave){
        this.chave = chave;
        this.valor = null;
        code ++;
    }

    public MultMap(int tamanho){
        tab = new TabelaHash<>(tamanho);
    }

    public void put(T c , K v ){

        tab.insere((T) new MultMap<T,K>(c,v));
    }

    public Set<MultMap<T, K>> findAll(T chave){
//        return tab.getLista(chave);

        T chaveMap = (T) new MultMap<T,K>(chave);

        if(tab.busca(chaveMap ) ){
            Set<MultMap<T, K>> lits = (Set<MultMap<T, K>>) tab.getLista( chaveMap );
            return lits;
//            for (T lit : lits)
//                System.out.println(lit);


        }else {
            return null;
//            System.out.println("Chave nao achada");
        }
    }


    @Override
    public int hashCode() {
        int h = 0;
        h = h + 31 + this.chave.hashCode();
        h = h + 31 + this.code.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {


        if(obj instanceof MultMap){
            MultMap m = (MultMap) obj;
            //compara se os valores dos 2 objetos sao iguais tanto a chave quanto a chave
            if(valor == null){
                return chave.equals(m.chave);
            }else {
                return chave.equals(m.chave) &&
                        valor.equals(m.valor);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if(valor == null){
            return  "null";
        }
        return valor+"";
//        return "MultMap{" +
//                "chave=" + chave +
//                ", valor=" + valor +
//                '}';
    }
}