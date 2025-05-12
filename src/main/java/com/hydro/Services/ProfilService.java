package com.hydro.Services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hydro.Entities.Profil;
import com.hydro.Repositories.ProfilRepository;
@Service
public class ProfilService {
	@Autowired
	 private ProfilRepository profilRepository ;
	 public List<Profil> getFilteredProfils(
	            String region,
	            Double latMin,
	            Double latMax,
	            Double lonMin,
	            Double lonMax,
	            Double profMin,
	            Double profMax,
	            LocalDate dateDebut,
	            LocalDate dateFin
	    ) {
	        Specification<Profil> spec = Specification.where(null);

	        if (latMin != null && latMax != null) {
	            spec = spec.and((root, query, cb) ->
	                    cb.between(root.get("lat"), latMin, latMax));
	        }

	        if (lonMin != null && lonMax != null) {
	            spec = spec.and((root, query, cb) ->
	                    cb.between(root.get("longitude"), lonMin, lonMax));
	        }

	        if (profMin != null && profMax != null) {
	            spec = spec.and((root, query, cb) ->
	                    cb.between(root.get("depth"), profMin, profMax));
	        }

	        if (dateDebut != null && dateFin != null) {
	            spec = spec.and((root, query, cb) ->
	                    cb.between(root.get("dateProfil"), Date.valueOf(dateDebut), Date.valueOf(dateFin)));
	        }

	        //if (region != null && !region.isEmpty()) {
	          //  spec = spec.and((root, query, cb) ->
	            //        cb.equal(root.get("campagne").get("region").get("codeReg"), region));
	      //  }

	        return profilRepository.findAll(spec);
	    }
}
