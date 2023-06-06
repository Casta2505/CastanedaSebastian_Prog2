package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Integer>{
	public Optional<Proveedor> findByNombre(String nombre); 
}
