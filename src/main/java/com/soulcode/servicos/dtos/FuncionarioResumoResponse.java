package com.soulcode.servicos.dtos;

import java.util.List;

public record FuncionarioResumoResponse(Integer id,
                                        String nome,
                                        String email,
                                        CargoResumoResponse cargo
) {}