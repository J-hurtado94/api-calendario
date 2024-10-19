package com.apicalendario.core.interfaces.repositorios;

import com.apicalendario.dominio.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoRepositorio extends JpaRepository<Tipo,Long> {

    Tipo findByTipo(String tipo);
}
