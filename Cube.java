                                    /**
* The class Cube represents a color cube used in the Insant Insanity puzzle.
*
*/
public class Cube{
	/**
	* Keeps track of the original orientation of the cube created.
	*/
	private final Color UP,FRONT,RIGHT,BACK,LEFT, DOWN; 
	/**
	* Represents a cube 
	*/
	private Color[] cube;
	/**
	* Stores the different orientations generated by the method next
	*/
	private Color[][] colorCombo;
	/**
	* Rotations keeps tracks of the number of times the method rotate has been called in next and is reset after every three calls
	*/
	private int rotations;
	/**
	* Count is used to keep track of whether the method next should perform a right roll or left roll
	*/
	private int count;
	/**
	*Location is used to keep track of where the next orientation of the cube should be store in colorCombo
	*/
	private int location;
	/**
	* Pattern move keeps track of the number of which move the method next has done in it's 24 different moves and is reset every time it reaches 25
	*/
	private int patternMove;
	/** 
	* Creates a cube with the specified name.
	* @param colorList A list containing the colors of sides of the cube in the follow order up,front,right,back,left and down.
	* @throws IllegalColorException if a color in the given list of colors is null.
	*/

	public Cube(Color[] colorList){
		if(colorList == null){
			throw new NullPointerException("Null is not a valid parameter");
		}
		for(int i = 0; i < colorList.length; i++){
			if( colorList[i] == null){
				throw new IllegalColorException("NULL is not a color");
			}
		}
		this.UP = colorList[0];
		this.FRONT = colorList[1];
		this.RIGHT = colorList[2];
		this.BACK = colorList[3];
		this.LEFT = colorList[4];
		this.DOWN =  colorList[5];
		this.cube = colorList;
		this.colorCombo = new Color[24][6];
		this.rotations = 0;
		this.count = 0;
		this.location = 0;
		this.patternMove = 0;
	}
	/** 
	* Creates a deep copy of the cube given in the constructor with the specified name.
	* @param other The cube that is supposed to be deep copied.
	*
	*/

	public Cube(Cube other){
		if(other == null){
			throw new NullPointerException("Null is not a valid parameter");
		}
		int length = other.colorCombo.length;
		Color[] tempColorCom = new Color[6];
		this.UP = other.UP;
		this.FRONT = other.FRONT;
		this.RIGHT = other.RIGHT;
		this.BACK = other.BACK;
		this.LEFT = other.LEFT;
		this.DOWN = other.DOWN;
		this.rotations = other.rotations;
		this.count = other.count;
		this.location = other.location;
		this.patternMove = other.patternMove;
		this.cube = colorArrayCopy(other.cube);
		this.colorCombo = matrixArrayCopy(other.colorCombo);
	}

	/** 
	* Returns a deep copy of this cube.
	*
	*   @return Returns a deep copy of this cube. 
	*/

	public Cube copy(){
		Cube copyOfCube = new Cube(this);
		return copyOfCube;
	}
	/**
	* Resets all the cubes to their original orientation.
	*/

	public void reset(){
		cube[0] = UP;
		cube[1] = FRONT;
		cube[2] = RIGHT;
		cube[3] = BACK;
		cube[4] = LEFT;
		cube[5] = DOWN;
		colorCombo = new Color[24][6];
		patternMove = 0;
		location = 0;
		count = 0;
		rotations = 0;

	}
	/** 
	* Rotates the cube to the right around the top-bottom axis so that the left side is now facing front.
	*/
	public void rotate(){
		Color tempFront = cube[1];
		Color tempRight = cube[2];
		Color tempBack = cube[3]; 
		Color tempLeft = cube[4];

		cube[1] = tempLeft;
		cube[2] = tempFront;
		cube[3] = tempRight;
		cube[4] = tempBack;

	}
	/*
	*Rotates the cube to the right around the back-front axis so that the left side is now up.
	*/

	public void rightRoll(){
		Color tempUp = cube[0];
		Color tempFront = cube[1];
		Color tempRight = cube[2];
		Color tempBack = cube[3]; 
		Color tempLeft = cube[4];
		Color tempDown = cube[5];

		cube[0] = tempLeft;
		cube[1] = tempFront;
		cube[2] = tempUp;
		cube[3] = tempBack;
		cube[4] = tempDown;
		cube[5] = tempRight;
	}
	/** 
	* Rotates the cube to the left around the back-front axis so that the right side is now up.
	*/

