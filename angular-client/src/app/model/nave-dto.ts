export class NaveDTO {
    constructor(
        public nombre: string,
        public idEstrella: number,
        public nombreEstrella: string,
        public idPlaneta: number,
        public nombrePlaneta: string,
        public tipoNave: string,
        public creditos: number,
        public tiempoRestante: number,
        public capacidadMaxima: number,
        public capadidadUtilizada: number,
        public foto: string,
        public velocidad: number
    ) {
    }
}
