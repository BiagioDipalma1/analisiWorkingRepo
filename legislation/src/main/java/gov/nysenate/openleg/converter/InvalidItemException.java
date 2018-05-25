import java.lang.RuntimeException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BiagioDipalma
 */
public class InvalidItemException extends RuntimeException{
    InvalidItemException(String msg){
        super(msg);
    }
}
