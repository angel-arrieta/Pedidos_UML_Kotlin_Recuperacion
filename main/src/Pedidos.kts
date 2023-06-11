import java.time.LocalDate

// Clase que representa un producto
data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val impuestos: Double
)

// Clase que representa un pedido
data class Pedido(
    val id: Int,
    val cliente: String,
    val fecha: LocalDate,
    val productos: List<Producto>,
    val pagos: List<Pago>,
    var estado: String
) {
    fun calcularCosteTotal(): Double {
        var total = 0.0
        for (producto in productos) {
            total += producto.precio * (1 + producto.impuestos)
        }
        return total
    }
}

// Clase que representa un pago
sealed class Pago

data class PagoTarjeta(
    val numeroTarjeta: String,
    val fechaCaducidad: String,
    val tipoTarjeta: String
) : Pago()

data class PagoEfectivo(
    val tipoMoneda: String
) : Pago()

data class PagoCheque(
    val nombre: String,
    val banco: String
) : Pago()

// Clase que representa el sistema de gestión de pedidos
class SistemaGestionPedidos {
    private val productos: MutableMap<String, Producto> = mutableMapOf()
    private val pedidos: MutableMap<Int, Pedido> = mutableMapOf()

    fun agregarProducto(nombre: String, producto: Producto) {
        productos[nombre] = producto
    }

    fun registrarPedido(id: Int, cliente: String, fecha: LocalDate, productos: List<Producto>) {
        val nuevoPedido = Pedido(id, cliente, fecha, productos, mutableListOf(), "pdte")
        pedidos[id] = nuevoPedido
    }

    fun agregarPago(idPedido: Int, pago: Pago) {
        val pedido = pedidos[idPedido]
        pedido?.pagos?.add(pago)
    }

    fun actualizarEstadoPedido(idPedido: Int, nuevoEstado: String) {
        val pedido = pedidos[idPedido]
        pedido?.estado = nuevoEstado
    }

    fun verificarStock(): Map<String, Boolean> {
        val stock: MutableMap<String, Boolean> = mutableMapOf()
        for ((nombre, producto) in productos) {
            stock[nombre] = pedidoContieneProducto(nombre)
        }
        return stock
    }

    private fun pedidoContieneProducto(nombreProducto: String): Boolean {
        for (pedido in pedidos.values) {
            for (producto in pedido.productos) {
                if (producto.nombre == nombreProducto) {
                    return true
                }
            }
        }
        return false
    }
}

// Ejemplo de uso del sistema de gestión de pedidos
fun main() {
    val sistema = SistemaGestionPedidos()

    // Agregar productos al sistema
    sistema.agregarProducto(
        "Producto1",
        Producto("Producto1", "Descripción del Producto1", 10.0, 0.08)
    )
    sistema.agregarProducto(
        "Producto2",
        Producto("Producto2", "Descripción del Producto2", 20.0, 0.1)
    )

    // Registrar un pedido
    val fechaPedido = LocalDate.now()
    sistema.registrarPedido(
        1,
        "Cliente1",
        fechaPedido,
        listOf(
            sistema.getProductos()["Producto1"]!!,
            sistema.getProductos()["Producto2"]!!
        )
    )

    // Agregar pagos al pedido
    sistema.agregarPago(1, PagoTarjeta("1234567890", "12/25", "Visa"))
    sistema.agregarPago(1, PagoEfectivo("USD"))
    sistema.agregarPago(1, PagoCheque("John Doe", "Banco A"))

    // Actualizar estado del pedido
    sistema.actualizarEstadoPedido(1, "entgdo")

    // Verificar el stock
    val stock = sistema.verificarStock()
    for ((nombreProducto, disponible) in stock) {
        println("$nombreProducto: ${if (disponible) "Disponible" else "Agotado"}")
    }
}

