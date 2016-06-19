package com.inkodex.avisos;

/**
 * Created by JuanDiego on 16/06/2016.
 */
public class Aviso {
    private int mId; // es el identificador de aviso
    private String mContent; // contiene el texto del aviso
    private int mImportant;// es un valor numerico 1 importante 0 no importante

    public Aviso (int id, String content, int important){
        mId = id;
        mContent = content;
        mImportant = important;
    }

    public int getId(){
        return mId;
    }
    public void setId (int id){
        mId = id;
    }
    public int getImportant(){
        return mImportant;
    }
    public void setImportant (int important){
        mImportant = important;
    }
    public String getContent(){
        return mContent;
    }
    public void setContent(String content) {
        mContent = content;
    }
}
