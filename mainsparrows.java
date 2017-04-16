package com.saveliy.sparrows;

import java.util.*;
import java.io.*;
import java.math.*;

public class mainsparrows{
	
	static int aos; 							// (amount of sparrows) кол-во активных воробьев  
	static boolean babushkac = true; 			//флаг работы бабушки, если true бабушка работает
	
	public static void main(String[] args) {
		long before = System.currentTimeMillis();
		int i;
		int M = 5; 								//изначальное кол-во воробьев

		babushka bab = new babushka(); 			//создаем объект класса бабушка
		Thread babwork = new Thread(bab);		//создаем Thread объекта
		babwork.start();						// запускаем бабушку
		
		sparrow[] sp = new sparrow[100];		//создаем массив объеков класса воробей

		for ( i = 1; i < 100 ; i++) {
			sp[i] = new sparrow(i);				// создаем 100 воробьев
		}

		i = 1;
		Thread[] t = new Thread[100];			//создаем 100 thread'ов
		
		for ( i = 1; i < M + 1; i++){	
			t[i] = new Thread(sp[i]);			
			t[i].start();						//запускаем первые М Thread'ов
			aos++;
		}
		
		i = M + 1;
		
		while(true){
			if (aos == 0){						//когда количество наевшихся воробьев будет 0,
				break;							//новых воробьев не пускаем
			}

			t[i] = new Thread(sp[i]);
			t[i].start();
			aos++;
			i++;
			try{
					Thread.sleep(3000);			//каждые 3 секунды прилетает новый воробей
				} catch(InterruptedException ex){}
		}

		babushkac = false;
		babwork.interrupt();
		try{						//бабушка идет домой
			babwork.join();
		}catch(InterruptedException ex){};
		System.out.println("Program finished in " + ((System.currentTimeMillis() - before)/1000) +" seconds");

	}

}