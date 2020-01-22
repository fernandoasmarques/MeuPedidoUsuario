package br.com.meupedidoapp.meupedidousuario.entities

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

class ProdutoUnidade() : Produto(), Parcelable {
    val preco: BigDecimal? = null

    constructor(parcel: Parcel) : this(){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProdutoUnidade> {
        override fun createFromParcel(parcel: Parcel): ProdutoUnidade {
            return ProdutoUnidade(parcel)
        }

        override fun newArray(size: Int): Array<ProdutoUnidade?> {
            return arrayOfNulls(size)
        }
    }
}
