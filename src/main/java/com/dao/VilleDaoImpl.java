package com.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.config.JDBCConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import  java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.Statement;

public class VilleDaoImpl implements VilleDao {

	private JDBCConfiguration jdbcConfiguration;

	public VilleDaoImpl(JDBCConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }
	
	@Override
	public List<Ville> getVilles() {
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        List<Ville> villes = new ArrayList<Ville>();
        
            try {
				connexion = jdbcConfiguration.getConnection();
				statement = connexion.createStatement();
	            resultat = statement.executeQuery("SELECT * FROM ville_france;");
	            
	            while(resultat.next()) {
	            	villes.add(new Ville(resultat.getInt("Code_commune_INSEE"),resultat.getString("Nom_commune"),
	            			resultat.getInt("Code_postal"),resultat.getString("Libelle_acheminement"),
	            			resultat.getString("Ligne_5"),resultat.getString("Latitude"),
	            			resultat.getString("Longitude")));
	            	
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       return villes;
	}
	
	public Ville chercheVilleCP(String cp) {
		
		Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
        String result="";
            try {
				connexion = jdbcConfiguration.getConnection();
	            statement = connexion.prepareStatement("SELECT * FROM ville_france WHERE Code_postal = ?;");
	            statement.setString(1, cp);
	            resultat = statement.executeQuery();
	            
	            resultat.next();
	            
	            Ville ville = new Ville(resultat.getInt("Code_commune_INSEE"),resultat.getString("Nom_commune"),
            			resultat.getInt("Code_postal"),resultat.getString("Libelle_acheminement"),
            			resultat.getString("Ligne_5"),resultat.getString("Latitude"),
            			resultat.getString("Longitude"));
	            
	            return ville;
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
		
	}

	@Override
	public String postVille(String INSEE, String commune, String cp, String libelle, String ligne5, String latitude,
			String longitude) {
		Connection connexion = null;
        PreparedStatement statement = null;
            try {
				connexion = jdbcConfiguration.getConnection();
	            statement = connexion.prepareStatement("Insert Into ville_france Values(?,?,?,?,?,?,?);");
	            
	            statement.setString(1, INSEE);
	            statement.setString(2, commune);
	            statement.setString(3, cp);
	            statement.setString(4, libelle);
	            statement.setString(5, ligne5);
	            statement.setString(6, latitude);
	            statement.setString(7, longitude);
	            
	            statement.executeUpdate();

	            return "ville ajoutee a la base de donnees";
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		return "ERREUR, Verifiez vos parametres";
	}
	
	@Override
	public String putVille(String INSEE, String commune, String cp, String libelle, String ligne5, String latitude,
			String longitude) {
		Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
            try {
				connexion = jdbcConfiguration.getConnection();
				statement = connexion.prepareStatement("SELECT * from ville_france WHERE Code_commune_insee=?");
				statement.setString(1, INSEE);
				resultat = statement.executeQuery();
				
				if(resultat.next()) {
					statement = connexion.prepareStatement("Insert Into ville_france Values(?,?,?,?,?,?,?);");
		            
		            statement.setString(1, INSEE);
		            statement.setString(2, commune);
		            statement.setString(3, cp);
		            statement.setString(4, libelle);
		            statement.setString(5, ligne5);
		            statement.setString(6, latitude);
		            statement.setString(7, longitude);
		            
		            statement.executeUpdate();

		            return "ville ajoutee a la base de donnees";
				}
				else {
					return "ville deja existante";
				}
				
	            
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		return "ERREUR, Verifiez vos parametres";
	}
	
	@Override
	public String deleteVille(String INSEE) {
		Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
            try {
            	connexion = jdbcConfiguration.getConnection();
				statement = connexion.prepareStatement("SELECT * from ville_france WHERE Code_commune_insee=?");
				statement.setString(1, INSEE);
				resultat = statement.executeQuery();
				
				if(resultat.next()) {
					
					statement = connexion.prepareStatement("DELETE from ville_france WHERE Code_commune_insee=?");
					statement.setString(1, INSEE);
					statement.executeUpdate();
					
		            return "ville supprimee";
				}
				else {
					return "ville inexistante";
				}
            	
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		return "Ville effacee";
	}
}
