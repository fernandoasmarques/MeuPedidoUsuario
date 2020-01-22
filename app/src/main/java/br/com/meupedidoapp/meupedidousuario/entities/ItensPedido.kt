package br.com.meupedidoapp.meupedidousuario.entities

import android.os.Parcel
import android.os.Parcelable

class ItensPedido(var produto: Produto?, var qtde: Int) : Parcelable{

    constructor(parcel: Parcel) : this(
        TODO("produto"),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(qtde)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItensPedido> {
        override fun createFromParcel(parcel: Parcel): ItensPedido {
            return ItensPedido(parcel)
        }

        override fun newArray(size: Int): Array<ItensPedido?> {
            return arrayOfNulls(size)
        }
    }
}