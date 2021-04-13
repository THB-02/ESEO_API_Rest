package com.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.config.JDBCConfiguration;

import java.beans.Customizer;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import  java.sql.Statement;

public class VilleDaoImpl implements VilleDao {

	private static final Logger LOGGER = Logger.getLogger(Customizer.class);
	private JDBCConfiguration jdbcConfiguration;

	public VilleDaoImpl(JDBCConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }
	
	@Override
	public List<Ville> getVilles() {
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        List<Ville> villes = new ArrayList<>();
        
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
				LOGGER.log(Level.ERROR, e);
			}
            finally {
            	try {
            		if(statement != null) {
            			statement.close();
            		}
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, e);
				}finally {
	            	try {
	            		if(resultat != null) {
	            			resultat.close();
	            		}
					} catch (SQLException e) {
						LOGGER.log(Level.ERROR, e);
					}
	            }
            }
       return villes;
	}
	
	public Ville chercheVilleCP(String cp) {
		
		Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
            try {
				connexion = jdbcConfiguration.getConnection();
	            statement = connexion.prepareStatement("SELECT * FROM ville_france WHERE Code_postal = ?;");
	            statement.setString(1, cp);
	            resultat = statement.executeQuery();
	            
	            resultat.next();
	            
	            return new Ville(resultat.getInt("Code_commune_INSEE"),resultat.getString("Nom_commune"),
            			resultat.getInt("Code_postal"),resultat.getString("Libelle_acheminement"),
            			resultat.getString("Ligne_5"),resultat.getString("Latitude"),
            			resultat.getString("Longitude"));
	            
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, e);
			}
            finally {
            	try {
            		if(statement != null) {
            			statement.close();
            		}
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, e);
					
				}finally {
	            	try {
	            		if(resultat != null) {
	            			resultat.close();
	            		}
					} catch (SQLException e) {
						LOGGER.log(Level.ERROR, e);
					}
	            }
            }
		
		return null;
		
	}

	@Override
	public String postVille(String insee, String commune, String cp, String libelle, String ligne5, String latitude,
			String longitude) {
		Connection connexion = null;
        PreparedStatement statement = null;
            try {
				connexion = jdbcConfiguration.getConnection();
	            statement = connexion.prepareStatement("Insert Into ville_france Values(?,?,?,?,?,?,?);");
	            
	            statement.setString(1, insee);
	            statement.setString(2, commune);
	            statement.setString(3, cp);
	            statement.setString(4, libelle);
	            statement.setString(5, ligne5);
	            statement.setString(6, latitude);
	            statement.setString(7, longitude);
	            
	            statement.executeUpdate();

	            return "ville ajoutee a la base de donnees";
	            
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, e);
			}
            finally {
            	try {
            		if(statement != null) {
            			statement.close();
            		}
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, e);
				}
            }
		return "ERREUR, Verifiez vos parametres";
	}
	
	@Override
	public String putVille(String insee, String commune, String cp, String libelle, String ligne5, String latitude,
			String longitude) {
		Connection connexion = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        ResultSet resultat = null;
            try {
				connexion = jdbcConfiguration.getConnection();
				statement = connexion.prepareStatement("SELECT * from ville_france WHERE Code_commune_insee=?");
				statement.setString(1, insee);
				resultat = statement.executeQuery();
				
				if(resultat.next()) {
					statement2 = connexion.prepareStatement("Insert Into ville_france Values(?,?,?,?,?,?,?);");
		            
		            statement2.setString(1, insee);
		            statement2.setString(2, commune);
		            statement2.setString(3, cp);
		            statement2.setString(4, libelle);
		            statement2.setString(5, ligne5);
		            statement2.setString(6, latitude);
		            statement2.setString(7, longitude);
		            
		            statement2.executeUpdate();

		            return "ville ajoutee a la base de donnees";
				}
				else {
					return "ville deja existante";
				}
				
	            
	            
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, e);
			}finally {
            	try {
            		if(statement != null) {
            			statement.close();
            		}
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, e);
				}finally {
	            	try {
	            		if(resultat != null) {
	            			resultat.close();
	            		}
					} catch (SQLException e) {
						LOGGER.log(Level.ERROR, e);
					}finally {
		            	try {
		            		if(statement2 != null) {
		            			statement2.close();
		            		}
						} catch (SQLException e) {
							LOGGER.log(Level.ERROR, e);
						}
		            }
	            }
            }
            
            
		return "ERREUR, Verifiez vos parametres";
	}
	
	@Override
	public String deleteVille(String insee) {
		Connection connexion = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        ResultSet resultat = null;
            try {
            	connexion = jdbcConfiguration.getConnection();
				statement = connexion.prepareStatement("SELECT * from ville_france WHERE Code_commune_insee=?");
				statement.setString(1, insee);
				resultat = statement.executeQuery();
				
				if(resultat.next()) {
					
					statement2 = connexion.prepareStatement("DELETE from ville_france WHERE Code_commune_insee=?");
					statement2.setString(1, insee);
					statement2.executeUpdate();
					
		            return "ville supprimee";
				}
				else {
					return "ville inexistante";
				}
            	
				

			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, e);
			}
            finally {
            	try {
            		if(statement != null) {
            			statement.close();
            		}
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, e);
				}finally {
	            	try {
	            		if(resultat != null) {
	            			resultat.close();
	            		}
					} catch (SQLException e) {
						LOGGER.log(Level.ERROR, e);
					}finally {
		            	try {
		            		if(statement2 != null) {
		            			statement2.close();
		            		}
						} catch (SQLException e) {
							LOGGER.log(Level.ERROR, e);
						}
		            }
	            }
            }
            
		return "Ville effacee";
	}
}
