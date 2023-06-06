package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Ciudad;


public interface CiudadRepository extends CrudRepository<Ciudad, Integer>{
	
	public Ciudad findByNombre(String ciudad);
	
	public List<Ciudad> findAll();
	
}
