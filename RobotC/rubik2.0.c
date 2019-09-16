#pragma config(Sensor, port2,  lTouch,         sensorVexIQ_LED)
#pragma config(Sensor, port4,  sensorD,        sensorVexIQ_Distance)
#pragma config(Sensor, port9,  rTouch,         sensorVexIQ_LED)
#pragma config(Motor,  motor1,          rPusher,       tmotorVexIQ, PIDControl, reversed, encoder)
#pragma config(Motor,  motor5,          bMotor,        tmotorVexIQ, PIDControl, encoder)
#pragma config(Motor,  motor8,          lPusher,       tmotorVexIQ, PIDControl, reversed, encoder)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

int maxR = 60;
int maxL = 150;
int maxB = 90;

void testP() {
	setMotorTarget(lPusher, 60, 110);
	waitUntilMotorStop(lPusher);
	setMotorTarget(lPusher,  1, 70);
	waitUntilMotorStop(lPusher);
}



void push() {
	setMotorTarget(lPusher, maxL, 90);
	waitUntilMotorStop(lPusher);
	setMotorTarget(lPusher,  1, 70);
	waitUntilMotorStop(lPusher);

	moveMotorTarget(rPusher, maxR, 90);
	waitUntilMotorStop(rPusher);
	moveMotorTarget(rPusher, maxR * -1, 90);
	waitUntilMotorStop(rPusher);
}

void moveBaseLeft() {
	moveMotorTarget(bMotor, maxB -10, 70);
	waitUntilMotorStop(bMotor);
	int fix = getMotorEncoder(bMotor);
	moveMotorTarget(bMotor, maxB - fix, 10);
	resetMotorEncoder(bMotor);
}

void TurnDown(){
	resetMotorEncoder(bMotor);
	moveMotorTarget(rPusher, maxR, 70);
	waitUntilMotorStop(rPusher);


	moveMotorTarget(bMotor, 123, 70);
	waitUntilMotorStop(bMotor);

	moveMotorTarget(rPusher, maxR * -1, -70);
	waitUntilMotorStop(rPusher);

	int fix = 90 - getMotorEncoder(bMotor);

	moveMotorTarget(bMotor, fix , 20);
}


void getColor(string &color)
{
	color = "none";
	int hue = getColorHue(sColor);
	if((hue>=0 && hue <10) || (hue <= 255 && hue >248)) {
		color = "RED";
	}
	else if(hue >= 165 && hue <= 175) {
		color = "BLUE";
	}
	else if(hue >= 75 && hue <= 98) {
		color = "GREEN";
	}
	else if(hue >= 55 && hue <= 68) {
		color = "YELLOW";
	}
	else if(hue >= 180 && hue <= 215) {
		color = "MAGENTA";
	}
	else if(hue >= 120 && hue <= 155) {
		color = "CYAN";
	}
	else if(hue >= 158 && hue <= 162) {
		color = "WHITE";
	}

	else if(hue >= 25 && hue <= 35) {
		color = "ORANGE";
	}

}

char currentFace;

void moveToR(){
	switch(currentFace) {
	case 'L':
		push();
		push();
		break;
	case 'F':
		push();
		break;
	case 'R':

		break;
	case 'B':
		push();
		push();
		push();
		break;
	case 'D':
		push();
		moveBaseLeft();
		break;
	case 'U':
		push();
		moveBaseRight();
		break;
	}

	currentFace = 'R';
}

void moveToU(){
	switch(currentFace) {
	case 'L':

		break;
	case 'F':

		break;
	case 'R':

		break;
	case 'B':

		break;
	case 'D':

		break;
	case 'U':

		break;
	}


}

void moveToF(){

}
void moveToL (){

}
void moveToD(){

}



bool runRead = false;

void startRead(){
	while(runRead){
		int hue = getColorHue(sColor);
		string colorName;
		getColor(colorName);
		displayCenteredBigTextLine(1, colorName);
		displayCenteredBigTextLine(4, "%d", hue);
		switch (colorName){
		case "RED":
			moveToR();
			break;


		case  "BLUE":
			moveToU()
			break;
		case  "GREEN":
			moveToF()
			break;

		case  "MAGENTA":
			moveToL();
			break;
		case  "CYAN":
			moveToD()
			break;

		case  "WHITE":

			break;

		case "YELLOW":

			break;

		case "ORANGE":

			break;

		}
	}
}

task main()
{
	resetMotorEncoder(lPusher);
	resetMotorEncoder(rPusher);
	resetMotorEncoder(bMotor);

	while(true){
		int lVal = getMotorEncoder(lPusher);
		int rVal = getMotorEncoder(rPusher);
		int bVal = getMotorEncoder(bMotor);
		float sVal = getDistanceValue(sensorD);

		displayCenteredBigTextLine(0, "%d", lVal);
		displayCenteredBigTextLine(2, "%d", rVal);
		displayCenteredBigTextLine(4, "%f", bVal);

		if(getTouchLEDValue(lTouch) == 1) {
			startRead();
		}

		if(getTouchLEDValue(rTouch) == 1) {
			TurnDown();
		}

		if(getTouchLEDValue(rTouch) == 1) {
			TurnDown();
		}
	}
}
