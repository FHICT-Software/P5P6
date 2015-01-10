//<editor-fold defaultstate="collapsed" desc="Jibberish">
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aex.push;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Koen
 */
public class AEXBanner extends Application
{
    //@FXML Label tfAEX;
    public void setKoersen(final String koersen)
    {
      //???
       // tfAEX.setText(koersen);
       
    }

    @Override
    public void start(Stage stage) throws Exception
    {          

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Parent root = FXMLLoader.load(getClass().getResource("AEXBanner.fxml")); 
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        
//         AnimationTimer timer = new AnimationTimer()
//        {
//            private long prevUpdate;
//
//            @Override
//            public void handle(long now)
//            {
//                long lag = now - prevUpdate;
//                if(lag>=20000000)
//                {
//                    prevUpdate = now;
//                }
//            }
//            @Override public void start()
//            {
//                prevUpdate = System.nanoTime();
//                super.start();
//            }
//        };
//        
//        timer.start();
        
        
    }
    
    public static void main(String[] args)
    {
        Application.launch(args);           
    }
    
}
