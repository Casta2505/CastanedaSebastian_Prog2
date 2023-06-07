package co.edu.unbosque.SebastianCastanedaProyectoFinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sucursales")
public class Sucursal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String direccion;

	@ManyToOne
	@JoinColumn(name = "idCiudad")
	private Ciudad idCiudad;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Ciudad getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Ciudad idCiudad) {
		this.idCiudad = idCiudad;
	}

}
