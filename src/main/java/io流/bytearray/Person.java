package io流.bytearray;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2406571742466841216L;
	public Dog d=new Dog();
	public Cat c=new Cat();
	public Dog getD() {
		return d;
	}
	public void setD(Dog d) {
		this.d = d;
	}
	public Cat getC() {
		return c;
	}
	public void setC(Cat c) {
		this.c = c;
	}
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	private void writeObject(ObjectOutputStream o) throws IOException {
		System.out.println("重写的方法");
		this.setName("hiofaoihio");
		o.defaultWriteObject();
	}
}
class Cat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2970299933023635777L;
	private String name;
	private String hobby;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
}
class Dog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6411887847035473056L;
	private String name;
	private String hobby;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	private void writeObject(ObjectOutputStream o) throws IOException {
		System.out.println("重写的方法");
		o.defaultWriteObject();
	}
}