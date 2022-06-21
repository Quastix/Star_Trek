package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;

import java.util.stream.Stream;

public interface SausRepository {
    Stream<Saus> findAll();
}
