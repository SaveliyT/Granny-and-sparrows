package com.saveliy.sparrows;

import java.util.*;
import java.io.*;
import java.math.*;


public class sparrow implements Runnable{
	
	int num;											//индивидуальный номер воробья
	int eaten = 0;										//количество крошек, которое съел данный воробей
	static final int p = 6;								//сколько надо съесть, чтобы наестся								


	void sparrow(){

	}
	
	sparrow(int num){
		this.num = num;			
	}

	@Override
	public void run(){

		if (num > data.max){		
			data.max = num;								//присваиваем макс номер активного воробья номер посл воробья
			data.judjemax = num;
			data.flag = 0;								//если воробей с макс номером до этого ест, чтобы он не присвоил себе обратно
		}												//макс номер вместо последнего прилетевшего воробья
		System.out.println("sp " + num + " JOINED");
		
		waitingforfood(num, eaten);						//ждем семок
	}



	static void waitingforfood(int num, int eaten){ 
		while (eaten < p){								// == while(true)
			while(data.seeds < 1){						//если есть нечего, ждем
				System.out.println("BABUSHKA, WE NEED MORE SEEDS");
				Thread.yield();
				try{
					Thread.sleep(200);
					data.tjudje.join();						
				} catch (InterruptedException ex){System.out.println("EXCEPTION: interrupting sleeping process");} 
			}

			if(data.seeds >= 0){
				fight(num, eaten);						//если есть семки, идем за них драться	
			}
		}

	}	

	static void fight( int num, int eaten){ 		
		
		data.skill[num - 1] = (int)(Math.random()*(1024));	//каждый записывает в массив рандомное число
		try{
			Thread.sleep(100);
		} catch(InterruptedException ex){ System.out.println("EXCEPTION: interrupting sleeping process");}
		
		if (num == data.max){ 							//только активный воробей с макс номером заходит сюда
			if (!data.tjudje.isAlive()){ 				//если сюда еще зашел новый воробей с другим макс номером, его не пускать 
				try{
					data.tjudje.start();				//запускаем судью
				} catch (IllegalThreadStateException ex){System.out.println("EXCEPTION: judje has already started");}
			}
			try{
				data.tjudje.join();						//ждем решения судьи
			} catch (InterruptedException ex){System.out.println("EXCEPTION: interrupting sleeping process");} 
			data.tjudje = new Thread(data.jdj);			//создаем новый поток для судьи, чтобы запустить в след раз

		} else{
			try{
				Thread.sleep(1);
				data.tjudje.join();						// все остальные воробьи ждут решения судьи
;
			} catch (InterruptedException ex){System.out.println("EXCEPTION: interrupting sleeping process");} }

		if (num == data.winner){						//победитель идет хавать
			eating(num, eaten);
		}
		 
		if (eaten < p){									//остальные на новую разборку
			waitingforfood(num, eaten);
		}
	}	


	static   void eating(int num, int eaten){//
		try{
			Thread.sleep(10);
		} catch(InterruptedException ex){
			System.out.println("EXCEPTION: interrupting sleeping process");
		}
		eaten++;
		data.seeds--;									//похавал
		System.out.println("sp" + num + " ate, " + eaten + " seeds already ate");		
		data.flag = 0;								

		if(eaten >= p ) {								// если наелся, давай досвидания
			System.out.println("SPARROW " + num + " FINISHED");
			mainsparrows.aos--;
			data.skill[num - 1] = -1;					// в массиве, куда записываем размер скилла, у наевшегося воробья всегда -1
			for(int i = data.max; i > 0; i--){			//если макс номер был у этого воробья, то 
				if (data.skill[data.max - 1] <= 0){		//ищем другого активного воробья с макс номером
					data.max--;
				} else {break;}
			}

			System.out.println("sprws left " + mainsparrows.aos);	//печатаем сколько активных воробьев осталось	
			Thread.currentThread().stop();							//мы будем скучать по тебе

		} else {													//если похавал, но не наелся
			data.skill[num - 1] = 0;								//в массив для скилла записываем 0, чтобы не выиграть случайно
			if (num == data.max && mainsparrows.aos != 1){			//если макс номер был у него, и он не последний
				data.flag = 1;
				for(int i = data.judjemax; i > 0; i--){				//ищем другого активного воробья с макс номером
					if (data.skill[data.max - 1] <= 0){
						data.max--;
					} else {break;}
				}
			}

			if (mainsparrows.aos != 1){								//если он не последний из активных
				while (true){
					if (data.tjudje.isAlive()){						//ждем пока другой воробей не включит судью
						try{										
							data.tjudje.join()	;						//ждем пока судья не завершит пропущенный этим воробьем розыгрыщ
						}catch(InterruptedException ex){System.out.println("EXCEPTION: interrupting sleeping process");} 
						break;
					}
				}
			}
			if (data.flag == 1){									//возвращаем ему макс номер, если он у него был, и если
				data.max = num;										//новый воробей не подлетел			
			}
			waitingforfood(num, eaten);								//идем с остальными на новый розыгрыш семки
			
		}
	}
 


	
}