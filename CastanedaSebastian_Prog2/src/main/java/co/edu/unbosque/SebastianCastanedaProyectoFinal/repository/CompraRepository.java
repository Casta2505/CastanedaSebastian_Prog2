package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Compra;

public interface CompraRepository extends CrudRepository<Compra, Integer>{

    public List<Compra> findAll();

    public Optional<Compra> findById(String proveedor);

}
