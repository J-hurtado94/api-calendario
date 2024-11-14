package com.apicalendario.aplicacion;

import com.apicalendario.core.interfaces.repositorios.ICalendarioRepositorio;
import com.apicalendario.core.interfaces.repositorios.ITipoRepositorio;
import com.apicalendario.dominio.Calendario;
import com.apicalendario.dominio.Tipo;
import com.apicalendario.dominio.dtos.Festivo;
import com.apicalendario.dominio.dtos.RespuestaFestivo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class CalendarioService {

    private final WebClient webClient;
    private final ICalendarioRepositorio calendarioRepositorio;
    private final ITipoRepositorio tipoRepositorio;

    public CalendarioService(WebClient.Builder webClientBuilder, ICalendarioRepositorio calendarioRepositorio, ITipoRepositorio tipoRepositorio) {
        this.webClient = webClientBuilder.baseUrl("http://apifestivoscolombia:3000/festivos").build();

        this.calendarioRepositorio = calendarioRepositorio;
        this.tipoRepositorio = tipoRepositorio;
    }

    public Mono<RespuestaFestivo> obtenerAnosRelacionados(String anio) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/obtener/{anio}").build(anio))
                .retrieve()  // Para obtener la respuesta
                .bodyToMono(RespuestaFestivo.class);
    }

    @Transactional
    public boolean procesarCalendario(String anio) {

        RespuestaFestivo respuestaFestivo = obtenerAnosRelacionados(anio).block();
        if (respuestaFestivo == null || respuestaFestivo.getFestivos().isEmpty()) {
            return false;
        }

        List<Festivo> festivos = respuestaFestivo.getFestivos();
        Map<LocalDate, String> festivosMap = festivos.stream()
                .collect(Collectors.toMap(Festivo::getFecha, Festivo::getNombre));


        LocalDate inicioAnio = LocalDate.of(Integer.parseInt(anio), 1, 1);
        LocalDate finAnio = LocalDate.of(Integer.parseInt(anio), 12, 31);

        for (LocalDate fecha = inicioAnio; !fecha.isAfter(finAnio); fecha = fecha.plusDays(1)) {
            Tipo tipoDia;
            String descripcion;


            String diaDeLaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.of("es","CO"));

            if (festivosMap.containsKey(fecha)) {

                tipoDia = tipoRepositorio.findByTipo("Día laboral");
                descripcion = diaDeLaSemana;
            } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {

                tipoDia = tipoRepositorio.findByTipo("Fin de Semana");
                descripcion = diaDeLaSemana;
            } else {

                tipoDia = tipoRepositorio.findByTipo("Día festivo");
                descripcion = diaDeLaSemana;
            }

            Calendario calendario = new Calendario();
            calendario.setTipo(tipoDia);
            calendario.setFecha(fecha);

            calendario.setDescription(descripcion);

            calendarioRepositorio.save(calendario);
        }

        return true;
    }


    public List<Calendario> listarCalendarioPorAnio(String anio) {
        return calendarioRepositorio.findByFechaYear(anio);
    }














}
