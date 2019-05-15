package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Map<Integer,Country> mappa;
	private BordersDAO dao;
	private SimpleGraph<Country,DefaultEdge> grafo;
	private List<String> countries;
	
	public Model() {
	
		mappa = new HashMap<Integer,Country>();
		dao = new BordersDAO();
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
	}

	public void creaGrafo(int anno) {
		
		countries = dao.loadAllCountries(mappa);
		Graphs.addAllVertices(grafo, mappa.values());
		dao.getCountryPairs(anno, grafo, mappa);
		System.out.println(grafo.vertexSet());
		dao.setGradi(anno, grafo, mappa);
	}
	
	public Map<Integer,Country> getMap(){
		return this.mappa;
	}
	
	public List<String> getCountries(){
		return this.countries;
	}
	
	public int compConn(Country vertex) {
		ConnectivityInspector<Country,DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		ArrayList<Country> res = new ArrayList<Country>(ci.connectedSetOf(vertex));
		return res.size();
	}
	
	public Set<Country> getVicini(Country paese){
	 
		Map<Country,Country> visitati = new HashMap<Country, Country>();
		visitati.put(paese, null);
		BreadthFirstIterator<Country,DefaultEdge> it = new BreadthFirstIterator<Country, DefaultEdge>(grafo,paese);
		it.addTraversalListener(new TraversalListener<Country,DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
				Country sorgente = grafo.getEdgeSource(ev.getEdge());
				Country destinazione = grafo.getEdgeTarget(ev.getEdge());

				if(!visitati.containsKey(destinazione) && visitati.containsKey(sorgente))
					visitati.put(destinazione, sorgente);
				else if(!visitati.containsKey(sorgente) && visitati.containsKey(destinazione))
					visitati.put(sorgente, destinazione);
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
				// TODO Auto-generated method stub
				
			}

		});
		
		while(it.hasNext()) {
			it.next();
		}
		Set<Country> vicini = new HashSet<Country>(visitati.keySet());
		
		return vicini;
	}
}
