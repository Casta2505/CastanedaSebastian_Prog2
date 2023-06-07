package co.edu.unbosque.SebastianCastanedaProyectoFinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Ciudad;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.CiudadRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalRepository;

@Controller
@RequestMapping
public class SucursalController {
	@Autowired
	private SucursalRepository daoSucursal;
	@Autowired
	private CiudadRepository daoCiudad;
	
	@GetMapping("/ListarSucursales")
	public String getSucursales(Model model){
		List<Sucursal> all = (List<Sucursal>)daoSucursal.findAll();
		if(all.isEmpty()) {
			return "";
		}
		model.addAttribute("sucursales",all);
		return "Sucursales";
	}
	
	@GetMapping("/")
	public String getSucursales(){
		return "Inicio";
	}
	
	@GetMapping("/ListarSucursalesCiudades")
	public String getSucursalesCiudad(@RequestParam(value = "nombre", required = false) String nombre, Model model) {
	  if (nombre != null && !nombre.isEmpty()) {
	    Ciudad ciudad = daoCiudad.findByNombre(nombre);
	    List<Sucursal> all = daoSucursal.findAllByIdCiudad(ciudad);
	    model.addAttribute("sucursales", all);
	  } else {
	    List<Sucursal> all = daoSucursal.findAll();
	    model.addAttribute("sucursales", all);
	  }
	  return "Sucursales";
	}
}
