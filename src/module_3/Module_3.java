/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module_3;

import java.util.ArrayList;
import java.io.*;

/**
 *
 * @author h17lukar
 */
public class Module_3 {

    public static void main(String[] args)throws java.io.IOException {
        
        CourseList cl = new CourseList();

		
	String[] m = {"Add Course", "Remove Course", 
	"Select Course", "Write to .txt", "Read from .txt","Exit"};
		

	//create an instance of
	Menu menu = new Menu(m, cl);
	menu.getMenu();
    }
    
}
