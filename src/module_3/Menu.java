
package module_3;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author h17lukar
 */
public class Menu {
    
    private final int NOPT = 6;				//number of options in the meenu
	Course course;					//Course object
	CourseList courseList;				//CourseList object
	String[] menu = new String[NOPT];		//String array of menu options
	boolean course_entered; 			//will be false until a course has been entered

	/**
	* Constructor that takes a string of options and a 
	* CourseList object
	*/
	public Menu(String[] m, CourseList cl)
	{
		for(int i = 0; i < NOPT; i++){
			menu[i] = m[i];
		}

		courseList = new CourseList(cl);
		course_entered = false;
	}

	/**
	* This is the menu of the program. It will, if courses has been entered
	* display all the contents of the CourseList instance. 
	*/
	public void getMenu() throws java.io.IOException
	{	
		//scanner object so the user can make choices
		Scanner key = new Scanner(System.in);
		int user_choice;

		if(course_entered){		//if a course has been entered, go on to display the following
			System.out.println("Courses registered: ");
			System.out.print(courseList);
			System.out.print("------------------------------------------ \n");
			//task 6, # courses
			System.out.print("# courses: " + courseList.getCourseListlength());
			//task 6, # hours
			System.out.print(", # hours: " + courseList.getTotalHours());
			System.out.print(", % passed: " + courseList.getTotalPass() + "\n");
			System.out.print("------------------------------------------ \n");

		} else{		//if no course has been entered yet, don't display any contents from the courseList
			System.out.print("Courses registered: \n");
			System.out.print("------------------------------------------ \n");
			System.out.print("# courses: 0, # hours: 0, % passed: - \n");
			System.out.print("------------------------------------------ \n");
		}
	
		//prints the menu
		for(int i = 0; i < NOPT; i++)
		{
			System.out.printf("(%d) %-15s", i+1, menu[i]);
		}
		//formating for the user to choose an option
		System.out.println();
		System.out.print("------------------------------------------ \n");
		System.out.print("Choose an option, 1 to " + NOPT + ": ");

		//user enters choice, no validation
		user_choice = key.nextInt();
		userChoice(user_choice);
	
	}

