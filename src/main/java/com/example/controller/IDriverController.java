package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Driver;
import com.example.exception.DriverNotFoundException;
import com.example.exception.InvalidUserOrPasswordException;
import com.example.service.IDriverService;
import com.example.util.ILoginService;


@RestController
@RequestMapping("/driver")
public class IDriverController {

	@Autowired
	IDriverService ids;

	@Autowired
	ILoginService ls;

	/**
	 * validateDriver
	 */

	@PostMapping("/login")
	public String validateDriver(@RequestBody Driver driver) throws InvalidUserOrPasswordException {
		String response;
		try {
			response = ls.validateCredintials(driver);
		} catch (Exception e) {
			throw new InvalidUserOrPasswordException("Invalid Username/Password");
		}
		return response;
	}

	/**
	 * viewDriver
	 */

	@GetMapping("/{driverId}")
	public Driver viewDriver(@PathVariable int driverId) throws DriverNotFoundException {
		Driver driver = null;
		try {
			driver = ids.getDriverById(driverId);

		} catch (Exception e) {
			throw new DriverNotFoundException("Driver with Id: " + driverId + " Not Found!");
		}
		return driver;
	}

	/**
	 * viewBestDrivers
	 */

	@GetMapping("/bestdrivers")
	public List<Driver> viewBestDrivers() throws DriverNotFoundException {
		List<Driver> lis = null;
		try {
			lis = ids.viewBestDrivers();
		} catch (Exception e) {
			throw new DriverNotFoundException("Best Drivers List is Empty");
		}
		return lis;
	}

	/**
	 * insertDriver
	 */

	@PostMapping
	public List<Driver> insertDriver(@RequestBody Driver driver) {
		return ids.insertDriver(driver);
	}

	/**
	 * updateDriver
	 */
	@SuppressWarnings("unused")
	@PutMapping
	public Driver updateDriver(@RequestBody Driver driver) throws DriverNotFoundException {
		Driver driverCheck = null;
		Driver d = null;
		try {
			driverCheck = viewDriver(driver.getDriverId());
			d = ids.updateDriver(driver);
		} catch (Exception e) {
			throw new DriverNotFoundException("Driver with Id: " + driver.getDriverId() + " Not Found to Update!");
		}
		return d;
	}

	/**
	 * deleteDriver
	 */

	@DeleteMapping("/{driverId}")
	public List<Driver> deleteDriver(@PathVariable int driverId) throws DriverNotFoundException {
		List<Driver> d = null;
		try {
			d = ids.deleteDriver(driverId);
		} catch (Exception e) {
			throw new DriverNotFoundException("Driver with Id: " + driverId + " Not FOund To Delete!");
		}
		return d;
	}

}
