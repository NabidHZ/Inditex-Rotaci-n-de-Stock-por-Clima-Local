package com.inditex.controller;

import com.inditex.model.Producto;
import com.inditex.model.Tienda;
import com.inditex.repository.TiendaRepository;
import com.inditex.service.RecomendacionService;
import com.inditex.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recomendaciones")
public class RecomendacionesWebController {
    private final TiendaRepository tiendaRepository;
    private final RecomendacionService recomendacionService;
    private final WeatherService weatherService;

    public RecomendacionesWebController(TiendaRepository tiendaRepository, RecomendacionService recomendacionService, WeatherService weatherService) {
        this.tiendaRepository = tiendaRepository;
        this.recomendacionService = recomendacionService;
        this.weatherService = weatherService;
    }

    @GetMapping
    public String mostrarRecomendaciones(
            @RequestParam(value = "tiendaId", required = false) Long tiendaId,
            @RequestParam(value = "genero", required = false) String genero,
            @RequestParam(value = "seccion", required = false) String seccion,
            @RequestParam(value = "fecha", required = false) String fechaStr,
            Model model) {
        List<Tienda> tiendas = tiendaRepository.findAll();
        model.addAttribute("tiendas", tiendas);
        model.addAttribute("generoSeleccionado", genero);
        model.addAttribute("seccionSeleccionada", seccion);

        java.time.LocalDate hoy = java.time.LocalDate.now();
        java.time.LocalDate fechaSeleccionada = (fechaStr != null && !fechaStr.isEmpty())
                ? java.time.LocalDate.parse(fechaStr)
                : hoy;
        model.addAttribute("selectedFecha", fechaSeleccionada.toString());

        if (tiendaId != null) {
            Tienda tienda = tiendaRepository.findById(tiendaId).orElse(null);
            if (tienda != null) {
                boolean lluvia = weatherService.hayPrevisionLluvia(
                        String.valueOf(tienda.getLatitud()), String.valueOf(tienda.getLongitud()), fechaSeleccionada);
                double temperatura = weatherService.obtenerTemperaturaMaxima(
                        String.valueOf(tienda.getLatitud()), String.valueOf(tienda.getLongitud()), fechaSeleccionada);
                List<Producto> productos = recomendacionService.recomendarProductos(tienda, fechaSeleccionada);
                if (genero != null && !genero.isEmpty()) {
                    productos = productos.stream().filter(p -> genero.equals(p.getGenero())).collect(java.util.stream.Collectors.toList());
                    if (seccion != null && !seccion.isEmpty()) {
                        productos = productos.stream().filter(p -> seccion.equals(p.getSeccion())).collect(java.util.stream.Collectors.toList());
                    }
                }
                List<Producto> impermeables = productos.stream()
                        .filter(p -> Boolean.TRUE.equals(p.getImpermeable()))
                        .collect(java.util.stream.Collectors.toList());
                java.util.Map<String, Object> dia = new java.util.HashMap<>();
                dia.put("fecha", fechaSeleccionada);
                dia.put("lluvia", lluvia);
                dia.put("temperatura", temperatura);
                dia.put("productos", productos);
                dia.put("impermeables", impermeables);
                model.addAttribute("recomendacionDia", dia);
                model.addAttribute("selectedTiendaId", tiendaId);
            }
        }
        return "recomendaciones";
    }
}

