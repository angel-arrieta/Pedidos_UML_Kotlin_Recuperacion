class Pedido {
    fecha: date
    productos: Producto[]
    costeTotal: float
    pagos: Pago[]
    estado: string
}
class Cliente {
    pedidos: Pedido[]
}
class Producto {
    nombre: string
    descripcion: string
    precio: float
    impuestos: float
    stock: int
}
class Pago {
    fecha: date
    cantidad: float
    formaPago: FormaPago
}
class FormaPago {
    tipo: string
}
class Card {
    numero: string
    fechaCaducidad: date
    tipoTarjeta: string
}
class Cash {
    tipoMoneda: string
}
class Cheque {
    nombre: string
    banco: string
}