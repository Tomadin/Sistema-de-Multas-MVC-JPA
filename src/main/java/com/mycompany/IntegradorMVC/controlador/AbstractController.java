
package com.mycompany.IntegradorMVC.controlador;


public abstract class AbstractController {
    protected AbstractController caller;
    
    public AbstractController(){}
    
    public AbstractController(AbstractController caller){
        this.caller = caller;
    }
    
    public abstract void back();
}
