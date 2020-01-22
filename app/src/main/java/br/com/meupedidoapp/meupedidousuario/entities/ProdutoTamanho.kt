package br.com.meupedidoapp.meupedidousuario.entities

import android.os.Parcel
import android.os.Parcelable

open class ProdutoTamanho() : Produto(), Parcelable {
    val tamanho: Array<Tamanho>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProdutoTamanho> {
        override fun createFromParcel(parcel: Parcel): ProdutoTamanho {
            return ProdutoTamanho(parcel)
        }

        override fun newArray(size: Int): Array<ProdutoTamanho?> {
            return arrayOfNulls(size)
        }
    }

}
