export class ProductoDTO {

    constructor(
        public id: number,
        public nombre: string,
        public volumen: number,
        public imagen: string,
        public precioDemanda: number,
        public precioOferta: number,
        public max: number,
    ) { }
}
