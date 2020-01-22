package br.com.meupedidoapp.meupedidousuario.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class Atributo implements Parcelable {
    private String nome;
    private TipoOpcaoEnum tipoOpcao;
    private int qtdeMin;
    private int qtdeMax;
    private boolean obrigatorio;

    public Atributo() {
    }

    protected Atributo(Parcel in) {
        nome = in.readString();
        qtdeMin = in.readInt();
        qtdeMax = in.readInt();
        obrigatorio = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(qtdeMin);
        dest.writeInt(qtdeMax);
        dest.writeByte((byte) (obrigatorio ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Atributo> CREATOR = new Creator<Atributo>() {
        @Override
        public Atributo createFromParcel(Parcel in) {
            return new Atributo(in);
        }

        @Override
        public Atributo[] newArray(int size) {
            return new Atributo[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public TipoOpcaoEnum getTipoOpcao() {
        return tipoOpcao;
    }

    public int getQtdeMin() {
        return qtdeMin;
    }

    public int getQtdeMax() {
        return qtdeMax;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }
}



