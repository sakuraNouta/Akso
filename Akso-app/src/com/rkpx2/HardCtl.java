package com.rkpx2;


public class HardCtl {
	static {
		System.loadLibrary("HardCtl");
	}
	public native static int led1_dev_init();
	public native static int led1_dev_on();
	public native static int led1_dev_off();
	public native static int led1_dev_read();
	public native static int led1_dev_close();
	
	public native static int led2_dev_init();
	public native static int led2_dev_on();
	public native static int led2_dev_off();
	public native static int led2_dev_read();
	public native static int led2_dev_close();
	
	public native static int led3_dev_init();
	public native static int led3_dev_on();
	public native static int led3_dev_off();
	public native static int led3_dev_read();
	public native static int led3_dev_close();
	
	
	public native static int key1_dev_init();
	public native static int key1_dev_receive();
	public native static int key1_dev_close();
	
	public native static int key2_dev_init();
	public native static int key2_dev_receive();
	public native static int key2_dev_close();
	
	public native static int key3_dev_init();
	public native static int key3_dev_receive();
	public native static int key3_dev_close();
	
	public native static int beep_dev_init();
	public native static int beep_dev_on();
	public native static int beep_dev_read();
	public native static int beep_dev_off();
	public native static int beep_dev_close();
	
	public native static int ds18b20_dev_init();
	public native static int ds18b20_dev_get();
	public native static int ds18b20_dev_close();
	
	public native static int ir_dev_open();
	public native static int ir_dev_read();
	public native static int ir_dev_close();
	
	public native static int bodyir_dev_open();
	public native static int bodyir_dev_read();
	public native static int bodyir_dev_close();
	
	public native static int light_dev_open();
	public native static int light_dev_read();
	public native static int light_dev_close();
	
	public native static int smoke_dev_open();
	public native static int smoke_dev_read();
	public native static int smoke_dev_close();
}
