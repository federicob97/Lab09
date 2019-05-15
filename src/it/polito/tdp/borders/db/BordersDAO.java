package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<String> loadAllCountries(Map<Integer,Country> mappa) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<String> result = new ArrayList<String>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(c.getName());
				mappa.put(c.getCode(), c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, SimpleGraph<Country,DefaultEdge> grafo, Map<Integer,Country> mappa) {
		
		String sql = "SELECT state1no, state2no, conttype " + 
				"FROM contiguity " + 
				"WHERE conttype = 1 AND state1no>state2no AND YEAR <= ? ";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Border b = new Border(mappa.get(rs.getInt("state1no")),mappa.get(rs.getInt("state2no")),rs.getInt("conttype"));
				grafo.addEdge(b.getState1(), b.getState2());
				result.add(b);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	public void setGradi(int anno, SimpleGraph<Country,DefaultEdge> grafo, Map<Integer,Country> mappa) {
		
		String sql = "SELECT state1no, COUNT(state1no) AS cnt " + 
				"FROM contiguity " + 
				"WHERE conttype = 1 AND YEAR <= ? " + 
				"GROUP BY state1no";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				if(mappa.get(rs.getInt("state1no"))!=null)
					mappa.get(rs.getInt("state1no")).setGrado(rs.getInt("cnt"));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
		
	}
}
