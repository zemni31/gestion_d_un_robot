public class MainTestRobot {
    public static void main(String[] args) {
        try {
            RobotLivraison robot = new RobotLivraison("R2D2", 0, 0);
           
            System.out.println(robot);
          
            robot.demarrer();

     
            robot.connecter("reseau-test");

            robot.chargerColis("Paris");
            //robot.effectuerTache();
            
          

            System.out.println(robot);
            robot.FaireLivraison(20, 20);

           
           robot.envoyerDonnees("donnees1");
           robot.deconnecter();
//robot.recharger(40);
           
            
            System.out.println("\nHistorique des actions :");
            System.out.println(robot.getHistorique());
            System.out.println(robot);
        } catch (RobotException e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
