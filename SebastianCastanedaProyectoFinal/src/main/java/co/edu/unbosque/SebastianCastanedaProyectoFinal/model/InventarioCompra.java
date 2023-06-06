package co.edu.unbosque.SebastianCastanedaProyectoFinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "InventarioCompras")
public class InventarioCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idInventario")
	private Inventario idInventario;
	
	@ManyToOne
	@JoinColumn(name = "idCompra")
	private Compra idCompra;
	
	private Integer cantidad;
	
	private Double costoUnitario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Inventario getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(Inventario idInventario) {
		this.idInventario = idInventario;
	}

	public Compra getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Compra idCompra) {
		this.idCompra = idCompra;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}
	
	
}
