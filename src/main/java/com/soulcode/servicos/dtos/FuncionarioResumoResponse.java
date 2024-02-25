package com.soulcode.servicos.dtos;

public record FuncionarioResumoResponse(Integer id,
                                        String nome,
                                        String email,
                                        CargoResumoResponse cargo
) {}