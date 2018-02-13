package module_3;

/**
 *
 * @author h17lukar
 */
public class Course {
    private String title;		//title of the course
	private String grade;		//grade of the course
	private double credits;		//credits of the course
	private int hours;		//hours studied for course
	private String date;		//Task 9

	/*--------------------*/

	/*
	input validation will work as:
	if value is not within a specific range, set to 0
	and let the user know this has happened.
	*/

	//constructor for non finished course
	public Course(String title)
	{
		this.title = title;
		grade = "U";
		credits = 0;
		hours = 0;
		date = "";
	}

	//constructor for ongoing or failed
	public Course(String title, int hours)
	{
		this.title = title;
		//hour should be some value between 0 to 500
		if(hours > 0 && hours < 500){
			this.hours = hours;
		} else {
			this.hours = 0;
			System.out.println(hours + 
				" is not a reasonable amount of hours" +
				", hours has been set to 0");
		}
		grade = "U";
		credits = 0;
		date = "";
	}

	/**
	* Constructor which is actually used in the program
	*/
	public Course(String title, String grade,
		int hours, double credits)
	{
		this.title = title;
		//hour should be some value between 0 to 500
		if(hours > 0 && hours < 500){
			this.hours = hours;
		} else {
			this.hours = 0;
			System.out.println("WARNING: " + hours + 
				" is not a reasonable amount of hours" +
				", hours has been set to 0");
		}
		//grade can only be U, G or VG
		if(grade.equals("U") || grade.equals("G") || grade.equals("VG")){
			this.grade = grade;
		} else {
			this.grade = "U";
			System.out.println("WARNING: "+ grade + " is not a valid lettergrade" +
				", grade has been set to U");
		}
		//courses are between 0 and 30
		if(credits >= 0 && credits <= 30){
			this.credits = credits;
		} else {
			this.credits = 0;
			System.out.println("WARNING: "+ credits + " is not a valid number of credits, " +
				"credits has been set to 0");
		}

		date = "";
	}

	/**
	* Constructor that deals with the user reading a course from a .txt file.
	* has a boolean to have a different signature compared to the constructor
	* only taking a title. I will assume that this file was written from 
	* the program and therefore not require input validation
	* @param course String of one line with all course information
	* @param txt Boolean which indicates that the string comes from a .txt file
	*/
	public Course(String course, boolean txt)
	{
		int accumulator = 0; //keeps check of what type of value
		int last_pos = 0;	//keeps track of where last ',' was found
		boolean hasDate;

		//need to check if the course has a date entry
		for(int i = 0; i < course.length(); i++)
		{
			if(course.charAt(i) == ',')
				accumulator += 1;
		}

		// if we have 4 commas, a date has been entered
		if(accumulator == 3){
			hasDate = false;
		} else {
			hasDate = true;
		}

		//reset accumolator for the real part
		accumulator = 0;

		/*The string is basically a line from a .csv file and 
		in this program I will expect the txt file to have been 
		written from my program and therefore follow a strict
		pattern*/
		for(int i = 0; i < course.length(); i++)
		{
			if(course.charAt(i) == ',' && accumulator == 0){
				//this case has the course name
				title = course.substring(0, i);
				accumulator += 1;
				last_pos = i; //keep track of where the last ',' was found

			} else if(course.charAt(i) == ',' && accumulator == 1){
				//this case has the course grade
				grade = course.substring((last_pos + 1), i);
				accumulator += 1;
				last_pos = i;

			} else if(course.charAt(i) == ',' && accumulator == 2 && hasDate){
				//this case has the course credits
				credits = Double.parseDouble(course.substring((last_pos + 1), i));
				accumulator += 1;
				last_pos = i;

				//hours no date
			} else if(course.charAt(i) == ',' && accumulator == 2 && !hasDate){
				credits = Double.parseDouble(course.substring((last_pos + 1), i));
				hours = Integer.parseInt(course.substring((i + 1)));

				//hours date
			} else if(course.charAt(i) == ',' && accumulator == 3 && hasDate){
				//this case has the course hours
				hours = Integer.parseInt(course.substring((last_pos + 1), i));
				date = course.substring((i + 1));
			}
		}

		// strange behaviour were sometimes the program will 
		// write null to date. Not proud of this solution
		if(hasDate && date.equals("null")){
				date = null;
		}
	}