	/**
	* User has made some choice, so a method should do what the user wants
	* and reprint the getMenu option. This will check the value of the user
	* and perform the task.
	* @param int c is what the user entered in the menu 
	*/
	public void userChoice(int user) throws java.io.IOException
	{
		//create scanner object outside the nested ifs
		Scanner key = new Scanner(System.in);
		
		if(user == 1){	//the user wants to add a course
			
			System.out.println("(1) Add Course:");

			//go through all the entries that are needed for the course object			
			String name;
			System.out.print("Enter name of course: ");
			name = key.nextLine();

			String grade;
			System.out.print("Enter grade, set to U if unfinished: ");
			grade = key.nextLine();

			double credits;
			System.out.print("Enter credits for course, 0 if unfinished: ");
			credits = key.nextDouble();

			int hours;
			System.out.print("Enter hours studied for course: ");
			hours = key.nextInt();

			System.out.println();

			// generate a course object
			course = new Course(name, grade, hours, credits);
			// add to the list of courses
			courseList.addCourse(course);

			//the menu should know a course has been entered
			course_entered = true;

			/*option 2 and 3 both involve finding index by submitting name so some parts
			of these options are the same. Note that this is the case where a course 
			has been entered to the courseList.*/
		} else if((user == 2 || user == 3) && course_entered){
			
			String course_name;  //name of course entered
			int course_position = 0; //what position in the array. Need to be initialized to not have error.
			boolean flag = false; //need to let the user know if an invalid name was supplied

			//use the course_entered 
			System.out.print("Choose a course using course name: ");
			course_name = key.nextLine();
			
			//first of all I want to make sure that the loop even starts to begin with
			while(!flag)
			{
				//get the index position of course name
				for(int i = 0; i < courseList.getCourseListlength(); i++)
				{
					//check each course title until right is found
					if(course_name.equalsIgnoreCase(courseList.getCourseList().get(i).getTitle())){
						//if found
						course_position = i;
						flag = true; 
					}
				}
				//if the position was not found, the user will be asked to try again
				if(!flag){
					System.out.print(course_name + " is not a valid course name. Try again: ");
					course_name = key.nextLine();
				}
			}

			//here is where option 2 & 3 goes separate ways
			if(user == 3){
				//print info
				System.out.println(courseList.getCourseList().get(course_position));

				//from here the user should be given the choice between going back or changing the information
				System.out.print("Would you like to update any information on the course? (Y or N): ");
				course_name = key.nextLine(); 	//no need to create any new String object
				
				if(course_name.equalsIgnoreCase("Y")){
					System.out.print("Choose what field you want to update using a number: (1) Grade, (2) Credits, (3) Hours: ");
					int update_choice = key.nextInt();
					
					//good time to try the switch statement!
					switch(update_choice)
					{
						case 1:
							System.out.print("Set the new letter grade: ");
							//consume what ever it was called in case of case 1 in the switch statement
							key.nextLine();
							course_name = key.nextLine();
							//now we want to check whether the user goes from U to G or VG
							if(courseList.getCourseList().get(course_position).getGrade().equals("U") &&
								(course_name.equals("G") || course_name.equals("VG"))){
								System.out.print("Enter date of passing (yy-mm-dd): ");
								String date = key.nextLine();
								//set grade with date information, setGrade is loaded (can't remember the word for it)
								courseList.getCourseList().get(course_position).setGrade(course_name, date);
								
								//and prevent the user from going back from G or VG to U
							} else if(course_name.equals("U") && 
								    (courseList.getCourseList().get(course_position).getGrade().equals("G") ||
									courseList.getCourseList().get(course_position).getGrade().equals("VG"))){

								System.out.println("Not allowed to go from 'G' or 'VG' to 'U'.");

							} else{
								//set the grade of course 
								courseList.getCourseList().get(course_position).setGrade(course_name);
							}
							break;
						case 2:
							System.out.print("Set the credits: ");
							double new_credit = key.nextDouble();
							courseList.getCourseList().get(course_position).setCredits(new_credit);
							break;
						case 3:
							System.out.print("Set the hours: ");
							int new_hours = key.nextInt();
							courseList.getCourseList().get(course_position).setHours(new_hours);
							break;
						default:
							System.out.println("No valid choice was entered");
							break;

					}
				} else {
					//make a new line between this and the reprinted  menu
					System.out.println();
				}
			} else if(user == 2){	//if user has entered == 2 we want to remove the course from array list
				courseList.getCourseList().remove(course_position);
				//let the user know
				System.out.println(course_name + " has been removed from the course list.");
			}

			//user wants to write to .txt file
		} else if((user == 2 || user == 3) && !course_entered){
			System.out.println("No course has been entered yet!");
		}else if(user == 4){ 
			System.out.print("Enter filename: ");
			//take the desired user filename, not checking for .txt ending
			String filename = key.nextLine();

			// use the toTxt method of courseList to write it to file
			courseList.toTxt(filename); 

			//user wants to read courses from .txt file
		} else if(user == 5){
			System.out.print("Enter filename: ");
			String filename = key.nextLine();

			File file = new File(filename);
			//user will just go back to the menu if filename not found
			if(!file.exists()){
				System.out.println("File not found");
			} else {
				//open the file for reading
				Scanner inputFile = new Scanner(file);
				// for Course constructor
				boolean txt = true;
				while(inputFile.hasNext())
				{
					//use the constructor taking a string and a boolean
					course = new Course(inputFile.nextLine(), txt);
					// add to the list of courses
					courseList.addCourse(course);
				}
				
				//want to display the contents in the menu
				course_entered = true;				
			}
		} else { 
			System.exit(0);
		}

		//return to the menu
		System.out.println();
		getMenu();

	}
    
}
