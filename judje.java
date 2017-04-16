package com.saveliy.sparrows;

import java.util.*;
import java.io.*;
import java.math.*;

public class judje implements Runnable{

	int maxx;										//макс на данный момент значение

	@Override
	public void run(){

		maxx = data.skill[0];						//изначально макс значение у первого воробья
		System.out.print("sparrows points: ");		//печатаем у кого сколько
		for(int i = 0; i < data.judjemax; i++){	
			System.out.print(data.skill[i] + " ");	//печатаем у кого сколько
			if ( data.skill[i] >= maxx){			//если находим воробья, у которого скилл больше чем макс значение 
				maxx = data.skill[i];				//присваиваем макс значение скиллу этого воробья
				data.winner = i + 1;				//и номер победителя номеру этого воробья
			}
		}
		System.out.printf("\n");	

		try{
			Thread.sleep(100);						//просто потянем время, чтоб воробьи подождали
		} catch(InterruptedException ex){}
	
	}
		
}