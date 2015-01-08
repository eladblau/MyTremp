package com.example.mytremp;

public class Tremp implements Comparable<Tremp>{

	private int _id;
	private String name;
	private String address;
	private String phone;
	private String description;
	private int gender; // 1 = male ; 0 = female
	private String numberToPick;
	private String date;
	private String time;
	
	
	
	public Tremp(int _id, String name, String address, String phone,
			String description, int gender, String numberToPick, String date,
			String time){
        super();
        this._id = _id;
        this.name = name;
        this.address = address;
        this.phone = phone;
		this.numberToPick = numberToPick;
		this.description = description;
		this.gender = gender;
		this.date = date;
		this.time = time;
		
	}
	
	public Tremp(){
		super();
	}
	
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String getNumberToPick() {
		return numberToPick;
	}
	public void setNumberToPick(String numberToPick) {
		this.numberToPick = numberToPick;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}
	@Override
	public String toString() {
		return "Restaurant [_id=" + _id + ", name=" + name + ", address="
				+ address + ", phone=" + phone + ", description=" + description
				+ ", gender=" + gender +  "]";
	}

    //Should be compered by DateTime
	@Override
	public int compareTo(Tremp another) {
		int another_pcikUpNumber = Integer.parseInt(another.getNumberToPick());
		int this_pickUpNumber = Integer.parseInt(this.getNumberToPick());
		if (another_pcikUpNumber < this_pickUpNumber ){
			return 1;
		}
		else if (another_pcikUpNumber > this_pickUpNumber ){
			return -1;
		}
		return 0;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}

