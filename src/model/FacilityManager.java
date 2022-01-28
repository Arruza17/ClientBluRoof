/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import exceptions.BusinessLogicException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface FacilityManager {
    public List<Facility> selectAll() throws BusinessLogicException;
    public List<Facility> selectByDate(String date) throws BusinessLogicException;
    public List<Facility> selectByType(String type) throws BusinessLogicException;
    public void remove(Long l) throws BussinessLogicException;
    public Facility selectById(Long id) throws BusinessLogicException;
    public void create(Facility f) throws BusinessLogicException;
    public void update(Facility f,Long id) throws BusinessLogicException;
}
