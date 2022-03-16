package com.turkcell.rentAcar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "id")
public class Customer extends User{
	
	@Column(name="customer_id", insertable= false, updatable = false)
	private int customerId;
	
	@OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Rental> rentals;
}
