/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import exceptions.BusinessLogicException;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface FacilityManager {
    public List<Facility> selectAll() throws BusinessLogicException;
    
}