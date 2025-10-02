
public abstract class RobotConnect extends Robot implements connectable {
protected boolean connecte;
protected String reseauConnecte;

	public RobotConnect(String id,int x,int y) {
		super(id,x,y);
		connecte=false;
		 reseauConnecte=null;
		
	}
@Override	
public void connecter(String reseau) throws RobotException {
	 try {
         verifierEnergie(5);
       
     } catch (Energie_Insuffisante_Exception e) {
         ajouterHistorique("Échec de connexion: " + e.getMessage());
         throw new RobotException("echec de connexion : " + e.getMessage(),e);
     }
	  reseauConnecte=reseau;
      connecte=true;
      consommerEnergie(5);
      ajouterHistorique("Robot Connecté sur le reseau "+reseau+" avec "+consommationReelle+"% de consommation");
}
@Override
public void deconnecter() {
	connecte=false;
	 reseauConnecte=null;
	 ajouterHistorique("Robot deconnecté");
}
@Override
public void envoyerDonnees(String donnees)throws RobotException {
	
	
	    if (!connecte) {
	        ajouterHistorique("Robot déconnecté ! Échec d'envoi des données.");
	        throw new RobotException("Robot déconnecté ! Impossible d'envoyer des données.");
	    }

	    
	    try {
	        verifierEnergie(3); 
	    } catch (Energie_Insuffisante_Exception e) {
	        ajouterHistorique("Échec d'envoi des données : " + e.getMessage());
	        throw new Energie_Insuffisante_Exception("Énergie insuffisante pour envoyer des données.",e);
	    }

	    consommerEnergie(3);
	    ajouterHistorique("Robot a envoyé des données avec "+consommationReelle+"% de consommation.");
	}

}