	public void leftRoll(){
		Color tempUp = cube[0];
		Color tempFront = cube[1];
		Color tempRight = cube[2];
		Color tempBack = cube[3]; 
		Color tempLeft = cube[4];
		Color tempDown = cube[5];

		cube[0] = tempRight;
		cube[1] = tempFront;
		cube[2] = tempDown;
		cube[3] = tempBack;
		cube[4] = tempUp;
		cube[5] = tempLeft;
	}
	/**
	* Checks if calling the method next() will work
	* @return Returns true if caling the method next() will work and false otherwise.
 	*/

	public boolean hasNext(){
		if(patternMove == 24){
			return false;
		}
		return true;
	}
	/** 
	* Rotates the orientation of the cube in the following in order: Identity, Rotate, Rotate, Rotate, Right roll, Rotate, Rotate, Rotate, Right roll. Rotate, Rotate, Rotate, Left roll, Rotate, Rotate, Rotate, Left roll, Rotate, Rotate, Rotate, Right roll, Rotate, Rotate, Rotate.
	* @throws IllegalStateException If the call to next rotates the cube to an orientation that has already been seen since the last time it has been reset.
	*/

	public void next(){
		Color[] tempArray = new Color[6];
		//Adding the previous combo to the Combination list
		for(int i =0; i < 6; i++){
			tempArray[i] = cube[i];
		}
		if(location != 24){
			colorCombo[location] = tempArray;
			location++;
		}

		if(patternMove == 0) {
			reset();
		}
		else {
			if(rotations == 3){
				rotations = 0;

				if(count < 2){
					rightRoll();
					count++;
				}
				else if(count >= 2){
					leftRoll();
					if(count == 3){
						count = 0;  
					}
					count++;
				}
			}
			else if(rotations != 3){
				rotate();
				rotations++;
			}	

			if(!validMove()){
				throw new IllegalStateException();
			}
		}	
		patternMove ++;	
		if(patternMove == 25){
			patternMove = 0;
		}


	}
	/**
	* Checks to see if orientation of the cube is one that has already been seen since the last reset.
	* @return Returns true if the cube's oritentation has not been since the last reset, and false otherwise.
	*/
	
	public boolean validMove(){
		int length = colorCombo.length;
		int valid = 0;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < 6; j++){
				if(colorCombo[i][j] != cube[j]){
					valid++;
				}
			}

			if(valid == 0){
				return false;
			}
			valid = 0;
		}

		return true;
	}
	/**
	* Returns a deep copy of a color array
	* @param array The color array to be copied.
	* @return Returns a deep copy of the color array.
	*/

	private Color[] colorArrayCopy(Color[] array){
		if(array == null){
			throw new NullPointerException("Null is not a valid parameter");
		}
		int length = array.length;
		Color[] copyArray = new Color[length];

		for(int i = 0; i < length; i++){
			copyArray[i] = array[i];
		}
		return copyArray;
	}
	/**
	* Returns a deep copy of a 2D color array.
	* @param array The 2D color array to be copied.
	* @return Returns a deep copy of the 2D color array given in parameter.
	*/

	private Color[][] matrixArrayCopy(Color[][] array){
		if(array == null){
			throw new NullPointerException("Null is not a valid parameter");
		}
		int length = array.length;
		Color[][] copyArray = new Color[24][6];

		for(int i = 0; i < length; i++){
			copyArray[i] = colorArrayCopy(array[i]);
		}
		return copyArray;
	}

	/**
	* Getter for the color at the top position of the cube.
	* @return Returns the top position of the cube.
	*/
	public Color getUp() {
		return this.cube[0];
	}

	/**
	* Getter for the color at the front position of the cube.
	* @return Returns the front position of the cube.
	*/
	public Color getFront() {
		return this.cube[1];
	}

	/**
	* Getter for the color at the right position of the cube.
	* @return Returns the right position of the cube.
	*/

	public Color getRight() {
		return this.cube[2];
	}

	/**
	* Getter for the color at the back position of the cube.
	* @return Returns the back position of the cube.
	*/

	public Color getBack() {
		return this.cube[3];
	}

	/**
	* Getter for the color at the left position of the cube.
	* @return Returns the left position of the cube.
	*/

	public Color getLeft() {
		return this.cube[4];
	}

	/**
	* Getter for the color at the down position of the cube.
	* @return Returns the down position of the cube.
	*/

	public Color getDown() {
		return this.cube[5];
	}

	/**
	* Builds a string representation of the cube
	* @return Returns a string representation of the cube
	*/

	public String toString(){
		String cubeString = "[";
		for(int index = 0; index < cube.length -1;index ++){
			cubeString += cube[index] + " ,";

		}
		return cubeString + cube[5] + "]";

	}

}