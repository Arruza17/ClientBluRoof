/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import exceptions.BusinessLogicException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.FacilityRestfulClient;

/**
 *
 * @author 2dam
 */
public class FacilityManagerImplementation implements FacilityManager {

    private FacilityRestfulClient frc;

    @Override
    public List<Facility> selectAll() throws BusinessLogicException {

        List<Facility> fac = null;
        try {
            fac = frc.findAll(new GenericType<List<Facility>>() {
            });
        } catch (ClientErrorException e) {
            Logger.getLogger(FacilityManagerImplementation.class.getName()).log(Level.SEVERE, null, e);

        }
        return fac;
    }

    public FacilityManagerImplementation() {
        frc = new FacilityRestfulClient();

    }

    @Override
    public List<Facility> selectByDate(String date) throws BusinessLogicException {
        List<Facility> fac = null;
        try {
            fac = frc.findFacilityByAdqDate(new GenericType<List<Facility>>() {
            }, date);
        } catch (ClientErrorException e) {
            Logger.getLogger(FacilityManagerImplementation.class.getName()).log(Level.SEVERE, null, e);
        }
        return fac;

    }

    @Override
    public List<Facility> selectByType(String type) throws BusinessLogicException {
        List<Facility> fac = null;
        try {
            fac = frc.findFacilityByType(new GenericType<List<Facility>>() {
            }, type);
        } catch (ClientErrorException ex) {
        }
        return fac;
    }

    @Override
    public Facility selectById(Long id) throws BusinessLogicException {
        Facility fac = null;
        try {
            fac = frc.find(new GenericType<Facility>() {
            }, id.toString());
        } catch (ClientErrorException ex) {
        }
        return fac;
    }
    @Override
    public void remove(Long id) throws BussinessLogicException {
        try {
            frc.remove(String.valueOf(id));
        } catch (ClientErrorException e) {
            throw new BussinessLogicException(e.getMessage());
        }
    }

    @Override
    public void create(Facility f) throws BusinessLogicException {
         try {
            frc.create(f);
        } catch (ClientErrorException e) {
            throw new BusinessLogicException();
        }
    }

    @Override
    public void update(Facility f, Long id) throws BusinessLogicException {
        try {
            frc.edit(f, id.toString());
        } catch (ClientErrorException e) {
            throw new BusinessLogicException();
        }
    }
}
