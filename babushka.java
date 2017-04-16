package com.saveliy.sparrows;

import java.util.*;
import java.io.*;
import java.math.*;

public class babushka implements Runnable{

	static int N = 30; 								//количество выбрасываемых семок за 1 раз
	static int Q = 6000;							//период выбрасывания семок(в мс)

	@Override
	public void run(){
		throwseed();								//бабушка, накорми их всех
		System.out.println("BABUSHKA FINISHED");
	}



	synchronized void throwseed(){
		while(mainsparrows.babushkac){				//пока главный не сказал домой, работаем
			
			for (int i = 0; i < N ; i++){
				data.seeds++;						//по одной семке с периодом 0.1с выбрасывается N семок				
				try{
					Thread.sleep(100);
				} catch(InterruptedException ex){}	
			}

			System.out.println("SEEDS ON TABLE:" + data.seeds);		
			try{
				Thread.sleep(Q);					//ждем Q секунд
			} catch(InterruptedException ex){}	
		}
	}
}