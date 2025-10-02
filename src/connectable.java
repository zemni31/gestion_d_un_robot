
public interface connectable {

	void connecter ( String reseau ) throws RobotException ;
	void deconnecter ( );
	void envoyerDonnees ( String donnees ) throws RobotException ;
}
