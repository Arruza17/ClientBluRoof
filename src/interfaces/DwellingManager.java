/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.BussinessLogicException;
import java.util.List;
import model.Dwelling;

/**
 *
 * @author Ander Arruza
 */
public interface DwellingManager {
    public List<Dwelling> findAll() throws BussinessLogicException;
}