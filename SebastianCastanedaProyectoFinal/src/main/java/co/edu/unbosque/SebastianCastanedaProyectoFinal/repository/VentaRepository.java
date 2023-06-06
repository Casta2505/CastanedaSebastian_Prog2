package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Venta;

public interface VentaRepository extends CrudRepository<Venta, Integer>{
	
	public List<Venta> findAll();
	
	public List<Venta> findByIdEmpleado(Empleado empleado);
}
