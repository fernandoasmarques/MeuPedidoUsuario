package br.com.meupedidoapp.meupedidousuario.entities

abstract class Produto {
    val nome: String? = null
    val imagem: String? = null
    val ingredientes: List<String>? = null
    val tipoProduto: TipoProdutoEnum? = null
    val isProdutoAtivo: Boolean = false
    val isVisivel: Boolean = false
    val atributos: Array<Atributo>? = null
}
