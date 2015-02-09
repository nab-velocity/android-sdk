/**
 * 
 */
package com.velocity.models.returnUnLinked;

/**
 * @author ranjitk
 *
 */
public class BillingData {

	private Name name;

	private Address address;

	private String businessName;

	private Phone phone;

	private Email email;

	private Fax fax;

	public Name getName() {
		if (name == null) {
			name = new Name();
		}

		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Phone getPhone() {
		if (phone == null) {
			phone = new Phone();
		}

		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Email getEmail() {
		if (email == null) {
			email = new Email();
		}

		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Fax getFax() {
		if (fax == null) {
			fax = new Fax();
		}
		return fax;
	}

	public void setFax(Fax fax) {
		this.fax = fax;
	}

}
