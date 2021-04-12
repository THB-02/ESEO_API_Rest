package com.dao;

import java.util.List;

public interface VilleDao {

	public  List<Ville> getVilles();
	public Ville chercheVilleCP(String cp);
	public String postVille(String INSEE, String commune, String cp, String libelle, String ligne5, String latitude, String longitude);
	public String putVille(String INSEE, String commune, String cp, String libelle, String ligne5, String latitude, String longitude);
	public String deleteVille(String INSEE);
}
