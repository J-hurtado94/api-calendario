package com.apicalendario.presentacion;

import com.apicalendario.aplicacion.CalendarioService;
import com.apicalendario.dominio.Calendario;
import com.apicalendario.dominio.dtos.RespuestaFestivo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController

public class CalendarioController {

    private final CalendarioService calendarioService;

    public CalendarioController(CalendarioService calendarioService) {
        this.calendarioService = calendarioService;
    }

    @GetMapping("/festivos/obtener/{anio}")
    public Mono<RespuestaFestivo> obtenerFestivos(@PathVariable String anio) {
        return calendarioService.obtenerAnosRelacionados(anio);
    }

    @GetMapping("/calendario/generar/{anio}")
    public Mono<Boolean> procesarCalendario(@PathVariable String anio) {
        boolean resultado = calendarioService.procesarCalendario(anio);
        return Mono.just(resultado);
    }

    @GetMapping("calendario/listar/{anio}")
    public List<Calendario> obtenerCalendario(@PathVariable String anio) {
        return calendarioService.listarCalendarioPorAnio(anio);
    }
}