	//copy constructor
	public Course(Course course)
	{
		title = course.title;
		grade = course.grade;
		credits = course.credits;
		hours = course.hours;
		date = course.date;
	}

	/*--------------------*/

	//mutators 

	/**
	* Should still keep the validation parts
	*/
	public void setGrade(String grade)
	{
		if(grade.equals("U") || grade.equals("G") || grade.equals("VG")){
			this.grade = grade;
		} else{
			System.out.println("Not a valid entry. No changes made.");
		}
	}

	/**
	* Load the setGrade function, so that it can take a date if the user goes from U to G or VG
	* @param grade is the letter grade of the course
	* @param date is the date of going from U to G or VG
	*/
	public void setGrade(String grade, String date)
	{
		//use the checkDate method in the if statement.
		if((grade.equals("U") || grade.equals("G") || grade.equals("VG")) &&
			checkDate(date) == true){

			this.grade = grade;
			this.date = date;
			
		} else{
			System.out.println("Not a valid entry. No changes made.");
		}
	}

	/**
	* This method takes the date in setGrade and double checks
	* that it (1) follows format and given that it does, (2)
	* nothing unrealistic. This check is so subpar but fills
	* the criteria in task 8 and then some.
	*/
	public boolean checkDate(String dat)
	{
		int check;
		boolean flag = true;
		
		//check length of string
		if(dat.length() != 8){
			
			flag = false;
			
			//check that position 2 and 5 contains '-'
		} else if((dat.charAt(2) != '-') && (dat.charAt(5) != '-')){
			
			flag = false;

			/*check year so that it is between 0 and 17. 
			Note that integer parse of 07 (for example) becomes 7*/
		} else if(!(Integer.parseInt(dat.substring(0,2)) >= 0 && 
			        Integer.parseInt(dat.substring(0,2)) <= 17)){

			flag = false;

			//check that month is between 1 & 12
		} else if(!(Integer.parseInt(dat.substring(3,5)) > 0 && 
					Integer.parseInt(dat.substring(3,5)) <= 12)){

			flag = false;

			//lastly we check that day is a value between 1 & 31
		} else if(!(Integer.parseInt(dat.substring(6,8)) > 0 &&
					Integer.parseInt(dat.substring(6,8)) <= 31)){
			flag = false;
		}

		return flag;
	}

	/**
	* Should still keep the validation parts
	*/
	public void setCredits(double credits)
	{
		if(credits >= 0 && credits <= 30){
			this.credits = credits;
		} else {
			System.out.println("Not a valid entry. No changes made.");
		}

	}

	/**
	* Should still keep the validation parts
	*/
	public void setHours(int hours)
	{
		if(hours > 0 && hours < 500){
			this.hours = hours;
		} else{
			System.out.println("Not a valid entry. No changes made.");
		}
	}

	/*------------------*/
	// accessors
	public int getHours()
	{
		return hours;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDate()
	{
		return date;
	}

	public String getGrade()
	{
		return grade;
	}

	public String toString()
	{
		String str;

		//return string will depend on whether date entry has been entered
		if(date == "" || date == null){
			str = "Course name: " + title + ",   Grade: " + grade + ",   Credits: " + 
				credits + ",   Hours: " + hours;
		}else{
			str = "Course name: " + title + ",   Grade: " + grade + ",   Credits: " + 
				credits + ",   Hours: " + hours + ",	Date passed: " + date;
		}

		return str;
	}

	/**
	* This is only for the method of courseList of writing courses
	* to a txt file. Want it to have a easy format to read in data
	*/
	public String toString(boolean txt)
	{
		String str;

		//return string will depend on whether date entry has been entered
		if(date == "" || date == null){
			str = title + "," + grade + "," + credits + "," + hours;
		}else{
			str = title + "," + grade + "," + credits + "," + hours + "," + date;
		}

		return str;
	}
}
