package com.example.spaceinvaders.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompraVentaDTO {

    //COMPRA Y VENTA
    private Long idProducto;
    private Integer cantidadProducto;
    private Long idPlaneta;
    private Long idNave;
    private Float total;

    public CompraVentaDTO(Long idProducto, Integer cantidadProducto, Long idPlaneta, Long idNave, Float total) {
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.idPlaneta = idPlaneta;
        this.idNave = idNave;
        this.total = total;
    }
}
