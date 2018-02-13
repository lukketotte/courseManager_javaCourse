
package module_3;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author h17lukar
 */
public class CourseList {
    	Course course;
	ArrayList<Course> courseList = new ArrayList<Course>();

	/**
	* Constructor takes a Course object and uses
	* the copy constructor of Course class and then
	* add the created course to array list.
	*/
	public CourseList(Course course)
	{
		this.course = new Course(course);
		//add this course to the course list
		courseList.add(course);
	}

	/**
	* Constructor that doesn't take any argument
	*/
	public CourseList()
	{
		course = null;
	}

	/**
	* Copy constructor
	*/
	public CourseList(CourseList cl)
	{
		courseList.addAll(cl.getCourseList());
	}

	/**
	* Add course to array list
	*/
	public void addCourse(Course course)
	{
		//true if name is unique in the array list
		if(checkName(course.getTitle())){
			courseList.add(course);
			System.out.println("Course successfully added.");
		} else { //let the user now it won't be added
			System.out.println("Course already exists in the list!");
		}
	}

	/**
	* This function takes the course name
	* and checks the array list to see that
	* the user doen't add a pre-existing 
	* course name. Only used in this class, private?
	* @param nameCheck is the course name.
	* @return uniqueName is the flag returned by the method.
	*/
	public boolean checkName(String nameCheck)
	{
		boolean uniqueName = true;	//will assume the name is unique

		//go through the names of the array list and set return to false if we find the name
		for(int i = 0; i < courseList.size(); i++)
		{
			if(nameCheck.equalsIgnoreCase(courseList.get(i).getTitle())){
				uniqueName = false;
			}
		}

		return uniqueName;
	}

	/**
	* Get the course list
	*/
	public ArrayList<Course> getCourseList()
	{
		return courseList;
	}

	/**
	* Part of problem 6.
	* Get length of course list
	*/
	public int getCourseListlength()
	{
		return courseList.size();
	}

	/**
	* Part of problem 6.
	* Get number of hours studied
	*/
	public int getTotalHours()
	{
		int hours = 0;
		for(int i = 0; i < courseList.size(); i++)
		{
			hours += courseList.get(i).getHours();
		}

		return hours;
	}

	/**
	* This method should allow the user to see % of courses passed
	* using the lettergrade
	* @passed the percentage of courses passed based on the letter grade
	*/

	public String getTotalPass()
	{
		String passed; 		//return object
		double sum = 0;			//double s.t we get the quotient to be double

		//go through the entire array list of courses
		for(int i = 0; i < courseList.size(); i++)
		{
			if(courseList.get(i).getGrade().equals("G") || courseList.get(i).getGrade().equals("VG")){
				sum += 1;
			}
		}

		//percentage of passed based on the letter grade. If user adds course and removes it will say NaN
		//because division by zero
		if(Double.isNaN(sum/courseList.size())){
			passed = "-";
		} else{
			passed = String.format("%.2f", 100*(sum/courseList.size()));
		}

		return passed;
	}

	/**
	* should take a string which will be the name of the txt file.
	* @param filename is the name of the txt file the user wants to create.
	*/
	public void toTxt(String filename) throws java.io.IOException
	{
		//in case the user provides filename which already exists
		Scanner key = new Scanner(System.in);
		
		//take the filename, assume it has .txt in the end
		File file = new File(filename);
		
		//stacked toString method of Course class
		boolean txt = true;

		//open the file
		PrintWriter outputFile = new PrintWriter(file);
		//write the data to the file
		for(int i = 0; i < courseList.size(); i++)
		{
			outputFile.println(courseList.get(i).toString(txt));
		}
		outputFile.close();
		System.out.println("Courses written to " + filename);
	}

	/**
	* toString method
	* @return is the String of all courses
	*/
	public String toString()
	{
		String str = "";

		//go through the entire array list of courses
		for(int i = 0; i < courseList.size(); i++)
		{
			str += (i+1) + ", " + courseList.get(i).toString() + "\n";
		}
		return str;
	}
}
