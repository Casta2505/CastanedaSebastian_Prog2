package co.edu.unbosque.SebastianCastanedaProyectoFinal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Inventario;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalRepository;

@Controller
@RequestMapping
public class InventarioController {
	@Autowired
	private InventarioRepository daoInventarioRepository;

	@Autowired
	private SucursalRepository daoSucursalRepository;

	@GetMapping("/ListarInventarioPorSucursal")
	public String mostrarInventarioPorSucursal(Model model) {
		List<Sucursal> sucursales = daoSucursalRepository.findAll();
		model.addAttribute("sucursalesInventario", sucursales);
		return "InventarioPorSucursal";
	}

	@GetMapping("/MostrarInventario")
	public String mostrarInventario(@RequestParam("sucursalId") Integer sucursalId, Model model) {
		Optional<Sucursal> sucursalOptional = daoSucursalRepository.findById(sucursalId);
		if (sucursalOptional.isPresent()) {
			Sucursal sucursal = sucursalOptional.get();
			List<Inventario> inventario = daoInventarioRepository.findAllByIdSucursal(sucursal);
			model.addAttribute("listarinventario", inventario);
			model.addAttribute("sucursalSeleccionada", sucursal);
		}
		model.addAttribute("sucursalesInventario", daoSucursalRepository.findAll());
		return "InventarioPorSucursal";
	}

	@GetMapping("/ListarInventario")
	public String listarInventario(Model model) {
		List<Inventario> inventario = daoInventarioRepository.findAll();
		model.addAttribute("listarinventario", inventario);
		model.addAttribute("sucursalesInventario", daoSucursalRepository.findAll());
		return "InventarioPorSucursal";
	}
}
