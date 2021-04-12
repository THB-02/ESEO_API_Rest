package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.config.JDBCConfiguration;
import com.dao.Ville;
import com.dao.VilleDao;

@RestController
public class VilleController {
	
	@ResponseBody
	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	public List<Ville> appelGet(@RequestParam(value = "cp", required=false) String cp) {
		System.out.println("Appel GET");
		List<Ville> villes = new ArrayList<Ville>();
		
		VilleDao villeDao;
		JDBCConfiguration jdbcConfiguration = JDBCConfiguration.getInstance();
	    villeDao = jdbcConfiguration.getVilleDao();
	    if(cp == null) {
	    	return villeDao.getVilles();
	    }
	    else {
	    	villes.add(villeDao.chercheVilleCP(cp));
	    	return villes;
	    }
	}
	
	@ResponseBody
	@RequestMapping(value = "/postVille", method = RequestMethod.POST)
	public String appelPost(@RequestParam(value = "insee") String insee,
			@RequestParam(value = "commune") String commune,
			@RequestParam(value = "cp") String cp,
			@RequestParam(value = "libelle") String libelle,
			@RequestParam(value = "ligne5") String ligne5,
			@RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "longitude") String longitude) {
		System.out.println("Appel POST");
		
		VilleDao villeDao;
		JDBCConfiguration jdbcConfiguration = JDBCConfiguration.getInstance();
	    villeDao = jdbcConfiguration.getVilleDao();
	    
	    return villeDao.postVille(insee, commune, cp, libelle, ligne5, latitude, longitude);

	}
	
	@ResponseBody
	@RequestMapping(value = "/putVille", method = RequestMethod.PUT)
	public String appelPut(@RequestParam(value = "insee") String insee,
			@RequestParam(value = "commune") String commune,
			@RequestParam(value = "cp") String cp,
			@RequestParam(value = "libelle") String libelle,
			@RequestParam(value = "ligne5") String ligne5,
			@RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "longitude") String longitude) {
		System.out.println("Appel PUT");
		
		VilleDao villeDao;
		JDBCConfiguration jdbcConfiguration = JDBCConfiguration.getInstance();
	    villeDao = jdbcConfiguration.getVilleDao();
	    
	    return villeDao.putVille(insee, commune, cp, libelle, ligne5, latitude, longitude);

	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteVille", method = RequestMethod.DELETE)
	public String appelDelete(@RequestParam(value = "insee") String insee){
		System.out.println("Appel DELETE");
		
		VilleDao villeDao;
		JDBCConfiguration jdbcConfiguration = JDBCConfiguration.getInstance();
	    villeDao = jdbcConfiguration.getVilleDao();
	    
	    return villeDao.deleteVille(insee);

	}
}
