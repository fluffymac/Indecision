/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indecision;

import environment.ApplicationStarter;
import java.awt.Dimension;

/**
 *
 * @author mayajones
 */
public class Indecision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ApplicationStarter.run("Indecision Decisions", new IndecisionEnvironment());
        ApplicationStarter.run(args, "Indecision Decisions", new Dimension(900, 600), new IndecisionEnvironment());
    }
    
}
