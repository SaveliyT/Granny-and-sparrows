package com.saveliy.sparrows;

import java.util.*;
import java.io.*;
import java.math.*;

public class data {
	static int seeds = 1;					//кол-во семок на столе
	static int[] skill = new int[100];		//массив для розыгрыша
	static int max = 0;						//макс номер активного воробья
	static int judjemax;					//
	static int winner = 0;					//номер победителя					
	static int flag = 0;					//
	static judje jdj = new judje();			//объект класса судья
	static Thread tjudje = new Thread(jdj); //поток судьи
}

