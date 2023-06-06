package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>{
	
	public List<Producto> findAll();
}
