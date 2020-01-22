package br.com.meupedidoapp.meupedidousuario.entities;

import android.os.Parcel;
import android.os.Parcelable;

public final class Tema implements Parcelable {

    private String corTextoTitulo;
    private String corPrimaria;
    private String corPrimariaDark;
    private String corPrimariaLight;
    private String corTextoPrimario;
    private String corSecundaria;
    private String corSecundariaDark;
    private String corSecundariaLight;
    private String corTextoSecundario;

    public Tema() {}

    private Tema(Parcel in) {
        corTextoTitulo = in.readString();
        corPrimaria = in.readString();
        corPrimariaDark = in.readString();
        corPrimariaLight = in.readString();
        corTextoPrimario = in.readString();
        corSecundaria = in.readString();
        corSecundariaDark = in.readString();
        corSecundariaLight = in.readString();
        corTextoSecundario = in.readString();
    }

    public static final Creator<Tema> CREATOR = new Creator<Tema>() {
        @Override
        public Tema createFromParcel(Parcel in) {
            return new Tema(in);
        }

        @Override
        public Tema[] newArray(int size) {
            return new Tema[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(corTextoTitulo);
        dest.writeString(corPrimaria);
        dest.writeString(corPrimariaDark);
        dest.writeString(corPrimariaLight);
        dest.writeString(corTextoPrimario);
        dest.writeString(corSecundaria);
        dest.writeString(corSecundariaDark);
        dest.writeString(corSecundariaLight);
        dest.writeString(corTextoSecundario);
    }

    public String getCorTextoTitulo() {
        return corTextoTitulo;
    }

    public String getCorPrimaria() {
        return corPrimaria;
    }

    public String getCorPrimariaDark() {
        return corPrimariaDark;
    }

    public String getCorPrimariaLight() {
        return corPrimariaLight;
    }

    public String getCorTextoPrimario() {
        return corTextoPrimario;
    }

    public String getCorSecundaria() {
        return corSecundaria;
    }

    public String getCorSecundariaDark() {
        return corSecundariaDark;
    }

    public String getCorSecundariaLight() {
        return corSecundariaLight;
    }

    public String getCorTextoSecundario() {
        return corTextoSecundario;
    }
}
