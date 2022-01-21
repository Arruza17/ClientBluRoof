/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BussinessLogicException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import model.Dwelling;

/**
 *
 * @author Ander Arruza
 */
public interface DwellingManager {
    public List<Dwelling> findAll() throws BussinessLogicException;
    
    public List<Dwelling> findByDate(Date date) throws BussinessLogicException;
    
    public List<Dwelling> findByRating(Integer rating) throws BussinessLogicException;
    
    public void remove(Long id) throws BussinessLogicException;
}
