package com.hydro.Services;

import com.hydro.Entities.Parametres;
import com.hydro.Repositories.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesureService {

    @Autowired
    private MesureRepository mesureRepository;

    public List<Parametres> getDistinctParametresByProfilId(String idProfil) {
        return mesureRepository.findDistinctParametresByProfilId(idProfil);
    }
}
