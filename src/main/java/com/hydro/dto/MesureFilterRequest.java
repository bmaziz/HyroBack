package com.hydro.dto;

import java.util.List;

import lombok.Data;
@Data
public class MesureFilterRequest {
    private List<String> idProfils;
    private List<String> codeParams;

}
